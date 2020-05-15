package com.xiaohuashifu.tm.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.tm.TmApplicationTests;
import com.xiaohuashifu.tm.pojo.do0.UserAuthCodeDO;
import com.xiaohuashifu.tm.pojo.query.UserAuthCodeQuery;
import com.xiaohuashifu.tm.result.Result;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAuthCodeServiceTest extends TmApplicationTests {

    @Autowired
    private UserAuthCodeService userAuthCodeService;

    @Test
    public void generateAndSaveUserAuthCode() {
        Result<UserAuthCodeDO> generateAndSaveUserAuthCodeResult = userAuthCodeService.generateAndSaveUserAuthCode();
        System.out.println(generateAndSaveUserAuthCodeResult);
        Assert.assertTrue(generateAndSaveUserAuthCodeResult.isSuccess());
    }

    @Test
    public void getUserAuthCode() {
        System.out.println(userAuthCodeService.getUserAuthCode(1));
    }

    @Test
    public void listUserAuthCodes() {
        UserAuthCodeQuery userAuthCodeQuery = new UserAuthCodeQuery();
        Result<PageInfo<UserAuthCodeDO>> pageInfoResult = userAuthCodeService.listUserAuthCodes(userAuthCodeQuery);
        System.out.println(pageInfoResult);

        userAuthCodeQuery.setUsed(true);
        pageInfoResult = userAuthCodeService.listUserAuthCodes(userAuthCodeQuery);
        System.out.println(pageInfoResult);
    }

    @Test
    public void updateUserAuthCode() {
        UserAuthCodeDO userAuthCodeDO = new UserAuthCodeDO();
        userAuthCodeDO.setId(2);
        userAuthCodeDO.setUsed(true);
        System.out.println(userAuthCodeService.updateUserAuthCode(userAuthCodeDO));
    }

}