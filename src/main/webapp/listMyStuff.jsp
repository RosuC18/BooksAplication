<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List my stuff</title>
    <script src="actiuni.js" type="text/javascript" ></script>
    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
</head>
<body>

<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("id");
    Object email = s.getAttribute("email");
    if(o==null)
    {
        response.sendRedirect("login.html");
    }
%>

Hello <b><%=email%></b>
</p>

<input type="text" placeholder="Search" onkeyup="search(this.value)">


</p>
</p>

<input type="text" id="openbook" placeholder="Open Book" onkeyup="search(this.value)">
<input type="button" id="openbook" value="Open Book" onClick="openBook()">

</p>
</p>

<input type="text"  id="delBook" placeholder="Delete book by author">
<input type="button" id="delete" value="Delete Book" onClick="deleteBook()">
<input type="button" id="deleteAll" value="Delete All Book" onClick="deleteAll()">
</p>
<%----%>

<div id="listOfToDo">
    <table border="1">
        <thead>
        <tr>
<%--            <th onclick="sorteazaNume(this)">Obiect &dArr;</th>--%>
            <th>Author</th>
            <th>Title</th>
        </tr>
        </thead>
        <tbody id="obiect">

        </tbody>


    </table>
</div>

<script>
    loadToDo();
</script>

</p>
<input type="text" id="author" placeholder="Add author" />
<input type="text" id="title" placeholder="Add title" />
<input type="button" id="add" value="New" onClick="newToDo()" />
</p>
<a href ="logout.jsp">Logout</a>

<%--<a href ="http://localhost:8080/BookAplication_war_exploded/userManagement?action=OUT">Logout</a>--%>
</body>
</html>