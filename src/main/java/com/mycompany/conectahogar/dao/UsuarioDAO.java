package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.util.SecurityUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public boolean crearUsuario(Usuario usuario) {
        // Se añaden los nuevos campos a la consulta SQL
        String sql = "INSERT INTO usuarios (nombre, apellido, dni, correoElectronico, contrasena, telefono, direccion, tipoUsuario, edad, sexo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String hashedPassword = SecurityUtil.hashPassword(usuario.getContrasena());

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getDni());
            pstmt.setString(4, usuario.getCorreoElectronico());
            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, usuario.getTelefono());
            pstmt.setString(7, usuario.getDireccion());
            pstmt.setString(8, usuario.getTipoUsuario().name());
            pstmt.setInt(9, usuario.getEdad()); // ¡NUEVO!
            pstmt.setString(10, usuario.getSexo()); // ¡NUEVO!

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId_Usuario(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear usuario: {}", e.getMessage(), e);
        }
        return false;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        // ... (el código del switch para instanciar Cliente/Tecnico)
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipoUsuario"));
        Usuario usuario;
        switch (tipoUsuario) {
            case TECNICO -> usuario = new Tecnico();
            case CLIENTE -> usuario = new Cliente();
            default -> usuario = new Usuario();
        }
        // ... (código para setear los campos existentes)
        usuario.setId_Usuario(rs.getInt("id_Usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setCorreoElectronico(rs.getString("correoElectronico"));
        usuario.setEdad(rs.getInt("edad"));
        usuario.setSexo(rs.getString("sexo"));
        
        usuario.setTipoUsuario(tipoUsuario);
        
        return usuario;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por ID {}: {}", id, e.getMessage(), e);
        }
        return null;
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correoElectronico = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por correo: {}", e.getMessage(), e);
        }
        return null;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, dni = ?, correoElectronico = ?, telefono = ?, direccion = ? WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getDni());
            pstmt.setString(4, usuario.getCorreoElectronico());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getDireccion());
            pstmt.setInt(7, usuario.getId_Usuario());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar usuario {}: {}", usuario.getId_Usuario(), e.getMessage(), e);
        }
        return false;
    }
    
    public boolean eliminarUsuario(int id) {
        // La BD está configurada con ON DELETE CASCADE, por lo que al eliminar
        // el usuario, se eliminarán los registros de cliente o tecnico asociados.
        String sql = "DELETE FROM usuarios WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario {}: {}", id, e.getMessage(), e);
        }
        return false;
    }
}