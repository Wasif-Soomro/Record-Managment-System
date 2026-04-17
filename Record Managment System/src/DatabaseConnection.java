import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // MySQL configuration
    private static final String URL = "jdbc:mysql://localhost:3306/record_management_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Wasif482007";

    // Returns a DB connection
    public static Connection getConnection() throws SQLException {
        try {
            // Driver loading (for modern JDBC this is often auto, but included as requested)
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add mysql-connector-j to classpath.", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Creates table if not exists
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS records ("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "age INT NOT NULL, "
                + "department VARCHAR(100) NOT NULL, "
                + "marks DOUBLE NOT NULL"
                + ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'records' is ready.");
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
}