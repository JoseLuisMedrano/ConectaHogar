package model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/conectahogar?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static final Logger LOGGER = Logger.getLogger(ConexionBD.class.getName());

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.log(Level.INFO, "Driver JDBC de MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "ERROR: No se pudo cargar el driver JDBC de MySQL. Asegúrate de que la dependencia esté en tu pom.xml y haya sido descargada correctamente.", e);
            throw new RuntimeException("Error fatal: No se pudo cargar el driver JDBC de MySQL.", e);
        }
    }

    private ConexionBD() {
       
    }


    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.log(Level.INFO, "Conexión a la base de datos obtenida exitosamente.");
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la conexión a la base de datos en URL: " + URL + " con usuario: " + USER, e);
            throw e; 
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                LOGGER.log(Level.INFO, "Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión a la base de datos.", e);
            }
        }
    }

    public static void close(java.sql.PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar PreparedStatement.", e);
            }
        }
    }

    public static void close(java.sql.ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar ResultSet.", e);
            }
        }
    }
}