import java.lang.reflect.Method;

public class Driver1 {

    public static void main(String[] args) {

        SampleTests tests = new SampleTests();

        int count = 0;

        Method[] methods =
                tests.getClass().getDeclaredMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(Run.class)) {

                try {
                    method.invoke(tests);
                    count++;
                } catch (Exception e) {
                    System.out.println(
                        "Failed to run: " + method.getName()
                    );
                }
            }
        }

        System.out.println("\nTotal @Run methods executed: " + count);
    }
}