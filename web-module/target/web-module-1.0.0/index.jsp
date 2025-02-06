<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  if (session.getAttribute("login") == null) {
    response.sendRedirect("login.jsp");
  }
%>

<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>