package etu1821.servlet;

public final class Mapping {
    private String className;
    private String method;

    public String getClassName() {
        return className;
    }

    public String getMethod() {
        return method;
    }

    private void setClassName(String className) {
        this.className = className;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    public Mapping(String className, String method) {
        this.setClassName(className);
        this.setMethod(method);
    }
}
