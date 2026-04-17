public class Record {
    private int id;
    private String name;
    private int age;
    private String department;
    private double marks;

    // Default constructor
    public Record() {
    }

    // Parameterized constructor
    public Record(int id, String name, int age, String department, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.marks = marks;
    }

    // Getters and Setters (Encapsulation)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   

    public int getAge() {
        return age;
    }   

    public void setAge(int age) {
        this.age = age;
    }   

    public String getDepartment() {
        return department;
    }   

    public void setDepartment(String department) {
        this.department = department;
    }   

    public double getMarks() {
        return marks;
    }   

    public void setMarks(double marks) {
        this.marks = marks;
    }   

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-5d %-15s %-8.2f", id, name, age, department, marks);
    }
}