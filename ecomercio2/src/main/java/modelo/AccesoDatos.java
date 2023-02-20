package modelo;

import java.sql.*;
import java.util.ArrayList;

public class AccesoDatos {
    private static AccesoDatos modelo = null;
    private static Connection conexion = null;
    private PreparedStatement stmt_pedidos = null;
    private PreparedStatement stmt_incrementar = null;
    private PreparedStatement stmt_user = null;


    // Conecto a la base de datos y preparo las sentencias
    private AccesoDatos() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/etienda", "root", "");
            this.stmt_pedidos = conexion.prepareStatement("SELECT * FROM pedidos WHERE cod_cliente = ?");
            this.stmt_incrementar = conexion.prepareStatement("UPDATE clientes SET veces = veces + 1 WHERE cod_cliente = ?");
            this.stmt_user = conexion.prepareStatement("SELECT * FROM clientes WHERE nombre = ? AND clave = ?");
        } catch (Exception e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized AccesoDatos initModelo() {
        if (modelo == null) {
            modelo = new AccesoDatos();
        }
        return modelo;
    }
    // Cierro la conexión
    public static void closeModelo() {
        if (modelo != null) {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Devuelvo los pedidos de un cliente
    public ArrayList<Pedido> getPedidos(int cod_cliente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            this.stmt_pedidos.setInt(1, cod_cliente);
            ResultSet rs = this.stmt_pedidos.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setNumped(rs.getInt("numped"));
                pedido.setCod_cliente(rs.getInt("cod_cliente"));
                pedido.setProducto(rs.getString("producto"));
                pedido.setPrecio(rs.getInt("precio"));
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los pedidos: " + e.getMessage());
            e.printStackTrace();
        }
        return pedidos;
    }

    // comprobar si el nombre y clave estan en la base de datos
    public Cliente checkUser(String nombre, String clave) {
        Cliente cli = null;

        try {
            this.stmt_user.setString(1, nombre);
            this.stmt_user.setString(2, clave);
            ResultSet rs = this.stmt_user.executeQuery();
            if (rs.next()) {
                cli = new Cliente();
                cli.setCod_cliente(rs.getInt("cod_cliente"));
                cli.setNombre(rs.getString("nombre"));
                cli.setVeces(rs.getInt("veces"));


            }
        } catch (Exception e) {
            System.err.println("Error al comprobar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return cli;
    }

    public int incrementarVeces(String nombre){
        int veces = 0;
        try {
            this.stmt_incrementar.setString(1, nombre);
            veces = this.stmt_incrementar.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return veces;

    }


}
