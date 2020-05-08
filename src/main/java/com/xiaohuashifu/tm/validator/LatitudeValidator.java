package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.Latitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * 描述: latitude校验器
 *  必须0-90
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class LatitudeValidator implements ConstraintValidator<Latitude, BigDecimal> {

    /**
     * 最小的latitude
     */
    private final BigDecimal MIN_LATITUDE = new BigDecimal("0");

    /**
     * 最大的latitude
     */
    private final BigDecimal MAX_LATITUDE = new BigDecimal("90");

    @Override
    public boolean isValid(BigDecimal latitude, ConstraintValidatorContext constraintValidatorContext) {
        if (latitude == null) {
            return true;
        }
        return latitude.compareTo(MIN_LATITUDE) >= 0 && latitude.compareTo(MAX_LATITUDE) <= 0;
    }
}
