package BMI_Calculator.src.bmicalculator;

import javax.swing.*;
import java.awt.*;

public class DietPlans extends JDialog {
    public DietPlans(JFrame parent) {
        super(parent, "Diet Plans", true);
        setSize(500, 400);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Underweight Plan
        tabbedPane.addTab("Underweight", createDietPanel(
            "• Increase calorie intake with healthy foods\n" +
            "• Eat 5-6 small meals daily\n" +
            "• Include nuts, seeds, and dairy\n" +
            "• Focus on protein-rich foods\n" +
            "• Combine with strength training"
        ));
        
        // Normal Weight Plan
        tabbedPane.addTab("Normal Weight", createDietPanel(
            "• Maintain balanced diet\n" +
            "• Variety of fruits and vegetables\n" +
            "• Whole grains over refined\n" +
            "• Lean protein sources\n" +
            "• Regular physical activity"
        ));
        
        // Overweight Plan
        tabbedPane.addTab("Overweight", createDietPanel(
            "• Calorie deficit (500-750 kcal less)\n" +
            "• High protein intake\n" +
            "• Reduce added sugars\n" +
            "• Increase fiber intake\n" +
            "• Cardio + strength training"
        ));
        
        // Obese Plan
        tabbedPane.addTab("Obese", createDietPanel(
            "• Consult healthcare provider\n" +
            "• Structured meal plan\n" +
            "• Behavior modification\n" +
            "• Possible medical supervision\n" +
            "• Gradual activity increase"
        ));
        
        add(tabbedPane);
    }
    
    private JPanel createDietPanel(String content) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }
}