package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;
import second.controller.ControllerSearchProducts;
import second.help.ApiHelper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ApiSearchProducts extends RestApi{

    public ApiSearchProducts(String path, Controller controller) {
        super(path, controller);
    }

    @Override
    public void registerApi() {
        server.getHttpServer().createContext(path, this::startApi );
    }

    @Override
    public void unregisterApi() {
        server.getHttpServer().removeContext(path);
    }

    @Override
    protected void startApi(HttpExchange exchange) {
        System.out.println("GET /products/search");
        JsonResponse jsonResponse = new JsonResponse();

        String queryString = exchange.getRequestURI().getQuery();

        Object object = null;

        if (controller instanceof ControllerSearchProducts){
            object=((ControllerSearchProducts)controller).searchProducts(queryString);
        }else {
            logger.error("Wrong Controller", controller);
        }

        jsonResponse.data = ApiHelper.prepareJson(object);
        ApiHelper.checkIfResponseIsOK(jsonResponse);
        ApiHelper.sendResponse(exchange, jsonResponse);

    }
}
