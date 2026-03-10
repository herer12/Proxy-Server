package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import logic.Login;
import model.LoginRequest;
import service.Logger;
import service.Repos;
import service.session.SessionRepository;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestApiLoginController {
    private Logger logger = Logger.getInstance();
    private Repos repos;
    public RestApiLoginController(Repos repos) {
        this.repos = repos;
    }
    public void registerRestAPILogin(com.sun.net.httpserver.HttpServer server) {
        server.createContext("/login", this::apiCalledUpon);
    }

    private void apiCalledUpon(HttpExchange exchange) {
        logger.info("Received request to /api/login");

        if (!ApiHelper.checkIFPost(exchange)) return;

        InputStream in = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response;

        try {
            LoginRequest loginRequest = mapper.readValue(in, LoginRequest.class);
            logger.info("Login Request: " + loginRequest.email + " " + loginRequest.password);

            Login login = new Login();
            if (login.checkData(loginRequest, repos, exchange)) {

                logger.info("Login successful");

                exchange.getResponseHeaders().add("Set-Cookie", "session=" + login.getSessionId() + "; HttpOnly; Path=/");

                 response = new HashMap<>();
                response.put("success", true);
                response.put("sessionId", login.getSessionId());
            }else {
                 response = new HashMap<>();
                response.put("success", false);
                response.put("sessionId", login.getSessionId());
            }

            String json = mapper.writeValueAsString(response);

            logger.info("JSON: " + json);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
            in.close();

        } catch (Exception e) {
            logger.severe("Could not parse login request: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
