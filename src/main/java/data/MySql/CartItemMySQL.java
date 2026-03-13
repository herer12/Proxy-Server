package data.MySql;

import data.repos.CartItemRepository;
import model.Database.CartItem;
import service.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class CartItemMySQL implements CartItemRepository {
    private DatabaseManagerMySQL dbManager;
    private Logger logger = Logger.getInstance();

    public CartItemMySQL(DatabaseManagerMySQL dbManager){
        this.dbManager = dbManager;
    }

    String getCartItemByCartIDQuery = "SELECT cart_item_id, cart_id, product_id, quantity, price FROM cart_item WHERE cart_id = ?";
    String changeQuantityQuery = "UPDATE cart_item SET quantity = ? WHERE cart_item_id = ?";
    String addCartItemQuery = "INSERT INTO cart_item (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    private CartItem mapCartItem(ResultSet resultSet){
        try{
            int cartItemID = resultSet.getInt("cart_item_id");
            int cartID = resultSet.getInt("cart_id");
            int productID = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");
            double price = resultSet.getDouble("price");
            logger.info("Cart Item ID: " + cartItemID);

            return new CartItem(cartItemID,new CartMySQL(dbManager).getCartByID(cartID), new ProductMySQL(dbManager).getProductByID(productID), quantity, price);
        }catch (Exception e){
            logger.severe("Could not map cart item: " + e.getMessage());
            return null;
        }
    }

    private LinkedList<CartItem> mapCartItems(ResultSet resultSet){
        LinkedList<CartItem> cartItems = new LinkedList<>();
        try {
            while (resultSet.next()){
                cartItems.add(mapCartItem(resultSet));
            }
            return cartItems;
        }catch (Exception e){
            logger.severe("Could not map cart items: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        logger.info("Adding Cart Item to MySQL");
        try (Connection connection = dbManager.mysqlConnection()){

            PreparedStatement statement = connection.prepareStatement(addCartItemQuery);
            statement.setInt(1,cartItem.getCart().getCartID());
            statement.setInt(2,cartItem.getProduct().getProductID());
            statement.setInt(3,cartItem.getQuantity());
            statement.setDouble(4,cartItem.getPrice());
            statement.executeUpdate();
            logger.info("Cart Item added to MySQL");

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
        }
    }

    @Override
    public LinkedList<CartItem> getCartItemByCartID(int id) {
        logger.info("Getting cart items from MySQL");
        logger.info("Cart ID: " + id);
        try (Connection connection =dbManager.mysqlConnection()){

            PreparedStatement statement = connection.prepareStatement(getCartItemByCartIDQuery);
            statement.setInt(1,id);
            ResultSet resultSet =statement.executeQuery();
            logger.info("Cart Item from MySQL");
            if (resultSet.next()) {
                logger.info("Cart Item found");
                return mapCartItems(resultSet);
            }
            throw new RuntimeException();
        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void changeQuantity(CartItem cartItem) {
        logger.info("Changing quantity in MySQL");
        try (Connection connection = dbManager.mysqlConnection()){

            PreparedStatement statement = connection.prepareStatement(changeQuantityQuery);
            statement.setInt(1,cartItem.getQuantity());
            statement.setInt(2,cartItem.getCartItemID());
            statement.executeUpdate();
            logger.info("Quantity changed in MySQL");

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
        }

    }

}
