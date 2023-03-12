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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

@MultipartConfig
public final class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> mappingUrls;

    private void setMappingUrls() {
        List<Class> listes = PackageManager.getClassesInMyApplication(Url.class);
        for (Class liste: listes) {
            for (Method method: liste.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Url.class)) {
                    this.mappingUrls.put(method.getAnnotation(Url.class).url(), new Mapping(liste.getName(), method.getName()));
                }
            }
        }
    }

    @Override
    public void init() {
        setMappingUrls();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
    }
}