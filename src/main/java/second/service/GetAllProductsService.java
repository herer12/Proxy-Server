package second.service;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.objects.Product;
import second.repository.general.ProductsDAO;

import java.util.LinkedList;

public class GetAllProductsService {

    private final ProductsDAO repo;
    private Logger logger = Logger.getLogger(GetAllProductsService.class);

    public GetAllProductsService(ProductsDAO repo) {
        this.repo = repo;
    }

    public LinkedList<Product> execute() {
        try {
            return repo.getAllProducts();
        }catch (Exception e){
            logger.error("Error getting all products", e);
            return null;
        }

    }
}
