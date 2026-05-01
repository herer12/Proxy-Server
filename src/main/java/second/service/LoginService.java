package second.service;

import at.herer12_erik_van_haentjens.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
import second.objects.Account;
import second.repository.general.AccountDAO;

public class LoginService {
    private Logger logger = Logger.getLogger(LoginService.class);
    private AccountDAO accountDAO;

    public LoginService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public boolean execute(Account account){
        Account accountDatabase =accountDAO.getAccountByEMail(account.getEmail());
        logger.info("Account in Database: "+accountDatabase);
        if (accountDatabase == null) return false;
        logger.info("Account found");
        return BCrypt.checkpw(account.getPassword(), accountDatabase.getPassword());
    }
}
