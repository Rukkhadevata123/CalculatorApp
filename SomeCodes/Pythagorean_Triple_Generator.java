import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;

public class Pythagorean_Triple_Generator extends JFrame {
    private final JTextField inputField;
    private final JTextArea outputArea;

    public Pythagorean_Triple_Generator() {
        setTitle("Pythagorean Triple Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField(10);
        JButton generateButton = new JButton("Generate");
        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        generateButton.addActionListener(this::generatePythagoreanTriples);

        pack();
        setLocationRelativeTo(null);
    }

    private void generatePythagoreanTriples(ActionEvent e) {
        outputArea.setText("");

        int x = getIntInput();

        ArrayList<int[]> triples = new ArrayList<>();

        for (int i = 1; i < x; i++) {
            for (int u = i + 1; u <= x; u++) {
                if (gcd(i, u) == 1 && (i % 2 != u % 2)) {
                    int q = 2 * i * u;
                    int w = Math.abs(u * u - i * i);
                    int c = u * u + i * i;
                    int[] sb = { q, w, c };
                    Arrays.sort(sb); // 对每个勾股数进行排序
                    triples.add(sb);
                }
            }
        }

        Collections.sort(triples, (a, b) -> {
            if (a[0] == b[0])
                return Integer.compare(a[1], b[1]);
            return Integer.compare(a[0], b[0]);
        }); // 根据每个勾股数的第一个元素进行排序

        triples.forEach(triple -> outputArea.append(Arrays.toString(triple) + "\n"));
    }

    private int getIntInput() {
        try {
            int input = Integer.parseInt(inputField.getText());
            if (input <= 0) {
                outputArea.append("Invalid input. Please enter a positive integer.\n");
                return 0;
            }
            return input;
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter an integer.\n");
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pythagorean_Triple_Generator gui = new Pythagorean_Triple_Generator();
            gui.setVisible(true);
        });
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
