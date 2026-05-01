package second.controller;

import com.sun.net.httpserver.HttpExchange;
import second.help.session.SessionManager;
import second.view.JsonResponse;

public class ControllerLoggedIn extends Controller{

    public JsonResponse isLoggedIn(HttpExchange exchange){
        boolean succses = SessionManager.hasValidSession(exchange);
        if (succses) return new JsonResponse(true, 1, "User is logged in");
        return new JsonResponse(false, 0, "User is not logged in");
    }
}
