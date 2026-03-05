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

    /**
     * Starts the HTTP server by binding it to the port specified in the configuration.
     * The port is retrieved using the {@code config.getInt("Port")} method. Upon successful
     * initialization, the server begins accepting client connections.
     *
     * This method also logs an informational message indicating that the server has started,
     * along with the port number it is bound to.
     *
     * @throws Exception If an error occurs while creating or starting the HTTP server. This
     *                   may include issues such as an invalid port number or binding failure.
     */
    public void start() throws Exception {

        int port = config.getInt("Port");

        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.start();

        logger.info("HTTP Server gestartet auf Port " + port);
    }
}