/*import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidationExample {

    /**
     * Main method to demonstrate input validation, ensuring the user enters
     * a valid byte within the required range (0 to 5).

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        byte number = 0;
        final byte MIN_VALUE = 0;
        final byte MAX_VALUE = 5;

        System.out.println("--- Byte Input Validator (Range: " + MIN_VALUE + " to " + MAX_VALUE + ") ---");

        while (!validInput) {
            System.out.print("Please enter a whole number between " + MIN_VALUE + " and " + MAX_VALUE + ": ");
            try {
                number = scanner.nextByte();

                if (number >= MIN_VALUE && number <= MAX_VALUE) {
                    validInput = true;
                } else {
                    System.out.println("Invalid range. The number must be between " + MIN_VALUE + " and " + MAX_VALUE + ".");
                }

            } catch (InputMismatchException e) {

                System.out.println("Invalid input. Please enter a valid whole number (byte type).");
                scanner.next(); // Consume the invalid input to prevent an infinite loop
            }
        }

        System.out.println("\nSUCCESS: You entered a valid number: " + number);
        scanner.close();
    }
}

*/