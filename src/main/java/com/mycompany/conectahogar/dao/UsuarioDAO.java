// Archivo: com/mycompany/conectahogar/dao/UsuarioDAO.java
package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario; // ¡Importante para el enum!

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Para obtener claves generadas
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public boolean crearUsuario(Usuario usuario) {
        // Asegúrate de que el 'direccion' también se incluya en el SQL si lo mapeas en la BD
        String sql = "INSERT INTO usuarios (nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exito = false;

        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getDireccion()); // ¡Añadido direccion!
            stmt.setString(7, usuario.getDni());
            stmt.setString(8, usuario.getTipoUsuario().getRolBD()); // ¡Cambiado a getTipoUsuario() y getRolBD()!

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId_Usuario(rs.getInt(1));
                    logger.info("Usuario creado exitosamente con ID: {}", usuario.getId_Usuario());
                    exito = true;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear usuario: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexionBD.cerrarConexion(conn);
            } catch (SQLException e) {
                logger.error("Error al cerrar recursos en crearUsuario: " + e.getMessage(), e);
            }
        }
        return exito;
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        // Asegúrate de seleccionar 'direccion' también si existe en tu tabla
        String sql = "SELECT id_Usuario, nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, rol FROM usuarios WHERE id_Usuario = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreoElectronico(rs.getString("correoElectronico"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setDireccion(rs.getString("direccion")); // ¡Añadido direccion!
                    usuario.setDni(rs.getString("dni"));
                    // ¡Cambiado a setTipoUsuario y TipoUsuario.fromString!
                    usuario.setTipoUsuario(TipoUsuario.fromString(rs.getString("rol"))); 
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por ID {}: {}", idUsuario, e.getMessage(), e);
        }
        return usuario;
    }
    
    public Usuario obtenerUsuarioPorCorreo(String correoElectronico) {
        // Asegúrate de seleccionar 'direccion' también si existe en tu tabla
        String sql = "SELECT id_Usuario, nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, rol FROM usuarios WHERE correoElectronico = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correoElectronico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreoElectronico(rs.getString("correoElectronico"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setDireccion(rs.getString("direccion")); // ¡Añadido direccion!
                    usuario.setDni(rs.getString("dni"));
                    // ¡Cambiado a setTipoUsuario y TipoUsuario.fromString!
                    usuario.setTipoUsuario(TipoUsuario.fromString(rs.getString("rol"))); 
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por correo electrónico {}: {}", correoElectronico, e.getMessage(), e);
        }
        return usuario;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        // Asegúrate de que el 'direccion' también se actualice si lo mapeas en la BD
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correoElectronico = ?, contrasena = ?, telefono = ?, direccion = ?, dni = ?, rol = ? WHERE id_Usuario = ?";
        boolean exito = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getDireccion()); // ¡Añadido direccion!
            stmt.setString(7, usuario.getDni());
            // ¡Cambiado a getTipoUsuario() y getRolBD()!
            stmt.setString(8, usuario.getTipoUsuario().getRolBD());
            stmt.setInt(9, usuario.getId_Usuario());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Usuario con ID {} actualizado exitosamente.", usuario.getId_Usuario());
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar usuario con ID {}: {}", usuario.getId_Usuario(), e.getMessage(), e);
        }
        return exito;
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_Usuario = ?";
        boolean exito = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Usuario con ID {} eliminado exitosamente.", idUsuario);
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario con ID {}: {}", idUsuario, e.getMessage(), e);
        }
        return exito;
    }

    public Usuario validarCredenciales(String correoElectronico, String contrasena) {
        // Asegúrate de seleccionar 'direccion' también si existe en tu tabla
        String sql = "SELECT id_Usuario, nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, rol FROM usuarios WHERE correoElectronico = ? AND contrasena = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correoElectronico);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreoElectronico(rs.getString("correoElectronico"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setDireccion(rs.getString("direccion")); // ¡Añadido direccion!
                    usuario.setDni(rs.getString("dni"));
                    // ¡Cambiado a setTipoUsuario y TipoUsuario.fromString!
                    usuario.setTipoUsuario(TipoUsuario.fromString(rs.getString("rol"))); 
                    logger.info("Credenciales válidas para el usuario: {}", correoElectronico);
                } else {
                    logger.warn("Intento de login fallido para el correo: {}", correoElectronico);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al validar credenciales para el correo {}: {}", correoElectronico, e.getMessage(), e);
        }
        return usuario;
    }
}