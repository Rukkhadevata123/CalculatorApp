import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BreakoutGame extends Application {

    // Define some constants for the game
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int BALL_RADIUS = 10;
    public static final int PADDLE_WIDTH = 80;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_Y = HEIGHT - 50;
    public static final int BRICK_ROWS = 5;
    public static final int BRICK_COLS = 10;
    public static final int BRICK_WIDTH = WIDTH / BRICK_COLS;
    public static final int BRICK_HEIGHT = 15;
    public static final int BRICK_GAP = 4;

    // Define some constants for the difficulty levels
    public static final double SLOW_SPEED = 2;
    public static final double NORMAL_SPEED = 3;
    public static final double FAST_SPEED = 4;
    public static final double LONG_LENGTH = 120;
    public static final double SHORT_LENGTH = 40;

    // Create the ball, the paddle and the bricks
    private Circle ball;
    private Rectangle paddle;
    private Rectangle[][] bricks;

    // Create the animation timeline
    private Timeline animation;

    // Create the variables for the ball's velocity and the game's score
    private double dx = NORMAL_SPEED;
    private double dy = NORMAL_SPEED;
    private int score = 0;
    private Label scoreLabel;  // 添加用于显示得分的标签
    private int timeLeft = 3; // 初始化准备时间为3秒
    private Label messageLabel;

    // The main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the main pane
        BorderPane mainPane = new BorderPane();

        // Create the menu pane and add it to the top of the main pane
        GridPane menuPane = new GridPane();
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setHgap(10);
        menuPane.setVgap(10);
        mainPane.setTop(menuPane);

        // Create the labels and buttons for the menu pane
        Label speedLabel = new Label("Ball Speed:");
        Button slowButton = new Button("Slow");
        Button normalButton = new Button("Normal");
        Button fastButton = new Button("Fast");
        Label lengthLabel = new Label("Paddle Length:");
        Button longButton = new Button("Long");
        Button normalLengthButton = new Button("Normal");
        Button shortButton = new Button("Short");
        Button startButton = new Button("Start");

        // Add the labels and buttons to the menu pane
        menuPane.add(speedLabel, 0, 0);
        menuPane.add(slowButton, 1, 0);
        menuPane.add(normalButton, 2, 0);
        menuPane.add(fastButton, 3, 0);
        menuPane.add(lengthLabel, 0, 1);
        menuPane.add(longButton, 1, 1);
        menuPane.add(normalLengthButton, 2, 1);
        menuPane.add(shortButton, 3, 1);
        menuPane.add(startButton, 2, 2);

        // Create the game pane and add it to the center of the main pane
        Pane gamePane = new Pane();
        mainPane.setCenter(gamePane);

        // Create the ball and add it to the game pane
        ball = new Circle((double) WIDTH / 2, (double) HEIGHT / 2, BALL_RADIUS);
        ball.setFill(Color.BLACK);
        gamePane.getChildren().add(ball);

        // Create the paddle and add it to the game pane
        paddle = new Rectangle((double) WIDTH / 2 - (double) PADDLE_WIDTH / 2, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFill(Color.BLACK);
        gamePane.getChildren().add(paddle);
        primaryStage.setResizable(false);

        // Create the bricks and add them to the game pane
        bricks = new Rectangle[BRICK_ROWS][BRICK_COLS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j] = new Rectangle(j * BRICK_WIDTH + BRICK_GAP, i * BRICK_HEIGHT + BRICK_GAP, BRICK_WIDTH - 2 * BRICK_GAP, BRICK_HEIGHT - 2 * BRICK_GAP);
                if (i == 0) {
                    bricks[i][j].setFill(Color.RED); // 设置红色
                } else {
                    bricks[i][j].setFill(Color.hsb(i * 360.0 / BRICK_ROWS, 0.8, 0.8));
                }
                gamePane.getChildren().add(bricks[i][j]);
            }
        }

        // Create the scene and set the background color
        Scene scene = new Scene(mainPane, WIDTH, HEIGHT + 60);
        scene.setFill(Color.WHITE);

        // Set the mouse event handler for the scene
        scene.setOnMouseMoved(this::handleMouseMoved);

        // Create the animation timeline and set the cycle count to indefinite
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> moveBall()));
        animation.setCycleCount(Timeline.INDEFINITE);

        // Set the action event handlers for the buttons
        slowButton.setOnAction(e -> dx = dy = SLOW_SPEED);
        normalButton.setOnAction(e -> dx = dy = NORMAL_SPEED);
        fastButton.setOnAction(e -> dx = dy = FAST_SPEED);
        longButton.setOnAction(e -> paddle.setWidth(LONG_LENGTH));
        normalLengthButton.setOnAction(e -> paddle.setWidth(PADDLE_WIDTH));
        shortButton.setOnAction(e -> paddle.setWidth(SHORT_LENGTH));
        startButton.setOnAction(e -> {
            // Start the animation and hide the menu pane
            animation.play();

        });
        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.BLACK);
        gamePane.getChildren().add(scoreLabel);
        scoreLabel.setLayoutX(10); // 设置得分标签的水平布局
        scoreLabel.setLayoutY(100); // 设置得分标签的垂直布局

        messageLabel = new Label("Get ready!"); // 创建显示消息的标签并设置初始内容
        messageLabel.setTextFill(Color.BLACK);
        messageLabel.setVisible(false); // 初始时隐藏
        gamePane.getChildren().add(messageLabel);
        messageLabel.setLayoutX((double) (WIDTH - 100) / 2); // 设置消息标签的水平布局
        messageLabel.setLayoutY((double) HEIGHT / 2 - 100); // 设置消息标签的垂直布局

        startButton.setOnAction(e -> {
            resetGame();
            startCountdown();
            showAndHideMessage("Get ready!");
            System.out.println("Get ready!");
        });

        // Set the scene and the title for the stage and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Breakout Game");
        primaryStage.show();
    }

    // Define the method to handle the mouse moved event
    private void startCountdown() {
        Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timeLeft > 0) {

                String message = "Game starting in " + timeLeft + "...";
                System.out.println(message);
                showAndHideMessage(message);
                timeLeft--;
            } else {
                System.out.println("Game started!");
                showAndHideMessage("Game started!");
                animation.play(); // 开始动画
                updateScoreLabel(); // 更新得分标签
                timeLeft = 0; // 重置准备时间
                animation.play(); // 开始游戏动画
            }
        }));
        countdown.setCycleCount(4); // 设置循环次数为4，即3秒倒计时+1秒开始后立即刷新界面
        countdown.play();
    }

    private void updateScoreLabel() {
        Timeline scoreUpdater = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            scoreLabel.setText("Score: " + score); // 实时更新得分标签
        }));
        scoreUpdater.setCycleCount(Timeline.INDEFINITE);
        scoreUpdater.play();
    }

    private void handleMouseMoved(MouseEvent e) {
        // Get the mouse x position and adjust the paddle position accordingly
        double mouseX = e.getX();
        paddle.setX(mouseX - paddle.getWidth() / 2);
    }

    // Define the method to move the ball and check for collisions
    private void showAndHideMessage(String message) {
        Timeline messageTimeline = new Timeline(
                new KeyFrame(Duration.millis(0), e -> {
                    messageLabel.setText(message);
                    messageLabel.setVisible(true);
                }),
                new KeyFrame(Duration.millis(4000), e -> {
                    messageLabel.setVisible(false); // 1秒后隐藏消息
                })
        );
        messageTimeline.play();
    }

    private void resetGame() {
        // 重置球的位置
        ball.setCenterX((double) WIDTH / 2);
        ball.setCenterY((double) HEIGHT / 2);

        // 重置板的位置
        paddle.setX((double) WIDTH / 2 - (double) PADDLE_WIDTH / 2);

        // 重置砖块的可见性
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j].setVisible(true);
            }
        }

        // 重置得分
        score = 0;

        // 更新得分标签
        updateScoreLabel();

        // 重新显示菜单

    }

    private void moveBall() {
        // Check if the ball hits the left or right wall
        if (ball.getCenterX() < BALL_RADIUS || ball.getCenterX() > WIDTH - BALL_RADIUS) {
            // Reverse the x velocity
            dx = -dx;
        }

        // Check if the ball hits the top wall
        if (ball.getCenterY() < BALL_RADIUS) {
            // Reverse the y velocity
            dy = -dy;
        }

        // Check if the ball hits the bottom wall
        if (ball.getCenterY() > HEIGHT - BALL_RADIUS) {
            // Stop the animation and display the game over message
            animation.stop();
            showAndHideMessage("Game over! Your score is " + score + ".");
            System.out.println("Game over! Your score is " + score);

        }

        // Check if the ball hits the paddle
        if (ball.intersects(paddle.getBoundsInLocal())) {
            // Reverse the y velocity and increase the score
            dy = -dy;
            if (dx == SLOW_SPEED) {
                score += 1;
            } else if (dx == NORMAL_SPEED) {
                score += 2;
            } else if (dx == FAST_SPEED) {
                score += 3;
            }
            if (paddle.getWidth() == LONG_LENGTH) {
                score += 1;
            } else if (paddle.getWidth() == PADDLE_WIDTH) {
                score += 2;
            } else if (paddle.getWidth() == SHORT_LENGTH) {
                score += 3;
            }
            System.out.println("Score: " + score);
        }

        // Check if the ball hits any brick
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                // If the brick is visible and the ball intersects it
                if (bricks[i][j].isVisible() && ball.intersects(bricks[i][j].getBoundsInLocal())&& !Color.RED.equals(bricks[i][j].getFill())) {
                    // Reverse the y velocity, hide the brick and increase the score
                    dy = -dy;
                    bricks[i][j].setVisible(false);
                    if (dx == SLOW_SPEED) {
                        score += 1;
                    } else if (dx == NORMAL_SPEED) {
                        score += 2;
                    } else if (dx == FAST_SPEED) {
                        score += 3;
                    }
                    if (paddle.getWidth() == LONG_LENGTH) {
                        score += 1;
                    } else if (paddle.getWidth() == PADDLE_WIDTH) {
                        score += 2;
                    } else if (paddle.getWidth() == SHORT_LENGTH) {
                        score += 3;
                    }
                    System.out.println("Score: " + score);
                }
            }
        }

        // Update the ball's position
        ball.setCenterX(ball.getCenterX() + dx);
        ball.setCenterY(ball.getCenterY() + dy);

    }
}
