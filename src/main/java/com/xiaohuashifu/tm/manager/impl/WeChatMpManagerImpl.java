package com.xiaohuashifu.tm.manager.impl;

import com.google.gson.Gson;
import com.xiaohuashifu.tm.manager.WeChatMpManager;
import com.xiaohuashifu.tm.manager.constant.WeChatGrantTypeEnum;
import com.xiaohuashifu.tm.manager.constant.WeChatMp;
import com.xiaohuashifu.tm.manager.constant.WeChatMpConsts;
import com.xiaohuashifu.tm.pojo.dto.AccessTokenDTO;
import com.xiaohuashifu.tm.pojo.dto.Code2SessionDTO;
import com.xiaohuashifu.tm.pojo.dto.DailyVisitTrendDTO;
import com.xiaohuashifu.tm.pojo.dto.MessageTemplateDTO;
import com.xiaohuashifu.tm.pojo.dto.WeChatMpResponseDTO;
import com.xiaohuashifu.tm.result.ErrorCode;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.CacheService;
import com.xiaohuashifu.tm.service.constant.RedisStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * 描述: 微信小程序通用接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 16:51
 */
@Component("weChatMpManager")
public class WeChatMpManagerImpl implements WeChatMpManager {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMpManagerImpl.class);

    private final CacheService cacheService;

    private final RestTemplate restTemplate;
    
    private final Gson gson;

    /**
     * 团队管理小程序access-token的redis key名
     */
    private static final String REDIS_KEY = "tm:wechat:mp:access:token";

    @Autowired
    public WeChatMpManagerImpl(CacheService cacheService, RestTemplate restTemplate, Gson gson) {
        this.cacheService = cacheService;
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    /**
     * 获取access-token
     *
     * @return AccessTokenDTO
     */
    @Override
    public Optional<String> getAccessToken() {
        String accessToken = cacheService.get(REDIS_KEY);
        if (accessToken == null) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }
        return Optional.of(accessToken);
    }

    /**
     * 获取新的access-token
     * 并添加到redis
     * 并设置过期时间
     *
     * @return AccessTokenDTO
     */
    @Override
    public Optional<AccessTokenDTO> getNewAccessToken() {
        // 获取access-token
        String url = MessageFormat.format("{0}?grant_type={1}&appid={2}&secret={3}",
                WeChatMpConsts.ACCESS_TOKEN_URL, WeChatGrantTypeEnum.CLIENT_CREDENTIAL.getValue(),
                WeChatMp.TM.getAppId(), WeChatMp.TM.getSecret());
        ResponseEntity<AccessTokenDTO> entity = restTemplate.getForEntity(url, AccessTokenDTO.class);
        if (Objects.requireNonNull(entity.getBody()).getAccess_token() == null) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }

        // 添加到redis
        String status = cacheService.set(REDIS_KEY, entity.getBody().getAccess_token());
        if (!status.equals(RedisStatus.OK.name())) {
            logger.warn("Set access token fail.");
            return Optional.empty();
        }

        // 设置access-token在redis的过期时间
        Long expire = cacheService.expire(REDIS_KEY, entity.getBody().getExpires_in());
        if (expire == 0) {
            logger.warn("Set redis expire fail. {}", entity.getBody().getAccess_token());
        }

        return Optional.of(entity.getBody());
    }

    /**
     * 通过code获取openid
     * @param code String
     * @param mpName 小程序名
     * @return String
     */
    @Override
    @Deprecated
    public String getOpenid(String code, String mpName) {
        WeChatMp weChatMp = WeChatMp.valueOf(mpName);
        return getOpenid(code, weChatMp);
    }

    /**
     * 通过code获取openid
     * @param code String
     * @param weChatMp 小程序类别
     * @return String
     */
    @Override
    public String getOpenid(String code, WeChatMp weChatMp) {
        Code2SessionDTO code2SessionDTO = getCode2Session(code, weChatMp);
        return code2SessionDTO.getOpenid();
    }

    /**
     * 发送模板消息
     *
     * @param messageTemplate 模板消息
     * @return 是否发送成功
     */
    @Override
    public boolean sendTemplateMessage(MessageTemplateDTO messageTemplate) {
        Optional<String> accessToken = getAccessToken();
        if (!accessToken.isPresent()) {
            return false;
        }

        String url = MessageFormat.format(WeChatMpConsts.TEMPLATE_MESSAGE_URL + "?access_token={0}",
                accessToken.get());
        ResponseEntity<WeChatMpResponseDTO> responseEntity = restTemplate.postForEntity(url, messageTemplate, WeChatMpResponseDTO.class);
        System.out.println(responseEntity.getBody());
        return Objects.requireNonNull(responseEntity.getBody()).getErrcode().equals(0);
    }

    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param weChatMp 小程序类别
     * @return Code2SessionDTO
     */
    private Code2SessionDTO getCode2Session(String code, WeChatMp weChatMp) {
        String url = MessageFormat.format("{0}?appid={1}&secret={2}&js_code={3}&grant_type={4}",
                WeChatMpConsts.CODE2SESSION_URL, weChatMp.getAppId(),
                weChatMp.getSecret(), code, WeChatGrantTypeEnum.AUTHORIZATION_CODE.getValue());
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return responseEntity.getBody();
    }

    /**
     * 获取访问趋势
     * @param paramMap 存放请求参数的map
     */
	@Override
	public Result<List<DailyVisitTrendDTO>> getDailyVisitTrend(List<String> dateList) {
		List<DailyVisitTrendDTO> list = new ArrayList<>(31);
		String accessToken = cacheService.get(WeChatMpManagerImpl.REDIS_KEY);
        String url = MessageFormat.format("{0}?access_token={1}", WeChatMpConsts.DAILY_VISIT_TREND_URL, accessToken);
        Map<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < dateList.size(); i++) {
        	paramMap.put("begin_date", dateList.get(i));
        	paramMap.put("end_date", dateList.get(i));
        	HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(paramMap));
        	String jsonData = restTemplate.postForObject(url, requestEntity, String.class);
        	ArrayList listInData = (ArrayList) gson.fromJson(jsonData, Map.class).get("list");
        	if (listInData.size() > 0) {
        		DailyVisitTrendDTO dailyVisitTrend = gson.fromJson(listInData.get(0).toString(), DailyVisitTrendDTO.class);        	
        		list.add(dailyVisitTrend);
        	}else {
        		list.add(null);
        	}
        }
        
        if (list.size() == 0) {
            logger.warn("Get daily-visit trend fail.");
            return Result.fail(ErrorCode.INVALID_OPERATION_NOT_FOUND, "Daily-visit trend not found");
        }
        return Result.success(list);
	}

}
