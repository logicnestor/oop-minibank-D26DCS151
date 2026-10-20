
public class Task {
    private final int id;
    private final String description;

    public Task(int id, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Task description cannot be empty");
        }

        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + ". " + description;
    }
}
