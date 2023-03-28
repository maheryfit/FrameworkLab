package mg.modele;

import etu1821.annotation.Url;
import etu1821.servlet.ModelView;

public class Emp {
    @Url({ "/", "" })
    public ModelView getAll() {
        return new ModelView("test.jsp");
    }
}