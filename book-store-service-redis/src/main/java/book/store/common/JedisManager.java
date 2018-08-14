package book.store.common;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;

public class JedisManager {
    private static JedisPool jedisPool;


    public static synchronized Jedis getJedis() {
        if (jedisPool == null) {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxTotal(5);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
            poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
            poolConfig.setNumTestsPerEvictionRun(3);
            poolConfig.setBlockWhenExhausted(true);
            jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        }
        return jedisPool.getResource();
    }

    public static void main(String[] args) throws Exception {
        Jedis jedis = JedisManager.getJedis();
        jedis.lpush("test#1", "1000");
        jedis.lpush("test#1", "2000");
        System.out.println(jedis.lrange("test#1", 0, 1));
    }

}
