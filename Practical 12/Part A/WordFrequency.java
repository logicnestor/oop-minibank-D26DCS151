
import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is easy and Java is powerful. "
                + "Java is popular and easy to learn.";

        // Normalize case and remove punctuation
        String cleaned = paragraph.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "");

        String[] words = cleaned.trim().split("\\s+");

        // Count word frequencies
        Map<String, Integer> frequency = new HashMap<>();

        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        // Sort entries by frequency descending, then word ascending
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());

        entries.sort(
                Comparator
                        .<Map.Entry<String, Integer>>comparingInt(
                                Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );

        System.out.println("Word Frequencies (Highest First):");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
