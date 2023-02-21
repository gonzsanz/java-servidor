package modelo;

import java.sql.*;

public class AccesoDatos {
    private static AccesoDatos modelo = null;
    private static Connection conexion = null;
    private PreparedStatement stmt_user = null;
    private PreparedStatement stmt_coche = null;
    private PreparedStatement stmt_updatecoche = null;
    private PreparedStatement stmt_updatecliente = null;

    public static synchronized AccesoDatos init() {
        if (modelo == null) {
            modelo = new AccesoDatos();
        }
        return modelo;
    }


    private AccesoDatos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // CAMBIAR LA CONTRASEÑA EN CASO DE SER NECESARIO, LA MIA NO ES ROOT
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/mycardb", "root", "");

            this.stmt_user = conexion.prepareStatement("SELECT * FROM clientes WHERE cod_cli = ? AND clave = ?");
            this.stmt_coche = conexion.prepareStatement("SELECT * FROM vehiculos WHERE localidad = ?  AND bateria > 0 AND estado = 0 ORDER BY bateria DESC LIMIT 1");
        } catch (Exception e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Compruebo que el usuario existe
    public Cliente login(String cod_cli, String clave) {
        Cliente cliente = null;

        try {
            this.stmt_user.setString(1, cod_cli);
            this.stmt_user.setString(2, clave);
            ResultSet rs = this.stmt_user.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCod_cli(rs.getString("cod_cli"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setClave(rs.getString("clave"));
                cliente.setCod_car(rs.getInt("cod_car"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }


    // Compruebo que el coche existe
    public Vehiculo getVehiculo(String localidad) {
        Vehiculo vehiculo = null;

        try {

            this.stmt_coche.setString(1, localidad);
            ResultSet rs = this.stmt_coche.executeQuery();

            if (rs.next()) {
                vehiculo = new Vehiculo();
                vehiculo.setCod_car(rs.getInt("cod_car"));
                vehiculo.setLocalidad(rs.getString("localidad"));
                vehiculo.setEstado(rs.getInt("estado"));
                vehiculo.setBateria(rs.getInt("bateria"));
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return vehiculo;
    }

    // Actualizo las tablas cuando reservo
    public void reservar(Cliente cli, Vehiculo vc) {
        try {
            this.stmt_updatecoche = conexion.prepareStatement("UPDATE vehiculos SET estado = 1 WHERE cod_car = ?");
            this.stmt_updatecliente = conexion.prepareStatement("UPDATE clientes SET cod_car = ? WHERE cod_cli = ?");

            this.stmt_updatecoche.setInt(1, vc.getCod_car());
            this.stmt_updatecliente.setInt(1, vc.getCod_car());
            this.stmt_updatecliente.setString(2, cli.getCod_cli());

            this.stmt_updatecoche.executeUpdate();
            this.stmt_updatecliente.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
