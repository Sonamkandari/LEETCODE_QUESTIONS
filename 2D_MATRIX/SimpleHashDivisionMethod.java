import java.util.Scanner;

public class SimpleHashDivisionMethod {

    // Simple hash function using division method
    public static int simpleDivisionHash(String key, int tableSize) {
        int keySum = 0;

        // Sum ASCII values of characters in the string
        for (int i = 0; i < key.length(); i++) {
            keySum += (int) key.charAt(i);
        }

        // Apply division method
        return keySum % tableSize;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string to hash: ");
        String inputString = scanner.nextLine();

        int tableSize = 10;  // Example hash table size

        int hashIndex = simpleDivisionHash(inputString, tableSize);

        System.out.println("Hash index of '" + inputString + "' is: " + hashIndex);

        scanner.close();
    }
}
