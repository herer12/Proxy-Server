package logic;

import com.sun.net.httpserver.HttpExchange;
import service.Logger;
import service.session.SessionRepository;

import java.util.UUID;

public class SessionManager {
    private static Logger logger = Logger.getInstance();

    public static UUID createSessionId(int user_id){
        SessionRepository sessionRepository = new SessionRepository();
        return sessionRepository.createSession(user_id);
    }

    public static boolean hasValidSession(HttpExchange exchange){
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        logger.info("Cookie: " + cookie);

        if(cookie != null && cookie.contains("session")){

            String sessionId = cookie.split("=")[1];

            SessionRepository repo = new SessionRepository();

            if(repo.isSessionValid(sessionId)){
                logger.info("Session is valid");

                return true;
            }
        }else {
            logger.severe("Session ID not found in cookie");
        }
        return false;

    }
    public static int getUserIdFromSession(HttpExchange exchange){
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = cookie.split("=")[1];
        SessionRepository repo = new SessionRepository();
        return repo.getAccountId(sessionId);
    }
}
