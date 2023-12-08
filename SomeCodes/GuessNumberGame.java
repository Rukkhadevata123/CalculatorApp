import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import javax.swing.*;

public class GuessNumberGame extends JFrame {
    private static final String INVALID_INPUT_RANGE = "Invalid input: Number should be between 1 and ";

    private Set<Integer> guessedSet; // 记录已经猜过的数字的集合

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessNumberGame game = new GuessNumberGame();
            game.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        setTitle("Guess Number Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel guessCountLabel = new JLabel("How many do you want to guess?");
        JTextField guessCountField = new JTextField();

        JLabel highestNumberLabel = new JLabel("What is the highest number?");
        JTextField highestNumberField = new JTextField();

        JButton startButton = new JButton("Start Game");

        startButton.addActionListener(e -> {
            int guessCount;
            int highestNumber;

            try {
                guessCount = Integer.parseInt(guessCountField.getText());
                highestNumber = Integer.parseInt(highestNumberField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
                return;
            }

            startGame(guessCount, highestNumber);
        });

        panel.add(guessCountLabel);
        panel.add(guessCountField);
        panel.add(highestNumberLabel);
        panel.add(highestNumberField);
        panel.add(startButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame(int guessCount, int highestNumber) {
        guessedSet = new HashSet<>();

        List<Integer> playerGuesses = new ArrayList<>();
        int[] guessedNumbers = new int[highestNumber + 1];

        for (int i = 0; i < guessCount; i++) {
            int guess = getIntInputWithValidation(highestNumber);
            playerGuesses.add(guess);
            guessedNumbers[guess]++;
        }

        if (hasDuplicate(playerGuesses)) {
            JOptionPane.showMessageDialog(this, "You are not allowed to enter the same numbers!");
            playerGuesses.clear();
        }

        int[] computerGuesses = generateRandomNumbers(highestNumber, guessCount);
        Arrays.sort(computerGuesses);

        int matchCount = getMatchCount(playerGuesses, computerGuesses);

        String computerGuessesString = buildNumberListAsString(computerGuesses);
        String playerGuessesString = buildNumberListAsString(playerGuesses.stream().mapToInt(Integer::intValue).toArray());

        String message;

        if (matchCount == 0) {
            message = "Absolutely different! Bad luck!";
        } else if (matchCount == guessCount) {
            message = "Absolutely the same! Pretty Good Luck!";
        } else {
            double accuracy = (double) matchCount / guessCount * 100;
            message = matchCount + " numbers the same, good luck."
                    + "\nYour accuracy: " + accuracy + "%";
        }

        JOptionPane.showMessageDialog(this,
                "Computer's guesses: " + computerGuessesString
                        + "\nYour guesses: " + playerGuessesString
                        + "\n" + message);
    }

    private int getIntInputWithValidation(int highestNumber) {
        int input=1;
        boolean isValid;

        do {
            String inputString = JOptionPane.showInputDialog(this, "Enter your guess:");

            try {
                input = Integer.parseInt(inputString);

                if (input < 1 || input > highestNumber) {
                    JOptionPane.showMessageDialog(this, INVALID_INPUT_RANGE + highestNumber);
                    isValid = false;
                } else if (guessedSet.contains(input)) {
                    JOptionPane.showMessageDialog(this, "You have already entered that number. Please enter a different number.");
                    isValid = false;
                } else {
                    guessedSet.add(input);
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer.");
                isValid = false;
            }
        } while (!isValid);

        return input;
    }

    private boolean hasDuplicate(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        return set.size() != list.size();
    }

    private int[] generateRandomNumbers(int range, int count) {
        List<Integer> numbers = new ArrayList<>(range);
        for (int i = 1; i <= range; i++) {
            numbers.add(i);
        }

        Set<Integer> randomSet = new HashSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (randomSet.size() < count) {
            int rand = random.nextInt(1, range + 1);
            randomSet.add(rand);
        }

        return randomSet.stream().mapToInt(Integer::intValue).toArray();
    }

    private int getMatchCount(List<Integer> list1, int[] arr2) {
        Set<Integer> set2 = Arrays.stream(arr2).boxed().collect(Collectors.toSet());
        List<Integer> intersection = list1.stream()
                .filter(set2::contains)
                .collect(Collectors.toList());
        return intersection.size();
    }

    private String buildNumberListAsString(int[] numbers) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
    }
}
