package BMI_Calculator.src.bmicalculator;

import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bmi_calculator";
        String user = "bmi_user";
        String password = "bmi123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}