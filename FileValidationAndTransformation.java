import java.io.*;
import java.util.*;

public class FileValidationAndTransformation {
    public static void main(String[] args) {
        List<String> validRecords = new ArrayList<>();
        List<String> invalidRecords = new ArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidRecord(line)) {
                    validRecords.add(transformRecord(line));
                } else {
                    invalidRecords.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (String validRecord : validRecords) {
                writer.write(validRecord);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
        }

        System.out.println("Number of valid records: " + validRecords.size());
        System.out.println("Number of invalid records: " + invalidRecords.size());

        if (!invalidRecords.isEmpty()) {
            System.out.println("Invalid records:");
            for (String invalidRecord : invalidRecords) {
                System.out.println(invalidRecord);
            }
        }
    }

    public static boolean isValidRecord(String record) {
        String[] parts = record.split(",");
        return parts.length == 3;
    }

    public static String transformRecord(String record) {
        String[] parts = record.split(",");
        // Transforming into a new format with columns rearranged
        String transformedRecord = parts[1] + "," + parts[0] + "," + parts[2];
        return transformedRecord;
    }
}
