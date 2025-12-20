package lib.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {

    private static final String LOG_FILE = "logs.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerUtil() {
        // Prevent instantiation
    }

    public static void log(String user, String action) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logLine = timestamp + " | " + user + " | " + action;

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(LOG_FILE, true))) {

            writer.write(logLine);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du log : " + e.getMessage());
        }
    }
}

