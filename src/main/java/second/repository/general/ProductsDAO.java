package second.repository.general;

import second.objects.Product;

import java.util.LinkedList;

public interface ProductsDAO{
    LinkedList<Product> getAllProducts();
    LinkedList<Product> getProductsLikeQuery(String query);
}
