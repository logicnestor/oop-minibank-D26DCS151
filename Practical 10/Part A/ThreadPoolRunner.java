import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 1; i <= 10; i++) {
                final int taskId = i;

                pool.submit(() -> {
                    String threadName =
                            Thread.currentThread().getName();

                    System.out.println(
                            "Task " + taskId
                            + " executed by " + threadName
                    );

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(
                                "Task " + taskId + " interrupted."
                        );
                    }
                });
            }

        } finally {
            // Stop accepting new tasks
            pool.shutdown();

            try {
                // Wait for submitted tasks to finish
                if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All tasks completed.");
    }
}