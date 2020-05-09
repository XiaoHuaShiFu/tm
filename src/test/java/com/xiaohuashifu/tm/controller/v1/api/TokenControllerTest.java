package com.xiaohuashifu.tm.controller.v1.api;

import com.xiaohuashifu.tm.helper.TokenTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    private TokenTestHelper tokenTestHelper;

    /**
     * 该接口无法测试
     */
    public void postTokenByCode() {
    }

    /**
     * 测试POST/v1/tokens接口
     * @throws Exception .
     */
    @Test
    public void postToken() throws Exception {
        System.out.println(tokenTestHelper.getToken());
    }
}