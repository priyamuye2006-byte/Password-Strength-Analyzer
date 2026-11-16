
import java.util.*;
import java.security.SecureRandom;

public class PasswordStrengthAnalyzer {

    static Scanner sc = new Scanner(System.in);
    static SecureRandom random = new SecureRandom();

    static Set<String> commonPasswords = new HashSet<>(
        Arrays.asList(
            "password", "123456", "12345678",
            "qwerty", "admin", "password123",
            "abc123", "iloveyou"
        )
    );

    static void analyzePassword(String password) {

        int score = 0;
        ArrayList<String> suggestions = new ArrayList<>();
        if (password.length() >= 12) {
            score += 30;
            System.out.println("Length: Passed");
        } else if (password.length() >= 8) {
            score += 20;
            System.out.println("Length: Acceptable");
        } else {
            suggestions.add("Use at least 12 characters.");
            System.out.println("Length: Failed");
        }

        if (password.matches(".*[A-Z].*")) {
            score += 10;
            System.out.println("Uppercase: Passed");
        } else {
            suggestions.add("Add uppercase letters.");
            System.out.println("Uppercase: Failed");
        }

        if (password.matches(".*[a-z].*")) {
            score += 10;
            System.out.println("Lowercase: Passed");
        } else {
            suggestions.add("Add lowercase letters.");
            System.out.println("Lowercase: Failed");
        }

        if (password.matches(".*[0-9].*")) {
            score += 10;
            System.out.println("Numbers: Passed");
        } else {
            suggestions.add("Include numbers.");
            System.out.println("Numbers: Failed");
        }

        if (password.matches(".*[^a-zA-Z0-9].*")) {
            score += 10;
            System.out.println("Special Characters: Passed");
        } else {
            suggestions.add("Include special characters like @, #, !");
            System.out.println("Special Characters: Failed");
        }

        Set<Character> unique = new HashSet<>();

        for (char c : password.toCharArray()) {
            unique.add(c);
        }

        if (!password.isEmpty() &&
            (double) unique.size() / password.length() >= 0.6) {
            score += 10;
            System.out.println("Uniqueness: Passed");
        } else {
            suggestions.add("Avoid repeating characters.");
            System.out.println("Uniqueness: Failed");
        }

        if (!commonPasswords.contains(password.toLowerCase())) {
            score += 10;
            System.out.println("Common Password Check: Passed");
        } else {
            suggestions.add("Avoid commonly used passwords.");
            System.out.println("Common Password Check: Failed");
        }

        String lower = password.toLowerCase();

        if (!lower.contains("1234") &&
            !lower.contains("abcd") &&
            !lower.contains("qwerty") &&
            !lower.contains("9876")) {

            score += 10;
            System.out.println("Pattern Check: Passed");

        } else {
            suggestions.add("Avoid predictable sequences.");
            System.out.println("Pattern Check: Failed");
        }
        System.out.println("\n----- PASSWORD RESULT -----");
        System.out.println("Score: " + score + "/100");

        if (score >= 80) {
            System.out.println("Strength: VERY STRONG");
        } else if (score >= 60) {
            System.out.println("Strength: STRONG");
        } else if (score >= 40) {
            System.out.println("Strength: MEDIUM");
        } else {
            System.out.println("Strength: WEAK");
        }
        System.out.println("\n----- SUGGESTIONS -----");

        if (suggestions.isEmpty()) {
            System.out.println("Excellent! Your password passed all checks.");
        } else {
            for (String suggestion : suggestions) {
                System.out.println("- " + suggestion);
            }
        }

        if (score < 80) {
            System.out.println("\nSuggested Strong Password:");
            System.out.println(generatePassword());
        }
    }

    static String generatePassword() {

        String characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789@#$%&*!";

        String password;

        do {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 16; i++) {
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }

            password = sb.toString();

        } while (!password.matches(".*[A-Z].*") ||
                 !password.matches(".*[a-z].*") ||
                 !password.matches(".*[0-9].*") ||
                 !password.matches(".*[@#$%&*!].*"));

        return password;
    }

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("    PASSWORD STRENGTH ANALYZER");
        System.out.println("==================================");

        System.out.print("Enter your password: ");

        String password = sc.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
        } else {
            analyzePassword(password);
        }

        sc.close();
    }
}
