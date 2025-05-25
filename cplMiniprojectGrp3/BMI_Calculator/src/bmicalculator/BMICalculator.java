package BMI_Calculator.src.bmicalculator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.sql.SQLException;

public class BMICalculator extends JFrame {
    private JTextField heightField, weightField, nameField;
    private JLabel resultLabel, categoryLabel;
    private JComboBox<String> unitComboBox;
    private JButton calculateButton, viewDataButton, saveButton;
    private BMIIndicator bmiIndicator;

    private double currentBmi;
    private String currentCategory;

    public BMICalculator() {
        setTitle("BMI Calculator - Health Companion");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));

        // Initialize components
        nameField = new JTextField();
        unitComboBox = new JComboBox<>(new String[]{"Metric (kg, cm)", "Imperial (lbs, inches)"});
        heightField = new JTextField();
        weightField = new JTextField();
        calculateButton = new JButton("Calculate BMI");
        saveButton = new JButton("Save Result");
        saveButton.setEnabled(false);
        resultLabel = new JLabel("BMI: ", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel = new JLabel("Category: ", JLabel.CENTER);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        viewDataButton = new JButton("View Saved Data");
        bmiIndicator = new BMIIndicator();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setOpaque(false);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Unit System:"));
        inputPanel.add(unitComboBox);
        inputPanel.add(new JLabel("Height:"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Weight:"));
        inputPanel.add(weightField);
        inputPanel.add(calculateButton);
        inputPanel.add(saveButton);
        inputPanel.add(resultLabel);
        inputPanel.add(categoryLabel);

        // Indicator Panel
        JPanel indicatorPanel = new JPanel(new BorderLayout());
        indicatorPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        indicatorPanel.setOpaque(false);
        indicatorPanel.add(bmiIndicator, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(viewDataButton);

        // Add to frame
        add(inputPanel, BorderLayout.CENTER);
        add(indicatorPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        calculateButton.addActionListener(this::calculateBMI);
        saveButton.addActionListener(this::saveRecord);
        viewDataButton.addActionListener(e -> viewSavedData());

        // Disable save if input is changed after calculation
        DocumentListener inputChangeListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { saveButton.setEnabled(false); }
            public void removeUpdate(DocumentEvent e) { saveButton.setEnabled(false); }
            public void changedUpdate(DocumentEvent e) { saveButton.setEnabled(false); }
        };
        heightField.getDocument().addDocumentListener(inputChangeListener);
        weightField.getDocument().addDocumentListener(inputChangeListener);
    }

    private void calculateBMI(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            boolean isMetric = unitComboBox.getSelectedIndex() == 0;
            String unit = isMetric ? "Metric" : "Imperial";

            if (!isMetric) {
                height *= 2.54;  // inches to cm
                weight *= 0.453592;  // lbs to kg
            }

            currentBmi = weight / ((height / 100) * (height / 100));
            currentCategory = getBMICategory(currentBmi);

            resultLabel.setText(String.format("BMI: %.2f", currentBmi));
            categoryLabel.setText("Category: " + currentCategory);
            bmiIndicator.updateBMI(currentBmi);
            saveButton.setEnabled(true); // Enable Save

        } catch (NumberFormatException ex) {
            bmiIndicator.updateBMI(0);
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for height and weight",
                "Input Error", JOptionPane.ERROR_MESSAGE);
            saveButton.setEnabled(false);
        }
    }

    private void saveRecord(ActionEvent e) {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name before saving",
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean isMetric = unitComboBox.getSelectedIndex() == 0;
            String unit = isMetric ? "Metric" : "Imperial";

            UserRecord record = new UserRecord(
                0,
                LocalDateTime.now(),
                nameField.getText(),
                Double.parseDouble(heightField.getText()),
                Double.parseDouble(weightField.getText()),
                unit,
                currentBmi,
                currentCategory
            );

            DatabaseManager.saveRecord(record);
            JOptionPane.showMessageDialog(this, "Record saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            saveButton.setEnabled(false);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to database: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid height/weight values",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal weight";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }

    private void viewSavedData() {
        new UserDataDialog(this).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BMICalculator calculator = new BMICalculator();
            calculator.setVisible(true);
        });
    }
}
