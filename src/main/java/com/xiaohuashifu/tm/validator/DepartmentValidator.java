package com.xiaohuashifu.tm.validator;


import com.xiaohuashifu.tm.constant.Department;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 部门校验器
 *  必须符合是 [ALL | UNKNOWN | ZZB | XSB | RSB | YFB | CWB | CGB | PZB | GZB | KFB] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class DepartmentValidator implements ConstraintValidator<com.xiaohuashifu.tm.validator.annotation.Department, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        for (Department department : Department.values()) {
            if (s.equals(department.name())) {
                return true;
            }
        }
        return false;
    }

}
