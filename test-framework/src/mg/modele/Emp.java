package mg.modele;

import etu1821.annotation.Url;
import etu1821.helper.FileUploader;
import etu1821.servlet.ModelView;

import java.util.HashMap;

import etu1821.annotation.Auth;
import etu1821.annotation.ParamName;
import etu1821.annotation.Scope;
import etu1821.annotation.SessionField;

@Scope
public class Emp {
    private int id;
    private FileUploader file;
    @SessionField
    private HashMap<String, Object> sessions;

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

    public void setSessions(HashMap<String, Object> sessions) {
        this.sessions = sessions;
    }

    public HashMap<String, Object> getSessions() {
        return this.sessions;
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
        ModelView modele = new ModelView();
        Dept[] depts = new Dept[3];
        depts[0] = new Dept(1, "Tristesse");
        depts[1] = new Dept(2, "Désespoir");
        depts[2] = new Dept(3, "Acceptation");
        modele.addItem("Nom", name)
                .addItem("Prenom", "Mahery")
                .addItem("id", id)
                .addItem("Age", 18)
                .addItem("Emotions", depts);
        return modele;
    }

    @Url("/to-admin")
    public ModelView toAdmin() {
        ModelView modele = new ModelView("toadmin.jsp");
        modele.addItem("identifiant", "Je suis identifié");
        modele.addItem("idUtilisateur", 2);
        modele.addSession("role", "admin");
        modele.addSession("idUtilisateur", 2);
        return modele;
    }

    @Url("/admin")
    @Auth("admin")
    public ModelView onlyForAdmin() {
        ModelView modele = new ModelView("admin.jsp");
        modele.addItem("idUtilisateur", this.getSessions().get("idUtilisateur"));
        modele.addItem("identifiant", "Je suis dans admin.jsp");
        return modele;
    }
}