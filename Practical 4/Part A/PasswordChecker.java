public class PasswordChecker {

    // Check whether password length is at least 8
    public static boolean hasMinimumLength(String pw) {
        return pw.length() >= 8;
    }

    // Check whether password contains an uppercase letter
    public static boolean hasUppercase(String pw) {
        return pw.matches(".*[A-Z].*");
    }

    // Check whether password contains a digit
    public static boolean hasDigit(String pw) {
        return pw.matches(".*[0-9].*");
    }

    // Check whether password contains a special character
    public static boolean hasSpecialCharacter(String pw) {
        return pw.matches(".*[^a-zA-Z0-9].*");
    }

    // Calculate password strength
    public static String strength(String pw) {

        int count = 0;

        if (hasMinimumLength(pw)) {
            count++;
        }

        if (hasUppercase(pw)) {
            count++;
        }

        if (hasDigit(pw)) {
            count++;
        }

        if (hasSpecialCharacter(pw)) {
            count++;
        }

        if (count <= 1) {
            return "Weak";
        } else if (count <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}