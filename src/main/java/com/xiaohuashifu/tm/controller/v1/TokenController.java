package com.xiaohuashifu.tm.controller.v1;

import com.xiaohuashifu.tm.aspect.annotation.ErrorHandler;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.pojo.vo.TokenVO;
import com.xiaohuashifu.tm.result.Result;
import com.xiaohuashifu.tm.service.TokenService;
import com.xiaohuashifu.tm.validator.annotation.JobNumber;
import com.xiaohuashifu.tm.validator.annotation.Password;
import com.xiaohuashifu.tm.validator.annotation.TokenType;
import com.xiaohuashifu.tm.validator.annotation.WeChatMpCode;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


/**
 * 描述: Token Web层
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-10 21:10
 */
@RestController
@RequestMapping("v1/tokens")
@Validated
public class TokenController {

    private final Mapper mapper;

    private final TokenService tokenService;

    @Autowired
    public TokenController(Mapper mapper, TokenService tokenService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    /**
     * 创建token凭证，限微信小程序使用
     *
     * @param code 微信小程序的wx.login()接口返回值
     * @param tokenType token类型
     * @return TokenAO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * INVALID_PARAMETER_NOT_FOUND: The specified openid does not exist.
     *
     * INTERNAL_ERROR: Failed to create token.
     * INTERNAL_ERROR: Failed to set expire.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object postToken(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.") @WeChatMpCode String code,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The tokenType must be not blank.")
            @TokenType String tokenType) {
        Result<TokenAO> result = tokenService.createAndSaveTokenByCode(tokenType, code);

        return result.isSuccess() ? mapper.map(result.getData(), TokenVO.class) : result;
    }

    /**
     * 创建token凭证，通过工号+密码
    *
     * @param jobNumber 工号
     * @param password 密码
     * @param tokenType token类型
     * @return TokenAO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * INVALID_PARAMETER_NOT_FOUND: The specified openid does not exist.
     *
     * INTERNAL_ERROR: Failed to create token.
     * INTERNAL_ERROR: Failed to set expire.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object postToken(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The jobNumber must be not blank.")
            @JobNumber String jobNumber,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The password must be not blank.")
            @Password String password,
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The tokenType must be not blank.")
            @TokenType String tokenType) {
        Result<TokenAO> result = tokenService.createAndSaveToken(tokenType, jobNumber, password);

        return result.isSuccess() ? mapper.map(result.getData(), TokenVO.class) : result;
    }

}
