package edu.minibank.service;

import edu.minibank.model.annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository<T> {
    private final Map<String, T> data = new HashMap<>();

    public T save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        String id = extractId(entity);
        data.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public T findById(String id) { return data.get(id); }
    public ArrayList<T> findAll() { return new ArrayList<>(data.values()); }
    public boolean delete(String id) { return data.remove(id) != null; }
    public int size() { return data.size(); }

    private String extractId(T entity) {
        for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        if (value == null) throw new IllegalArgumentException("@Id field cannot be null");
                        return value.toString();
                    } catch (ReflectiveOperationException ex) {
                        throw new IllegalStateException("Cannot read @Id field", ex);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Entity type " + entity.getClass().getName() + " has no @Id field");
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) throw new IllegalArgumentException("Values must not be empty");
        double sum = 0;
        for (N value : values) {
            if (value == null) throw new IllegalArgumentException("Values must not contain null");
            sum += value.doubleValue();
        }
        return sum / values.length;
    }
}
