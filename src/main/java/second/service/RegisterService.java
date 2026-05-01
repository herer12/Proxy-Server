package second.service;

import at.herer12_erik_van_haentjens.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
import second.objects.Account;
import second.repository.general.AccountDAO;

public class RegisterService {
    private Logger logger = Logger.getLogger(RegisterService.class);
    private AccountDAO accountDAO;
    public RegisterService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public boolean createAccount(Account account){
        String password = account.getPassword();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        account.setPassword(password);

        return accountDAO.createAccount(account);
    }

    public boolean checkIfAccountExists(Account account){
        Account accountDatabase = accountDAO.getAccountByEMail(account.getEmail());
        logger.debug("Account in Database: " + accountDatabase);
        return accountDatabase != null;
    }
}
