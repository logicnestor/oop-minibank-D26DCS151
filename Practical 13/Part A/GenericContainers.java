
class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}

class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}

public class GenericContainers {

    // Generic method that returns the first element of any array
    public static <T> T first(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(
                    "Array must not be null or empty");
        }

        return array[0];
    }

    public static void main(String[] args) {
        // Box with different types
        Box<Integer> intBox = new Box<>(100);
        Box<String> stringBox = new Box<>("Hello Java");

        System.out.println("Integer Box: " + intBox.get());
        System.out.println("String Box: " + stringBox.get());

        intBox.set(200);
        System.out.println("Updated Integer Box: " + intBox.get());

        // Pair with two different types
        Pair<String, Integer> student =
                new Pair<>("Riya", 101);

        System.out.println("Student Name: " + student.getFirst());
        System.out.println("Student ID: " + student.getSecond());

        // Generic method with different array types
        Integer[] numbers = {10, 20, 30};
        String[] names = {"Amit", "Riya", "Sara"};
        Double[] prices = {12.5, 25.0, 37.5};

        System.out.println("First number: " + first(numbers));
        System.out.println("First name: " + first(names));
        System.out.println("First price: " + first(prices));
    }
}
