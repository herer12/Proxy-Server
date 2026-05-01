package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;
import second.controller.ControllerAllProducts;
import second.help.ApiHelper;

public class ApiAllProducts extends RestApi {

    public ApiAllProducts(String path, Controller controller) {
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
        logger.debug("GET /products");
        JsonResponse jsonResponse = new JsonResponse();

        Object object = null;

        if (controller instanceof ControllerAllProducts) {
            object = ((ControllerAllProducts) controller).getAllProducts();
            jsonResponse.setSuccess(1);
        } else {
            logger.error("Wrong Controller", controller);
            jsonResponse.setSuccess(0);
        }


        jsonResponse.data =object;

        ApiHelper.checkIfResponseIsOK(jsonResponse);

        ApiHelper.sendResponse(exchange, jsonResponse);


    }
}
