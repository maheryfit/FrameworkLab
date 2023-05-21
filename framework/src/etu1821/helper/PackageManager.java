/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1821.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;

import etu1821.annotation.ParamName;
import etu1821.annotation.Url;
import etu1821.servlet.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class PackageManager {

    /**
     * 
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
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

    /**
     * 
     * @param mapping
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws Exception
     */
    public static Object getObjectFromMappingUsingMethod(Mapping mapping, HttpServletRequest request)
            throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, Exception {
        Method method = null;
        Object cl = getObjectFromMapping(mapping);
        if (!request.getParameterMap().isEmpty() && request.getMethod().toLowerCase().equals("post")) {
            sendDataToModel(request, cl);
        }
        List<Method> methods = Arrays.asList(cl.getClass().getDeclaredMethods());
        method = methods.stream()
                .filter(mtd -> mtd.isAnnotationPresent(Url.class)
                        && mtd.getName().equals(mapping.getMethod()))
                .findFirst().get();
        Object o = null;
        if (!request.getParameterMap().isEmpty() && request.getMethod().toLowerCase().equals("get")) {
            o = treatMethodGet(request, method, cl);
        } else {
            try {
                o = method.invoke(cl);
            } catch (Exception e) {
                o = treatMethodGet(request, method, cl);
            }
        }
        return o;
    }

    /**
     * 
     * @param request
     * @param method
     * @param object
     * @return
     * @throws Exception
     */
    private static <T> Object treatMethodGet(HttpServletRequest request, Method method, Object object)
            throws Exception {
        LinkedList<T> paramValues = new LinkedList<>();
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(ParamName.class)) {
                paramValues.add(adequatObjectForParameter(request, parameter, method));
            } else {
                throw new Exception(
                        "You must annotated the argument of the method with the annotation ParamName",
                        new Throwable("You forgot to annotate the argument of the method"));
            }
        }
        return method.invoke(object, paramValues.toArray());
    }

    /**
     * 
     * @param request
     * @param parameter
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private static <T> T adequatObjectForParameter(HttpServletRequest request, Parameter parameter, Method method)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (request.getParameter(parameter.getAnnotation(ParamName.class).value()) == null) {
            if (parameter.getType().getSimpleName().equals("int")
                    || parameter.getType().getSimpleName().equals("Integer")
                    || parameter.getType().getSimpleName().equals("Double")
                    || parameter.getType().getSimpleName().equals("double")
                    || parameter.getType().getSimpleName().equals("long")
                    || parameter.getType().getSimpleName().equals("Long")
                    || parameter.getType().getSimpleName().equals("float")
                    || parameter.getType().getSimpleName().equals("Float")) {
                return (T) (Number) 0;
            }
            return null;
        }
        T obj = null;
        if (parameter.getType().getSimpleName().equals("int")
                || parameter.getType().getSimpleName().equals("Integer")) {
            obj = (T) (Integer) Integer
                    .parseInt(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("float")
                || parameter.getType().getSimpleName().equals("Float")) {
            obj = (T) (Float) Float
                    .parseFloat(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("Long")
                || parameter.getType().getSimpleName().equals("long")) {
            obj = (T) (Long) Long
                    .parseLong(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("double")
                || parameter.getType().getSimpleName().equals("Double")) {
            obj = (T) (Double) Double
                    .parseDouble(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("String")) {
            obj = (T) (String) request.getParameter(parameter.getAnnotation(ParamName.class).value())
                    .trim();
        } else if (parameter.getType().getSimpleName().equals("Date")) {
            obj = (T) (Date) Date
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("Time")) {
            obj = (T) (Time) Time
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("Timestamp")) {
            obj = (T) (Timestamp) Timestamp
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim());
        } else if (parameter.getType().getSimpleName().equals("LocalDate")) {
            obj = (T) (LocalDate) Date
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim())
                    .toLocalDate();
        } else if (parameter.getType().getSimpleName().equals("LocalTime")) {
            obj = (T) (LocalTime) Time
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim())
                    .toLocalTime();
        } else if (parameter.getType().getSimpleName().equals("LocalDateTime")) {
            obj = (T) (LocalDateTime) Timestamp
                    .valueOf(request.getParameter(parameter.getAnnotation(ParamName.class).value())
                            .trim())
                    .toLocalDateTime();
        }
        return obj;
    }

    /**
     * 
     * @param request
     * @param object
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static void sendDataToModel(HttpServletRequest request, Object object)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        // TODO
        FileUploader fileUploader = new FileUploader();
        request.getParameterNames().asIterator().forEachRemaining(name -> {
            if (isFieldExistsInClass(object.getClass(), name)) {
                try {
                    Field field = getFieldExistsInClass(object.getClass(), name);
                    if (request.getParameter(name).equals("")) {
                        throw new IllegalArgumentException("You must enter something in the input " + name,
                                new Throwable("Input " + name + " contains nothing"));
                    }
                    Method method = object.getClass().getDeclaredMethod("set" + capitalizeFirstLetter(name),
                            field.getType());
                    if (field.getType().isInstance(fileUploader)) {
                        treatFileUpload(request.getPart(name), object, method);
                    } else {
                        castingInputValue(request, field, name, method, object);
                    }
                } catch (NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                } catch (ServletException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * 
     * @param part
     * @param object
     * @param method
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     */
    private static void treatFileUpload(Part part, Object object, Method method)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        FileUploader fileUploader = new FileUploader(part.getSubmittedFileName(), "",
                part.getInputStream().readAllBytes());
        method.invoke(object, fileUploader);
    }

    /**
     * 
     * @param clazz
     * @param nameField
     * @return True or False
     */
    private static boolean isFieldExistsInClass(Class<?> clazz, String nameField) {
        try {
            Field field = clazz.getDeclaredField(nameField);
            return field != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param request
     * @param field
     * @param name
     * @param method
     * @param object
     * @throws NumberFormatException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static void castingInputValue(HttpServletRequest request, Field field, String name, Method method,
            Object object)
            throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (field.getType().getSimpleName().equals("int")
                || field.getType().getSimpleName().equals("Integer")) {
            method.invoke(object, Integer.parseInt(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("double")
                || field.getType().getSimpleName().equals("Double")) {
            method.invoke(object, Double.parseDouble(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("float")
                || field.getType().getSimpleName().equals("Float")) {
            method.invoke(object, Float.parseFloat(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("long")
                || field.getType().getSimpleName().equals("Long")) {
            method.invoke(object, Long.parseLong(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("String")) {
            method.invoke(object, request.getParameter(name).trim());
        } else if (field.getType().getSimpleName().equals("Date")) {
            method.invoke(object, Date.valueOf(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("Time")) {
            method.invoke(object, Time.valueOf(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("Timestamp")) {
            method.invoke(object, Timestamp.valueOf(request.getParameter(name).trim()));
        } else if (field.getType().getSimpleName().equals("LocalDate")) {
            method.invoke(object, Date.valueOf(request.getParameter(name).trim()).toLocalDate());
        } else if (field.getType().getSimpleName().equals("LocalTime")) {
            method.invoke(object, Time.valueOf(request.getParameter(name).trim()).toLocalTime());
        } else if (field.getType().getSimpleName().equals("LocalDateTime")) {
            method.invoke(object, Timestamp.valueOf(request.getParameter(name).trim()).toLocalDateTime());
        }
    }

    /**
     * 
     * @param cl
     * @param nameField
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    private static Field getFieldExistsInClass(Class<?> cl, String nameField)
            throws NoSuchFieldException, SecurityException {
        Field field = cl.getDeclaredField(nameField);
        return field;
    }

    /**
     * 
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            chars[0] = Character.toUpperCase(chars[0]);
        }
        return new String(chars);
    }

    /**
     * 
     * @param directory
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
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

    /**
     * 
     * @param mapping
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static Object getObjectFromMapping(Mapping mapping)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Class<?> clazz = Class.forName(mapping.getClassName());
        Object cl = clazz.getConstructor().newInstance();
        return cl;
    }
}
