import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

    public static String fillTemplate(
            String template,
            String[] names,
            String[] values) {

        // Pattern for placeholders like {name}, {id}, {date}
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");

        Matcher matcher = pattern.matcher(template);

        StringBuilder result = new StringBuilder();

        int lastEnd = 0;

        while (matcher.find()) {

            // Add text before the current placeholder
            result.append(template, lastEnd, matcher.start());

            // Get placeholder name without { }
            String placeholder = matcher.group(1);

            String replacement = "[?]";

            // Search for placeholder in names array
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(placeholder)) {
                    replacement = values[i];
                    break;
                }
            }

            // Add replacement
            result.append(replacement);

            lastEnd = matcher.end();
        }

        // Add remaining text after the last placeholder
        result.append(template, lastEnd, template.length());

        return result.toString();
    }
}