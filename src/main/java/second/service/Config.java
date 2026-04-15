package second.service;

import second.modell.Database_Type;

public final class Config {
    private String configPath = "../../config/Database_config.conf";
    private Database_Type database_type;
    private int port;
    private String MYSQL_URL;
    private String MYSQL_USER;
    private String MYSQL_PASSWORD;





    public int getPort() {
        return port;
    }

    void setPort(int port) {
        this.port = port;
    }

    public String getMYSQL_URL() {
        return MYSQL_URL;
    }

    void setMYSQL_URL(String MYSQL_URL) {
        this.MYSQL_URL = MYSQL_URL;
    }

    public String getMYSQL_USER() {
        return MYSQL_USER;
    }

    void setMYSQL_USER(String MYSQL_USER) {
        this.MYSQL_USER = MYSQL_USER;
    }

    public String getMYSQL_PASSWORD() {
        return MYSQL_PASSWORD;
    }

    public void setMYSQL_PASSWORD(String MYSQL_PASSWORD) {
        this.MYSQL_PASSWORD = MYSQL_PASSWORD;
    }

    public String getConfigPath() {
        return configPath;
    }

    void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public Database_Type getDatabase_type() {
        return database_type;
    }

    void setDatabase_type(Database_Type database_type) {
        this.database_type = database_type;
    }
}
