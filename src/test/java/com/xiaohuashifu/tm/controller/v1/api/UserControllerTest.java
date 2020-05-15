package com.xiaohuashifu.tm.controller.v1.api;

import com.xiaohuashifu.tm.constant.TokenType;
import com.xiaohuashifu.tm.pojo.ao.TokenAO;
import com.xiaohuashifu.tm.helper.TokenTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenTestHelper tokenTestHelper;

    /**
     * 无法测试，拿不到code
     */
    public void post() {
    }

    /**
     * 路径get方法
     * @throws Exception .
     */
    @Test
    public void get() throws Exception {
        TokenAO tokenAO = tokenTestHelper.getToken(TokenType.USER);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/3")
                .header("Authorization", tokenAO.getToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 查询get方法
     */
    @Test
    public void get1() throws Exception {
        TokenAO tokenAO = tokenTestHelper.getToken(TokenType.USER);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
                .header("Authorization", tokenAO.getToken())
                .param("pageNum", "1")
                .param("pageSize", "10")
                .param("idList", "3,4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 查询get方法
     */
    @Test
    public void put() throws Exception {
        TokenAO tokenAO = tokenTestHelper.getToken(TokenType.USER);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/users")
                .header("Authorization", tokenAO.getToken())
                .param("id", "3")
                .param("nickName", "小花师傅3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public void putAvatar() {
    }

    public void putAvatarForWeChatMP() {
    }

    public void putAvailable() {
    }
}