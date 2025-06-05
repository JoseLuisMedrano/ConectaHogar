package model.util; 

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {

    public static void main(String[] args) {
        Connection conn = null; 

        try {
            conn = ConexionBD.getConnection();

            if (conn != null) {
                System.out.println("¡Conexión a la base de datos exitosa!");

            } else {
                System.out.println("No se pudo establecer la conexión. (conn es nulo)");
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL al intentar conectar: " + e.getMessage());
            e.printStackTrace(); 
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(conn);
            System.out.println("Proceso de conexión finalizado. Conexión cerrada si se abrió.");
        }
    }
}
