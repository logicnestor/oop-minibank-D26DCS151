import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double marks;

    // This field will not be serialized
    private transient String sessionToken;

    public Student(int id, String name, double marks,
                   String sessionToken) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "Student{id=" + id
                + ", name='" + name
                + "', marks=" + marks
                + ", sessionToken=" + sessionToken + "}";
    }
}