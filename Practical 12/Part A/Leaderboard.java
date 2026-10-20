
import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Zara", 950);
        scores.put("Amit", 870);
        scores.put("Riya", 950);
        scores.put("Karan", 720);
        scores.put("Neha", 870);
        scores.put("Sara", 990);

        int topN = 4;

        List<Map.Entry<String, Integer>> leaderboard =
                new ArrayList<>(scores.entrySet());

        leaderboard.sort(
                Comparator
                        .<Map.Entry<String, Integer>>comparingInt(
                                Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );

        System.out.println("Top " + topN + " Players:");

        for (int i = 0; i < Math.min(topN, leaderboard.size()); i++) {
            Map.Entry<String, Integer> player = leaderboard.get(i);

            System.out.println(
                    (i + 1) + ". " + player.getKey()
                            + " - " + player.getValue()
            );
        }
    }
}
