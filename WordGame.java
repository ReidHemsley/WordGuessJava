import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGame {
    
    private static final String WORD_BANK_FILE_PATH = "WordGuessJava\\wordbank.txt";
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_WORD_LENGTH = 5;

    public static void main(String[] args) {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);

        while (playAgain) {
            // Generate a random word
            String secretWord = generateRandomWord();

            int attempts = 0;
            boolean guessedCorrectly = false;

            while (!guessedCorrectly && attempts < MAX_ATTEMPTS) {
                System.out.println("Enter your guess (5 letters): ");
                String guess = scanner.nextLine().toUpperCase();

                // Check if the guessed word length is valid
                if (guess.length() != secretWord.length()) {
                    System.out.println("Your guess must be exactly " + secretWord.length() + " letters long. Try again.");
                    continue;
                }

                // Check if the guessed word exceeds maximum length
                if (guess.length() > MAX_WORD_LENGTH) {
                    System.out.println("Your guess is too long. Try again.");
                    continue;
                }

                attempts++;

                // Display the user's guess in all caps, one letter at a time
                for (int i = 0; i < guess.length(); i++) {
                    System.out.print(guess.charAt(i) + " ");
                }
                System.out.println();

                // Display the arrows and correctness indicators
                for (int i = 0; i < secretWord.length(); i++) {
                    System.out.print("^ ");
                }
                System.out.println();

                // Track which letters have been matched
                boolean[] matched = new boolean[secretWord.length()];

                for (int i = 0; i < secretWord.length(); i++) {
                    char guessedChar = guess.charAt(i);
                    char secretChar = Character.toUpperCase(secretWord.charAt(i));
                
                    if (guessedChar == secretChar) {
                        System.out.print("O ");
                        matched[i] = true;
                    } else if (secretWord.toUpperCase().indexOf(guessedChar) != -1) {
                        System.out.print("~ ");
                    } else {
                        System.out.print("X ");
                    }
                }
                System.out.println();

                // Check if the guess is correct
                if (guess.equalsIgnoreCase(secretWord)) {
                    System.out.println("Congratulations! You guessed the word correctly: " + secretWord);
                    guessedCorrectly = true;
                } else {
                    System.out.println("Incorrect guess.");
                    System.out.println("Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you have run out of attempts. The word was: " + secretWord);
            }

            System.out.println("Do you want to play again? (Y/N)");
            String playAgainInput = scanner.nextLine().toUpperCase();
            if (!playAgainInput.equals("Y")) {
                playAgain = false;
            }
        }

        scanner.close();
    }

    private static String generateRandomWord() {
        List<String> wordBank = readWordBankFromFile();
        Random random = new Random();
        int randomIndex = random.nextInt(wordBank.size());
        return wordBank.get(randomIndex);
    }

    private static List<String> readWordBankFromFile() {
        List<String> wordBank = new ArrayList<>();
        try {
            File file = new File(WORD_BANK_FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                if (word.length() == MAX_WORD_LENGTH) {
                    wordBank.add(word);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Word bank file not found.");
        }
        return wordBank;
    }
}
