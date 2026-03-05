package data;

import service.GeneralPurposeConfig;
import service.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManagerMySQL {
    private final GeneralPurposeConfig config;
    Logger logger = Logger.getInstance();
    DatabaseManagerMySQL(GeneralPurposeConfig config) {
        this.config = config;
    }

    /**
     * Establishes a connection to a MySQL database using credentials retrieved from the configuration object.
     * Logs an informational message when attempting to connect, and logs a warning message if the connection fails.
     *
     * @return A {@code Connection} object representing the MySQL database connection, or {@code null}
     *         if the connection attempt fails.
     */
    public Connection mysqlConnection() {
        logger.info("Starte Verbindung zum MySql");
        String[] credentials = getCredentials();
        try {
            return DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
        }catch (Exception e) {
            logger.warning("No connection to Database MySql " + e.getMessage());
            return null;
        }

    }

    /**
     * Retrieves the MySQL database credentials required for establishing a connection.
     * The credentials include the MySQL URL, username, and password,
     * which are fetched from the provided configuration object.
     *
     * @return An array of Strings containing the database credentials in the following order:
     *         1. MySQL URL
     *         2. MySQL username
     *         3. MySQL password
     */
    private String[] getCredentials() {
        return new String[] {config.getString("MYSQL_URL"), config.getString("MYSQL_USER"), config.getString("MYSQL_PASSWORD")};
    }



}
