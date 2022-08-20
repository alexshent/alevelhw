package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.annotation.Singleton;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Injector {
    private final List<Object> singletons = new ArrayList<>();

    public Object getInstance(Class<?> instanceClass) {
        Object instance;
        if (instanceClass.isAnnotationPresent(Singleton.class)) {
            // singleton
            instance = getSingletonInstance(instanceClass);
            if (instance == null) {
                instance = createNewInstance(instanceClass);
                addSingletonInstance(instance);
            }
        } else {
            // not a singleton
            instance = createNewInstance(instanceClass);
        }
        return instance;
    }

    private Object createNewInstance(Class<?> instanceClass) {
        Object instance = null;
        try {
            instance = instanceClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    private Object getSingletonInstance(Class<?> instanceClass) {
        for (Object object : singletons) {
            if (object.getClass().equals(instanceClass)) {
                return object;
            }
        }
        return null;
    }

    private void addSingletonInstance(Object object) {
        singletons.add(object);
    }
}
