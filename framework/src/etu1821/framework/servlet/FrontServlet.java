package etu1821.framework.servlet;

import etu1821.annotation.Scope;
import etu1821.annotation.Url;
import etu1821.helper.PackageManager;
import etu1821.servlet.Mapping;
import etu1821.servlet.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

@MultipartConfig
public final class FrontServlet extends HttpServlet {

    private static final long serialVersionUID = 1273074928096412095L;
    private HashMap<String, Mapping> mappingUrls;
    private HashMap<Class<?>, Object> mappingUrlsScope;

    /**
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void setMappingUrls() throws IOException, ClassNotFoundException {
        HashMap<String, Mapping> annotatedMethods = new HashMap<>();
        HashMap<Class<?>, Object> mappingScope = new HashMap<>();
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
                    if (cls.isAnnotationPresent(Scope.class)) {
                        if (cls.getAnnotation(Scope.class).value().equals("Singleton")) {
                            if (!mappingScope.containsKey(cls)) {
                                mappingScope.put(cls, null);
                            }
                        }
                    }
                }
            }
        }
        mappingUrls = annotatedMethods;
        mappingUrlsScope = mappingScope;
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

    /**
     * 
     * @param request
     * @return
     */
    private String getURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());
        return path;
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ServletException
     * @throws Exception
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, ServletException, Exception {
        String path = getURI(request);
        Mapping map = this.mappingUrls.get(path);
        Object object = PackageManager.getObjectFromMappingUsingMethod(map, request, this.mappingUrlsScope);
        if (object instanceof ModelView) {
            ModelView modelView = (ModelView) object;
            sendDataToView(request, response, modelView);
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param modelView
     * @throws ServletException
     * @throws IOException
     */
    private void sendDataToView(HttpServletRequest request, HttpServletResponse response, ModelView modelView)
            throws ServletException, IOException {
        if (modelView.getData() instanceof HashMap<?, ?>) {
            modelView.getData().forEach((key, value) -> {
                request.setAttribute(key, value);
            });
        } else {
            throw new IllegalArgumentException("The field data must be an instance of HashMap<String, Object>");
        }
        String view = "";
        if (modelView.getView().startsWith("/")) {
            view = modelView.getView();
        } else {
            view = "/" + modelView.getView();
        }
        request.getRequestDispatcher("/" + view).forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalArgumentException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }
}