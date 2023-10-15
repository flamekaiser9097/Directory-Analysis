import java.io.*;
import java.util.*;

public class TextFileAnalyzer {
    public static void main(String[] args) {
        File directory = new File("E:\\HERO VIRED\\Java\\Directory Analysis");
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Invalid directory path");
            return;
        }

        List<File> textFiles = new ArrayList<>();
        Map<String, Integer> wordFrequency = new HashMap<>();
        
        processDirectory(directory, textFiles, wordFrequency);

        long totalSize = textFiles.stream().mapToLong(File::length).sum();

        System.out.println("List of .txt files:");
        textFiles.forEach(file -> System.out.println(file.getAbsolutePath()));

        System.out.println("Total size of .txt files (bytes): " + totalSize);

        System.out.println("Top 10 most frequent words:");
        wordFrequency.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(10)
            .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void processDirectory(File directory, List<File> textFiles, Map<String, Integer> wordFrequency) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                processDirectory(file, textFiles, wordFrequency);
            } else if (file.getName().toLowerCase().endsWith(".txt")) {
                textFiles.add(file);
                processTextFile(file, wordFrequency);
            }
        }
    }

    public static void processTextFile(File file, Map<String, Integer> wordFrequency) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z]", "");
                if (!word.isEmpty()) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + file.getName() + ": " + e.getMessage());
        }
    }
}
