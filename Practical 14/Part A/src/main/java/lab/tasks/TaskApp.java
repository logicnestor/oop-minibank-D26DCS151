
import java.util.Scanner;

public class TaskApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n--- Task Manager ---");
                System.out.println("1. Add task");
                System.out.println("2. View tasks");
                System.out.println("3. Remove task");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String input = scanner.nextLine();

                try {
                    switch (input) {
                        case "1" -> {
                            System.out.print("Enter description: ");
                            Task task = service.addTask(scanner.nextLine());
                            System.out.println("Added: " + task);
                        }
                        case "2" -> {
                            if (service.getAllTasks().isEmpty()) {
                                System.out.println("No tasks available.");
                            } else {
                                service.getAllTasks()
                                        .forEach(System.out::println);
                            }
                        }
                        case "3" -> {
                            System.out.print("Enter task ID: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            service.removeTask(id);
                            System.out.println("Task removed.");
                        }
                        case "4" -> running = false;
                        default -> System.out.println("Invalid menu option.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        System.out.println("Goodbye!");
    }
}
