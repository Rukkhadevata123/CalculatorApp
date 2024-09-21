import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GuessNumberGame {
    private static final String INVALID_INTEGER_INPUT = "Invalid input, please enter an integer: ";
    private static final String INVALID_INPUT_RANGE = "Invalid input: Number should be between 1 and ";

    private Set<Integer> guessedSet;
    private Scanner in;

    public static void main(String[] args) {
        GuessNumberGame game = new GuessNumberGame();
        game.startGame();
    }

    private void startGame() {
        in = new Scanner(System.in);

        System.out.print("How many do you want to guess? ");
        int guessCount = getIntegerInput();

        int highestNumber;
        do {
            System.out.print("What is the highest number? ");
            highestNumber = getIntegerInput();
            if (highestNumber < guessCount) {
                System.out.println("The highest number should be greater than the guess count.");
            }
        } while (highestNumber < guessCount);

        guessedSet = new HashSet<>();

        List<Integer> playerGuesses = new ArrayList<>();

        for (int i = 0; i < guessCount; i++) {
            int guess = getIntInputWithValidation(highestNumber);
            playerGuesses.add(guess);
        }

        int[] computerGuesses = generateRandomNumbers(highestNumber, guessCount);
        Arrays.sort(computerGuesses);

        int matchCount = getMatchCount(playerGuesses, computerGuesses);

        String computerGuessesString = buildNumberListAsString(computerGuesses);
        String playerGuessesString = buildNumberListAsString(
                playerGuesses.stream().mapToInt(Integer::intValue).toArray());

        System.out.println("Computer's guesses: " + computerGuessesString);
        System.out.println("Your guesses: " + playerGuessesString);

        if (matchCount == 0) {
            System.out.println("Absolutely different! Bad luck!");
        } else if (matchCount == guessCount) {
            System.out.println("Absolutely the same! Pretty Good Luck!");
        } else {
            System.out.println(matchCount + " numbers the same, good luck.");
            double accuracy = (double) matchCount / guessCount * 100;
            System.out.println("Your accuracy: " + accuracy + "%");
        }

        in.close();
    }

    private int getIntegerInput() {
        while (!in.hasNextInt()) {
            System.out.print(INVALID_INTEGER_INPUT);
            in.next();
        }
        return in.nextInt();
    }

    private int getIntInputWithValidation(int highestNumber) {
        int input;
        boolean isValid;

        do {
            System.out.print("Enter your guess: ");
            input = getIntegerInput();

            if (guessedSet.contains(input)) {
                System.out.print("You have already entered that number. Please enter a different number: ");
                isValid = false;
            } else if (input < 1 || input > highestNumber) {
                System.out.println(INVALID_INPUT_RANGE + highestNumber);
                isValid = false;
            } else {
                guessedSet.add(input);
                isValid = true;
            }
        } while (!isValid);

        return input;
    }

    private int[] generateRandomNumbers(int range, int count) {
        return ThreadLocalRandom.current().ints(1, range + 1).distinct().limit(count).toArray();
    }

    private int getMatchCount(List<Integer> list1, int[] arr2) {
        int count = 0;
        for (int num : arr2) {
            count += Collections.frequency(list1, num);
        }
        return count;
    }

    private String buildNumberListAsString(int[] numbers) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
    }
}