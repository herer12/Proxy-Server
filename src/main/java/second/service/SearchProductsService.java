package second.service;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.objects.Product;
import second.repository.general.ProductsDAO;

import java.util.LinkedList;

public class SearchProductsService {
    private final ProductsDAO repo;
    private Logger logger = Logger.getLogger(SearchProductsService.class);

    public SearchProductsService(ProductsDAO repo) {
        this.repo = repo;
    }

    public LinkedList<Product> execute(String searchQuery) {
        try {
            return repo.getProductsLikeQuery(searchQuery);
        }catch (Exception e){
            logger.error("Error getting searched products", e);
            return null;
        }

    }
}
