import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Mapper {

    public static <T> T populate(
            Class<T> clazz,
            String[] headers,
            String[] data) throws Exception {

        if (headers.length != data.length) {
            throw new IllegalArgumentException(
                "Header and data lengths do not match."
            );
        }

        // Map column name -> value
        Map<String, String> values = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            values.put(headers[i], data[i]);
        }

        T object = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column =
                    field.getAnnotation(Column.class);

            String columnName = column.name();

            if (!values.containsKey(columnName)) {
                System.out.println(
                    "Warning: missing column '" + columnName + "'"
                );
                continue;
            }

            field.setAccessible(true);

            String value = values.get(columnName);

            field.set(object, value);
        }

        return object;
    }
}