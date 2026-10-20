
package lab.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void addingTaskIncreasesCount() {
        TaskService service = new TaskService();

        assertEquals(0, service.getTaskCount());

        service.addTask("Study Java");

        assertEquals(1, service.getTaskCount());
    }

    @Test
    void addingMultipleTasksIncreasesCountCorrectly() {
        TaskService service = new TaskService();

        service.addTask("Study generics");
        service.addTask("Practice JUnit");
        service.addTask("Revise collections");

        assertEquals(3, service.getTaskCount());
    }

    @Test
    void removingUnknownTaskThrowsException() {
        TaskService service = new TaskService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.removeTask(999)
        );
    }

    @Test
    void blankDescriptionThrowsException() {
        TaskService service = new TaskService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addTask("   ")
        );

        assertEquals(0, service.getTaskCount());
    }
}
