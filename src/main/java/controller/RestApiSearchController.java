package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Database.Product;
import service.Logger;
import service.Repos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class RestApiSearchController {
    private Repos repos;
    private Logger logger = Logger.getInstance();
    public RestApiSearchController(Repos repos) {
        this.repos = repos;
    }

    public void registerRestAPISearch(HttpServer server) {
        server.createContext("/products/search", this::apiCalledUpon);
    }

    private void apiCalledUpon(HttpExchange exchange)  {
        logger.info("Received request to /api/products/search");
        //Crash if not Post
        if (!ApiHelper.checkIFPost(exchange)) return;

        InputStream in = exchange.getRequestBody();
        String responseBody="";
        try {
            responseBody = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }catch (IOException e){
            logger.severe("API response could not be sent: " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("API response received: " + responseBody);

        // Parse the JSON body to extract the search term
        ObjectMapper mapper = new ObjectMapper();
        String searchTerm = "";
        try {
            SearchRequest searchRequest = mapper.readValue(responseBody, SearchRequest.class);
            searchTerm = searchRequest.search != null ? searchRequest.search : "";
        } catch (Exception e) {
            logger.severe("Could not parse search request: " + e.getMessage());
            return;
        }

        ApiHelper.sendingJson(exchange, getProductsByKeyword(searchTerm));

    }

    private LinkedList<Product> getProductsByKeyword(String keyword) {
        return repos.getProductRepository().getAllProductsByName(keyword);
    }
    static class SearchRequest {
        public String search;
    }
}
