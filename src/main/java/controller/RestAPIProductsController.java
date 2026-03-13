package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Database.Product;
import model.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.Logger;
import service.Repos;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

public class RestAPIProductsController {
    private Repos repos;
    private Logger logger = Logger.getInstance();

    public RestAPIProductsController(Repos repos){
        this.repos = repos;
    }

    public void registerRestAPIProducts(HttpServer server) {
        logger.info("Registering REST API Products");
        server.createContext("/products/all", this::apiCalledUpon);
    }

    private void apiCalledUpon(HttpExchange exchange) {

       ApiHelper.sendingJson(exchange, getAllProducts());


    }
    private LinkedList<Product> getAllProducts(){
        return repos.getProductRepository().getAllProducts();
    }
}
