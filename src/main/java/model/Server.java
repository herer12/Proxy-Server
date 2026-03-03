package model;

import service.GeneralPurposeConfig;
import service.Logger;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    private final GeneralPurposeConfig config;
    private final Logger logger = Logger.getInstance();
    private HttpServer httpServer;

    public Server(GeneralPurposeConfig config) {
        this.config = config;
    }

    public void start() throws Exception {

        int port = config.getInt("Port");

        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.start();

        logger.info("HTTP Server gestartet auf Port " + port);
    }
}