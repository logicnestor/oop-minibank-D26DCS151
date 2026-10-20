import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class SaveLoadDemo {

    public static void main(String[] args) {

        String filename = "students.ser";

        Student[] students = {
            new Student(101, "Riya", 91.5, "TOKEN-A"),
            new Student(102, "Aman", 85.0, "TOKEN-B"),
            new Student(103, "Sara", 88.5, "TOKEN-C")
        };

        // Save the object array
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                         new FileOutputStream(filename))) {

            out.writeObject(students);
            System.out.println("Objects saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving objects: " + e.getMessage());
            return;
        }

        // Read the object array into fresh objects
        try (ObjectInputStream in =
                     new ObjectInputStream(
                         new FileInputStream(filename))) {

            Student[] restored = (Student[]) in.readObject();

            System.out.println("\nRestored objects:");

            for (Student student : restored) {
                System.out.println(student);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading objects: " + e.getMessage());
        }
    }
}