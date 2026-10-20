import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LogAnalyzer {

    public static void main(String[] args) {

        Path directory = Paths.get("logs");
        String keyword = "error";

        try {
            // Create a directory for sample logs
            Files.createDirectories(directory);

            // Create three sample files
            Files.write(
                directory.resolve("app1.txt"),
                List.of(
                    "INFO Application started",
                    "ERROR Database connection failed",
                    "INFO Retry successful"
                ),
                StandardCharsets.UTF_8
            );

            Files.write(
                directory.resolve("app2.txt"),
                List.of(
                    "INFO User logged in",
                    "WARNING Low memory",
                    "ERROR File not found",
                    "INFO Application stopped"
                ),
                StandardCharsets.UTF_8
            );

            Files.write(
                directory.resolve("app3.txt"),
                List.of(
                    "INFO Server started",
                    "INFO Request processed"
                ),
                StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            System.out.println("Could not create sample logs: "
                    + e.getMessage());
            return;
        }

        long totalLines = 0;
        long matchingLines = 0;

        try (var files = Files.list(directory)) {

            for (Path path : files.filter(Files::isRegularFile)
                                  .sorted()
                                  .toList()) {

                long fileLines = 0;
                long fileMatches = 0;

                try (BufferedReader reader =
                             Files.newBufferedReader(
                                 path, StandardCharsets.UTF_8)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileLines++;

                        if (line.toLowerCase()
                                .contains(keyword.toLowerCase())) {
                            fileMatches++;
                        }
                    }
                }

                long size = Files.size(path);

                totalLines += fileLines;
                matchingLines += fileMatches;

                System.out.println("File: " + path.getFileName());
                System.out.println("Size: " + size + " bytes");
                System.out.println("Lines: " + fileLines);
                System.out.println("Keyword matches: " + fileMatches);
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error analyzing logs: " + e.getMessage());
            return;
        }

        System.out.println("=== Overall Summary ===");
        System.out.println("Total lines: " + totalLines);
        System.out.println("Lines containing '" + keyword
                + "': " + matchingLines);
    }
}