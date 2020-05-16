package com.xiaohuashifu.tm.service.constant;

/**
 * 描述: Token相关常量
 *
 * @author xhsf
 */
public class TokenConstant {

    /**
     * <p>Token在redis里的key的前缀，token:{tokenType}:{userId}</p>
     * 该key用于id映射到TokenAO
     * <p>
     *     <strong>
     *      可以使用MessageFormat.format(TokenConstant.PREFIX_OF_TOKEN_FOR_REDIS_KEY, tokenType, userId)进行组装
     *     </strong>
     * </p>
     */
    public static final String PREFIX_OF_ID_TOKEN_FOR_REDIS_KEY = "token:{0}:{1}";

    /**
     * <p>Token在redis里的key的前缀，token:{qrcode}</p>
     *  该key用于token映射到TokenAO
     * <p>
     *     <strong>
     *      可以使用MessageFormat.format(TokenConstant.PREFIX_OF_TOKEN_FOR_REDIS_KEY, qrcode|qrcode)进行组装
     *     </strong>
     * </p>
     */
    public static final String PREFIX_OF_TOKEN_FOR_REDIS_KEY = "token:{0}";

}
