package BMI_Calculator.src.bmicalculator;

import java.sql.*;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/bmi_calculator";
    private static final String USER = "bmi_user";
    private static final String PASSWORD = "bmi123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void saveRecord(UserRecord record) throws SQLException {
        String sql = "INSERT INTO user_records (timestamp, name, height, weight, unit, bmi, category) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(record.getTimestamp()));
            pstmt.setString(2, record.getName());
            pstmt.setDouble(3, record.getHeight());
            pstmt.setDouble(4, record.getWeight());
            pstmt.setString(5, record.getUnit());
            pstmt.setDouble(6, record.getBmi());
            pstmt.setString(7, record.getCategory());
            pstmt.executeUpdate();
        }
    }

    public static List<UserRecord> getAllRecords() throws SQLException {
        List<UserRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM user_records ORDER BY timestamp DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserRecord record = new UserRecord(
                    rs.getInt("id"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("name"),
                    rs.getDouble("height"),
                    rs.getDouble("weight"),
                    rs.getString("unit"),
                    rs.getDouble("bmi"),
                    rs.getString("category")
                );
                records.add(record);
            }
        }
        return records;
    }

    public static void deleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM user_records WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static List<UserRecord> searchByName(String name) throws SQLException {
        List<UserRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM user_records WHERE name LIKE ? ORDER BY timestamp DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserRecord record = new UserRecord(
                    rs.getInt("id"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("name"),
                    rs.getDouble("height"),
                    rs.getDouble("weight"),
                    rs.getString("unit"),
                    rs.getDouble("bmi"),
                    rs.getString("category")
                );
                records.add(record);
            }
        }
        return records;
    }
}