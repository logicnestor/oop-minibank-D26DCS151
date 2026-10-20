public class MiniBank {

    public static void main(String[] args) {

        System.out.println("===== VALIDATOR TESTING =====");

        // Mobile
        System.out.println(
                "Valid mobile  : "
                        + Validator.isValidMobile("9876543210"));

        System.out.println(
                "Invalid mobile: "
                        + Validator.isValidMobile("1234567890"));

        // Email
        System.out.println(
                "Valid email   : "
                        + Validator.isValidEmail("rahul@gmail.com"));

        System.out.println(
                "Invalid email : "
                        + Validator.isValidEmail("rahul@gmail"));

        // PAN
        System.out.println(
                "Valid PAN     : "
                        + Validator.isValidPan("ABCDE1234F"));

        System.out.println(
                "Invalid PAN   : "
                        + Validator.isValidPan("ABC123"));

        // IFSC
        System.out.println(
                "Valid IFSC    : "
                        + Validator.isValidIfsc("SBIN0001234"));

        System.out.println(
                "Invalid IFSC  : "
                        + Validator.isValidIfsc("SBIN123"));

        System.out.println("\n===== COMMAND PARSER =====");

        String input = "DEPOSIT AC0001 500";

        Command command = CommandParser.parse(input);

        System.out.println("Type          : " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount        : " + command.amount());

        System.out.println("\n===== STATEMENT =====");

        Account account = new Account("Rahul", 5000);

        System.out.println(
                StatementFormatter.buildStatement(account));
    }
}