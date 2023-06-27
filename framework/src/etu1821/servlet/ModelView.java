package etu1821.servlet;

import java.util.HashMap;

public class ModelView {
    private String view;
    private HashMap<String, Object> data;
    private HashMap<String, Object> session;
    private boolean json;

    public void setJson(boolean json) {
        this.json = json;
    }

    public boolean getJson() {
        return json;
    }

    public void setView(String view) {
        if ((view.equals("") || view == null) && json == false)
            throw new IllegalArgumentException("The view must be a filename", new Throwable("View equals to nothing"));

        this.view = view;
    }

    private void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    private void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, Object> getData() {
        return this.data;
    }

    public HashMap<String, Object> getSession() {
        return this.session;
    }

    public String getView() {
        return this.view;
    }

    public ModelView addItem(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ModelView addSession(String key, Object value) {
        this.session.put(key, value);
        return this;
    }

    public ModelView(String view) {
        setJson(false);
        setView(view);
        setData(new HashMap<>());
        setSession(new HashMap<>());
    }

    public ModelView() {
        setJson(true);
        setView("");
        setData(new HashMap<>());
        setSession(new HashMap<>());
    }
}
