import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {

    // Insert record
    public boolean insertRecord(Record record) throws SQLException {
        String sql = "INSERT INTO records (id, name, age, department, marks) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, record.getId());
            ps.setString(2, record.getName());
            ps.setInt(3, record.getAge());
            ps.setString(4, record.getDepartment());
            ps.setDouble(5, record.getMarks());

            return ps.executeUpdate() > 0;
        }
    }

    // Get all records
    public List<Record> getAllRecords() throws SQLException {
        String sql = "SELECT * FROM records";
        List<Record> records = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Record record = new Record(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("marks")
                );
                records.add(record);
            }
        }

        return records;
    }

    // Update record by ID
    public boolean updateRecord(Record record) throws SQLException {
        String sql = "UPDATE records SET name = ?, age = ?, department = ?, marks = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, record.getName());
            ps.setInt(2, record.getAge());
            ps.setString(3, record.getDepartment());
            ps.setDouble(4, record.getMarks());
            ps.setInt(5, record.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // Delete record by ID
    public boolean deleteRecord(int id) throws SQLException {
        String sql = "DELETE FROM records WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Sort by Name ascending
    public List<Record> getRecordsSortedByName() throws SQLException {
        String sql = "SELECT * FROM records ORDER BY name ASC";
        return executeAndReturnList(sql);
    }

    // Sort by Marks descending
    public List<Record> getRecordsSortedByMarksDesc() throws SQLException {
        String sql = "SELECT * FROM records ORDER BY marks DESC";
        return executeAndReturnList(sql);
    }

    // Helper method
    private List<Record> executeAndReturnList(String sql) throws SQLException {
        List<Record> records = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Record record = new Record(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("marks")
                );
                records.add(record);
            }
        }

        return records;
    }
}