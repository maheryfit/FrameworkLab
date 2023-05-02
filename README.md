# FrameworkLab
## Présenter par ETU 001821
## Contraintes d'utilisation du framework:
<ul>
  <li>Utilisation de apache tomcat 10.0.7</li>
  <li>Classe HttpServlet dans le package jakarta</li>
  <li>JDK supérieur ou égale à JDK-17</li>
  <li>Mettre toutes les classes sous un seul package qui va être la racine</li>
  <li>Dans le fichier web.xml:
      <pre><code>
      &lt;servlet&gt;
          &lt;servlet-name&gt;FrontServlet&lt;/servlet-name&gt;
          &lt;servlet-class&gt;etu1821.framework.servlet.FrontServlet&lt;/servlet-class&gt;
          &lt;init-param&gt;
              &lt;param-name&gt;packageName&gt;/param-name&gt;
              &lt;param-value&gt;nom du package racine&lt;/param-value&gt;
              &lt;description&gt;Nom du package source&lt;/description&gt;
          &lt;/init-param&gt;
      &lt;/servlet&gt;
      &lt;servlet-mapping&gt;
          &lt;servlet-name&gt;FrontServlet&lt;/servlet-name&gt;
          &lt;url-pattern&gt;/&lt;/url-pattern&gt;
      &lt;/servlet-mapping&gt;
       </code></pre>
  </li>
</ul>
<h2> Méthode d'utilisation:</h2>
<ul>
  <li>Importer la classe <pre><code>import etu1821.servlet.ModelView;</code></pre> pour récupérer la classe ModelView</li>
  <li>La classe pour l'annotation des méthodes: Url<pre><code>@Url("/")</code></pre> ou <pre><code>@Url({ "/", "" })</code></pre> dans le package <pre><code>import etu1821.annotation.Url;</code></pre></li>
  <li>Pour récupérer une méthode de type GET: Utilisation de l'annotation 
    Exemple:
    <pre><code>
    @Url("/test")
    public ModelView test(@ParamName("id") int id, @ParamName("name") String nom) {
     
    }
    </code></pre> 
    dans le paramètre du fonction  dans le package <pre><code>import etu1821.annotation.ParamName</code></pre></li>
</ul>
