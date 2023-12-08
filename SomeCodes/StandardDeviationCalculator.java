import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class StandardDeviationCalculator extends JFrame {
    private JTextArea inputTextArea;
    private JButton calculateButton;
    private JLabel resultLabel;

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

        calculateButton = new JButton("计算标准差");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                double standardDeviation = calculateStandardDeviation(input);
                if (!Double.isNaN(standardDeviation)) {
                    resultLabel.setText("标准差: " + standardDeviation);
                } else {
                    resultLabel.setText("输入无效，请输入数字！");
                }
            }
        });
        contentPane.add(calculateButton, BorderLayout.SOUTH);

        resultLabel = new JLabel("结果将显示在这里");
        contentPane.add(resultLabel, BorderLayout.NORTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    private double calculateStandardDeviation(String input) {
        try {
            double[] numbers = Arrays.stream(input.trim().split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StandardDeviationCalculator::new);
    }
}
