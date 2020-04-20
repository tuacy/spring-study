package com.tuacy.component.spring.boot.data.redis;

import java.util.List;

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

    /**
     * EXPIREAT key timestamp
     * EXPIREAT 的作用和 EXPIRE类似，都用于为 key 设置生存时间。不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳 Unix timestamp 。
     * 起始版本：1.2.0
     *
     * @param key       指定的key
     * @param timestamp 到期时间，UNIX 时间戳 Unix timestamp
     * @return 1 如果设置了过期时间 0 如果没有设置过期时间，或者不能设置过期时间
     */
    int expireat(String key, long timestamp);

    /**
     * KEYS pattern
     * 查找所有符合给定模式pattern（正则表达式）的 key
     *
     * @param pattern 匹配key的正则表达式
     * @return 匹配到的key列表
     */
    List<String> keys(String pattern);

    /**
     * MOVE key db
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     * 起始版本：1.0.0
     *
     * @param key 指定的key
     * @param db  数据库db
     * @return 移动成功返回 1,失败则返回 0
     */
    int move(String key, String db);

    /**
     * PERSIST key
     * 移除给定key的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
     * 起始版本：2.2.0
     *
     * @param key 指定的key
     * @return 当生存时间移除成功时，返回 1 .如果 key 不存在或 key 没有设置生存时间，返回 0 .
     */
    int persist(String key);

    /**
     * PEXPIRE key milliseconds
     * 这个命令和EXPIRE命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像EXPIRE命令那样，以秒为单位。
     * 起始版本：2.6.0
     *
     * @param key          指定的key
     * @param milliseconds 毫秒
     * @return 设置成功，返回 1，key 不存在或设置失败，返回 0
     */
    int pexpire(String key, long milliseconds);

    /**
     * PEXPIREAT key milliseconds-timestamp
     * PEXPIREAT 这个命令和EXPIREAT命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳，而不是像EXPIREAT那样，以秒为单位。
     * 起始版本：2.6.0
     *
     * @param key                       指定key
     * @param millisecondsPlusTimestamp milliseconds - timestamp
     * @return 如果生存时间设置成功，返回 1 ,当 key 不存在或没办法设置生存时间时，返回 0
     */
    int pexpireat(String key, long millisecondsPlusTimestamp);

    /**
     * PTTL key
     * 这个命令类似于TTL命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像TTL命令那样，以秒为单位。
     * <p>
     * 在Redis 2.6和之前版本，如果key不存在或者key存在且无过期时间将返回-1。
     * <p>
     * 从 Redis 2.8开始，错误返回值发送了如下变化：
     * <p>
     * 如果key不存在返回-2
     * 如果key存在且无过期时间返回-1
     * 起始版本：2.6.0
     *
     * @param key 指定key
     * @return 以毫秒为单位, 或者返回一个错误值 (参考上面的描述).
     */
    long pttl(String key);

    /**
     * RANDOMKEY
     * 从当前数据库返回一个随机的key。
     * 起始版本：1.0.0
     *
     * @return 如果数据库没有任何key，返回nil，否则返回一个随机的key。
     */
    String randomkey();

    /**
     * RENAME key newkey
     * 将key重命名为newkey，如果key与newkey相同，将返回一个错误。如果newkey已经存在，则值将被覆盖。
     * 起始版本：1.0.0
     *
     * @param key    指定key
     * @param newKey 重命名之后的key
     * @return 状态回复（或者单行回复）以“+”开始以“\r\n”结尾的单行字符串形式
     */
    String rename(String key, String newKey);

    /**
     * RENAMENX key newkey
     * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。当 key 不存在时，返回一个错误。
     * 起始版本：1.0.0
     *
     * @param key    指定的key
     * @param newKey 重命名之后的key
     * @return 修改成功时，返回 1 。如果 newkey 已经存在，返回 0 。
     */
    int renameNx(String key, String newKey);

    /**
     * TTL key
     * 返回key剩余的过期时间。 这种反射能力允许Redis客户端检查指定key在数据集里面剩余的有效期。
     * 在Redis 2.6和之前版本，如果key不存在或者已过期时返回-1。
     * 从Redis2.8开始，错误返回值的结果有如下改变：
     * 如果key不存在或者已过期，返回 -2
     * 如果key存在并且没有设置过期时间（永久有效），返回 -1 。
     * 起始版本：1.0.0
     *
     * @param key 指定的key
     * @return key有效的秒数（TTL in seconds）,或者一个负值的错误 (参考上文)。
     */
    long ttl(String key);

    /**
     * TYPE key
     * 返回key所存储的value的数据结构类型，它可以返回string, list, set, zset 和 hash等不同的类型。
     * 起始版本：1.0.0
     *
     * @param key 指定key
     * @return 返回当前key的数据类型，如果key不存在时返回none
     */
    String type(String key);


}
