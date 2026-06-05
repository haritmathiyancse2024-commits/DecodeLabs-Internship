import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * DecodeLabs Java Internship - Project 1
 * Number Guessing Game
 * Features: Random generation, input validation,
 *           attempt limiter, scoring, replay option
 */
public class DecodeLabs_Java_P1 {

    // --- CONSTANTS ---
    static final int MIN_NUMBER  = 1;
    static final int MAX_NUMBER  = 100;
    static final int MAX_ATTEMPTS = 7;

    // --- SHARED OBJECTS ---
    static Random  random  = new Random();
    static Scanner scanner = new Scanner(System.in);

    // =============================================
    // MAIN METHOD — controls the session
    // =============================================
    public static void main(String[] args) {

        int totalScore  = 0;
        int roundNumber = 0;
        String playAgain;

        printWelcomeBanner();

        // Outer loop — controls multiple rounds
        do {
            roundNumber++;
            totalScore += playRound(roundNumber);

            printDivider();
            System.out.print("Play again? (y/n): ");
            scanner.nextLine();
            playAgain = scanner.nextLine().trim().toLowerCase();

        } while (playAgain.equals("y"));

        printFinalSummary(roundNumber, totalScore);
        scanner.close();
    }

    // =============================================
    // METHOD: plays one complete round
    // Returns the score earned in that round
    // =============================================
    static int playRound(int roundNumber) {

        int secretNumber = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
        int attemptsUsed = 0;
        boolean win      = false;
        int roundScore   = 0;

        System.out.println("\n--- ROUND " + roundNumber + " ---");
        System.out.println("Guess a number between "
                         + MIN_NUMBER + " and " + MAX_NUMBER);
        System.out.println("You have " + MAX_ATTEMPTS + " attempts.");
        printDivider();

        // Inner loop — controls guesses within one round
        while (attemptsUsed < MAX_ATTEMPTS && !win) {

            System.out.print("Attempt " + (attemptsUsed + 1)
                           + "/" + MAX_ATTEMPTS + " - Enter guess: ");

            try {
                int userGuess = scanner.nextInt();
                attemptsUsed++;

                if (userGuess == secretNumber) {
                    roundScore = (MAX_ATTEMPTS - attemptsUsed + 1) * 10;
                    printDivider();
                    System.out.println("CORRECT! Well done!");
                    System.out.println("Attempts used : " + attemptsUsed);
                    System.out.println("Round score   : " + roundScore);
                    win = true;

                } else if (userGuess > secretNumber) {
                    System.out.println("Too HIGH! Try lower.");

                } else {
                    System.out.println("Too LOW! Try higher.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid! Numbers only.");
                scanner.nextLine();
            }
        }

        // If player didn't win
        if (!win) {
            printDivider();
            System.out.println("Out of attempts!");
            System.out.println("The number was : " + secretNumber);
            System.out.println("Round score    : 0");
        }

        return roundScore;
    }

    // =============================================
    // METHOD: prints welcome banner
    // =============================================
    static void printWelcomeBanner() {
        System.out.println("=================================");
        System.out.println("  DECODELABS NUMBER GAME v1.0   ");
        System.out.println("=================================");
        System.out.println("  Batch 2026 | Java Project 1   ");
        System.out.println("=================================");
    }

    // =============================================
    // METHOD: prints final game summary
    // =============================================
    static void printFinalSummary(int rounds, int score) {
        System.out.println("\n=================================");
        System.out.println("          GAME SUMMARY          ");
        System.out.println("=================================");
        System.out.println("Rounds played  : " + rounds);
        System.out.println("Final score    : " + score);

        if (score >= 50) {
            System.out.println("Performance    : EXCELLENT!");
        } else if (score >= 30) {
            System.out.println("Performance    : GOOD WORK!");
        } else {
            System.out.println("Performance    : KEEP PRACTISING!");
        }

        System.out.println("=================================");
        System.out.println(" Thanks for playing DecodeLabs! ");
        System.out.println("=================================");
    }

    // =============================================
    // METHOD: prints a divider line
    // =============================================
    static void printDivider() {
        System.out.println("---------------------------------");
    }
}
