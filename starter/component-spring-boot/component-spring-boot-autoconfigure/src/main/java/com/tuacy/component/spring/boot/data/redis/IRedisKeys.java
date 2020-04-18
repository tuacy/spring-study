package com.tuacy.component.spring.boot.data.redis;

/**
 * redis key相关命令
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/4/19 0:46
 */
public interface IRedisKeys {

    /**
     * DEL key [key ...]
     * 删除指定的一批keys，如果删除中的某些key不存在，则直接忽略
     * 起始版本：1.0.0
     *
     * @param keys 指定的key
     * @return 被删除的keys的数量
     */
    int del(String... keys);

    /**
     * DUMP key
     * 序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键
     * 起始版本：2.6.0
     *
     * @param key 指定的key
     * @return 被序列化的值
     */
    String dump(String key);

    /**
     * EXISTS key [key ...]
     * 返回key是否存在
     * 起始版本：1.0.0
     *
     * @param keys 指定的key
     * @return 1 如果key存在;0 如果key不存在
     */
    int exists(String... keys);

    /**
     * EXPIRE key seconds
     * 设置key的过期时间，超过时间后，将会自动删除该key
     * 起始版本：1.0.0
     *
     * @param key 指定的key
     * @return 1 如果成功设置过期时间;0 如果key不存在或者不能设置过期时间
     */
    int expire(String key);

}
