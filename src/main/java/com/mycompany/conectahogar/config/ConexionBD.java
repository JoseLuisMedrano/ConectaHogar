package com.mycompany.conectahogar.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConexionBD {
    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class);

    // URL mejorada con parámetros para zona horaria y SSL
    private static final String URL = "jdbc:mysql://localhost:3306/conectahogar?serverTimezone=UTC&useSSL=false";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // Asegúrate que esta sea tu contraseña correcta

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver de MySQL JDBC cargado exitosamente.");
        } catch (ClassNotFoundException e) {
            logger.error("Error: No se encontró el driver de MySQL.", e);
            throw new RuntimeException("Fallo al cargar el driver de MySQL.", e);
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            logger.info("Conexión a la base de datos establecida exitosamente.");
            return conexion;
        } catch (SQLException e) {
            logger.error("Error al obtener la conexión a la base de datos: " + e.getMessage(), e);
            throw e;
        }
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                logger.info("Conexión a la base de datos cerrada exitosamente.");
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión a la base de datos: " + e.getMessage(), e);
            }
        }
    }
}