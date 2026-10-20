
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
        return task;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public void removeTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException(
                    "Task ID not found: " + id);
        }
    }
}
