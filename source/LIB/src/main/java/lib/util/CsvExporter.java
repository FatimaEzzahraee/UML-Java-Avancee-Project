package lib.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    private CsvExporter() {
        // Prevent instantiation
    }

    public static void export(String filePath, List<String[]> data) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (String[] row : data) {
                writer.write(String.join(";", row));
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du CSV : " + e.getMessage());
        }
    }
}

