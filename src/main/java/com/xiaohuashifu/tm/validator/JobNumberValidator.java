package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.JobNumber;
import com.xiaohuashifu.tm.validator.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 工号校验器
 *  必须符合正则表达式 ^20.{10}$
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class JobNumberValidator implements ConstraintValidator<JobNumber, String> {

    /**
     * 手机号码匹配模式
     */
    private static final String REGEX_JOB_NUMBER = "^20.{10}$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(REGEX_JOB_NUMBER);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        Matcher matcher = p.matcher(s);
        return matcher.matches();
    }
}
