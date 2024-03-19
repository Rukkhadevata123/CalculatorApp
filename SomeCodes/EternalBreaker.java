import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.util.Duration;

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

public class EternalBreaker extends Application {

    // Define some constants for the game
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int BALL_RADIUS = 10;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_Y = HEIGHT - 50;
    public static final int BRICK_ROWS = 5;
    public static final int BRICK_COLS = 10;
    public static final int BRICK_WIDTH = WIDTH / BRICK_COLS;
    public static final int BRICK_HEIGHT = 15;
    public static final int BRICK_GAP = 4;

    // Define some constants for the difficulty levels
    public static final double SLOW_SPEED = 3;
    public static final double NORMAL_SPEED = 4;
    public static final double FAST_SPEED = 5;
    public static final double LONG_LENGTH = 120;
    public static final double NORMAL_LENGTH = 80;
    public static final double SHORT_LENGTH = 40;

    private static final double buttonWidth = 100;
    private static double angle = Math.PI / 4;

    // Create the ball, the paddle and the bricks
    private Circle ball;
    private Rectangle paddle;
    private Rectangle[][] bricks;

    // Create the animation timeline
    private Timeline animation;
    private Timeline messageTimeline;

    private boolean isGameRunning = false;
    private boolean isGameRunningReset = false;
    private boolean isRandomMode = false;

    private Pane gamePane = new Pane();

    // Create the variables for the ball's velocity and the game's score
    private double dx = NORMAL_SPEED;
    private double dy = NORMAL_SPEED;
    private int score = 0;
    private Label scoreLabel; // Add a label to display the score
    private Label roundLabel; // Add a label to display the round
    private Label messageLabel; // Add a label to display messages
    private Label speedLabel2; // Add a label to display the speed
    private Label lengthLabel2; // Add a label to display the length
    private Label timeUsedLabel; // Add a label to display the time used
    private Label lastRoundTimeLabel; // Add a label to display the time used in the last round
    private Stopwatch stopwatch; // Create a stopwatch to record the time used
    private Stopwatch stopwatch2;
    private int timeLeft = 3; // Initialize the preparation time to 3 seconds
    private int gameround = 1;
    private int resetTimeLeft = 10; // Initialize the reset time to 10 seconds
    private double timeUsed; // Create a variable to store the time used
    private double timeUsed2; // Create a variable to store the time used in the last round
    private Timeline countdown;
    private Timeline resetCountdown;
    private Random random = new Random();

    // The main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    public class Stopwatch {
        private long start;
        private long pausedTime;

        public Stopwatch() {
            start = System.currentTimeMillis();
            pausedTime = 0;
        }

        public void pause() {
            pausedTime = System.currentTimeMillis();
        }

        public void resume() {
            start += System.currentTimeMillis() - pausedTime;
            pausedTime = 0;
        }

