package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EnterpriseConfig {

    private final Map<String, String> values = new ConcurrentHashMap<>();
    private final String filePath;

    private static volatile EnterpriseConfig instance;
    private static final Object LOCK = new Object();

    private final Logger logger = Logger.getInstance();

    private EnterpriseConfig(String filePath) {
        this.filePath = filePath;
        load();
    }

    public static EnterpriseConfig getInstance(String filePath) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new EnterpriseConfig(filePath);
                }
            }
        }
        return instance;
    }

    private void load() {
        logger.info("Lade Konfigurationsdatei: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }

                String[] parts = line.split("=", 2);

                if (parts.length != 2) {
                    logger.warning("Ungültige Konfigurationszeile: " + line);
                    continue;
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                values.put(key, value);
            }

            logger.info("Konfigurationsdatei erfolgreich geladen.");

        } catch (IOException e) {
            logger.severe("Fehler beim Laden der Konfiguration: " + e.getMessage());
            throw new RuntimeException("Konfigurationsdatei konnte nicht geladen werden.", e);
        }
    }

    public void reload() {
        values.clear();
        load();
        logger.info("Konfiguration neu geladen.");
    }

    public String getString(String key) {
        validateKey(key);
        return values.get(key);
    }

    public int getInt(String key) {
        validateKey(key);
        try {
            return Integer.parseInt(values.get(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wert für Key '" + key + "' ist keine gültige Zahl.");
        }
    }

    public boolean getBoolean(String key) {
        validateKey(key);
        return Boolean.parseBoolean(values.get(key));
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
        validateKey(key);
        try {
            return Enum.valueOf(enumType, values.get(key));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ungültiger Enum-Wert für Key '" + key + "'");
        }
    }

    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(values);
    }

    private void validateKey(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Konfigurations-Key nicht gefunden: " + key);
        }
    }
}