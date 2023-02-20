package controlador;

import modelo.Cliente;
import modelo.Pedido;
import modelo.AccesoDatos;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Servletpedidos", value = "/verpedidos")
public class Servletpedidos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Servletpedidos() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre;
        String clave;
        AccesoDatos ac = AccesoDatos.initModelo();
        try {
            nombre = request.getParameter("nombre");
            clave = request.getParameter("clave");

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Nombre o clave no validos";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
            return;
        }

        Cliente cli = ac.checkUser(nombre, clave);
        if (cli == null) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
            ArrayList<Pedido> pedidos = ac.getPedidos(cli.getCod_cliente());
            ac.incrementarVeces(cli.getNombre());
            request.setAttribute("pedidos",pedidos);
            request.setAttribute("cliente",cli);
            request.getRequestDispatcher("/WEB-INF/infocliente.jsp").forward(request, response);

    }

}
