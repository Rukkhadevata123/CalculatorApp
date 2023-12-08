import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class ModeCalculator extends JFrame {
    private JTextArea inputTextArea;
    private JButton calculateButton;
    private JLabel resultLabel;

    public ModeCalculator() {
        setTitle("众数计算器");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        calculateButton = new JButton("计算众数");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                java.util.List<Integer> modes = calculateMode(input);
                if (!modes.isEmpty()) {
                    resultLabel.setText("众数: " + modes.stream().map(Object::toString).collect(Collectors.joining(", ")));
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

    private java.util.List<Integer> calculateMode(String input) {
        try {
            int[] numbers = Arrays.stream(input.trim().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ModeCalculator::new);
    }
}
