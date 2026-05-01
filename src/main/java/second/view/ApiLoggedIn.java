package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;
import second.controller.ControllerLoggedIn;
import second.help.ApiHelper;

public class ApiLoggedIn extends RestApi{
    public ApiLoggedIn(String path, Controller controller) {
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
        logger.debug("GET /loggedin");
        JsonResponse jsonResponse = new JsonResponse();

        if (controller instanceof ControllerLoggedIn){
            jsonResponse =((ControllerLoggedIn) controller).isLoggedIn(exchange);
        }

        ApiHelper.checkIfResponseIsOK(jsonResponse);
        logger.info("Login: " + jsonResponse);
        ApiHelper.sendResponse(exchange, jsonResponse);

    }
}
