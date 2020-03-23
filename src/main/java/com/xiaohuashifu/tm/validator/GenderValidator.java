package com.xiaohuashifu.tm.validator;


import com.xiaohuashifu.tm.constant.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 性别校验器
 *  必须为 [UNKNOWN | MAN | WOMAN] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class GenderValidator implements ConstraintValidator<com.xiaohuashifu.tm.validator.annotation.Gender, String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        if (gender == null) {
            return true;
        }
        return gender.equals(Gender.UNKNOWN.name()) || gender.equals(Gender.MAN.name())
                || gender.equals(Gender.WOMAN.name());
    }
}
