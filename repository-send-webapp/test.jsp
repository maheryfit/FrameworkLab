Coucou <%= request.getAttribute("Nom")%> <%= request.getAttribute("Prenom")%> qui a <%= request.getAttribute("Age")%> id = <%= request.getAttribute("id")%>
<form method="post" enctype="multipart/form-data">
   <input type="number" name="id" placeholder="Entrer le id">
   <input type="file" name="file">
   <button type="submit">Valider</button>
</form>