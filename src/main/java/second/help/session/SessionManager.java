package second.help.session;

import com.sun.net.httpserver.HttpExchange;
import at.herer12_erik_van_haentjens.logging.Logger;

import java.util.UUID;

public class SessionManager {
    private static final Logger logger = Logger.getLogger(SessionManager.class);

    public static UUID createSessionId(int user_id){
        SessionRepository sessionRepository = new SessionRepository();
        return sessionRepository.createSession(user_id);
    }

    public static boolean hasValidSession(HttpExchange exchange){
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        logger.debug("Cookie: " + cookie);

        if(cookie != null && cookie.contains("session")){

            String sessionId = cookie.split("=")[1];

            SessionRepository repo = new SessionRepository();

            if(repo.isSessionValid(sessionId)){
                logger.info("Session is valid");

                return true;
            }
        }else {
            logger.info("Session ID not found in cookie");
        }
        return false;

    }
    public static int getUserIdFromSession(HttpExchange exchange){
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = cookie.split("=")[1];
        SessionRepository repo = new SessionRepository();
        return repo.getAccountId(sessionId);
    }
    public static void writeSessionCookie(HttpExchange exchange, UUID sessionId){
        exchange.getResponseHeaders().add("Set-Cookie", "session=" + sessionId);
    }
}
