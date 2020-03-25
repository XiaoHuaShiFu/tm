package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.WeChatMpCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 描述: 微信小程序wx.login()接口返回的code校验器
 *  长度必须是32
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class WeChatMpValidator implements ConstraintValidator<WeChatMpCode, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return s.length() == 32;
    }
}
