package second.service;

import second.objects.Product;
import second.repository.general.ProductsDAO;

import java.util.LinkedList;

public class GetAllProductsService {

    private final ProductsDAO repo;

    public GetAllProductsService(ProductsDAO repo) {
        this.repo = repo;
    }

    public LinkedList<Product> execute() {
        return repo.getAllProducts();
    }
}
