package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.annotation.Autowired;
import ua.com.alevel.alexshent.annotation.Singleton;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class Injector {
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Object> autowiredCache = new HashMap<>();
    private final Reflector reflector;

    public Injector(Reflector reflector) {
        this.reflector = reflector;
    }

    public void run() {
        System.out.println("RUN begin");

        List<Annotation> annotations = new ArrayList<>();
        annotations.add(new Singleton() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Singleton.class;
            }
        });

        Set<Class<?>> classes = reflector.getAllClassesWithAnnotations(annotations);
        classes.forEach(c -> {
            // check for singleton
            if (c.isAnnotationPresent(Singleton.class)) {
                Object instance = getSingletonInstance(c, null);
                System.out.println("singleton: " + instance);
            }
            // check for autowired constructors
            Arrays.stream(c.getDeclaredConstructors())
                    .filter((Constructor<?> constructor) -> constructor.isAnnotationPresent(Autowired.class))
                    .forEach(constructor -> {
                        Parameter[] parameters = constructor.getParameters();
                        if (parameters.length == 1) {
                            Object instance;
                            Class<?> parameterType = parameters[0].getType();
                            if (parameterType.isAnnotationPresent(Singleton.class)) {
                                instance = getSingletonInstance(parameterType, null);
                            } else {
                                instance = createNewInstance(parameterType, null);
                            }
                            System.out.println("autowired: " + instance);
                            autowiredCache.put(parameterType, instance);
                        }
                    });
        });
        System.out.println("RUN end");
    }

    public Object getInstance(Class<?> instanceType, Class<?> constructorParameterType) {
        Object instance;
        if (instanceType.isAnnotationPresent(Singleton.class)) {
            // singleton
            instance = getSingletonInstance(instanceType, constructorParameterType);
        } else {
            // not a singleton
            instance = createNewInstance(instanceType, constructorParameterType);
        }
        return instance;
    }

    private Object getSingletonInstance(Class<?> instanceType, Class<?> constructorParameterType) {
        if (!instanceType.isAnnotationPresent(Singleton.class)) {
            throw new IllegalArgumentException("class does not have the singleton annotation");
        }
        Object object = singletons.get(instanceType);
        if (object != null) {
            return object;
        }
        // create instance and add to singletons if not available
        Object instance = createNewInstance(instanceType, constructorParameterType);
        singletons.put(instanceType, instance);
        return instance;
    }

    private Object createNewInstance(Class<?> instanceType, Class<?> constructorParameterType) {
        Object instance;
        try {
            if (constructorParameterType != null) {
                Object constructorArgument = autowiredCache.get(constructorParameterType);
                instance = instanceType.getDeclaredConstructor(constructorParameterType).newInstance(constructorArgument);
            } else {
                instance = instanceType.getDeclaredConstructor().newInstance();
            }
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
}
