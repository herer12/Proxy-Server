package data.MySql;

import data.repos.CartRepository;
import model.Database.Cart;
import model.Database.CartItem;
import model.Database.User;
import service.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class CartMySQL implements CartRepository {
    private DatabaseManagerMySQL dbManager;
    Logger logger = Logger.getInstance();

    public CartMySQL(DatabaseManagerMySQL databaseManagerMySQL) {
        this.dbManager = databaseManagerMySQL;
    }

    String getCartQuery = "SELECT cart_id, account_id, created_at FROM shopping_cart WHERE account_id = ?";
    String getCartById = "SELECT cart_id, account_id, created_at FROM shopping_cart WHERE cart_id = ?";
    private String addCartQuery = "INSERT INTO shopping_cart (account_id, created_at) VALUES (?, ?)";

    private Cart mapCart(ResultSet resultSet){
        try {
            int cartId = resultSet.getInt("cart_id");
            int userId = resultSet.getInt("account_id");
            Timestamp date = resultSet.getTimestamp("created_at");
            LocalDateTime created = date.toLocalDateTime();
            logger.info("Cart ID: " + cartId);
            return new Cart(cartId, new UserMySQL(dbManager).getUserByID(userId), created);
        }catch (Exception e){
            logger.severe("Could not map cart: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void addCart(Cart cart) {
        logger.info("Add Cart to MySQL");
        try (Connection connection = dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(addCartQuery);
            statement.setInt(1,cart.getUser().getUserID());
            statement.setInt(2, Integer.parseInt(cart.getCreated_at().toString()));
            statement.executeUpdate();
            logger.info("Cart added to MySQL");
        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
        }
    }

    @Override
    public void updateCart(Cart cart) {

    }

    @Override
    public void deleteCart(Cart cart) {

    }

    @Override
    public Cart getCartByUser(User user) {
        logger.info("Getting cart from MySQL");
        logger.info("User ID: " + user.getUserID());
        try (Connection connection =dbManager.mysqlConnection()){
            logger.info(user.getUserID() + " " + user.getFirstName());

            PreparedStatement statement = connection.prepareStatement(getCartQuery);
            statement.setInt(1,user.getUserID());
            ResultSet resultSet =statement.executeQuery();
            logger.info(resultSet.toString());
            logger.info("Cart from MySQL");
            if (resultSet.next()) {
                logger.info("Cart found");
                return mapCart(resultSet);
            }
            logger.info("Cart not found");
            throw new RuntimeException();
        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Cart getCartByID(int id) {
        logger.info("Getting cart from MySQL");
        try (Connection connection =dbManager.mysqlConnection()){
            logger.info("User ID: " + id);

            PreparedStatement statement = connection.prepareStatement(getCartById);
            statement.setInt(1, id);
            ResultSet resultSet =statement.executeQuery();
            logger.info("Cart from MySQL");
            logger.info(resultSet.toString());
            if (resultSet.next()) {
                logger.info("Cart found");
                return mapCart(resultSet);
            }
            logger.info("Cart not found");
            throw new RuntimeException();
        }catch (Exception e){
            logger.info("Cart not found");
            logger.severe("Could not connect to MySQL: " + e.getMessage());
            return null;
        }
    }
}
