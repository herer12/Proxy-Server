package second.controller;

import second.objects.Product;
import second.service.SearchProductsService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ControllerSearchProducts extends Controller{

    private SearchProductsService service;

    public ControllerSearchProducts(SearchProductsService service) {
        this.service = service;
    }

    public LinkedList<Product> searchProducts(String httpRequestQuery){
        String query = "";

        if (httpRequestQuery != null) {
            for (String param : httpRequestQuery.split("&")) {
                String[] pair = param.split("=");

                if (pair[0].equals("query")) {
                    query = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                }
            }
        }
        return service.execute(query);
    }
}
