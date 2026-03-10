package logic;

import model.Database.User;
import model.E_Mail;
import model.RegisterUser;
import org.mindrot.jbcrypt.BCrypt;
import service.Logger;
import service.Repos;

import java.time.LocalDateTime;

public class Register {
    private Logger logger = Logger.getInstance();
    private RegisterUser user;
    private Repos repos;

    public Register(Repos repos){
        this.repos = repos;
    }

    public boolean checkData(RegisterUser user){

        logger.info("Registering user");

        E_Mail eMail = new E_Mail(user.email);

        if (!eMail.checkEMail()) {
            logger.warning("Invalid email");
            return false;
        }

        logger.info("Email is correct");

        User existingUser = repos.getUserRepository().getUserByEmail(user.email);

        if (existingUser != null) {
            logger.warning("Email already exists");
            return false;
        }

        logger.info("Email is not already in use");

        String hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt());

        User userReal = new User(
                0,
                user.firstName,
                user.lastName,
                user.email,
                hashedPassword,
                "0",
                LocalDateTime.now(),
                1
        );

        return repos.getUserRepository().addUser(userReal);
    }

}
