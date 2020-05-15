package com.xiaohuashifu.tm.controller.v1.api;

import com.xiaohuashifu.tm.TmApplicationTests;
import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.helper.TokenTestHelper;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

public class UserAuthCodeControllerTest extends TmApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenTestHelper tokenTestHelper;

    @Test
    public void post() throws Exception {
        TokenAO token = tokenTestHelper.getToken(TokenType.ADMIN);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/auth_codes")
                .header("Authorization", token.getToken()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void get() throws Exception {
        TokenAO token = tokenTestHelper.getToken(TokenType.ADMIN);
        System.out.println(mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/auth_codes")
                .header("Authorization", token.getToken())
                .param("used", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString());
    }
}