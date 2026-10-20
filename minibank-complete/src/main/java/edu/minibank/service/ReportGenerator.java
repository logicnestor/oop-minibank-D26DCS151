package edu.minibank.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReportGenerator {
    public String generate(Path logsFolder, Path reportFile) throws IOException {
        long deposits = 0, withdrawals = 0, transfers = 0;
        List<String> details = new ArrayList<>();
        if (Files.isDirectory(logsFolder)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsFolder)) {
                for (Path file : stream) {
                    if (!Files.isRegularFile(file)) continue;
                    BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
                    details.add(file.getFileName() + " | size=" + attrs.size()
                            + " bytes | modified=" + attrs.lastModifiedTime());
                    try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] p = line.split("\\|");
                            if (p.length >= 3) {
                                try {
                                    long amount = Long.parseLong(p[2].trim());
                                    if (p[1].trim().equals("DEPOSIT")) deposits += amount;
                                    else if (p[1].trim().equals("WITHDRAW")) withdrawals += amount;
                                    else if (p[1].trim().equals("TRANSFER")) transfers += amount;
                                } catch (NumberFormatException ignored) { /* skip malformed log line */ }
                            }
                        }
                    }
                }
            }
        }
        String report = "MINIBANK END-OF-DAY REPORT\n"
                + "Total deposits: ₹" + deposits + "\n"
                + "Total withdrawals: ₹" + withdrawals + "\n"
                + "Total transfers initiated: ₹" + transfers + "\n"
                + "Files:\n" + String.join("\n", details) + "\n";
        Path parent = reportFile.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        Files.writeString(reportFile, report, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return report;
    }
}
