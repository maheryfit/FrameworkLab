package etu1821.framework.servlet;

import java.io.*;
import java.util.HashMap;

import etu1821.servlet.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> mappingUrls;
    public void init() {
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void destroy() {
    }
}
