import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class SumCalculator extends JFrame {
    private JTextArea inputTextArea;
    private JButton calculateButton;
    private JLabel resultLabel;

    public SumCalculator() {
        setTitle("求和计算器");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        calculateButton = new JButton("计算求和");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                double sum = calculateSum(input);
                resultLabel.setText("求和结果: " + sum);
            }
        });
        contentPane.add(calculateButton, BorderLayout.SOUTH);

        resultLabel = new JLabel("结果将显示在这里");
        contentPane.add(resultLabel, BorderLayout.NORTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    private double calculateSum(String input) {
        try {
            double[] numbers = Arrays.stream(input.trim().split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            double sum = 0;
            for (double number : numbers) {
                sum += number;
            }

            return sum;
        } catch (NumberFormatException e) {
            return 0; // 输入无效，返回 0
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SumCalculator::new);
    }
}
