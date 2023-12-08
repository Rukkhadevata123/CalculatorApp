import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Test2GUI extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;

    public Test2GUI() {
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

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePythagoreanTriples();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void generatePythagoreanTriples() {
        outputArea.setText("");

        int x = getIntInput(inputField);

        ArrayList<int[]> triples = new ArrayList<>();

        for (int i = 1; i <= x; i += 2) {
            for (int u = i + 1; u <= x + 1; u += 2) {
                if (gcd(i, u) == 1) {
                    int q = 2 * i * u;
                    int w = Math.abs(u * u - i * i);
                    int e = u * u + i * i;
                    int[] sb = { q, w, e };
                    triples.add(sb);
                }
            }
        }

        Collections.sort(triples, (a, b) -> Integer.compare(a[0], b[0]));

        for (int[] triple : triples) {
            outputArea.append(Arrays.toString(triple) + "\n");
        }
    }

    private int getIntInput(JTextField textField) {
        try {
            int input = Integer.parseInt(textField.getText());
            if (input > 0) {
                return input;
            } else {
                outputArea.append("Invalid input. Please enter a positive integer.\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter an integer.\n");
        }
        return 0;
    }

    public static void main(String[] args) {
        Test2GUI gui = new Test2GUI();
        gui.setVisible(true);
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