        public double elapsedTime() {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }
    }

    private Label createLabel(String text, Color color, double x, double y, boolean visible) {
        Label label = new Label(text);
        label.setTextFill(color);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setVisible(visible);
        gamePane.getChildren().add(label);
        return label;
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
        Button slowSpeedButton = new Button("Slow(3)");
        Button normalSpeedButton = new Button("Normal(4)");
        Button fastSpeedButton = new Button("Fast(5)");
        Label lengthLabel = new Label("Paddle Length:");
        Button longLengthButton = new Button("Long(120)");
        Button normalLengthButton = new Button("Normal(80)");
        Button shortLengthButton = new Button("Short(40)");
        Label modeLabel = new Label("Mode:");
        Button hellMButton = new Button("Hell Mode");
        Button randomModeButton = new Button("Random Mode");
        Button startButton = new Button("Start");

        slowSpeedButton.setPrefWidth(buttonWidth);
        normalSpeedButton.setPrefWidth(buttonWidth);
        fastSpeedButton.setPrefWidth(buttonWidth);
        longLengthButton.setPrefWidth(buttonWidth);
        normalLengthButton.setPrefWidth(buttonWidth);
        shortLengthButton.setPrefWidth(buttonWidth);
        startButton.setPrefWidth(buttonWidth);
        hellMButton.setPrefWidth(buttonWidth);
        randomModeButton.setPrefWidth(buttonWidth);

        // Add the labels and buttons to the menu pane
        menuPane.add(speedLabel, 0, 0);
        menuPane.add(slowSpeedButton, 1, 0);
        menuPane.add(normalSpeedButton, 2, 0);
        menuPane.add(fastSpeedButton, 3, 0);
        menuPane.add(lengthLabel, 0, 1);
        menuPane.add(longLengthButton, 1, 1);
        menuPane.add(normalLengthButton, 2, 1);
        menuPane.add(shortLengthButton, 3, 1);
        menuPane.add(modeLabel, 0, 2);
        menuPane.add(hellMButton, 1, 2);
        menuPane.add(startButton, 2, 2);
        menuPane.add(randomModeButton, 3, 2);

        // Create the game pane and add it to the center of the main pane

        mainPane.setCenter(gamePane);

        // Create the ball and add it to the game pane
        ball = new Circle((double) WIDTH / 2, (double) HEIGHT / 2, BALL_RADIUS);
        ball.setFill(Color.BLACK);
        gamePane.getChildren().add(ball);

        // Create the paddle and add it to the game pane
        paddle = new Rectangle((double) WIDTH / 2 - (double) NORMAL_LENGTH / 2, PADDLE_Y, NORMAL_LENGTH, PADDLE_HEIGHT);
        paddle.setFill(Color.BLACK);
        gamePane.getChildren().add(paddle);

        // Create the bricks and add them to the game pane
        bricks = new Rectangle[BRICK_ROWS][BRICK_COLS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j] = new Rectangle(j * BRICK_WIDTH + BRICK_GAP, i * BRICK_HEIGHT + BRICK_GAP,
                        BRICK_WIDTH - 2 * BRICK_GAP, BRICK_HEIGHT - 2 * BRICK_GAP);
                if (i == 0) {
                    bricks[i][j].setFill(Color.RED);
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
        slowSpeedButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to slow speed when
                                                                                 // game is not running or
                // in hell mode
                dx = dy = SLOW_SPEED;
                isRandomMode = false;
                speedLabel2.setText("Speed: " + "Slow(3)");
            }
        });

        normalSpeedButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to normal speed when
                                                                                 // game is not running or
                // in hell mode
                dx = dy = NORMAL_SPEED;
                isRandomMode = false;
                speedLabel2.setText("Speed: " + "Normal(4)");
            }
        });

        fastSpeedButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to fast speed when
                                                                                 // game is not running or
                // in hell mode
                dx = dy = FAST_SPEED;
                isRandomMode = false;
                speedLabel2.setText("Speed: " + "Fast(5)");
            }
        });

        longLengthButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to long length when
                                                                                 // game is not running or
                // in hell mode
                paddle.setWidth(LONG_LENGTH);
                isRandomMode = false;
                paddle.setVisible(true); // Make the paddle visible
                lengthLabel2.setText("Length: " + "Long(120)");
            }
        });

        normalLengthButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to normal length
                                                                                 // when game is not running
                // or in hell mode
                paddle.setWidth(NORMAL_LENGTH);
                paddle.setVisible(true);
                isRandomMode = false;
                lengthLabel2.setText("Length: " + "Normal(80)");
            }
        });

        shortLengthButton.setOnAction(e -> {
            if (!isRandomMode && dx != 8 && isGameRunning || (!isGameRunning)) { // Allow to switch to short length when
                                                                                 // game is not running or
                // in hell mode
                paddle.setWidth(SHORT_LENGTH);
                paddle.setVisible(true);
                isRandomMode = false;
                lengthLabel2.setText("Length: " + "Short(40)");
            }
        });

        hellMButton.setOnAction(e -> {
            if (!isGameRunning) {
                dx = dy = 8;
                paddle.setWidth(80);
                paddle.setVisible(false); // Make the paddle invisible

                speedLabel2.setText("Speed: " + "HELL(8)");
                lengthLabel2.setText("Length: " + "TRANSPARENT(80)");
            }
        });

        randomModeButton.setOnAction(e -> {
            if (!isGameRunning) {
                isRandomMode = true;

                changeSpeed();
                changeLength();

            }
        });

        startButton.setOnAction(e -> {
            if (!isGameRunning) {
                isGameRunning = true;
                isGameRunningReset = true;
                resetGame();
                showAndHideMessage("Get ready!", 1);
                timeLeft = 3; // Reset the countdown
                startCountdown();
            }
        });

        scoreLabel = createLabel("Score: 0", Color.BLACK, 10, 100, true);
        roundLabel = createLabel("Round: 1", Color.BLACK, 10, 120, true);
        lastRoundTimeLabel = createLabel("Time used in the last round: 0.0 seconds", Color.BLACK, 10, 140, true);
        timeUsedLabel = createLabel("Time used in all rounds: 0.0 seconds", Color.BLACK, 10, 160, true);
        speedLabel2 = createLabel("Speed: " + "Normal(4)", Color.BLACK, 10, 180, true);
        lengthLabel2 = createLabel("Length: " + "Normal(80)", Color.BLACK, 10, 200, true);
        messageLabel = createLabel("Get ready!", Color.BLACK, (double) (WIDTH - 100) / 2, (double) HEIGHT / 2 - 100,
                false);

        // Set the scene and the title for the stage and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eternal Breaker");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void startCountdown() {
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timeLeft > 0) {
                String message = "Game starting in " + timeLeft + " seconds...";
                showAndHideMessage(message, 1);
                timeLeft--;
            } else {
                showAndHideMessage("Game started!", 3);
                animation.play();
                stopwatch = new Stopwatch(); // Start the stopwatch
                stopwatch2 = new Stopwatch();
                updateScoreLabel();
                updateRoundLabel();
                timeLeft = 0;
                countdown.stop(); // Stop the countdown
                isGameRunning = true;
            }
        }));
        countdown.setCycleCount(4);
        countdown.play();
    }

    private void changeSpeed() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int randomSpeed = new Random().nextInt(3);
                if (randomSpeed == 0) {
                    dx = dy = isRandomMode ? SLOW_SPEED * (1 + Math.random()) : SLOW_SPEED;
                    speedLabel2.setText("Speed: " + "Slow(3)");
                } else if (randomSpeed == 1) {
                    dx = dy = isRandomMode ? NORMAL_SPEED * (1 + Math.random()) : NORMAL_SPEED;
                    speedLabel2.setText("Speed: " + "Normal(4)");
                } else {
                    dx = dy = isRandomMode ? FAST_SPEED * (1 + Math.random()) : FAST_SPEED;
                    speedLabel2.setText("Speed: " + "Fast(5)");
                }
            }
        });
    }

    private void changeLength() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int randomLength = new Random().nextInt(3);
                if (randomLength == 0) {
                    paddle.setWidth(isRandomMode ? SHORT_LENGTH * (1 + Math.random()) : SHORT_LENGTH);
                    paddle.setVisible(true);
                    lengthLabel2.setText("Length: " + "Short(40)");
                } else if (randomLength == 1) {
                    paddle.setWidth(isRandomMode ? NORMAL_LENGTH * (1 + Math.random()) : NORMAL_LENGTH);
                    paddle.setVisible(true);
                    lengthLabel2.setText("Length: " + "Normal(80)");
                } else {
                    paddle.setWidth(isRandomMode ? LONG_LENGTH * (1 + Math.random()) : LONG_LENGTH);
                    paddle.setVisible(true);
                    lengthLabel2.setText("Length: " + "Long(120)");
                }
            }
        });
    }

    private void updateScoreLabel() {
        Timeline scoreUpdater = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            scoreLabel.setText("Score: " + score);
        }));
        scoreUpdater.setCycleCount(Timeline.INDEFINITE);
        scoreUpdater.play();
    }

    private void updateRoundLabel() {
        Timeline roundUpdater = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            roundLabel.setText("Round: " + gameround);
        }));
        roundUpdater.setCycleCount(Timeline.INDEFINITE);
        roundUpdater.play();
    }

    private void updatetimeUsedLabel() {
        Timeline timeUsedUpdater = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            timeUsedLabel.setText("Time used in all rounds: " + timeUsed + " seconds");
        }));
        timeUsedUpdater.setCycleCount(Timeline.INDEFINITE);
        timeUsedUpdater.play();
    }

    private void updatetimeUsedLabel2() {
        Timeline timeUsedUpdater2 = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            lastRoundTimeLabel.setText("Time used in the last round: " + timeUsed2 + " seconds");
        }));
        timeUsedUpdater2.setCycleCount(Timeline.INDEFINITE);
        timeUsedUpdater2.play();
    }

    private void handleMouseMoved(MouseEvent e) {
        // Get the mouse x position and adjust the paddle position accordingly
        double mouseX = e.getX();
        paddle.setX(mouseX - paddle.getWidth() / 2);
    }

    private void showAndHideMessage(String message, int duration) {
        if (messageTimeline != null) {
            messageTimeline.stop(); // Stop the current Timeline
        }
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        messageTimeline = new Timeline(new KeyFrame(Duration.seconds(duration), e -> messageLabel.setVisible(false)));
        messageTimeline.play();
    }

    private void updateScore() {
        if (isRandomMode) {
            score += (int) (Math.random() * 10); // Add a random number from 0 to 9 to the score
        }
        if (dx == 8 && dy == 8) {
            score += 6;
        }
        if (!paddle.isVisible()) {
            score += 6;
        }
        if (dx != 8 && dy != 8) {
            if (dx == SLOW_SPEED) {
                score += 1;
            } else if (dx == NORMAL_SPEED) {
                score += 2;
            } else if (dx == FAST_SPEED) {
                score += 3;
            }
            if (paddle.getWidth() == LONG_LENGTH) {
                score += 1;
            } else if (paddle.getWidth() == NORMAL_LENGTH) {
                score += 2;
            } else if (paddle.getWidth() == SHORT_LENGTH) {
                score += 3;
            }
        }
    }

    private void resetGame() {

        ball.setCenterX((double) WIDTH / 2);
        ball.setCenterY((double) HEIGHT / 2);
        paddle.setX((double) WIDTH / 2 - (double) NORMAL_LENGTH / 2);

        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j].setVisible(true);
            }
        }

        score = 0;
        gameround = 1;
        updateScoreLabel();
        updateRoundLabel();

        timeUsed = 0;
        timeUsed2 = 0;
        updatetimeUsedLabel();
        updatetimeUsedLabel2();

        stopwatch = new Stopwatch();
        stopwatch2 = new Stopwatch();
    }

    private void restoreBricks() {
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                bricks[i][j].setVisible(true);
            }
        }
        gameround++;
        isGameRunningReset = true;
        resetTimeLeft = 10;
        stopwatch2 = new Stopwatch();
    }

    private double calculateHitFactor(double ballCenterX, double paddleX, double paddleWidth, boolean isRandomMode) {
        // Calculate the hit factor based on the position of the ball and the paddle
        double hitFactor = (ballCenterX - paddleX) / paddleWidth - 0.5;
        // If in random mode, add a random offset to the hit factor
        if (isRandomMode) {
            hitFactor += (Math.random() - 0.5) / 2.0;
        }
        return hitFactor;
    }

    private void moveBall() {

        // Check if the ball hits the left or right wall
        if (ball.getCenterX() < BALL_RADIUS + 1 || ball.getCenterX() > WIDTH - BALL_RADIUS - 1) {
            // Reverse the angle
            angle = Math.PI - angle;
            ball.setCenterX(ball.getCenterX() < BALL_RADIUS + 1 ? BALL_RADIUS + 1 : WIDTH - BALL_RADIUS - 1);

            // If isRandomMode is true, add a small random offset to the angle
            if (isRandomMode) {
                angle += new Random().nextDouble() * 0.3 - 0.15; // Random number between -0.15 and 0.15
            }
        }

        // Check if the ball hits the top wall
        if (ball.getCenterY() < BALL_RADIUS + BRICK_HEIGHT) {
            // Reverse the angle
            angle = -angle;
            ball.setCenterY(BALL_RADIUS + BRICK_HEIGHT + 1);

            // If isRandomMode is true, add a small random offset to the angle
            if (isRandomMode) {
                angle += new Random().nextDouble() * 0.3 - 0.15; // Random number between -0.15 and 0.15
            }
        }

        // Check if the ball hits the bottom wall
        if (ball.getCenterY() > HEIGHT - BALL_RADIUS) {
            // Stop the animation and display the game over message
            animation.stop();
            showAndHideMessage("Game over! Your score is " + score + ".", 3);
            isGameRunning = false;
            isRandomMode = false;

            // Extract the speed from the text of speedLabel2
            String speedText = speedLabel2.getText(); // "Speed: Normal(4)"
            String speedValueText = speedText.substring(speedText.indexOf("(") + 1, speedText.indexOf(")")); // "4"
            double speedValue = Double.parseDouble(speedValueText);
            dx = dy = speedValue;

            // Extract the length from the text of lengthLabel2
            String lengthText = lengthLabel2.getText(); // "Length: Normal(80)"
            String lengthValueText = lengthText.substring(lengthText.indexOf("(") + 1, lengthText.indexOf(")")); // "80"
            double lengthValue = Double.parseDouble(lengthValueText);
            paddle.setWidth(lengthValue);

            paddle.setVisible(true);

            // Stop the reset countdown
            if (resetCountdown != null) {
                resetCountdown.stop();
            }
        }

        // Check if the ball hits the paddle
        if (ball.intersects(paddle.getBoundsInLocal())) {
            // Calculate the hit factor
            double hitFactor = calculateHitFactor(ball.getCenterX(), paddle.getX(), paddle.getWidth(), isRandomMode);
            // Calculate the new angle based on the hit factor
            if (hitFactor == 0) {
                angle = Math.PI / 2; // Ball should bounce vertically
            } else {
                angle = hitFactor < 0 ? Math.PI - Math.max(hitFactor * -Math.PI / 2, Math.PI / 6)
                        : Math.max(hitFactor * Math.PI / 2, Math.PI / 6);
            }
            // Move the ball above the paddle
            ball.setCenterY(paddle.getY() - ball.getRadius());
            updateScore();
        }

        // Check if the ball hits any brick
        outerLoop:
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                // If the brick is visible and the ball intersects it
                if (bricks[i][j].isVisible() && ball.intersects(bricks[i][j].getBoundsInLocal())
                        && !Color.RED.equals(bricks[i][j].getFill())) {
                    // Calculate the hit factor
                    double hitFactor = calculateHitFactor(ball.getCenterX(), paddle.getX(), paddle.getWidth(),
                            isRandomMode);
                    // Calculate the new angle based on the hit factor
                    if (hitFactor == 0) {
                        angle = Math.PI / 2; // Ball should bounce vertically
                    } else {
                        angle = hitFactor < 0 ? Math.PI - Math.max(hitFactor * -Math.PI / 2, Math.PI / 6)
                                : Math.max(hitFactor * Math.PI / 2, Math.PI / 6);
                    }
                    bricks[i][j].setVisible(false);
                    updateScore();
                    // Break the outer loop
                    break outerLoop;
                }
            }
        }

        if (isRandomMode && random.nextInt(100) < 5) {
            changeSpeed();
            changeLength();
        }

        double randomFactor = isRandomMode ? (0.7 + Math.random() * 0.3) : 1.0;
        ball.setCenterX(ball.getCenterX() + dx * Math.cos(angle) * randomFactor);
        ball.setCenterY(ball.getCenterY() - dy * Math.sin(angle) * randomFactor);

        if (isGameRunningReset) {
            // Check if all bricks are broken
            boolean allBricksBroken = true;
            for (int i = 1; i < BRICK_ROWS; i++) { // Start from the second row
                for (int j = 0; j < BRICK_COLS; j++) {
                    if (bricks[i][j].isVisible()) {
                        allBricksBroken = false;
                        break;
                    }
                }
                if (!allBricksBroken) {
                    break;
                }
            }

            if (allBricksBroken) {
                isGameRunningReset = false;
                showAndHideMessage("All bricks broken! Resetting in 10 seconds...", 1);
                stopwatch.pause(); // Pause the stopwatch
                timeUsed = stopwatch.elapsedTime(); // Get the elapsed time
                timeUsed2 = stopwatch2.elapsedTime();
                timeUsedLabel.setText("Time used in all rounds: " + timeUsed + " seconds");
                lastRoundTimeLabel.setText("Time used in the last round: " + timeUsed2 + " seconds");

                // Start the countdown
                resetCountdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    if (resetTimeLeft > 0) {
                        String message = "Resetting in " + resetTimeLeft + " seconds...";
                        showAndHideMessage(message, 1);
                        resetTimeLeft--;
                    } else {
                        showAndHideMessage("Game restarted!", 3);
                        resetTimeLeft = 0;
                        restoreBricks();
                        resetCountdown.stop(); // Stop the countdown
                        stopwatch.resume(); // Resume the stopwatch
                    }
                }));
                resetCountdown.setCycleCount(11);
                resetCountdown.play();
            }
        }
    }

}
