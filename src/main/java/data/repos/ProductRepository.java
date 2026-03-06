package data.repos;

import model.Database.Category;
import model.Database.Product;

import java.util.LinkedList;

public interface ProductRepository {
    public LinkedList<Product> getAllProducts();
    public Product getProductByID(int id);
    public void addProduct(Product product);
    public void updateProduct(Product product);
    public void deleteProduct(int id);
    public LinkedList<Product> getAllProductsByName(String name);
    public LinkedList<Product> getAllProductsByCategory(Category category);
    public LinkedList<Product> getAllProductsByPriceRangeTopBot(double priceMax, double priceMin);
    
}
