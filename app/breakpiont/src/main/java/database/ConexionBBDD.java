package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBBDD {
    private static Connection conexion;

    public static Connection getConexion(){
        try {
            if(conexion == null || conexion.isClosed()){
                crearConexion();
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar el estado de la conexión");
            System.out.println(e.getMessage());
        }
        return conexion;
    }

    private static void crearConexion(){
        String database = "breakpoint";
        String user = "root";
        String pass = "";
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database, user, pass);
        } catch (SQLException e) {
            System.out.println("Error en la conexión con la base de datos");
            System.out.println(e.getMessage());
        }
    }
}