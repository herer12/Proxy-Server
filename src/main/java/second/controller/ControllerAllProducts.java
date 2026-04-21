package second.controller;

import second.repository.general.ProductsDAO;
import second.service.GetAllProductsService;
import second.view.ApiAllProducts;

public class ControllerAllProducts extends Controller {

    private final GetAllProductsService service;

    public ControllerAllProducts(GetAllProductsService service) {
        this.service = service;
    }

    public Object getAllProducts() {
        try {
            return service.execute();
        } catch (Exception e) {
            logger.error("Error getting all products", e);
            return null;
        }

    }
}
