package com.xiaohuashifu.tm.helper;

import com.google.gson.Gson;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * 描述: 一些关于token的测试工具方法
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2020-05-09 4:14
 */
@Component
public class TokenTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    /**
     * 调度/v1/tokens接口，如果成功返回TokenAO对象
     * @return TokenAO
     * @throws Exception .
     */
    public TokenAO getToken(TokenType tokenType) throws Exception {
        String resultString = mockMvc.perform(MockMvcRequestBuilders.post("/v1/tokens")
                .param("username", "201734020124")
                .param("password", "123456")
                .param("tokenType", tokenType.name()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return gson.fromJson(resultString, TokenAO.class);
    }

}
