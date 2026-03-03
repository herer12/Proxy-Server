package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {

    private static volatile Logger instance;
    private static final Object LOCK = new Object();

    private PrintWriter fileWriter;
    private boolean logToFile = false;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        // Standardmäßig nur Console-Logging
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }


    public void setLogFile(String filePath) {
        try {
            fileWriter = new PrintWriter(new FileWriter(filePath, true), true);
            logToFile = true;
            info("Logger initialisiert, Datei: " + filePath);
        } catch (IOException e) {
            severe("Logger: Log-Datei konnte nicht geöffnet werden: " + e.getMessage());
        }
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(dtf);
        String fullMessage = String.format("[%s] [%s] %s", timestamp, level, message);

        // Console
        System.out.println(fullMessage);

        // File
        if (logToFile && fileWriter != null) {
            fileWriter.println(fullMessage);
        }
    }

    // Public Log-Level Methoden
    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void severe(String message) {
        log("SEVERE", message);
    }


    public void close() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}