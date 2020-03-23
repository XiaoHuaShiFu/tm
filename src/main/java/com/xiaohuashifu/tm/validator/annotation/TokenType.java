package com.xiaohuashifu.tm.validator.annotation;

import com.xiaohuashifu.tm.validator.TokenTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: Token类型校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 15:12
 */
@Documented
@Constraint(validatedBy = {TokenTypeValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TokenType.List.class)
public @interface TokenType {

    String message() default "INVALID_PARAMETER: The name of tokenType must be [USER].";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        TokenType[] value();
    }

}
