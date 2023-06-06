package mg.modele;

import etu1821.annotation.Url;
import etu1821.servlet.ModelView;

public class Dept {
   private int id;

   private String nom;

   public void setId(int id) {
      this.id = id;
   }

   public void setNom(String nom) {
      this.nom = nom;
   }

   public String getNom() {
      return this.nom;
   }

   public int getId() {
      return this.id;
   }

   public Dept() {

   }

   @Url("dept")
   public ModelView showDept() {
      ModelView modelView = new ModelView("dept-test.jsp");
      id++;
      modelView.addItem("id", id);
      return modelView;
   }

}
