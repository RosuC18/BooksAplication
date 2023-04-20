
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%

    HttpSession s = request.getSession();
    s.invalidate();
    response.sendRedirect("login.html");

%>
</body>
</html>
