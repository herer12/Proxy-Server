package second.service.session;

import redis.clients.jedis.Jedis;

class RedisConnection {

    private static Jedis jedis;

    static Jedis getConnection() {

        if (jedis == null) {
            jedis = new Jedis("localhost", 6379);
        }

        return jedis;
    }
}