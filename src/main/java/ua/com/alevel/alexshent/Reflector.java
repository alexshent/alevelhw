package ua.com.alevel.alexshent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class Reflector {
    private final String packageNameRegularExpression;

    public Reflector(String packageNameRegularExpression) {
        this.packageNameRegularExpression = packageNameRegularExpression;
    }

    public Set<Class<?>> getAllClassesWithAnnotations(List<Annotation> annotations) {
        Set<Class<?>> result = new HashSet<>();
        List<Package> packages = this.getAllPackages(packageNameRegularExpression);
        packages.forEach(p -> {
            Set<Class<?>> classes = this.findAllClassesUsingClassLoader(p.getName());
            classes.forEach(c -> {
                Annotation[] declaredAnnotations = c.getDeclaredAnnotations();
                if (!Collections.disjoint(Arrays.asList(declaredAnnotations), annotations)) {
                    result.add(c);
                }
            });
        });
        return result;
    }

    private Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> this.getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Package> getAllPackages(String packageNameRegularExpression) {
        Package[] packages = Package.getPackages();
        return
                Arrays.stream(packages)
                        .filter((p) -> p.getName().matches(packageNameRegularExpression))
                        .toList();
    }
}
