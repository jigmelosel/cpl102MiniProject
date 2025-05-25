package BMI_Calculator.src.bmicalculator;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BMICalculator calculator = new BMICalculator();
            calculator.setVisible(true);
        });
    }
}