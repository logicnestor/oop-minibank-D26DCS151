import java.util.Scanner;

public class Drive2{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Chat log lines
        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you?",
            "10:15 Charlie"    // Malformed line
        };

        // Read keyword
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        int matches = 0;

        // StringBuilder for the final report
        StringBuilder report = new StringBuilder();

        // Process each log line
        for (String line : logs) {

            // Split into maximum 3 parts:
            // time, user, message
            String[] parts = line.split(" ", 3);

            // Skip malformed lines
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // Check whether message contains keyword
            if (ChatFilter.containsKeyword(message, keyword)) {

                matches++;

                report.append(
                    ChatFilter.formatLine(time, user, message)
                );

                report.append("\n");
            }
        }

        // Print result
        System.out.println("Matches: " + matches);

        if (matches > 0) {
            System.out.println(report);
        }

        sc.close();
    }
}