<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 21/02/2023
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.*" %>
<html>
<head>
    <title>BIENVENIDO</title>
    <% Cliente cli = (Cliente) request.getAttribute("cliente"); %>
</head>
<body>
    <h4>BIENVENIDO/A <%= cli.getNombre()  %></h4>
    <p><%= request.getAttribute("msg")%></p>

</body>
</html>
