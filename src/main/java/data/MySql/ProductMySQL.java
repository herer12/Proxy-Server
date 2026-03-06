package data.MySql;

import data.repos.ProductRepository;
import model.Database.Category;
import model.Database.Product;

import java.util.LinkedList;

public class ProductMySQL implements ProductRepository {

    @Override
    public LinkedList<Product> getAllProducts() {
        return null;
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
