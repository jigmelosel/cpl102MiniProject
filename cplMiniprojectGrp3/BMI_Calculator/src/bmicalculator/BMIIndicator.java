package BMI_Calculator.src.bmicalculator;

import javax.swing.*;
import java.awt.*;

public class BMIIndicator extends JPanel {
    private double bmi = 0.0;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 300;

    public BMIIndicator() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createTitledBorder("BMI Scale"));
        setToolTipText("Visual BMI indicator showing your health category");
    }

    public void updateBMI(double bmi) {
        this.bmi = bmi;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // White background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        int margin = 30;
        int barX = WIDTH / 2 - 15;
        int barY = margin;
        int barWidth = 30;
        int barHeight = HEIGHT - 2 * margin;

        // BMI ranges as percentages of bar height
        int[] heights = {
            (int)(barHeight * 0.16),  // Severe Thinness (BMI<16)
            (int)(barHeight * 0.06),  // Moderate Thinness (16-17)
            (int)(barHeight * 0.08),  // Mild Thinness (17-18.5)
            (int)(barHeight * 0.18),  // Normal (18.5-25)
            (int)(barHeight * 0.1),   // Overweight (25-30)
            (int)(barHeight * 0.12),  // Obese I (30-35)
            (int)(barHeight * 0.12),  // Obese II (35-40)
            (int)(barHeight * 0.18)   // Obese III (40+)
        };

        Color[] colors = {
            new Color(0x6AA6FF), // Severe Thinness
            new Color(0x5596E6), // Moderate Thinness
            new Color(0x4077CC), // Mild Thinness
            new Color(0x4CAF50), // Normal
            new Color(0xFFEB3B), // Overweight
            new Color(0xFF9800), // Obese I
            new Color(0xF44336), // Obese II
            new Color(0xB71C1C)  // Obese III
        };

        // Draw colored BMI ranges
        int currentY = barY;
        for (int i = 0; i < heights.length; i++) {
            g.setColor(colors[i]);
            g.fillRect(barX, currentY, barWidth, heights[i]);
            currentY += heights[i];
        }

        // Draw outline
        g.setColor(Color.DARK_GRAY);
        g.drawRect(barX, barY, barWidth, barHeight);

        // Draw pointer if BMI > 0
        if (bmi > 0) {
            double maxBMI = 45.0;
            double clampedBMI = Math.min(Math.max(bmi, 0), maxBMI);
            int pointerY = barY + (int)((1 - (clampedBMI / maxBMI)) * barHeight);

            g.setColor(Color.BLACK);
            int[] xPoints = {barX - 10, barX, barX - 10};
            int[] yPoints = {pointerY - 7, pointerY, pointerY + 7};
            g.fillPolygon(xPoints, yPoints, 3);
            
            // Draw current BMI value
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.drawString(String.format("%.1f", bmi), barX + barWidth + 5, pointerY + 5);
        }

        // Draw category labels
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        String[] categories = {"Underweight", "Normal", "Overweight", "Obese"};
        int[] categoryPositions = {barY + 20, barY + 100, barY + 180, barY + 240};
        
        for (int i = 0; i < categories.length; i++) {
            g.drawString(categories[i], 5, categoryPositions[i]);
        }
    }
}