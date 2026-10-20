
class GenericStack<T> {
    private final Object[] elements;
    private int top = -1;

    public GenericStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        elements = new Object[capacity];
    }

    public void push(T value) {
        if (top == elements.length - 1) {
            throw new IllegalStateException("Stack is full");
        }

        elements[++top] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        T value = (T) elements[top];
        elements[top] = null;
        top--;

        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        // Integer stack
        GenericStack<Integer> numbers = new GenericStack<>(5);

        numbers.push(10);
        numbers.push(20);
        numbers.push(30);

        System.out.println("Top number: " + numbers.peek());
        System.out.println("Removed number: " + numbers.pop());
        System.out.println("Number stack size: " + numbers.size());

        // String stack
        GenericStack<String> names = new GenericStack<>(5);

        names.push("Amit");
        names.push("Riya");

        System.out.println("Top name: " + names.peek());
        System.out.println("Removed name: " + names.pop());
        System.out.println("Name stack size: " + names.size());

        // Demonstrate empty-stack handling
        try {
            while (!names.isEmpty()) {
                names.pop();
            }

            names.pop();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
