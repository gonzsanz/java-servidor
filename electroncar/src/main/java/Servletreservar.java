import modelo.AccesoDatos;
import modelo.Cliente;
import modelo.Vehiculo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Servletreservar", value = "/reservar")
public class Servletreservar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cod_cli = request.getParameter("cod_cli");
        String clave = request.getParameter("clave");
        String localidad = request.getParameter("localidad");

        AccesoDatos ac = AccesoDatos.init();

        Cliente cliente = ac.login(cod_cli, clave);
        Vehiculo vehiculo = ac.getVehiculo(localidad);

        if (cliente == null){
            String msg = "ERROR. Los valores de código de cliente y contraseña no son válidos";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }


        if (cliente.getCod_car() != 0){
            String msg = ">Ya tiene reservado el vehículo "+ cliente.getCod_car();
            request.setAttribute("cliente", cliente);
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/vista.jsp").forward(request, response);
            return;
        }
        if (vehiculo == null){
            String msg = ">Actualmente no hay vehículos disponibles en "+ localidad;
            request.setAttribute("cliente", cliente);
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/vista.jsp").forward(request, response);
            return;
        }

        request.setAttribute("cliente", cliente);
        request.setAttribute("localidad", localidad);
        String msg = ">Dispone en "+ localidad + " del vehículo " + vehiculo.getCod_car();
        request.setAttribute("msg", msg);
        ac.reservar(cliente, vehiculo);
        request.getRequestDispatcher("/WEB-INF/vista.jsp").forward(request, response);

    }
}
