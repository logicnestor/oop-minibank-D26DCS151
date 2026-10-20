public class ChatFilter {

    public static boolean containsKeyword(String message, String keyword) {
        return message.toLowerCase().contains(keyword.toLowerCase());
    }

    
    public static String formatLine(String time, String user, String message) {
        return time + " " + user + ": " + message;
    }
}