package service.session;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class SessionRepository {

    private Jedis jedis;

    public SessionRepository() {
        jedis = RedisConnection.getConnection();
    }

    public UUID createSession(int accountId) {

        UUID sessionId = UUID.randomUUID();

        String key = "session:" + sessionId;

        jedis.set(key, String.valueOf(accountId));

        // Ablaufzeit (24 Stunden)
        jedis.expire(key, 86400);

        return sessionId;
    }

    public boolean isSessionValid(String sessionId) {

        String key = "session:" + sessionId;

        return jedis.exists(key);
    }

    public int getAccountId(String sessionId) {

        String key = "session:" + sessionId;

        String value = jedis.get(key);

        if(value == null){
            return -1;
        }

        return Integer.parseInt(value);
    }

}