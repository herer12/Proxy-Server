package logic;

import com.sun.net.httpserver.HttpExchange;
import model.Database.User;
import model.LoginRequest;
import service.Logger;
import service.Repos;
import org.mindrot.jbcrypt.BCrypt;
import service.session.SessionRepository;

import java.util.UUID;

public class Login {
    private static Logger logger = Logger.getInstance();
    private User user;

    public boolean checkData(LoginRequest request, Repos repos, HttpExchange exchange){
        user =repos.getUserRepository().getUserByEmail(request.email);
        if (user == null) return false;
        logger.info("Login attempt: " + request.email);
        if (!BCrypt.checkpw(request.password,user.getPassword())) return false;
        logger.info("Password correct");


        return true;
    }
    public UUID getSessionId() {
        return SessionManager.createSessionId(user.getUserID());
    }

}
