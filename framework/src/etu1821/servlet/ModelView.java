package etu1821.servlet;

import java.util.HashMap;

public class ModelView {
    private String view;

    private HashMap<String, Object> data;

    private void setView(String view) {
        if (view.equals(""))
            throw new IllegalArgumentException("The view must be a filename", new Throwable("View equals to nothing"));

        this.view = view;
    }

    private void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, Object> getData() {
        return this.data;
    }

    public String getView() {
        return this.view;
    }

    public ModelView addItem(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ModelView(String view) {
        setView(view);
        setData(new HashMap<>());
    }
}
