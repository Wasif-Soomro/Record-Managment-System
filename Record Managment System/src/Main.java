public class Main {
    public static void main(String[] args) {
        // Initialize DB/table before launching GUI
        DatabaseConnection.initializeDatabase();

        // Launch AWT Frame
        new Mainframe();
    }
}