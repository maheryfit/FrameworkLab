/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1821.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import etu1821.annotation.Url;
import etu1821.servlet.Mapping;

public final class PackageManager {
    public static List<Class<?>> getClassesInMyApplication(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new LinkedList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new LinkedList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    public static Object getObjectFromMapping(Mapping mapping)
            throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, InstantiationException {
        Method method = null;
        Class<?> clazz = Class.forName(mapping.getClassName());
        System.out.println(clazz.getName());
        Object cl = clazz.getConstructor().newInstance();
        List<Method> methods = Arrays.asList(cl.getClass().getDeclaredMethods());
        method = methods.stream()
                .filter(mtd -> mtd.isAnnotationPresent(Url.class)
                        && mtd.getName().equals(mapping.getMethod()))
                .findFirst().get();
        Object o = method.invoke(cl);
        return o;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new LinkedList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
