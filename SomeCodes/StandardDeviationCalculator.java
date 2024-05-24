import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class StandardDeviationCalculator extends JFrame {
    private final JTextArea inputTextArea;
    private final JLabel resultLabel;

    public StandardDeviationCalculator() {
        setTitle("标准差计算器");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton calculateButton = getjButton();
        contentPane.add(calculateButton, BorderLayout.SOUTH);

        resultLabel = new JLabel("结果将显示在这里");
        contentPane.add(resultLabel, BorderLayout.NORTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    private JButton getjButton() {
        JButton calculateButton = new JButton("计算标准差");
        calculateButton.addActionListener(e -> {
            String input = inputTextArea.getText();
            double standardDeviation = calculateStandardDeviation(input);
            if (!Double.isNaN(standardDeviation)) {
                resultLabel.setText("标准差: " + standardDeviation);
            } else {
                resultLabel.setText("输入无效，请输入数字！");
            }
        });
        return calculateButton;
    }

    private double calculateStandardDeviation(String input) {
        try {
            double[] numbers = Arrays.stream(input.trim().split("\\s+")).mapToDouble(Double::parseDouble).toArray();

            double sum = Arrays.stream(numbers).sum();
            double mean = sum / numbers.length;

            double squaredDiffSum = Arrays.stream(numbers).map(x -> Math.pow(x - mean, 2)).sum();

            double variance = squaredDiffSum / numbers.length;
            return Math.sqrt(variance);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StandardDeviationCalculator::new);
    }
}
