import java.util.Scanner;

public class InputValidator {

    public static int getIntInput(Scanner scanner, String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 0 && value <= 7) {
                    return value;
                } else {
                    System.out.println("Invalid range. The number must be between 0 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int option = getIntInput(scanner, "1. Add new task\n2. Update a task\n3. Delete a task\n4. See all tasks\n5. See to do tasks\n6. See in-progress tasks\n7. See done tasks\n0. Exit\n");
        System.out.println("You entered: " + option);
    }
}

