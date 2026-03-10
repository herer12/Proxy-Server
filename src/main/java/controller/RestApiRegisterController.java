package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import logic.Register;
import model.Database.User;
import model.RegisterUser;
import service.Logger;
import service.Repos;

import java.io.InputStream;

public class RestApiRegisterController {
    private Logger logger = Logger.getInstance();
    private Repos repos;
    public RestApiRegisterController(Repos repos) {
        this.repos = repos;
    }
    public void registerApiRegister(HttpServer server){
        logger.info("Registering REST API Register");
        server.createContext("/login/register", this::apiRegisterCalledUpon);
    }
    private void apiRegisterCalledUpon(HttpExchange exchange){
        logger.info("Received request to /api/register");
        if (!ApiHelper.checkIFPost(exchange)) return;

        InputStream in = exchange.getRequestBody();

        ObjectMapper mapper = new ObjectMapper();

        try {

            RegisterUser user = mapper.readValue(in, RegisterUser.class);

            Register register = new Register(repos);
            if (register.checkData(user)){
                logger.info("Register successful");
                exchange.sendResponseHeaders(200, 0);
                exchange.getResponseBody().close();
                in.close();
                return;
            }

            logger.info("Register failed");

            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            in.close();



        }catch (Exception e){
            logger.severe("Could not parse register request: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
