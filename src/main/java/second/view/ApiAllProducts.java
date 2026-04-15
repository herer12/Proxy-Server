package second.view;

import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;

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

    }

    @Override
    protected void startApi(HttpExchange exchange) {

    }
}
