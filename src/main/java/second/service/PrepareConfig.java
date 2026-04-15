package second.service;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.modell.Database_Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PrepareConfig {
    private final Map<String, String> values = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(PrepareConfig.class);
    public Config config = new Config();

    private PrepareConfig(String path) {
        loadConfig(path);
        applyToConfig();
    }

    private void loadConfig(String path) {
        logger.debug("Loading config from " + path);

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    values.put(parts[0].trim(), parts[1].trim());
                }
            }
        }catch (Exception e) {
            logger.error("Could not load config: ", e.getMessage());
        }
    }

    private void applyToConfig() {
        values.forEach(this::applyEntry);
    }

    private void applyEntry(String key, String value) {

        switch (key) {
            case "database_type":
                config.setDatabase_type(Database_Type.valueOf(value));
                break;
            case "configPath":
                config.setConfigPath(value);
                break;
            case "port":
                config.setPort(Integer.parseInt(value));
                break;
            case "MYSQL_URL":
                config.setMYSQL_URL(value);
                break;
            case "MYSQL_USER":
                config.setMYSQL_USER(value);
                break;
            case "MYSQL_PASSWORD":
                config.setMYSQL_PASSWORD(value);
                break;
        }
    }

    public void saveConfig(String path) {
        logger.debug("Saving config to " + path);

        try (java.io.BufferedWriter writer =
                     new java.io.BufferedWriter(new java.io.FileWriter(path))) {

            for (Map.Entry<String, String> entry : values.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + ";");
                writer.newLine();
            }

            values.clear();
            loadConfig(path);
            applyToConfig();

        } catch (Exception e) {
            logger.error("Could not save config: ", e.getMessage());
        }
    }

    private static PrepareConfig instance;

    public static PrepareConfig getInstance() {
        if (instance == null) {
            instance = new PrepareConfig(config.getConfigPath());
        }
        return instance;
    }

}
