package edu.minibank.util;

import edu.minibank.model.annotation.MaxLength;
import edu.minibank.model.annotation.Positive;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class AnnotationValidator {
    private AnnotationValidator() {}
    public static String[] validate(Object obj) {
        if (obj == null) return new String[]{"Object must not be null"};
        List<String> errors = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (field.isAnnotationPresent(Positive.class) && value instanceof Number n) {
                    Positive rule = field.getAnnotation(Positive.class);
                    if (n.longValue() < 0) errors.add(field.getName() + " " + rule.message());
                }
                if (field.isAnnotationPresent(MaxLength.class) && value instanceof String s) {
                    int max = field.getAnnotation(MaxLength.class).value();
                    if (s.length() > max) errors.add(field.getName() + " length must be <= " + max);
                }
            } catch (ReflectiveOperationException | SecurityException ex) {
                errors.add("Could not validate field " + field.getName() + ": " + ex.getMessage());
            }
        }
        return errors.toArray(String[]::new);
    }
}
