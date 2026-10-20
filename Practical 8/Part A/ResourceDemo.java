import java.io.IOException;

public class ResourceDemo {

    static class FileProcessingSession implements AutoCloseable {

        public FileProcessingSession() {
            System.out.println("Resource opened.");
        }

        public void process() {
            System.out.println("Processing file...");

            throw new IllegalStateException(
                "Original error: file processing failed."
            );
        }

        @Override
        public void close() throws IOException {
            System.out.println("Resource closed.");

            throw new IOException(
                "Cleanup error: resource release failed."
            );
        }
    }

    public static void main(String[] args) {

        try (FileProcessingSession session =
                     new FileProcessingSession()) {

            session.process();

        } catch (IllegalStateException e) {

            System.out.println("Caught: " + e.getMessage());

            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println(
                    "Suppressed: " + suppressed.getMessage()
                );
            }

        } catch (IOException e) {

            // Handles an IOException if it is thrown
            // without an earlier exception being propagated.
            System.out.println("Cleanup error: " + e.getMessage());
        }
    }
}