package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;
import second.controller.ControllerRegister;
import second.help.ApiHelper;
import second.objects.Account;

public class ApiRegister extends RestApi{
    public ApiRegister(String path, Controller controller) {
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
        JsonResponse jsonResponse = new JsonResponse();
        logger.debug("POST /register");

        Account account = ApiHelper.deserializeRegister(exchange);

        if (controller instanceof ControllerRegister){
            jsonResponse=((ControllerRegister) controller).register(account);
        }else {
            logger.error("Wrong Controller", controller);
        }

        ApiHelper.checkIfResponseIsOK(jsonResponse);
        ApiHelper.sendResponse(exchange, jsonResponse);
    }

}
