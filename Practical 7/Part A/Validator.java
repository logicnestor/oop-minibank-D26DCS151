import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static List<String> validate(Object object) {

        List<String> errors = new ArrayList<>();

        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);

            try {
                Object value = field.get(object);

                // Check @NotBlank
                if (field.isAnnotationPresent(NotBlank.class)) {

                    if (value == null ||
                        value.toString().trim().isEmpty()) {

                        errors.add(field.getName() + " must not be blank.");
                    }
                }

                // Check @MaxLength
                if (field.isAnnotationPresent(MaxLength.class)) {

                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    int maxLength = annotation.value();

                    if (value != null &&
                        value.toString().length() > maxLength) {

                        errors.add(
                            field.getName()
                            + " must be at most "
                            + maxLength
                            + " characters."
                        );
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add(
                    "Could not access field: " + field.getName()
                );
            }
        }

        return errors;
    }
}