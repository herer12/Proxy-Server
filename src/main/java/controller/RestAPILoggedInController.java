package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import logic.SessionManager;
import service.Repos;

import java.io.IOException;
import java.io.OutputStream;

public class RestAPILoggedInController extends RestApiControllerAbstract {

    public RestAPILoggedInController(Repos repos) {
        super(repos);
    }

    @Override
    public void registerApi(HttpServer server) {
        server.createContext("/login/loggedin", this::apiCalledUpon);
    }

    @Override
    protected void apiCalledUpon(HttpExchange exchange)  {

        logger.info("Received request to /api/login/loggedin");

        boolean loggedIn = SessionManager.hasValidSession(exchange);

        String jsonResponse = """
        {
            "loggedIn": %s
        }
        """.formatted(loggedIn);

        byte[] responseBytes = jsonResponse.getBytes();

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        try {
            exchange.sendResponseHeaders(200, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }catch (IOException e){
            logger.severe("API response could not be sent: " + e.getMessage());
        }


    }
}