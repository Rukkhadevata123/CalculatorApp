package EightQueens;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EightQueens extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<ArrayList<Queen>> solutions;
    private int currentSolutionIndex;

    public EightQueens() {
        solutions = new ArrayList<>();
        solve(0, new Queen[8]);
        currentSolutionIndex = 0;
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Eight Queens");
        JPanel panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if ((row + col) % 2 == 0) {  // 判断行列之和的奇偶性
                            g.setColor(Color.WHITE);
                        } else {
                            g.setColor(Color.LIGHT_GRAY);
                        }
                        g.fillRect(col * getWidth() / 8, row * getHeight() / 8, getWidth() / 8, getHeight() / 8);
                    }
                }

                if (!solutions.isEmpty()) {
                    ArrayList<Queen> currentSolution = solutions.get(currentSolutionIndex);
                    for (Queen queen : currentSolution) {
                        int qCol = queen.getColumn();
                        int qRow = queen.getRow();
                        g.setColor(Color.RED);
                        g.fillOval(qCol * getWidth() / 8, qRow * getHeight() / 8, getWidth() / 8, getHeight() / 8);
                    }
                }
            }

        };
        JComboBox<String> comboBox1 = new JComboBox<>();
        for (int i = 1; i <= solutions.size(); i++) {
            comboBox1.addItem("Solution " + i);
        }
        comboBox1.addActionListener(event -> {
            JComboBox<?> comboBox = (JComboBox<?>) event.getSource();
            currentSolutionIndex = comboBox.getSelectedIndex();
            panel.repaint();
        });
        add(comboBox1, "North");
        add(panel);
        setVisible(true);
    }

    private void solve(int currentRow, Queen[] queens) {
        if (currentRow == 8) {
            ArrayList<Queen> solution = new ArrayList<>(Arrays.asList(queens));
            solutions.add(solution);
        } else {
            for (int i = 0; i < 8; i++) {
                boolean valid = true;
                for (int j = 0; j < currentRow; j++) {
                    if (queens[j].getColumn() == i || queens[j].getRow() - queens[j].getColumn() == currentRow - i || queens[j].getRow() + queens[j].getColumn() == currentRow + i) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    queens[currentRow] = new Queen(currentRow, i);
                    solve(currentRow + 1, queens);
                }
            }
        }
    }

    public static void main(String[] args) {
        new EightQueens();
    }
}
