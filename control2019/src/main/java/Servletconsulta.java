import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import modelo.AccesoDatos;


/**
 * Servlet implementation class Servletconsulta
 */
@WebServlet({"/Servletconsulta", "/procesarconsulta"})
public class Servletconsulta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servletconsulta() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cod_cliente = request.getParameter("cod_cliente");
        int minimo;
        int maximo;

        try {
            minimo = Integer.parseInt(request.getParameter("minimo"));
            maximo = Integer.parseInt(request.getParameter("maximo"));
        } catch (NumberFormatException e) {
            String msg = "Los valores de los parámetros minimo y maximo deben ser numéricos";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/infoerror.jsp").forward(request, response);
            return;
        }
        if (minimo > maximo || minimo == 0 || maximo == 0) {
            String msg = "El valor de mínimo debe ser mayor que el de máximo";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/infoerror.jsp").forward(request, response);
            return;
        }


        AccesoDatos mimodelo = AccesoDatos.initModelo();

        String msg;

        if (!mimodelo.hayMovimientos(cod_cliente)) {
            msg = "El código de cliente " + cod_cliente + " no se encuentra en la base de datos ";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/infoerror.jsp").forward(request, response);
            return;
        }

        if (!mimodelo.hayMovimientoImporte (cod_cliente, maximo, minimo)) {
            msg = "El código de cliente " + cod_cliente + " no tiene movimientos con importes comprendidos entre " + minimo + " y " + maximo;
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/WEB-INF/infoerror.jsp").forward(request, response);
            return;
        }

        request.setAttribute("movimientos", mimodelo.obtenerListaMovimientos(cod_cliente, maximo, minimo));
        request.getRequestDispatcher("/WEB-INF/informovimiento.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
