import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FileTreeReport {

    public static void main(String[] args) {

        Path root = Paths.get("sample-tree");
        Path report = Paths.get("file-report.txt");

        // Create sample directory structure
        try {
            Files.createDirectories(root.resolve("documents"));
            Files.createDirectories(root.resolve("images"));

            Files.writeString(
                root.resolve("readme.txt"),
                "This is a sample file.",
                StandardCharsets.UTF_8
            );

            Files.writeString(
                root.resolve("documents/notes.txt"),
                "Java NIO practice.",
                StandardCharsets.UTF_8
            );

            Files.writeString(
                root.resolve("images/info.txt"),
                "Sample image directory.",
                StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            System.out.println("Could not create sample tree: "
                    + e.getMessage());
            return;
        }

        List<String> reportLines = new ArrayList<>();
        long[] totals = new long[2];
        // totals[0] = file count
        // totals[1] = total size in bytes

        reportLines.add("FILE TREE REPORT");
        reportLines.add("Root: " + root.toAbsolutePath());
        reportLines.add("");

        try {
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(
                        Path file,
                        BasicFileAttributes attrs) {

                    if (attrs.isRegularFile()) {
                        String fileName = file.getFileName().toString();
                        String relativePath = root.relativize(file).toString();
                        long size = attrs.size();
                        Instant modified = attrs.lastModifiedTime()
                                                  .toInstant();

                        String entry = String.format(
                            "Name: %s | Path: %s | Size: %d bytes"
                            + " | Modified: %s",
                            fileName,
                            relativePath,
                            size,
                            modified
                        );

                        reportLines.add(entry);

                        totals[0]++;
                        totals[1] += size;
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(
                        Path file,
                        IOException exc) {

                    reportLines.add(
                        "Could not access: " + file
                        + " | Reason: " + exc.getMessage()
                    );

                    return FileVisitResult.CONTINUE;
                }
            });

            reportLines.add("");
            reportLines.add("=== SUMMARY ===");
            reportLines.add("Total files: " + totals[0]);
            reportLines.add("Total size: " + totals[1] + " bytes");

            Files.write(
                report,
                reportLines,
                StandardCharsets.UTF_8
            );

            System.out.println("Report generated: "
                    + report.toAbsolutePath());
            System.out.println("Total files: " + totals[0]);
            System.out.println("Total size: " + totals[1] + " bytes");

        } catch (IOException e) {
            System.out.println("Error generating report: "
                    + e.getMessage());
        }
    }
}