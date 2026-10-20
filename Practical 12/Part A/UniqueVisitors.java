
import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
                "Amit", "Riya", "Karan", "Amit",
                "Sara", "Riya", "Neha", "Karan"
        );

        // HashSet stores unique users
        Set<String> uniqueUsers = new HashSet<>(logins);

        // LinkedHashSet stores unique users in first-seen order
        Set<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Unique visitor count: " + uniqueUsers.size());

        System.out.println("First three distinct visitors:");
        int count = 0;

        for (String user : orderedUsers) {
            if (count == 3) {
                break;
            }

            System.out.println(user);
            count++;
        }
    }
}
