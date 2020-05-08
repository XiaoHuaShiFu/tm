package com.xiaohuashifu.tm.validator;

import com.xiaohuashifu.tm.validator.annotation.Longitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * 描述: longitude校验器
 *  必须0-180
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class LongitudeValidator implements ConstraintValidator<Longitude, BigDecimal> {

    /**
     * 最小的longitude
     */
    private final BigDecimal MIN_LONGITUDE = new BigDecimal("0");

    /**
     * 最大的longitude
     */
    private final BigDecimal MAX_LONGITUDE = new BigDecimal("180");

    @Override
    public boolean isValid(BigDecimal longitude, ConstraintValidatorContext constraintValidatorContext) {
        if (longitude == null) {
            return true;
        }
        return longitude.compareTo(MIN_LONGITUDE) >= 0 && longitude.compareTo(MAX_LONGITUDE) <= 0;
    }
}
