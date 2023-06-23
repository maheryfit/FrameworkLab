package mg.modele;

import java.util.Arrays;
import java.util.List;

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

   public Dept(int id, String nom) {
      this.id = id;
      this.nom = nom;
   }

   @Url("/dept")
   public ModelView showDept() {
      ModelView modelView = new ModelView("dept-test.jsp");
      id++;
      modelView.addItem("id", id);
      return modelView;
   }

   @Url("/dept-all")
   public List<Dept> findAll() {
      Dept[] depts = new Dept[3];
      depts[0] = new Dept(1, "Tristesse");
      depts[1] = new Dept(2, "Désespoir");
      depts[2] = new Dept(3, "Acceptation");
      return Arrays.asList(depts);
   }

}
