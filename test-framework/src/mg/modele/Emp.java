package mg.modele;

import etu1821.annotation.Url;
import etu1821.servlet.ModelView;
import etu1821.annotation.ParamName;

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

    @Url({ "/index", "/ajouter" })
    public ModelView getDefaultPage(@ParamName("id") int id, @ParamName("name") String name) {
        ModelView modele = new ModelView("default.jsp");
        modele.addItem("Nom", name)
                .addItem("Prenom", "Mahery")
                .addItem("id", id)
                .addItem("Age", 18);
        return modele;
    }
}