package com.xiaohuashifu.tm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 描述: Bean类型的类的检查器
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-23 1:26
 */
public class BeanUtils {

    private final static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {}

    /**
     * 判断类中每个属性是否都为空
     *
     * @param bean bean类
     * @return 是否全为空
     */
    public static boolean allFieldIsNull(Object bean) {
        try {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(bean);
                if (null != value) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Check all field is null error.", e);
            return false;
        }
        return true;
    }

}
