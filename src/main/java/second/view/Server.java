package second.view;

import at.herer12_erik_van_haentjens.logging.Logger;
import com.sun.net.httpserver.HttpServer;
import second.service.Config;
import second.service.PrepareConfig;

import java.net.InetSocketAddress;

public class Server {

    private final Config config;
    private final Logger logger = Logger.getLogger( Server.class);
    private HttpServer httpServer;
    private static volatile Server instance;
    private static final Object LOCK = new Object();

    public Server() {
        this.config = PrepareConfig.getInstance().config;
        start();
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
    private void start() {

        try {

            int port = config.getPort();

            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.start();

            logger.info("HTTP Server gestartet auf Port " + port);
        }catch (Exception e){
            logger.fatal("HTTP Server konnte nicht gestartet werden: " + e.getMessage());
        }
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public static Server getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new Server();
                }
            }
        }
        return instance;
    }
}
