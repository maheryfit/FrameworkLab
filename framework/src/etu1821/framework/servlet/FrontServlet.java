package etu1821.framework.servlet;

import etu1821.annotation.Url;
import etu1821.helper.PackageManager;
import etu1821.servlet.Mapping;
import etu1821.servlet.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

@MultipartConfig
public final class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> mappingUrls;

    private void setMappingUrls() throws IOException, ClassNotFoundException {
        HashMap<String, Mapping> annotatedMethods = new HashMap<>();
        String packageName = this.getInitParameter("packageName");
        List<Class<?>> classes = PackageManager.getClassesInMyApplication(packageName);
        for (Class<?> cls : classes) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(Url.class);
                if (annotation != null) {
                    List<String> values = Arrays.asList(method.getAnnotation(Url.class).value());
                    for (String value : values) {
                        annotatedMethods.put(value, new Mapping(cls.getName(), method.getName()));
                    }
                }
            }
        }
        mappingUrls = annotatedMethods;
    }

    @Override
    public void init() {
        try {
            setMappingUrls();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        return path;
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, ServletException {
        String path = getURI(request);
        System.out.println(path);
        Mapping map = this.mappingUrls.get(path);
        Object object = PackageManager.getObjectFromMapping(map);
        if (object instanceof ModelView) {
            System.out.println(object.getClass().getName());
            ModelView modelView = (ModelView) object;
            if (modelView.getData() instanceof HashMap<?, ?>) {
                modelView.getData().forEach((key, value) -> {
                    request.setAttribute(key, value);
                });
            } else {
                throw new IllegalArgumentException("The field data must be an instance of HashMap<String, Object>");
            }
            request.getRequestDispatcher("/" + modelView.getView()).forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }
}