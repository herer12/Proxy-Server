package Bootstrap;

import model.Server;
import service.GeneralPurposeConfig;
import service.Logger;

public final class Application {

    private static final Logger logger = Logger.getInstance();

    private Application() {}

    public static void start(String[] args) {

        try {

            logger.info("Starte Proxy Server...");

            // Config initialisieren
            GeneralPurposeConfig config = GeneralPurposeConfig.initialize(args);

            //  Server erzeugen (Dependency Injection vorbereitet)
            Server server = new Server(config);

            // Server starten
            server.start();

            logger.info("Application erfolgreich gestartet.");

        } catch (Exception e) {

            logger.severe("FATAL ERROR beim Start: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Fail Fast
        }
    }
}