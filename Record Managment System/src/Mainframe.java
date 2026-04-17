import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class Mainframe extends Frame implements ActionListener {

    private TextField tfId, tfName, tfAge, tfDepartment, tfMarks;
    private Button btnAdd, btnUpdate, btnDelete, btnDisplay, btnSortName, btnSortMarks, btnClear;
    private TextArea outputArea;
    private Label statusLabel;
    private RecordDAO recordDAO;

    // Theme
    private final Color BG_MAIN = new Color(236, 240, 243);
    private final Color BG_CARD = Color.WHITE;
    private final Color ACCENT = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(46, 204, 113);
    private final Color WARNING = new Color(243, 156, 18);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color DARK_TEXT = new Color(44, 62, 80);

    // Larger fonts
    private final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 28);
    private final Font SUBTITLE_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 18);
    private final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 18);
    private final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);
    private final Font OUTPUT_FONT = new Font("Monospaced", Font.PLAIN, 16);
    private final Font STATUS_FONT = new Font("SansSerif", Font.BOLD, 14);

    public Mainframe() {
        super("Record Management System");
        recordDAO = new RecordDAO();

        // Slightly bigger window
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));
        setBackground(BG_MAIN);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        Panel container = new Panel(new BorderLayout(14, 14));
        container.setBackground(BG_MAIN);

        // Header
        Panel headerPanel = new Panel(new BorderLayout());
        headerPanel.setBackground(ACCENT);
        headerPanel.setPreferredSize(new Dimension(1200, 80));

        Label title = new Label("Student Record Management System", Label.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(TITLE_FONT);
        headerPanel.add(title, BorderLayout.CENTER);

        Label subTitle = new Label("AWT + JDBC", Label.CENTER);
        subTitle.setForeground(new Color(230, 245, 255));
        subTitle.setFont(SUBTITLE_FONT);
        headerPanel.add(subTitle, BorderLayout.SOUTH);

        container.add(headerPanel, BorderLayout.NORTH);

        // Center
        Panel centerWrapper = new Panel(new BorderLayout(12, 12));
        centerWrapper.setBackground(BG_MAIN);

        Panel formCard = new Panel(new BorderLayout());
        formCard.setBackground(BG_CARD);

        Label formTitle = new Label("Enter Record Details", Label.CENTER);
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        formTitle.setForeground(DARK_TEXT);
        formCard.add(formTitle, BorderLayout.NORTH);

        Panel formPanel = new Panel(new GridLayout(6, 2, 14, 14));
        formPanel.setBackground(BG_CARD);

        formPanel.add(makeLabel("ID:"));
        tfId = makeTextField();
        formPanel.add(tfId);

        formPanel.add(makeLabel("Name:"));
        tfName = makeTextField();
        formPanel.add(tfName);

        formPanel.add(makeLabel("Age:"));
        tfAge = makeTextField();
        formPanel.add(tfAge);

        formPanel.add(makeLabel("Department:"));
        tfDepartment = makeTextField();
        formPanel.add(tfDepartment);

        formPanel.add(makeLabel("Marks:"));
        tfMarks = makeTextField();
        formPanel.add(tfMarks);

        Label blank = new Label("", Label.CENTER);
        formPanel.add(blank);

        btnClear = new Button("Clear Fields");
        styleButton(btnClear, new Color(149, 165, 166), Color.WHITE, BUTTON_FONT);
        formPanel.add(btnClear);

        formCard.add(formPanel, BorderLayout.CENTER);

        Panel outputCard = new Panel(new BorderLayout());
        outputCard.setBackground(BG_CARD);

        Label outputTitle = new Label("Records", Label.CENTER);
        outputTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        outputTitle.setForeground(DARK_TEXT);
        outputCard.add(outputTitle, BorderLayout.NORTH);

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setFont(OUTPUT_FONT);
        outputArea.setBackground(new Color(250, 252, 255));
        outputArea.setForeground(new Color(33, 37, 41));
        outputCard.add(outputArea, BorderLayout.CENTER);

        Panel splitPanel = new Panel(new GridLayout(1, 2, 12, 12));
        splitPanel.setBackground(BG_MAIN);
        splitPanel.add(formCard);
        splitPanel.add(outputCard);

        centerWrapper.add(splitPanel, BorderLayout.CENTER);
        container.add(centerWrapper, BorderLayout.CENTER);

        // Bottom actions
        Panel actionsPanel = new Panel(new GridLayout(2, 3, 12, 12));
        actionsPanel.setBackground(BG_MAIN);

        btnAdd = new Button("Add");
        btnUpdate = new Button("Update");
        btnDelete = new Button("Delete");
        btnDisplay = new Button("Display");
        btnSortName = new Button("Sort by Name");
        btnSortMarks = new Button("Sort by Marks");

        styleButton(btnAdd, SUCCESS, Color.WHITE, BUTTON_FONT);
        styleButton(btnUpdate, WARNING, Color.WHITE, BUTTON_FONT);
        styleButton(btnDelete, DANGER, Color.WHITE, BUTTON_FONT);
        styleButton(btnDisplay, ACCENT, Color.WHITE, BUTTON_FONT);
        styleButton(btnSortName, new Color(155, 89, 182), Color.WHITE, BUTTON_FONT);
        styleButton(btnSortMarks, new Color(52, 73, 94), Color.WHITE, BUTTON_FONT);

        actionsPanel.add(btnAdd);
        actionsPanel.add(btnUpdate);
        actionsPanel.add(btnDelete);
        actionsPanel.add(btnDisplay);
        actionsPanel.add(btnSortName);
        actionsPanel.add(btnSortMarks);

        statusLabel = new Label("Ready", Label.CENTER);
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(new Color(60, 60, 60));
        statusLabel.setBackground(new Color(245, 245, 245));

        Panel bottomWrapper = new Panel(new BorderLayout(0, 10));
        bottomWrapper.setBackground(BG_MAIN);
        bottomWrapper.add(actionsPanel, BorderLayout.CENTER);
        bottomWrapper.add(statusLabel, BorderLayout.SOUTH);

        container.add(bottomWrapper, BorderLayout.SOUTH);

        add(container, BorderLayout.CENTER);

        // Events
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnDisplay.addActionListener(this);
        btnSortName.addActionListener(this);
        btnSortMarks.addActionListener(this);
        btnClear.addActionListener(this);

        setVisible(true);
    }

    private Label makeLabel(String text) {
        Label l = new Label(text, Label.CENTER);
        l.setFont(LABEL_FONT);
        l.setForeground(DARK_TEXT);
        return l;
    }

    private TextField makeTextField() {
        TextField tf = new TextField();
        tf.setFont(INPUT_FONT);
        return tf;
    }

    private void styleButton(Button button, Color bg, Color fg, Font font) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(font);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnAdd) addRecord();
        else if (source == btnUpdate) updateRecord();
        else if (source == btnDelete) deleteRecord();
        else if (source == btnDisplay) displayAllRecords();
        else if (source == btnSortName) sortByName();
        else if (source == btnSortMarks) sortByMarks();
        else if (source == btnClear) clearFields();
    }

    private boolean validateInputs(boolean requireAllFields) {
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String age = tfAge.getText().trim();
        String dept = tfDepartment.getText().trim();
        String marks = tfMarks.getText().trim();

        if (id.isEmpty()) {
            showError("ID cannot be empty.");
            return false;
        }

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            showError("ID must be numeric.");
            return false;
        }

        if (requireAllFields) {
            if (name.isEmpty() || age.isEmpty() || dept.isEmpty() || marks.isEmpty()) {
                showError("All fields are required.");
                return false;
            }

            try {
                Integer.parseInt(age);
            } catch (NumberFormatException ex) {
                showError("Age must be numeric.");
                return false;
            }

            try {
                Double.parseDouble(marks);
            } catch (NumberFormatException ex) {
                showError("Marks must be numeric.");
                return false;
            }
        }

        return true;
    }

    private Record getRecordFromFields() {
        int id = Integer.parseInt(tfId.getText().trim());
        String name = tfName.getText().trim();
        int age = Integer.parseInt(tfAge.getText().trim());
        String dept = tfDepartment.getText().trim();
        double marks = Double.parseDouble(tfMarks.getText().trim());
        return new Record(id, name, age, dept, marks);
    }

    private void addRecord() {
        if (!validateInputs(true)) return;

        try {
            Record record = getRecordFromFields();
            boolean inserted = recordDAO.insertRecord(record);

            if (inserted) {
                showSuccess("Record added successfully.");
                displayAllRecords();
                clearFields();
            } else {
                showError("Record could not be added.");
            }
        } catch (SQLException ex) {
            if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("duplicate")) {
                showError("Duplicate ID! This ID already exists.");
            } else {
                showError("Database error: " + ex.getMessage());
            }
        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        if (!validateInputs(true)) return;

        try {
            Record record = getRecordFromFields();
            boolean updated = recordDAO.updateRecord(record);

            if (updated) {
                showSuccess("Record updated successfully.");
                displayAllRecords();
            } else {
                showError("No record found with that ID.");
            }
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void deleteRecord() {
        if (!validateInputs(false)) return;

        try {
            int id = Integer.parseInt(tfId.getText().trim());
            boolean deleted = recordDAO.deleteRecord(id);

            if (deleted) {
                showSuccess("Record deleted successfully.");
                displayAllRecords();
                clearFields();
            } else {
                showError("No record found with that ID.");
            }
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void displayAllRecords() {
        try {
            List<Record> records = recordDAO.getAllRecords();
            showRecords(records, "ALL RECORDS");
            setStatus("Displayed all records (" + records.size() + " found).", false);
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void sortByName() {
        try {
            List<Record> records = recordDAO.getRecordsSortedByName();
            showRecords(records, "RECORDS SORTED BY NAME (ASC)");
            setStatus("Sorted by Name (ascending).", false);
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void sortByMarks() {
        try {
            List<Record> records = recordDAO.getRecordsSortedByMarksDesc();
            showRecords(records, "RECORDS SORTED BY MARKS (DESC)");
            setStatus("Sorted by Marks (descending).", false);
        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
        }
    }

    private void showRecords(List<Record> records, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(centerText(title, 95)).append("\n");
        sb.append("===============================================================================================\n");
        sb.append(String.format("%-8s %-28s %-10s %-24s %-12s\n", "ID", "NAME", "AGE", "DEPARTMENT", "MARKS"));
        sb.append("-----------------------------------------------------------------------------------------------\n");

        if (records.isEmpty()) {
            sb.append(centerText("No records found.", 95)).append("\n");
        } else {
            for (Record r : records) {
                sb.append(String.format("%-8d %-28s %-10d %-24s %-12.2f\n",
                        r.getId(), r.getName(), r.getAge(), r.getDepartment(), r.getMarks()));
            }
        }

        outputArea.setText(sb.toString());
    }

    private String centerText(String text, int width) {
        if (text == null) text = "";
        if (text.length() >= width) return text;

        int leftPadding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < leftPadding; i++) {
            sb.append(' ');
        }
        sb.append(text);

        return sb.toString();
    }

    private void clearFields() {
        tfId.setText("");
        tfName.setText("");
        tfAge.setText("");
        tfDepartment.setText("");
        tfMarks.setText("");
        tfId.requestFocus();
        setStatus("Input fields cleared.", false);
    }

    private void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? DANGER : new Color(60, 60, 60));
    }

    private void showSuccess(String msg) {
        setStatus(msg, false);
        showDialog("Success", msg);
    }

    private void showError(String msg) {
        setStatus(msg, true);
        showDialog("Error", msg);
    }

    private void showDialog(String title, String message) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setBackground(Color.WHITE);

        Label msgLabel = new Label(message, Label.CENTER);
        msgLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dialog.add(msgLabel, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.setBackground(ACCENT);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("SansSerif", Font.BOLD, 14));
        ok.addActionListener(ev -> dialog.dispose());

        Panel btnPanel = new Panel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(ok);

        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setSize(500, 170);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}