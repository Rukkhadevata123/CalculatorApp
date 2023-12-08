// A base converter using Java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BaseConverter extends JFrame {
    // Declare the components
    private JComboBox<String> sourceBox; // To select the source base
    private JComboBox<String> targetBox; // To select the target base
    private JTextField inputField; // To input the number
    private JTextField outputField; // To output the number
    private JButton convertButton; // To perform the conversion
    private JLabel resultLabel; // To show the conversion result or error message
    private String[] bases; // To store the supported bases

    // Constructor
    public BaseConverter() {
        // Initialize the components
        bases = new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10", "16"}; // The supported bases
        sourceBox = new JComboBox<>(bases); // Create a combo box with the bases
        targetBox = new JComboBox<>(bases); // Create another combo box with the bases
        inputField = new JTextField(10); // Create a text field for input
        outputField = new JTextField(10); // Create a text field for output
        outputField.setEditable(false); // Make the output field read-only
        convertButton = new JButton("Convert"); // Create a button for conversion
        resultLabel = new JLabel("Select the source and target base, and enter the number to convert"); // Create a label for result or error message
        // Set the layout
        setLayout(new GridLayout(3, 2, 10, 10)); // Use a grid layout with 3 rows and 2 columns, and 10 pixels gap
        // Add the components to the frame
        add(sourceBox);
        add(targetBox);
        add(inputField);
        add(outputField);
        add(convertButton);
        add(resultLabel);
        // Add an action listener to the convert button
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected source and target base from the combo boxes
                int sourceBase = Integer.parseInt((String) sourceBox.getSelectedItem());
                int targetBase = Integer.parseInt((String) targetBox.getSelectedItem());
                // Get the input number from the text field
                String inputNumber = inputField.getText();
                // Check if the input number is valid
                try {
                    // Parse the input number to an integer with the source base
                    int number = Integer.parseInt(inputNumber, sourceBase);
                    // Convert the number to a string with the target base
                    String outputNumber = Integer.toString(number, targetBase);
                    // Set the output field with the output number
                    outputField.setText(outputNumber);
                    // Set the result label with a success message
                    resultLabel.setText("Conversion successful!");
                } catch (NumberFormatException ex) {
                    // Set the result label with an error message
                    resultLabel.setText("Invalid input. Please enter a valid number for the source base.");
                }
            }
        });
        // Set the frame properties
        setTitle("Base Converter");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // The main method
    public static void main(String[] args) {
        // Create an instance of the BaseConverter class
        new BaseConverter();
    }
}
