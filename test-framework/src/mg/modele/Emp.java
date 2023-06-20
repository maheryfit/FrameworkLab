package mg.modele;

import etu1821.annotation.Url;
import etu1821.helper.FileUploader;
import etu1821.servlet.ModelView;
import etu1821.annotation.Auth;
import etu1821.annotation.ParamName;
import etu1821.annotation.Scope;

@Scope
public class Emp {
    private int id;
    private FileUploader file;

    public void setFile(FileUploader file) {
        this.file = file;
    }

    public FileUploader getFile() {
        return file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Url({ "/", "" })
    public ModelView getAll() {
        ModelView modele = new ModelView("test.jsp");
        id++;
        modele.addItem("Nom", "Jean")
                .addItem("Prenom", "Mahery")
                .addItem("id", id)
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

    @Url("/to-admin")
    public ModelView toAdmin() {
        ModelView modele = new ModelView("toadmin.jsp");
        modele.addItem("identifiant", "Je suis identifié");
        modele.addSession("role", "admin");
        return modele;
    }

    @Url("/admin")
    @Auth("admin")
    public ModelView onlyForAdmin() {
        ModelView modele = new ModelView("admin.jsp");
        modele.addItem("identifiant", "Je suis dans admin.jsp");
        return modele;
    }
}