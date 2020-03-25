package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 描述: 电子邮件校验器
 *  长度必须小于100
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return s.length() <= 100;
    }
}
