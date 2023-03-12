/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1821.helper;

import etu1821.servlet.Mapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author mahery
 */
// On récupère toutes les propriétés d'environnement
/* Properties properties = System.getProperties(); */
        
// Pour chacune d'entre elle, et on affiche son nom et sa valeur 
/* for( Object key : properties.keySet() ) {
    String value = System.getProperty( (String) key );
    System.out.printf( "%-30s == %s\n", key, value );
} */
public final class PackageManager {
    private static List<Class> getClassesInMyApplication(String packageName) {
        List<Class> classes = new LinkedList<>();
        Package packageObj = Package.getPackage(packageName);
        String packagePath = packageObj.getName().replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            URL packageUrl = classLoader.getResource(packagePath);
            Path packageDir = Paths.get(packageUrl.toURI());
            Files.walk(packageDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        String className = packageName + "." + file.getFileName().toString().replace(".class", "");
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (URISyntaxException | IOException | NullPointerException | FileSystemNotFoundException e) {

        }
        return classes;
    }
    
    private static List<Class> getClassWithAnnotation(String packages) {
        return getClassesInMyApplication(packages).stream()
                .filter(cl -> cl.getDeclaredAnnotations().length > 0)
                .collect(Collectors.toList());
    }

    public static List<Class> getClassWithAnnotation(Class... annotations) {
        List<Class> listes = new ArrayList<>();
        Arrays.asList(Package.getPackages()).forEach(pack-> {
            listes.addAll(getClassWithAnnotation(pack.getName()));
        });
        List<Class> retour = new ArrayList<>();
        int validity = 0;
        for (Class cl: listes) {
            for (Class annotation: annotations) {
                if (cl.isAnnotationPresent(annotation))
                    validity++;
            }
            if (validity == annotations.length)
                retour.add(cl);
        }
        return retour;
    }

    public static List<Class> getClassesInMyApplication() {
        List<Class> listes = new LinkedList<>();
        Arrays.asList(Package.getPackages()).forEach(pack-> {
            listes.addAll(getClassesInMyApplication(pack.getName()));
        });
        return listes;
    }

    public static List<Class> getClassesInMyApplication(Class... annotations) {
        List<Class> listes = new LinkedList<>();
        Arrays.asList(Package.getPackages()).forEach(pack-> {
            listes.addAll(getClassesInMyApplication(pack.getName()));
        });
        List<Class> retour = listes.stream()
                .filter(liste -> areAnnotationPresent(liste, annotations))
                .collect(Collectors.toList());
        return retour;
    }

    private static boolean areAnnotationPresent(Class classe, Class... annotations) {
        List<Method> methods = Arrays.asList(classe.getDeclaredMethods())
                .stream().collect(Collectors.toList());
        for (Method method: methods) {
            for (Class annotation: annotations) {
                if (method.isAnnotationPresent(annotation))
                    return true;
            }
        }
        return false;
    }
}
