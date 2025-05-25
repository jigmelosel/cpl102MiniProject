package BMI_Calculator.src.bmicalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class HealthAdvicePanel extends JPanel {
    private JLabel titleLabel;
    private JTextArea adviceText;
    private JPanel buttonPanel;

    // Store advice text keyed by advice button name
    private HashMap<String, String> adviceMap;

    public HealthAdvicePanel() {
        setPreferredSize(new Dimension(250, 280));
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Health Advice"));

        titleLabel = new JLabel("Select advice to view");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        adviceText = new JTextArea();
        adviceText.setLineWrap(true);
        adviceText.setWrapStyleWord(true);
        adviceText.setEditable(false);
        adviceText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(new JScrollPane(adviceText), BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        add(buttonPanel, BorderLayout.SOUTH);

        adviceMap = new HashMap<>();

        // Pre-fill advice for categories
        adviceMap.put("Underweight", "Increase calorie intake with nutrient-rich foods. Include proteins and healthy fats.");
        adviceMap.put("Normal", "Maintain your healthy lifestyle with balanced diet and regular exercise.");
        adviceMap.put("Overweight", "Start moderate physical activity and reduce high-calorie foods.");
        adviceMap.put("Obese", "Consult a doctor. Begin a structured diet and exercise plan.");

        // Initially empty
        adviceText.setText("Calculate BMI to see advice.");
    }

    public void updateAdvice(String bmiCategory) {
        buttonPanel.removeAll();

        // Normalize category to keys for adviceMap
        String key = "Normal";
        if (bmiCategory.contains("Thinness") || bmiCategory.contains("Underweight"))
            key = "Underweight";
        else if (bmiCategory.contains("Normal"))
            key = "Normal";
        else if (bmiCategory.contains("Overweight"))
            key = "Overweight";
        else if (bmiCategory.contains("Obese"))
            key = "Obese";

        titleLabel.setText("Advice for: " + bmiCategory);

        String baseAdvice = adviceMap.getOrDefault(key, "Maintain a healthy lifestyle.");

        adviceText.setText(baseAdvice);

        // Add interactive buttons for demonstration
        String[] tips;
        switch (key) {
            case "Underweight":
                tips = new String[]{"Increase Protein", "Healthy Fats", "Frequent Meals"};
                break;
            case "Normal":
                tips = new String[]{"Balanced Diet", "Regular Exercise", "Stay Hydrated"};
                break;
            case "Overweight":
                tips = new String[]{"Cut Sugars", "Exercise More", "Watch Portions"};
                break;
            case "Obese":
                tips = new String[]{"Medical Consultation", "Structured Diet", "Professional Help"};
                break;
            default:
                tips = new String[]{"Healthy Habits"};
        }

        for (String tip : tips) {
            JButton btn = new JButton(tip);
            btn.addActionListener(e -> {
                // Show detailed advice per tip
                adviceText.setText(getTipDetails(tip));
            });
            buttonPanel.add(btn);
        }

        revalidate();
        repaint();
    }

    private String getTipDetails(String tip) {
        switch (tip) {
            case "Increase Protein":
                return "Include lean meats, eggs, dairy, legumes, and nuts to help build muscle.";
            case "Healthy Fats":
                return "Avocados, nuts, seeds, and olive oil provide essential fatty acids.";
            case "Frequent Meals":
                return "Eat smaller meals 5-6 times a day to boost metabolism.";
            case "Balanced Diet":
                return "Ensure a mix of carbs, proteins, and fats with plenty of fruits and veggies.";
            case "Regular Exercise":
                return "Aim for at least 150 minutes of moderate exercise per week.";
            case "Stay Hydrated":
                return "Drink at least 8 cups of water daily to maintain good metabolism.";
            case "Cut Sugars":
                return "Avoid sugary drinks and snacks to reduce excess calories.";
            case "Exercise More":
                return "Add cardio and strength training to burn fat and build muscle.";
            case "Watch Portions":
                return "Use smaller plates and measure portions to avoid overeating.";
            case "Medical Consultation":
                return "Seek professional advice for a personalized weight loss plan.";
            case "Structured Diet":
                return "Follow a diet plan that is sustainable and nutritionally balanced.";
            case "Professional Help":
                return "Consider counseling or support groups for lifestyle changes.";
            default:
                return "Maintain a healthy lifestyle with balanced diet and exercise.";
        }
    }
}
