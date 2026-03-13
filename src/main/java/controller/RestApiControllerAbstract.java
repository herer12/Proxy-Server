package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import service.Logger;
import service.Repos;

public abstract class RestApiControllerAbstract {
    protected Logger logger = Logger.getInstance();
    protected Repos repos;
    public RestApiControllerAbstract(Repos repos) {
        this.repos = repos;
    }
    public abstract void registerApi(HttpServer server);
    protected abstract void apiCalledUpon(HttpExchange exchange);
}
