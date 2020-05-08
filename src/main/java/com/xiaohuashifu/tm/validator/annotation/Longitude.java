package com.xiaohuashifu.tm.validator.annotation;

import com.xiaohuashifu.tm.validator.LongitudeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: longitude校验 0-180
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Documented
@Constraint(validatedBy = {LongitudeValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Longitude.List.class)
public @interface Longitude {

    String message() default "INVALID_PARAMETER: The range of longitude is from 0 to 180.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Longitude[] value();
    }

}
