package etu1821.servlet;

public class ModelView {
    private String view;

    private void setView(String view) {
        if (view.equals(""))
            throw new IllegalArgumentException("The view must be a filename", new Throwable("View equals to nothing"));

        this.view = view;
    }

    public String getView() {
        return this.view;
    }

    public ModelView(String view) {
        setView(view);
    }
}
