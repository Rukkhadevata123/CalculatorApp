import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GuessNumberGame {
    private static final String INVALID_INTEGER_INPUT = "Invalid input, please enter an integer: ";
    private static final String INVALID_INPUT_RANGE = "Invalid input: Number should be between 1 and ";

    private Set<Integer> guessedSet; // 记录已经猜过的数字的集合
    private Scanner in; // 将Scanner作为一个类的字段

    public static void main(String[] args) {
        GuessNumberGame game = new GuessNumberGame();
        game.startGame();
    }

    private void startGame() {
        in = new Scanner(System.in); // 初始化Scanner

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
            int expectedNumberOfCorrectGuesses = (int) Math.pow(guessCount, 2) / highestNumber; 
            System.out.println("Expected number of correct guesses: " + expectedNumberOfCorrectGuesses);
            double accuracy = (double) matchCount / guessCount * 100;
            System.out.printf("Your accuracy: %.2f%%%n", accuracy);
            double expectedAccuracy = (double) guessCount / highestNumber * 100; 
            System.out.printf("Expected accuracy: %.2f%%%n", expectedAccuracy); 
        }

        in.close(); // 关闭Scanner
    }

    private int getIntegerInput() {
        while (!in.hasNextInt()) {
            System.out.print(INVALID_INTEGER_INPUT);
            in.next(); // Discard invalid input
        }
        return in.nextInt();
    }

    private int getIntInputWithValidation(int highestNumber) {
        int input;
        boolean isValid;

        do {
            System.out.print("Enter your guess: ");
            input = getIntegerInput(); // 使用getIntegerInput方法来获取整数输入

            if (input < 1 || input > highestNumber) {
                System.out.println(INVALID_INPUT_RANGE + highestNumber);
                isValid = false;
            } else if (guessedSet.contains(input)) {
                System.out.print("You have already entered that number. Please enter a different number: ");
                isValid = false;
            } else {
                guessedSet.add(input);
                isValid = true;
            }
        } while (!isValid);

        return input;
    }

    private int[] generateRandomNumbers(int range, int count) {
        Set<Integer> randomSet = new HashSet<>();
        while (randomSet.size() < count) {
            int rand = ThreadLocalRandom.current().nextInt(1, range + 1);
            randomSet.add(rand);
        }

        return randomSet.stream().mapToInt(Integer::intValue).toArray();
    }

    private int getMatchCount(List<Integer> list1, int[] arr2) {
        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = Arrays.stream(arr2).boxed().collect(Collectors.toSet());
        set1.retainAll(set2); // 使用retainAll方法来计算交集
        return set1.size();
    }

    private String buildNumberListAsString(int[] numbers) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
    }
}