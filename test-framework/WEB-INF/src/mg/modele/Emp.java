package mg.modele;

import etu1821.annotation.Url;
import etu1821.servlet.ModelView;

public class Emp {
    @Url({ "/", "" })
    public ModelView getAll() {
        ModelView modele = new ModelView("test.jsp");
        modele.addItem("Nom", "Jean");
        return modele;
    }

    @Url({ "/index", "/id" })
    public ModelView getId() {
        return new ModelView("default.jsp");
    }
}