import java.util.List;

public class Driver {

    public static void main(String[] args) {

        SignupForm validForm =
                new SignupForm(
                    "riya",
                    "riya@gmail.com",
                    "9876543210"
                );

        SignupForm invalidForm =
                new SignupForm(
                    "",
                    "thisisanextremelylongemailaddress@example.com",
                    "12345678901234567890"
                );

        System.out.println("Valid Form:");
        List<String> errors1 = Validator.validate(validForm);

        if (errors1.isEmpty()) {
            System.out.println("No validation errors.");
        } else {
            for (String error : errors1) {
                System.out.println(error);
            }
        }

        System.out.println("\nInvalid Form:");
        List<String> errors2 = Validator.validate(invalidForm);

        if (errors2.isEmpty()) {
            System.out.println("No validation errors.");
        } else {
            for (String error : errors2) {
                System.out.println(error);
            }
        }
    }
}