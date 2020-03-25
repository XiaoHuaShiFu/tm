package com.xiaohuashifu.tm.validator.annotation;

import com.xiaohuashifu.tm.validator.DepartmentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 部门校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 15:12
 */
@Documented
@Constraint(validatedBy = {DepartmentValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Department.List.class)
public @interface Department {

    String message() default "INVALID_PARAMETER: The parameter of department must be one of" +
            "[ALL | UNKNOWN | ZZB | XSB | RSB | YFB | CWB | CGB | PZB | GZB | KFB].";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Department[] value();
    }


}
