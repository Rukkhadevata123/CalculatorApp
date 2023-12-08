import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class CalculatorApp extends JFrame {
    private JTextArea inputTextArea;
    private JButton averageCalculatorButton;
    private JButton modeCalculatorButton;
    private JButton standardDeviationCalculatorButton;
    private JButton sumCalculatorButton; // 求和按钮
    private JButton varianceCalculatorButton; // 求方差按钮
    private JLabel resultLabel;

    public CalculatorApp() {
        setTitle("计算器");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10)); // 将按钮面板改为一行五列
        averageCalculatorButton = createButton("平均数计算器", this::calculateAverage);
        buttonPanel.add(averageCalculatorButton);

        modeCalculatorButton = createButton("众数计算器", this::calculateMode);
        buttonPanel.add(modeCalculatorButton);

        standardDeviationCalculatorButton = createButton("标准差计算器", this::calculateStandardDeviation);
        buttonPanel.add(standardDeviationCalculatorButton);

        sumCalculatorButton = createButton("求和", this::calculateSum); // 新增求和按钮
        buttonPanel.add(sumCalculatorButton);

        varianceCalculatorButton = createButton("求方差", this::calculateVariance); // 新增求方差按钮
        buttonPanel.add(varianceCalculatorButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        resultLabel = new JLabel("结果将显示在这里");
        contentPane.add(resultLabel, BorderLayout.NORTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    private void calculateAverage(ActionEvent e) {
        String input = inputTextArea.getText();
        double average = calculateAverage(input);
        if (!Double.isNaN(average)) {
            resultLabel.setText("平均数: " + average);
        } else {
            resultLabel.setText("输入无效，请输入数字！");
        }
    }

    private void calculateMode(ActionEvent e) {
        String input = inputTextArea.getText();
        java.util.List<Integer> modes = calculateMode(input);
        if (!modes.isEmpty()) {
            resultLabel.setText("众数: " + modes.stream().map(Object::toString).collect(Collectors.joining(", ")));
        } else {
            resultLabel.setText("输入无效，请输入数字！");
        }
    }

    private void calculateStandardDeviation(ActionEvent e) {
        String input = inputTextArea.getText();
        double standardDeviation = calculateStandardDeviation(input);
        if (!Double.isNaN(standardDeviation)) {
            resultLabel.setText("标准差: " + standardDeviation);
        } else {
            resultLabel.setText("输入无效，请输入数字！");
        }
    }

    private void calculateSum(ActionEvent e) { // 新增求和的方法
        String input = inputTextArea.getText();
        double sum = calculateSum(input);
        if (!Double.isNaN(sum)) {
            resultLabel.setText("求和: " + sum);
        } else {
            resultLabel.setText("输入无效，请输入数字！");
        }
    }

    private void calculateVariance(ActionEvent e) { // 新增求方差的方法
        String input = inputTextArea.getText();
        double variance = calculateVariance(input);
        if (!Double.isNaN(variance)) {
            resultLabel.setText("方差: " + variance);
        } else {
            resultLabel.setText("输入无效，请输入数字！");
        }
    }

    private double calculateAverage(String input) {
        try {
            double[] numbers = parseInputNumbers(input);
            double sum = Arrays.stream(numbers).sum();
            return sum / numbers.length;
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private java.util.List<Integer> calculateMode(String input) {
        try {
            int[] numbers = parseInputIntegers(input);
            Map<Integer, Integer> frequencyMap = new HashMap<>();
            int maxFrequency = 0;

            for (int number : numbers) {
                int frequency = frequencyMap.getOrDefault(number, 0) + 1;
                frequencyMap.put(number, frequency);
                maxFrequency = Math.max(maxFrequency, frequency);
            }

            java.util.List<Integer> modes = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() == maxFrequency) {
                    modes.add(entry.getKey());
                }
            }

            return modes;
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
    }

    private double calculateStandardDeviation(String input) {
        try {
            double[] numbers = parseInputNumbers(input);
            double sum = Arrays.stream(numbers).sum();
            double mean = sum / numbers.length;
            double squaredDiffSum = Arrays.stream(numbers)
                    .map(x -> Math.pow(x - mean, 2))
                    .sum();
            double variance = squaredDiffSum / numbers.length;
            return Math.sqrt(variance);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private double calculateSum(String input) { // 新增求和的方法
        try {
            double[] numbers = parseInputNumbers(input);
            return Arrays.stream(numbers).sum();
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private double calculateVariance(String input) { // 新增求方差的方法
        try {
            double[] numbers = parseInputNumbers(input);
            double sum = Arrays.stream(numbers).sum();
            double mean = sum / numbers.length;
            double squaredDiffSum = Arrays.stream(numbers)
                    .map(x -> Math.pow(x - mean, 2))
                    .sum();
            return squaredDiffSum / numbers.length;
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private double[] parseInputNumbers(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    private int[] parseInputIntegers(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorApp::new);
    }
}
