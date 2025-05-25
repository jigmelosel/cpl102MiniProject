package BMI_Calculator.src.bmicalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class UserDataDialog extends JDialog {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton, deleteButton;

    public UserDataDialog(JFrame parent) {
        super(parent, "User Data Management", true);
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search by Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);
        
        // Table setup
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Timestamp", "Name", "Height", "Weight", "Unit", "BMI", "Category"}, 0);
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete Selected");
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Load data
        refreshTable();
        
        // Event listeners
        searchButton.addActionListener(e -> searchRecords());
        deleteButton.addActionListener(e -> deleteRecord());
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<UserRecord> records = DatabaseManager.getAllRecords();
            for (UserRecord record : records) {
                tableModel.addRow(new Object[]{
                    record.getId(),
                    record.getTimestamp(),
                    record.getName(),
                    String.format("%.2f", record.getHeight()),
                    String.format("%.2f", record.getWeight()),
                    record.getUnit(),
                    String.format("%.2f", record.getBmi()),
                    record.getCategory()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchRecords() {
        try {
            tableModel.setRowCount(0);
            List<UserRecord> records = DatabaseManager.searchByName(searchField.getText());
            for (UserRecord record : records) {
                tableModel.addRow(new Object[]{
                    record.getId(),
                    record.getTimestamp(),
                    record.getName(),
                    String.format("%.2f", record.getHeight()),
                    String.format("%.2f", record.getWeight()),
                    record.getUnit(),
                    String.format("%.2f", record.getBmi()),
                    record.getCategory()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error searching: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this record?", "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                DatabaseManager.deleteRecord(id);
                refreshTable();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}