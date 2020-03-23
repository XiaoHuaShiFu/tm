package com.xiaohuashifu.tm.service;

import redis.clients.jedis.Tuple;

import java.util.Map;
import java.util.Set;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-12 13:27
 */
public interface CacheService {

    Long hset(String key, String field, String value);

    Long hdel(String key, String field);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);

    String set(String key, String value);

    Long del(String key);

    String get(String key);

    Long lpush(String key, String... values);

    Long rpush(String key, String... values);

    String lpop(String key);

    String rpop(String key);

    Long llen(String key);

    Long zadd(String key, double score, String member);

    Set<Tuple> zrangeWithScores(String key, long start, long stop);

    Long zrank(String key, String member);

    Long zremrangeByRank(String key, long start, long stop);

    Long expire(String key, int seconds);

}
