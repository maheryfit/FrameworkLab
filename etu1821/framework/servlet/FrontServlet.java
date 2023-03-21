package etu1821.framework.servlet;

import etu1821.annotation.Url;
import etu1821.helper.PackageManager;
import etu1821.servlet.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig
public final class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> mappingUrls;

    private void setMappingUrls() throws IOException, ClassNotFoundException {
        HashMap<String, Mapping> annotatedMethods = new HashMap<>();
        String packageName = this.getInitParameter("packageName");
        List<Class> classes = PackageManager.getClassesInMyApplication(packageName);
        for (Class cls : classes) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(Url.class);
                if (annotation != null) {
                   annotatedMethods.put(method.getAnnotation(Url.class).value(), new Mapping( cls.getName(), method.getName()));
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        PrintWriter out = response.getWriter();
        for (Map.Entry<String, Mapping> map: mappingUrls.entrySet()) {
            out.println(map.getKey() + " " + map.getValue().getMethod());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }
}