package dev.dementisimus.autumn.plugin.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionField {

    private static final Map<String, Field> FIELDS = new HashMap<>();

    public static Field getField(Class<?> clazz, String fieldname) throws NoSuchFieldException {
        String location = clazz.getName() + ":" + fieldname;
        Field field = FIELDS.get(location);
        if (field == null) {
            field = clazz.getDeclaredField(fieldname);
            FIELDS.put(location, field);
        }
        return field;
    }

    public static Field getFieldAccessible(Class<?> clazz, String fieldname) throws NoSuchFieldException {
        Field field = getField(clazz, fieldname);
        field.setAccessible(true);
        return field;
    }

    public static <E> E getValue(Class<?> clazz, Object object, String fieldname) throws NoSuchFieldException, IllegalAccessException {
        return (E) getFieldAccessible(clazz, fieldname).get(object);
    }

    public static <E> E getValuePrintException(Class<?> clazz, Object object, String fieldname) {
        try {
            return getValue(clazz, object, fieldname);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Field findAnyField(Class<?> clazz, String name, Class<?> type) {
        Field field;

        if ((field = findField(clazz, name, type)) == null) {
            field = findDeclaredField(clazz, name, type);
        }

        return field;
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        if (name != null) {
            try {
                Field field = clazz.getField(name);
                if (type == null || matchType(type, field.getType())) {
                    return field;
                }
            } catch (NoSuchFieldException ignored) {
            }
        }

        if (type != null) {
            for (Field field : clazz.getFields()) {
                if (matchType(type, field.getType()) && (name == null || field.getName().equals(name))) {
                    return field;
                }
            }
        }

        return null;
    }

    public static Field findDeclaredField(Class<?> clazz, String name, Class<?> type) {
        Class<?> current;

        if (name != null) {
            current = clazz;

            do {
                try {
                    Field field = current.getDeclaredField(name);
                    if (type == null || type.isAssignableFrom(field.getType())) {
                        return field;
                    }
                } catch (NoSuchFieldException ignored) {
                }
            } while ((current = current.getSuperclass()) != null);
        }

        if (type != null) {
            current = clazz;

            do {
                for (Field field : current.getDeclaredFields()) {
                    if (type.isAssignableFrom(field.getType()) && (name == null || field.getName().equals(name))) {
                        return field;
                    }
                }
            } while ((current = current.getSuperclass()) != null);
        }

        return null;
    }

    private static boolean matchType(Class<?> a, Class<?> b) {
        if (a.isAssignableFrom(b)) {
            return true;
        } else if (a == Integer.class) {
            return b == Integer.class || b == Integer.TYPE;
        } else if (a == Double.class) {
            return b == Integer.class || b == Double.TYPE;
        } else if (a == Long.class) {
            return b == Long.class || b == Long.TYPE;
        } else if (a == Character.class) {
            return b == Character.class || b == Character.TYPE;
        } else if (a == Byte.class) {
            return b == Byte.class || b == Byte.TYPE;
        } else if (a == Float.class) {
            return b == Float.class || b == Float.TYPE;
        } else if (a != Boolean.class) {
            return false;
        } else {
            return b == Boolean.class || b == Boolean.TYPE;
        }
    }
}
