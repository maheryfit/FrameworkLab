package mg.modele;

import etu1821.annotation.Url;
import etu1821.servlet.ModelView;

public class Emp {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Url({ "/", "" })
    public ModelView getAll() {
        ModelView modele = new ModelView("test.jsp");
        modele.addItem("Nom", "Jean")
                .addItem("Prenom", "Mahery")
                .addItem("id", getId())
                .addItem("Age", 18);
        return modele;
    }

    @Url({ "/index", "/id" })
    public ModelView getDefaultPage() {
        return new ModelView("default.jsp");
    }
}