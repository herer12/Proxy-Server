package data.MySql;


import data.repos.ProductRepository;
import model.Database.Category;
import model.Database.Product;
import service.GeneralPurposeConfig;
import service.Logger;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class ProductMySQL implements ProductRepository {
    DatabaseManagerMySQL dbManager;
    Logger logger = Logger.getInstance();
    public ProductMySQL(DatabaseManagerMySQL dbManager){
        this.dbManager = dbManager;
    }

    String getAllProductsQuery = "SELECT product_id, name, price, category_id, description, created_at FROM product";


    private Product mapProduct(ResultSet resultSet){
        try {
            int productID = resultSet.getInt("product_id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int categoryID = resultSet.getInt("category_id");
            String description = resultSet.getString("description");

            Timestamp timestamp = resultSet.getTimestamp("created_at");
            Date created_at_date = new Date(timestamp.getTime());

            Category category = new CategoryMySQL(dbManager).getCategoryByID(categoryID);

            return new Product(productID, name, description, price, category, created_at_date);

        } catch (Exception e) {
            logger.severe("Could not map product: " + e.getMessage());
            e.printStackTrace();   // ← wichtig zum Debuggen
        }

        return null;
    }

    private LinkedList<Product> mapProducts(ResultSet resultSet){
        LinkedList<Product> products = new LinkedList<>();
        try {
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            logger.info("Products mapped");
        } catch (SQLException e) {
            logger.severe("Could not map products: " + e.getMessage());
            logger.warning("ResultSet bad");
        }


        return products;

    }

    @Override
    public LinkedList<Product> getAllProducts() {
        logger.info("Getting all products from MySQL");
        try (Connection connection =dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(getAllProductsQuery);
            ResultSet resultSet =statement.executeQuery();

            return mapProducts(resultSet);

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
            return null;
        }

    }

    @Override
    public Product getProductByID(int id) {
        return null;
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int id) {

    }

    @Override
    public LinkedList<Product> getAllProductsByName(String name) {
        return null;
    }

    @Override
    public LinkedList<Product> getAllProductsByCategory(Category category) {
        return null;
    }

    @Override
    public LinkedList<Product> getAllProductsByPriceRangeTopBot(double priceMax, double priceMin) {
        return null;
    }
}
