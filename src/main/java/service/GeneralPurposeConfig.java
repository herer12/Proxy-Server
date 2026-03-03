package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GeneralPurposeConfig {

    private final Map<String, String> values = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getInstance();

    /**
     * Constructs a new instance of the {@code GeneralPurposeConfig} class
     * and loads the configuration from the specified file path.
     *
     * @param path The file path to the configuration file. The path should point
     *             to a readable file containing key-value pairs of configuration
     *             settings. The file should have a format with each line containing
     *             a key-value pair separated by an equals sign (`=`). Lines starting
     *             with `#` or empty lines are ignored.
     *
     * @throws RuntimeException if the configuration file cannot be found, is unreadable,
     *                          or contains unexpected errors during loading.
     */
    private GeneralPurposeConfig(String path) {
        load(path);
    }

    /**
     * Initializes a new instance of the {@code GeneralPurposeConfig} class using the configuration
     * file path resolved from the provided arguments or environment variables.
     *
     * The method follows these steps in resolving the configuration file path:
     * - Checks if the command-line arguments include a parameter in the format `--config=<path>` and uses it if found.
     * - Falls back to the environment variable `PROXY_CONFIG_PATH` if it is set and non-blank.
     * - Otherwise, defaults to using a pre-defined path `config/Database_config.conf`.
     *
     * @param args An array of command-line arguments that may include configuration-related details.
     * @return A configured {@code GeneralPurposeConfig} instance loaded with key-value pairs from the resolved file.
     * @throws RuntimeException if the configuration file cannot be found or read.
     */
    public static GeneralPurposeConfig initialize(String[] args) {
        String path = resolveConfigPath(args);
        logger.info("Konfiguration: " + path);
        return new GeneralPurposeConfig(path);
    }

    /**
     * Resolves the configuration file path based on the provided command-line arguments,
     * environment variables, or a default fallback.
     *
     * The resolution process involves the following steps:
     * 1. Scans the provided command-line arguments for a parameter in the format `--config=<path>`
     *    and returns the corresponding value if found.
     * 2. Checks the `PROXY_CONFIG_PATH` environment variable. If set and not blank,
     *    its value is returned.
     * 3. Falls back to a predefined default path `config/Database_config.conf` if
     *    neither of the above options provides a valid value.
     *
     * @param args An array of command-line arguments that may include a configuration
     *             file path using the syntax `--config=<path>`.
     * @return A string representing the resolved configuration file path. This will
     *         either be the value from the command-line arguments, the environment
     *         variable, or a predefined default path.
     */
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

    /**
     * Loads the configuration from the specified file path and populates the internal key-value store.
     * The method reads the file line by line, parses key-value pairs separated by an equals sign (`=`),
     * and ignores empty lines or lines starting with a comment symbol (`#`).
     *
     * @param path The file path to the configuration file. The file should be readable and formatted
     *             with key-value pairs separated by `=`. Lines ending with a semicolon (`;`) will have
     *             the semicolon removed. Lines that are empty or start with `#` are ignored.
     * @throws RuntimeException if*/
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
            logger.severe("Konfigurationsdatei konnte nicht gelesen werden: " + path);
            throw new RuntimeException("Konfigurationsdatei nicht gefunden: " + path, e);
        }
    }

    /**
     * Retrieves the string value associated with the specified key.
     * The method first checks for an environment variable with a name matching
     * the uppercase version of the key. If an environment variable is found, its
     * value is returned. Otherwise, the method falls back to retrieving the value
     * associated with the key from the internal configuration.
     *
     * @param key The key whose associated string value is to be retrieved. This
     *            should be a valid key present in the configuration or the environment.
     * @return The string value associated with the provided key. If the key is not found
     *         in the environment or the configuration, the method may return null.
     */
    public String getString(String key) {
        String envOverride = System.getenv(key.toUpperCase());
        return envOverride != null ? envOverride : values.get(key);
    }

    /**
     * Retrieves the integer value associated with the specified key.
     * The method uses {@code getString(key)} to fetch the value as a string and then converts it to an integer.
     *
     * @param key The key for which the integer value needs to be retrieved. This should be a valid key```java
    /**
     * Converts the string value associated with the specified key to an integer.
     * The value is retrieved using the {@code getString} method and

     * * then parsed into an integer.
     *
     * @param key The key whose associated value is to be retrieved and            converted present to in an the integer configuration.
    .
     * *            @ Thereturn key The should integer map value to corresponding a to valid the string specified representation key of.
    an * integer @.
    throws * Number @FormatreturnException The if integer the representation value of associated the with value the associated key with cannot the be specified parsed key as.
    an * integer @.
    throws */
    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    /**
     * Retrieves the enum constant of the specified enum type corresponding to the value
     * associated with the provided key. The value is retrieved as a string using the {@code getString} method
     * and then converted into the corresponding enum constant.
     *
     * @param <T>      The type of the enum.
     * @param key      The key whose associated string value is to be retrieved and converted.
     *                 This should be a valid key present in the configuration or the environment.
     * @param enumType The {@code Class} object of the enum type from which to return a constant.
     *                 Must not be null.
     * @return The enum constant of the specified enum type corresponding to the value found for the key.
     *         If the key is not found or the value cannot map to a valid enum constant, an exception is thrown.
     * @throws IllegalArgumentException if the specified enum type has no constant with the name
     *                                  corresponding to the retrieved string value.
     * @throws NullPointerException     if the provided {@code enumType} is null or no value exists for the key.
     */
    public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
        return Enum.valueOf(enumType, getString(key));
    }
}