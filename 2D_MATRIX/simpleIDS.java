import java.util.Scanner;

public class simpleIDS{

    public static void main(String[] args) {
        final String correctPassword = "SecurePass123";  // Predefined correct password
        final int maxAttempts = 5;

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Intrusion Detection System ===");

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.print("Attempt " + attempt + "/" + maxAttempts + ": Enter password: ");
            String userInput = scanner.nextLine();

            if (userInput.equals(correctPassword)) {
                System.out.println("Access Granted");
                scanner.close();
                return;  // Stop further attempts
            } else {
                System.out.println("Incorrect password.");
            }
        }

        // If the loop completes without correct password
        System.out.println("Intrusion Detected! Too many failed attempts.");

        scanner.close();
    }
}
