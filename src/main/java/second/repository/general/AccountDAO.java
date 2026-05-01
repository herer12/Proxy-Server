package second.repository.general;

import second.objects.Account;

public interface AccountDAO  {
    Account getAccountByEMail(String email);
    boolean createAccount(Account account);
}
