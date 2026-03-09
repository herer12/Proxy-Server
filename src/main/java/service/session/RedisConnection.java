package service.session;

import redis.clients.jedis.Jedis;

public class RedisConnection {

    private static Jedis jedis;

    public static Jedis getConnection() {

        if (jedis == null) {
            jedis = new Jedis("localhost", 6379);
        }

        return jedis;
    }
}