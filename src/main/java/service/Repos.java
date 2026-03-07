package service;

import data.Database_Typ;
import data.MySql.*;
import data.repos.*;

public class Repos {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private ReviewRepository reviewRepository;
    private CategoryRepository categoryRepository;

    private GeneralPurposeConfig config;
    Database_Typ database_type;
    public Repos(GeneralPurposeConfig config) {
        this.config = config;
        database_type = config.getEnum("Database_Typ", Database_Typ.class);
        switch (database_type) {
            case MYSQL -> mySqlSetup();

        }
    }
    private void mySqlSetup(){
        DatabaseManagerMySQL databaseManagerMySQL = new DatabaseManagerMySQL(config);
        userRepository = new UserMySQL();
        productRepository = new ProductMySQL(databaseManagerMySQL);
        cartRepository = new CartMySQL();
        orderRepository = new OrderMySQL();
        addressRepository = new AddressMySQL();
        reviewRepository = new ReviewMySQL();
        categoryRepository = new CategoryMySQL(databaseManagerMySQL);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public CartRepository getCartRepository() {
        return cartRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public AddressRepository getAddressRepository() {
        return addressRepository;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public GeneralPurposeConfig getConfig() {
        return config;
    }

    public Database_Typ getDatabase_type() {
        return database_type;
    }
}
