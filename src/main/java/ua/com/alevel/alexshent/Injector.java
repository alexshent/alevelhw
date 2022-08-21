package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.annotation.Singleton;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {
    private final List<Object> singletons = new ArrayList<>();
    private final Reflector reflector;

    public Injector(Reflector reflector) {
        this.reflector = reflector;
    }

    public void run() {
        System.out.println("RUN begin");
        Set<Class<?>> classes = reflector.getAllClassesOfPackages();
        classes.forEach(c -> {
                    if (c.isAnnotationPresent(Singleton.class)) {
                        Object instance = getSingletonInstance(c);
                        System.out.println(instance);
                    }
                });
        System.out.println("RUN end");
    }

    public Object getInstance(Class<?> instanceClass) {
        Object instance;
        if (instanceClass.isAnnotationPresent(Singleton.class)) {
            // singleton
            instance = getSingletonInstance(instanceClass);
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
        if (!instanceClass.isAnnotationPresent(Singleton.class)) {
            throw new IllegalArgumentException("class does not have the singleton annotation");
        }
        for (Object object : singletons) {
            if (object.getClass().equals(instanceClass)) {
                return object;
            }
        }
        // create instance and add to singletons if not available
        Object instance = createNewInstance(instanceClass);
        singletons.add(instance);
        return instance;
    }
}
