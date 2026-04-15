package second.view;

import at.herer12_erik_van_haentjens.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import second.controller.Controller;

public abstract class RestApi {
    protected Logger logger = Logger.getLogger(RestApi.class);
    protected final Server server = Server.getInstance();
    protected String path;
    protected Controller controller;

    public RestApi(String path, Controller controller) {
        this.path = path;
        this.controller = controller;
        registerApi();
    }

    public abstract void registerApi();
    public abstract void unregisterApi();
    protected abstract void startApi(HttpExchange exchange);
}
