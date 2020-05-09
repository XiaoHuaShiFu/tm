package com.xiaohuashifu.tm.service;

import com.xiaohuashifu.tm.pojo.do0.UserDO;
import com.xiaohuashifu.tm.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private com.xiaohuashifu.tm.service.UserService userService;

    @Test
    public void getOpenid() {
    }

    /**
     * 该接口无法测试，因为缺少code，该code需要在微信小程序内调用wx.login()接口
     */
    @Test
    public void getUserByCode() {
        System.out.println("该接口无法测试，因为缺少code，该code需要在微信小程序内调用wx.login()接口");
    }

    @Test
    public void getUserByJobNumber() {

        Result<UserDO> getUserByJobNumber = userService.getUserByJobNumber("201734020124");
        assertTrue(getUserByJobNumber.isSuccess());
    }

    @Test
    public void saveUser() {

    }

    public void getUser() {

    }

    public void listUsers() {

    }

    public void updateUser() {

    }

    public void updateAvatar() {

    }
}