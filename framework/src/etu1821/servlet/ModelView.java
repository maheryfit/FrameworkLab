package etu1821.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelView {
    private String view;
    private HashMap<String, Object> data;
    private HashMap<String, Object> session;
    private boolean json;
    private boolean invalidateSession;
    private List<String> sessionToRemove = new ArrayList<>();

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

    /**
     * @return boolean return the json
     */
    public boolean isJson() {
        return json;
    }

    /**
     * @return boolean return the invalidateSession
     */
    public boolean isInvalidateSession() {
        return invalidateSession;
    }

    /**
     * @param invalidateSession the invalidateSession to set
     */
    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }

    /**
     * @return List<String> return the sessionToRemove
     */
    public List<String> getSessionToRemove() {
        return sessionToRemove;
    }

    public ModelView removeSession(String key) {
        this.sessionToRemove.add(key);
        return this;
    }

}
