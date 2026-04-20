package second.repository;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.help.Config;
import second.help.PrepareConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    private static MySqlConnection instance;

    private final Config config;
    private final Logger logger;

    private final String url;
    private final String user;
    private final String password;

    private MySqlConnection() {
        this.config = PrepareConfig.getInstance().config;
        this.logger = Logger.getLogger(MySqlConnection.class);

        this.url = config.getMYSQL_URL();
        this.user = config.getMYSQL_USER();
        this.password = config.getMYSQL_PASSWORD();
    }

    public static MySqlConnection getInstance() {
        if (instance == null) {
            synchronized (MySqlConnection.class) {
                if (instance == null) {
                    instance = new MySqlConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.fatal("Database connection failed: " + e.getMessage(), e);
            throw new RuntimeException("DB connection failed", e);
        }
    }
}