package data.MySql;

import data.repos.UserRepository;
import model.Database.*;
import model.Location;
import service.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

public class UserMySQL implements UserRepository {

    private DatabaseManagerMySQL dbManager;
    private Logger logger = Logger.getInstance();

    public UserMySQL(DatabaseManagerMySQL dbManager){
        this.dbManager = dbManager;
    }

    String getUsers = "SELECT account_id, firstname, lastname, email, password, created_at, is_active FROM account ";
    String getUserByID = getUsers + "WHERE account_id = ?";
    String getUserByEmail = getUsers + "WHERE email = ?";
    String insertUser = "INSERT INTO account (firstname, lastname, email, password, created_at) VALUES (?, ?, ?, ?, ?)";

    private User mapUser(ResultSet rs) throws SQLException {

        int userId = rs.getInt("account_id");
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String email = rs.getString("email");
        String password = rs.getString("password");
        int isActive = rs.getInt("is_active");

        Timestamp date = rs.getTimestamp("created_at");
        LocalDateTime created = date.toLocalDateTime();

        return new User(userId, firstName, lastName, email, password, "0", created, isActive);
    }

    private LinkedList<User> mapUsers(ResultSet resultSet){
        LinkedList<User> users = new LinkedList<>();
        try {
            while (resultSet.next()){
                users.add(mapUser(resultSet));
            }
            logger.info("Products mapped");
        } catch (SQLException e) {
            logger.severe("Could not map products: " + e.getMessage());
            logger.warning("ResultSet bad");
        }
        return users;
    }

    @Override
    public LinkedList<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info("Getting user from MySQL");
        try (Connection connection =dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(getUserByEmail);
            statement.setString(1, email );
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()) {
                User user = mapUser(resultSet);
                logger.info("User found: " + user.getCreated());
                return user;
            }

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());

        }
        return null;
    }

    @Override
    public User getUserByID(int id) {
        logger.info("Getting user from MySQL");
        try (Connection connection =dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(getUserByID);
            statement.setInt(1, id );
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()) {
                User user = mapUser(resultSet);
                logger.info("User found: " + user.getCreated());
                return user;
            }

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());

        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        logger.info("Inserting user into MySQL");
        try (Connection connection =dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(insertUser);
            logger.info("Prepared statement: " + statement.toString());

            statement.setString(1,user.getFirstName());
            logger.info("first name: " + user.getFirstName());
            statement.setString(2,user.getLastName());
            logger.info("last name: " + user.getLastName());
            statement.setString(3,user.getEmail());
            logger.info("email: " + user.getEmail());
            statement.setString(4,user.getPassword());
            logger.info("password: " + user.getPassword());
            statement.setTimestamp(5, Timestamp.valueOf(user.getCreated()));
            logger.info("created: " + user.getCreated());
            CartMySQL cartMySQL = new CartMySQL(dbManager);
            cartMySQL.addCart(new Cart(user.getUserID(), user, user.getCreated()));


            statement.executeUpdate();
            return true;

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Address getUserAddress(int id) {
        return null;
    }

    @Override
    public LinkedList<User> getAllUsersByAddress(Location location) {
        return null;
    }

    @Override
    public Cart getShoppingCart(User user) {
        return null;
    }
}
