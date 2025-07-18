package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;

import java.sql.*;
import java.util.Date;

public class UsuarioDAO {

    // La consulta base ahora une 'usuarios' y 'tecnicos' para traer todo en un solo viaje.
    private final String BASE_SELECT_SQL = "SELECT u.*, t.especialidad, t.disponibilidad, t.perfil_activo " +
                                           "FROM usuarios u " +
                                           "LEFT JOIN tecnicos t ON u.id_Usuario = t.id_Usuario ";

    /**
     * Obtiene un usuario completo (con sus datos de técnico si aplica) usando su correo.
     */
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = BASE_SELECT_SQL + "WHERE u.correoElectronico = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuarioCompleto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Crea el registro BASE en la tabla 'usuarios'. Es el primer paso del registro.
     */
    public boolean crearUsuarioBase(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getCorreoElectronico());
            pstmt.setString(4, usuario.getContrasena());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getDireccion());
            pstmt.setString(7, usuario.getDni());
            pstmt.setString(8, usuario.getTipoUsuario().name());

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId_Usuario(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mapea el resultado de la consulta completa (con JOIN) a un objeto Cliente o Tecnico.
     */
    private Usuario mapearUsuarioCompleto(ResultSet rs) throws SQLException {
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipoUsuario"));
        Usuario usuario;

        // Asignamos los datos comunes de la tabla 'usuarios'
        if (tipoUsuario == TipoUsuario.TECNICO) {
            Tecnico tecnico = new Tecnico();
            tecnico.setEspecialidad(rs.getString("especialidad"));
            tecnico.setDisponibilidad(rs.getString("disponibilidad"));
            tecnico.setPerfilActivo(rs.getBoolean("perfil_activo"));
            usuario = tecnico;
        } else {
            usuario = new Cliente();
        }

        usuario.setId_Usuario(rs.getInt("id_Usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setCorreoElectronico(rs.getString("correoElectronico"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setDni(rs.getString("dni"));
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
        
        return usuario;
    }
}