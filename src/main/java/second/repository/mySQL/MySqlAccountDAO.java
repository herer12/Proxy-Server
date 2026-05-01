package second.repository.mySQL;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.objects.Account;
import second.repository.MySqlConnection;
import second.repository.general.AccountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class MySqlAccountDAO implements AccountDAO {

    private Logger logger = Logger.getLogger(MySqlAccountDAO.class);
    private String sqlQueryAccountByEMail = "Select account_id, firstname, lastname, email, password, phone, created_at, is_active From account Where email = ?";
    private String sqlQueryCreateAccount = "Insert into account (firstname, lastname, email, password, phone, created_at, is_active) Values (?,?,?,?,?,?,?)";

    private Account buildAccount(ResultSet rs){
        try {

            int accountId = rs.getInt("account_id") ;
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String phone = rs.getString("phone");
            Timestamp created_at = rs.getTimestamp("created_at");
            int is_active = rs.getInt("is_active");

            return new Account(accountId, firstname, lastname, email, password, phone, created_at, is_active);

        }catch (Exception e){
            logger.error("Error building account", e);
            return null;
        }
    }

    @Override
    public Account getAccountByEMail(String email) {
        logger.debug("Getting account by email: " + email);
        try (Connection connection = MySqlConnection.getInstance().getConnection()){

            PreparedStatement stmt = connection.prepareStatement(sqlQueryAccountByEMail);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return buildAccount(rs);
            }
        }catch (Exception e){
            logger.error("Error getting account by email", e);
        }
        return null;
    }

    @Override
    public boolean createAccount(Account account) {
        logger.debug("Creating account: " + account);
        try (Connection connection = MySqlConnection.getInstance().getConnection()){

            PreparedStatement stmt = connection.prepareStatement(sqlQueryCreateAccount);
            stmt.setString(1, account.getFirstname());
            stmt.setString(2, account.getLastname());
            stmt.setString(3, account.getEmail());
            stmt.setString(4, account.getPassword());
            stmt.setString(5, account.getPhone());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(7, 1);
            stmt.executeUpdate();

            return true;
        }catch (Exception e){
            logger.error("Error creating account", e);
            return false;
        }
    }
}
