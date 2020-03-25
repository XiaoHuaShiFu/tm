package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.Id;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: id校验器
 *  必须大于等于0
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class IdValidator implements ConstraintValidator<Id, Integer> {

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return true;
        }
        return id >= 0;
    }
}
