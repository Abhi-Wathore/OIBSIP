import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int totalScore = 0;
        int rounds = 5; // Set the number of rounds
        int maxAttempts = 5; // Set the maximum number of attempts per round

        System.out.println("Welcome to the Guess the Number Game!");
        System.out.println("You have " + rounds + " rounds to guess the number. Good luck!\n");

        // Loop through multiple rounds
        for (int round = 1; round <= rounds; round++) {
            int targetNumber = random.nextInt(100) + 1; // Generate random number between 1 and 100
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("Round " + round + " - Guess the number between 1 and 100:");
            
            // Limit the number of attempts
            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == targetNumber) {
                    guessedCorrectly = true;
                    int points = (maxAttempts - attempts + 1) * 10; // Points based on attempts remaining
                    totalScore += points;
                    System.out.println("Correct! You guessed the number in " + attempts + " attempts. Points earned: " + points + "\n");
                } else if (userGuess < targetNumber) {
                    System.out.println("The number is higher than " + userGuess);
                } else {
                    System.out.println("The number is lower than " + userGuess);
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The number was " + targetNumber + ".\n");
            }
        }

        // Display the final score after all rounds
        System.out.println("Game Over! Your total score is: " + totalScore);
        scanner.close();
    }
}
