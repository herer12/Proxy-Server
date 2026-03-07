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

        try {
            logger.info("API called upon");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(getAllProducts());

            logger.info("JSON: " + json);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
            logger.info("API response sent");

        } catch (IOException e) {
            logger.severe("API response could not be sent: " + e.getMessage());
        }


    }
    private LinkedList<Product> getAllProducts(){
        return repos.getProductRepository().getAllProducts();
    }
}
