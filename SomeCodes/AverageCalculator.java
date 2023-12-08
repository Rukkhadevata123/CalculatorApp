import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AverageCalculator extends JFrame implements ActionListener {
    private JTextArea inputTextArea;
    private JButton calculateButton;
    private JLabel resultLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            AverageCalculator frame = new AverageCalculator();
            frame.setTitle("Average Calculator");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.createGUI();
            frame.setVisible(true);
        });
    }

    private void createGUI() {
        JPanel inputPanel = new JPanel();
        JLabel inputLabel = new JLabel("Enter numbers (separated by spaces): ");
        inputTextArea = new JTextArea(5, 20); // 修改为更多的行和列
        JScrollPane scrollPane = new JScrollPane(inputTextArea); // 添加滚动窗格
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        inputPanel.add(inputLabel);
        inputPanel.add(scrollPane);
        inputPanel.add(calculateButton);

        JPanel resultPanel = new JPanel();
        resultLabel = new JLabel("Result will be displayed here");
        resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 14f));

        resultPanel.add(resultLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            String input = inputTextArea.getText();
            String[] numArray = input.split(" ");

            int sum = 0;
            int count = 0;
            for (String num : numArray) {
                try {
                    int value = Integer.parseInt(num);
                    sum += value;
                    count++;
                } catch (NumberFormatException ex) {
                    // Ignore if the input is not a valid number
                }
            }

            if (count > 0) {
                double average = (double) sum / count;
                resultLabel.setText(String.format("Average: %.2f", average));
            } else {
                resultLabel.setText("Invalid input! Please enter numbers.");
            }
        }
    }
}
