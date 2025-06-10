package com.mycompany.conectahogar.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger; // Importar la clase Logger
import org.slf4j.LoggerFactory; // Importar la clase LoggerFactory

public class ConexionBD {
    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class); // Instancia del logger

    // ¡Importante! En un entorno de producción, estas credenciales NO deben ir aquí.
    // Considera usar variables de entorno, JNDI o archivos de configuración externos.
    private static final String URL = "jdbc:mysql://localhost:3306/conectahogar";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    static {
        // Cargar el driver JDBC una sola vez cuando la clase es cargada
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver de MySQL JDBC cargado exitosamente.");
        } catch (ClassNotFoundException e) {
            logger.error("Error: No se encontró el driver de MySQL.", e);
            // Si el driver no se encuentra, la aplicación no puede funcionar
            throw new RuntimeException("Fallo al cargar el driver de MySQL.", e);
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            logger.debug("Conexión a la base de datos obtenida exitosamente.");
            return conexion;
        } catch (SQLException e) {
            logger.error("Error al obtener la conexión a la base de datos: " + e.getMessage(), e);
            throw e; // Relanza la excepción para que el DAO o Servlet la maneje
        }
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                logger.debug("Conexión a la base de datos cerrada exitosamente.");
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión a la base de datos: " + e.getMessage(), e);
            }
        }
    }
}