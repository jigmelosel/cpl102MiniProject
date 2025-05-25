package BMI_Calculator.src.bmicalculator;


import java.time.LocalDateTime;

public class UserRecord {
    private int id;
    private LocalDateTime timestamp;
    private String name;
    private double height;
    private double weight;
    private String unit;
    private double bmi;
    private String category;

    public UserRecord(int id, LocalDateTime timestamp, String name, 
                     double height, double weight, String unit, 
                     double bmi, String category) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.unit = unit;
        this.bmi = bmi;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getName() { return name; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getUnit() { return unit; }
    public double getBmi() { return bmi; }
    public String getCategory() { return category; }
}