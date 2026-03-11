package bootstrap;

import controller.*;
import model.Server;
import service.GeneralPurposeConfig;
import service.Logger;
import service.Repos;

public final class Application {

    private static final Logger logger = Logger.getInstance();

    private Application() {}

    /**
     * Starts the application by initializing the necessary configuration, creating
     * and starting the server, and handling any exceptions that occur during startup.
     *
     * @param args Command-line arguments passed to the application, used to
     *             initialize the configuration.
     */
    public static void start(String[] args) {

        try {
            logger.setLogFile("../../logs/proxy.log");

            logger.info("Starte Proxy Server...");

            // Config initialisieren
            GeneralPurposeConfig config = GeneralPurposeConfig.initialize(args);

            //  Server erzeugen (Dependency Injection vorbereitet)
            Server server = new Server(config);

            Repos repos = new Repos(config);

            // Server starten
            server.start();

            RestApiSearchController apiSearchController = new RestApiSearchController(repos);
            apiSearchController.registerRestAPISearch(server.getHttpServer());

            RestAPIProductsController apiProductsController = new RestAPIProductsController(repos);
            apiProductsController.registerRestAPIProducts(server.getHttpServer());

            RestApiRegisterController apiRegisterController = new RestApiRegisterController(repos);
            apiRegisterController.registerApiRegister(server.getHttpServer());

            RestApiLoginController apiLoginController = new RestApiLoginController(repos);
            apiLoginController.registerRestAPILogin(server.getHttpServer());

            RestAPILoggedInController apiLoggedInController = new RestAPILoggedInController(repos);
            apiLoggedInController.registerApi(server.getHttpServer());


            logger.info("Application erfolgreich gestartet.");

        } catch (Exception e) {

            logger.severe("FATAL ERROR beim Start: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Fail Fast
        }
    }
}