import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game_of_Life_GUI extends JFrame {

    private Game_of_Life game;
    private final JTextField rowField;
    private final JTextField colField;
    private final JTextField iterField;
    private final JTextField speedField;
    private GamePanel gamePanel;
    private JFrame gameFrame;
    private Timer timer;

    public Game_of_Life_GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        rowField = new JTextField(5);
        colField = new JTextField(5);
        iterField = new JTextField(5);
        speedField = new JTextField(5);
        JButton startButton = new JButton("开始游戏");

        inputPanel.add(new JLabel("行数:"));
        inputPanel.add(rowField);
        inputPanel.add(new JLabel("列数:"));
        inputPanel.add(colField);
        inputPanel.add(new JLabel("重复次数:"));
        inputPanel.add(iterField);
        inputPanel.add(new JLabel("速度(ms):"));
        inputPanel.add(speedField);
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.NORTH);

        startButton.addActionListener(e -> {
            int rows = Integer.parseInt(rowField.getText());
            int cols = Integer.parseInt(colField.getText());
            int iterations = Integer.parseInt(iterField.getText());
            int speed = Integer.parseInt(speedField.getText());

            game = new Game_of_Life(rows, cols);
            game.initializeBoard();

            gamePanel = new GamePanel();
            gamePanel.setBoard(game.getBoard());

            gameFrame = new JFrame();
            gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameFrame.add(gamePanel);
            gameFrame.setTitle("生命游戏 - 游戏窗口");
            gameFrame.setSize(cols * 40, rows * 40);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            timer = new Timer(speed, new ActionListener() {
                int iter = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (iter < iterations) {
                        game.updateBoard();
                        gamePanel.setBoard(game.getBoard());
                        gamePanel.repaint();
                        iter++;
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        });

        setTitle("生命游戏 - 主窗口");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game_of_Life_GUI();
    }

    static class GamePanel extends JPanel {
        private int[][] board;

        public void setBoard(int[][] board) {
            this.board = board;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (board != null) {
                int cellWidth = getWidth() / board[0].length;
                int cellHeight = getHeight() / board.length;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length; j++) {
                        if (board[i][j] == 1) {
                            g.setColor(Color.RED);
                            g.fillOval(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                        g.setColor(Color.BLACK);
                        g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
    }
}