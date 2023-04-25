# FrameworkLab
## Présenter par ETU 001821
## Contraintes d'utilisation du framework:
<ul>
  <li>Utilisation de apache tomcat 10.0.7</li>
  <li>Classe HttpServlet dans le package jakarta</li>
  <li>JDK supérieur ou égale à JDK-17</li>
  <li>Mettre toutes les classes sous un seul package qui va être la racine</li>
  <li>Dans le fichier web.xml:
    ```
       <servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1821.framework.servlet.FrontServlet</servlet-class>
        <init-param>
            <param-name>packageName</param-name>
            <param-value>[nom du package racine]</param-value>
            <description>Nom du package source</description>
        </init-param>
      </servlet>
      <servlet-mapping>
          <servlet-name>FrontServlet</servlet-name>
          <url-pattern>/</url-pattern>
      </servlet-mapping>
    ```
  </li>
</ul>
