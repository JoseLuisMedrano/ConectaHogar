// Archivo: com/mycompany/conectahogar/dao/ClienteDAO.java
package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClienteDAO {

    private static final Logger logger = LoggerFactory.getLogger(ClienteDAO.class);
    private UsuarioDAO usuarioDAO;

    public ClienteDAO() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean crearCliente(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            // ¡Cambiado a setTipoUsuario()!
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
            
            if (!usuarioDAO.crearUsuario(cliente)) {
                logger.error("Fallo al crear el usuario base para el cliente.");
                return false;
            }

            if (cliente.getId_Usuario() == 0) {
                logger.error("La ID de usuario no fue generada después de crear el usuario base para el cliente.");
                return false;
            }

            String sql = "INSERT INTO clientes (id_Usuario, direccion) VALUES (?, ?)";
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, cliente.getId_Usuario());
            stmt.setString(2, cliente.getDireccion());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    logger.info("Cliente con id_Usuario {} y dirección {} creado exitosamente.", cliente.getId_Usuario(), cliente.getDireccion());
                    exito = true;
                }
                rs.close();
            }

        } catch (SQLException e) {
            logger.error("Error al crear cliente: " + e.getMessage(), e);
            usuarioDAO.eliminarUsuario(cliente.getId_Usuario());
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexionBD.cerrarConexion(conn);
            } catch (SQLException e) {
                logger.error("Error al cerrar recursos en crearCliente: " + e.getMessage(), e);
            }
        }
        return exito;
    }

    // Método completado: obtenerClientePorIdUsuario
    public Cliente obtenerClientePorIdUsuario(int idUsuario) {
        String sql = "SELECT u.id_Usuario, u.nombre, u.apellido, u.correoElectronico, u.contrasena, u.telefono, u.direccion, u.dni, c.direccion as cliente_direccion " + // Asegúrate de seleccionar direccion del usuario y del cliente si es diferente
                     "FROM usuarios u JOIN clientes c ON u.id_Usuario = c.id_Usuario WHERE u.id_Usuario = ? AND u.rol = ?";
        Cliente cliente = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, TipoUsuario.CLIENTE.getRolBD()); // Usando el valor en la BD del enum

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Nota: Si la 'direccion' del cliente es la misma que la del usuario,
                    // y el campo 'direccion' de Cliente fue removido (como sugerí),
                    // entonces deberías usar rs.getString("direccion") directamente para el usuario.
                    // Si cliente_direccion existe y es una dirección específica del cliente,
                    // entonces necesitarías un campo 'direccionCliente' en la clase Cliente.
                    // Por simplicidad, asumiré que la dirección de Usuario es la principal.
                    cliente = new Cliente(
                        rs.getInt("id_Usuario"),
                        rs.getString("correoElectronico"),
                        rs.getString("contrasena"), // Considera no cargar la contraseña
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("direccion"), // Dirección del Usuario
                        rs.getString("dni"),
                        null // fechaRegistro no se selecciona en este query, se puede cargar después o ajustar el query
                    );
                    // Si tu modelo Cliente tiene una dirección propia (cliente_direccion), la setearías aquí.
                    // cliente.setDireccionCliente(rs.getString("cliente_direccion")); 

                    logger.info("Cliente con id_Usuario {} encontrado.", idUsuario);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener cliente por ID de usuario {}: {}", idUsuario, e.getMessage(), e);
        }
        return cliente;
    }

    // Aquí irían otros métodos como actualizarCliente, eliminarCliente, etc.,
    // que también necesitarían las mismas correcciones de 'setTipoUsuario'/'getTipoUsuario' y 'dni'.
    // Dado que el resto del archivo fue truncado, no puedo corregirlo aquí,
    // pero aplica las mismas lógicas.
    public boolean actualizarCliente(Cliente cliente) {
        boolean exitoUsuario = false;
        boolean exitoCliente = false;

        // 1. Actualizar la información del usuario base
        exitoUsuario = usuarioDAO.actualizarUsuario(cliente);

        if (exitoUsuario) {
            // 2. Actualizar la información específica del cliente (si la tabla 'clientes' tiene más campos además de id_Usuario y direccion)
            String sql = "UPDATE clientes SET direccion = ? WHERE id_Usuario = ?";
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, cliente.getDireccion()); // getDireccion heredado de Usuario
                stmt.setInt(2, cliente.getId_Usuario());

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    logger.info("Información específica del cliente con id_Usuario {} actualizada exitosamente.", cliente.getId_Usuario());
                    exitoCliente = true;
                }
            } catch (SQLException e) {
                logger.error("Error al actualizar información del cliente con id_Usuario {}: {}", cliente.getId_Usuario(), e.getMessage(), e);
            }
        } else {
            logger.error("No se pudo actualizar el usuario base para el cliente con id_Usuario {}.", cliente.getId_Usuario());
        }
        return exitoUsuario && exitoCliente; // Ambas operaciones deben ser exitosas
    }

    public boolean eliminarCliente(int idUsuario) {
        boolean exitoCliente = false;
        boolean exitoUsuario = false;

        // 1. Eliminar la información específica del cliente
        String sql = "DELETE FROM clientes WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Información específica del cliente con id_Usuario {} eliminada exitosamente.", idUsuario);
                exitoCliente = true;
            } else {
                logger.warn("No se encontró información de cliente para eliminar con id_Usuario {}.", idUsuario);
                exitoCliente = true; // Considerar éxito si no hay información específica, porque el objetivo es eliminar el usuario
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar información específica del cliente con id_Usuario {}: {}", idUsuario, e.getMessage(), e);
            return false;
        }

        // 2. Eliminar el usuario base asociado
        if (exitoCliente) {
            exitoUsuario = usuarioDAO.eliminarUsuario(idUsuario);
            if (!exitoUsuario) {
                logger.error("Fallo al eliminar el usuario base con ID {} después de eliminar su información de cliente.", idUsuario);
            }
        }
        return exitoCliente && exitoUsuario;
    }
}