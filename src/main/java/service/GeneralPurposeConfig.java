package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GeneralPurposeConfig {

    private final Map<String, String> values = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getInstance();

    private GeneralPurposeConfig(String path) {
        load(path);
    }

    public static GeneralPurposeConfig initialize(String[] args) {
        String path = resolveConfigPath(args);
        return new GeneralPurposeConfig(path);
    }

    private static String resolveConfigPath(String[] args) {

        // Argument prüfen
        for (String arg : args) {
            if (arg.startsWith("--config=")) {
                return arg.substring(9);
            }
        }

        //  ENV prüfen
        String env = System.getenv("PROXY_CONFIG_PATH");
        if (env != null && !env.isBlank()) {
            return env;
        }

        // Default
        return "config/Database_config.conf";
    }

    private void load(String path) {

        logger.info("Lade Konfiguration aus: " + path);

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

        } catch (IOException e) {
            throw new RuntimeException("Konfigurationsdatei nicht gefunden: " + path, e);
        }
    }

    public String getString(String key) {
        String envOverride = System.getenv(key.toUpperCase());
        return envOverride != null ? envOverride : values.get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
        return Enum.valueOf(enumType, getString(key));
    }
}