import java.util.Scanner;
import java.util.InputMismatchException;

// Custom exception
class DivideByZeroException extends Exception {

    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    // Performs the calculation
    static double calculate(double a, double b, char operator)
            throws DivideByZeroException {

        switch (operator) {
            case '+':
                return a + b;

            case '-':
                return a - b;

            case '*':
                return a * b;

            case '/':
                if (b == 0) {
                    throw new DivideByZeroException(
                        "Cannot divide by zero."
                    );
                }
                return a / b;

            default:
                throw new IllegalArgumentException(
                    "Invalid operator. Use +, -, *, or /."
                );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempt = 0;

        while (!success) {

            attempt++;
            System.out.println("\nAttempt " + attempt);

            try {
                System.out.print("Enter first number: ");
                double a = Double.parseDouble(sc.nextLine().trim());

                System.out.print("Enter operator (+, -, *, /): ");
                String operatorInput = sc.nextLine().trim();

                if (operatorInput.length() != 1) {
                    throw new IllegalArgumentException(
                        "Enter exactly one operator."
                    );
                }

                char operator = operatorInput.charAt(0);

                System.out.print("Enter second number: ");
                double b = Double.parseDouble(sc.nextLine().trim());

                double result = calculate(a, b, operator);

                System.out.println("Result: " + result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid number. Please enter numeric values."
                );

            } catch (DivideByZeroException e) {
                System.out.println("Calculation error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());

            } finally {
                System.out.println(
                    "[LOG] Attempt " + attempt + " completed."
                );
            }
        }

        sc.close();
        System.out.println("Calculator finished.");
    }
}