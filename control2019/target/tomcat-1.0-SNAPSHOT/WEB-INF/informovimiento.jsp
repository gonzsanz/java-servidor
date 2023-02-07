<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%-- Importo las clases necesarias --%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modelo.Movimiento" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Consulta de movimientos </title>
</head>
<body>
<table border="1">
    <tr>
        <th>num_mov</th>
        <th>cod_cliente</th>
        <th>concepto</th>
        <th>importe</th>
    </tr>
    <%

    ArrayList<Movimiento> lista = (ArrayList<Movimiento>) request.getAttribute("movimientos");

    for (Movimiento m : lista) {
        out.println("<tr>");
        out.println("<td>" + m.getNum_mov() + "</td>");
        out.println("<td>" + m.getCod_cliente() + "</td>");
        out.println("<td>" + m.getConcepto() + "</td>");
        out.println("<td>" + m.getImporte() + "</td>");
        out.println("</tr>");
    }
    %>

</table>
    <p>Se han encontrado un total de <b><%= lista.size() %></b> movimientos</p>
</body>
</html>