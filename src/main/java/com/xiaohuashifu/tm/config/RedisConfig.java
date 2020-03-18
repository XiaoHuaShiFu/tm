package com.xiaohuashifu.tm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 描述: Redis配置
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Configuration
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        // 设置字符串序列化器，这样Spring就会把Redis的key当成字符串处理
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
