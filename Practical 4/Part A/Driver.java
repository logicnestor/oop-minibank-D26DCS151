public class Driver {

    public static void main(String[] args) {

        String[] passwords = {
            "abc",
            "abcdefgh",
            "Abcdefgh",
            "abcd1234",
            "Abcd1234!",
            "Password1"
        };

        for (String password : passwords) {

            System.out.println("Password: " + password);

            System.out.println(
                "Length >= 8: "
                + PasswordChecker.hasMinimumLength(password)
            );

            System.out.println(
                "Uppercase: "
                + PasswordChecker.hasUppercase(password)
            );

            System.out.println(
                "Digit: "
                + PasswordChecker.hasDigit(password)
            );

            System.out.println(
                "Special Character: "
                + PasswordChecker.hasSpecialCharacter(password)
            );

            System.out.println(
                "Strength: "
                + PasswordChecker.strength(password)
            );

            System.out.println("-----------------------------");
        }
    }
}