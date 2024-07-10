import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileEncryptorDecryptor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Would you like to encrypt or decrypt a file? ");
        String mode = scanner.nextLine().trim().toLowerCase();

        if (!mode.equals("encrypt") && !mode.equals("decrypt")) {
            System.out.println("Invalid choice. Please choose 'encrypt' or 'decrypt'.");
            return;
        }

        System.out.print("Enter the file name or path: ");
        String filePath = scanner.nextLine().trim();

        System.out.print("Enter the shift value (integer): ");
        int shift;
        try {
            shift = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid shift value. Please enter an integer.");
            return;
        }

        if (mode.equals("decrypt")) {
            shift = -shift;
        }

        processFile(filePath, shift, mode);
    }

    private static void processFile(String filePath, int shift, String mode) {
        String outputFilePath = filePath.substring(0, filePath.lastIndexOf('.')) + "_" + mode + "ed" + filePath.substring(filePath.lastIndexOf('.'));
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(caesarCipher(line, shift));
                writer.newLine();
            }

            System.out.println("File has been " + mode + "ed and saved as " + outputFilePath);

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isUpperCase(character)) {
                result.append((char) ((character + shift - 'A') % 26 + 'A'));
            } else if (Character.isLowerCase(character)) {
                result.append((char) ((character + shift - 'a') % 26 + 'a'));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}

