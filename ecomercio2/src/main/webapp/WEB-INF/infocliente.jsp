<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modelo.Pedido" %>
<%@ page import="modelo.Cliente" %>
<html>
<head>
    <title>CLIENTE</title>
</head>
<body>
<%
    ArrayList<Pedido> pedidos = (ArrayList<Pedido>) request.getAttribute("pedidos");
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
  <h3>Bienvenido usuario: <%=cliente.getNombre() %>. Has entrado <%=cliente.getVeces() %> veces en nuestra web</h3>
  <h5>Esta es su lista de pedidos del cliente con código <%= cliente.getCod_cliente() %> </h5>

    <table border="1px solid black">
        <tr>
            <th>Numped</th>
            <th>cod_cliente</th>
            <th>producto</th>
            <th>precio</th>
        </tr>
        <% for (Pedido p : pedidos) { %>
            <tr>
                <td><%= p.getNumped() %></td>
                <td><%= p.getCod_cliente() %></td>
                <td><%= p.getProducto() %></td>
                <td><%= p.getPrecio() %></td>
            </tr>
            <%}%>
    </table>
</body>
</html>
