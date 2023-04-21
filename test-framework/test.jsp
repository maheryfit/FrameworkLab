Coucou <%= request.getAttribute("Nom")%> <%= request.getAttribute("Prenom")%> qui a <%= request.getAttribute("Age")%> id = <%= request.getAttribute("id")%>
<form method="post">
   <input type="number" name="id" placeholder="Entrer le id">
   <button type="submit">Valider</button>
</form>