// A countdown program using JavaSwing
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Countdown extends JFrame {
    // Declare the components
    private JTextField inputField; // To input the seconds
    private JButton startButton; // To start the countdown
    private JLabel resultLabel; // To show the countdown result
    private Timer timer; // To control the countdown logic
    private int seconds; // To store the input seconds

    // Constructor
    public Countdown() {
        // Initialize the components
        inputField = new JTextField(10);
        startButton = new JButton("Start");
        resultLabel = new JLabel("Enter the seconds and press start");
        timer = new Timer(1000, new TimerListener()); // Create a timer with 1 second interval
        // Set the layout
        setLayout(new FlowLayout());
        // Add the components to the frame
        add(inputField);
        add(startButton);
        add(resultLabel);
        // Add an action listener to the start button
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the input seconds from the text field
                try {
                    seconds = Integer.parseInt(inputField.getText());
                    // Check if the input is valid
                    if (seconds > 0) {
                        // Start the timer
                        timer.start();
                        // Disable the input field and the start button
                        inputField.setEnabled(false);
                        startButton.setEnabled(false);
                    } else {
                        // Show an error message
                        resultLabel.setText("Invalid input. Please enter a positive integer.");
                    }
                } catch (NumberFormatException ex) {
                    // Show an error message
                    resultLabel.setText("Invalid input. Please enter a positive integer.");
                }
            }
        });
        // Set the frame properties
        setTitle("Countdown");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // A private inner class for the timer listener
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Check if the seconds are zero
            if (seconds == 0) {
                // Stop the timer
                timer.stop();
                // Show the final message
                resultLabel.setText("Time's up!");
                // Enable the input field and the start button
                inputField.setEnabled(true);
                startButton.setEnabled(true);
            } else {
                // Decrement the seconds
                seconds--;
                // Show the current seconds
                resultLabel.setText("Seconds remaining: " + seconds);
            }
        }
    }

    // The main method
    public static void main(String[] args) {
        // Create an instance of the Countdown class
        new Countdown();
    }
}
