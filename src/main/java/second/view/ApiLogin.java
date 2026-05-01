package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;
import second.controller.ControllerLogin;
import second.help.ApiHelper;
import second.help.session.SessionManager;
import second.objects.Account;

import java.util.UUID;

public class ApiLogin extends RestApi{

    public ApiLogin(String path, Controller controller) {
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
        logger.debug("POST /login");

        Account account = ApiHelper.deserializeLogin(exchange);

        if (controller instanceof ControllerLogin){
            jsonResponse=((ControllerLogin) controller).login(account);

            UUID sessionID = SessionManager.createSessionId(account.getAccount_id());
            SessionManager.writeSessionCookie(exchange, sessionID);
        }else {
            logger.error("Wrong Controller", controller);
        }

        logger.info("Login: " + jsonResponse.hasSuccess());



        jsonResponse.data =jsonResponse.hasSuccess();

        ApiHelper.checkIfResponseIsOK(jsonResponse);

        ApiHelper.sendResponse(exchange, jsonResponse);
    }
}
