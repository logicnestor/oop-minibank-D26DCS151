import java.util.Random;
import java.util.Scanner;

public class RPSLS {

    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    // Returns:
    //  1  -> a beats b
    // -1  -> b beats a
    //  0  -> tie
    static int winner(Move a, Move b) {

        if (a == b) {
            return 0;
        }

        return switch (a) {
            case ROCK -> (b == Move.LIZARD || b == Move.SCISSORS) ? 1 : -1;

            case PAPER -> (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;

            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;

            case LIZARD -> (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;

            case SPOCK -> (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        System.out.println("=== Rock-Paper-Scissors-Lizard-Spock ===");
        System.out.println("Enter one of: ROCK, PAPER, SCISSORS, LIZARD, SPOCK");

        for (int round = 1; round <= 5; round++) {

            System.out.println("\nRound " + round);

            System.out.print("Your move: ");
            String input = sc.nextLine().trim().toUpperCase();

            Move playerMove;

            try {
                playerMove = Move.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move! Please enter a valid move.");
                round--;
                continue;
            }

            Move computerMove =
                    Move.values()[random.nextInt(Move.values().length)];

            int result = winner(playerMove, computerMove);

            System.out.println("Your move     : " + playerMove);
            System.out.println("Computer move : " + computerMove);

            if (result == 1) {
                System.out.println("Round winner: You");
                playerScore++;
            } 
            else if (result == -1) {
                System.out.println("Round winner: Computer");
                computerScore++;
            } 
            else {
                System.out.println("Round result: Tie");
            }

            System.out.println("Score: You " + playerScore
                    + " - " + computerScore + " Computer");
        }

        System.out.println("\n=== Final Result ===");

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "-"
                    + computerScore);
        } 
        else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "-"
                    + playerScore);
        } 
        else {
            System.out.println("It's a tie " + playerScore + "-"
                    + computerScore);
        }

        sc.close();
    }
}