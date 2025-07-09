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
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);
    private final TecnicoDAO tecnicoDAO;

    public UsuarioDAO() {
        this.tecnicoDAO = new TecnicoDAO();
    }

    public boolean crearUsuarioBase(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correoElectronico, contrasena, telefono, direccion, dni, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getCorreoElectronico());
            pstmt.setString(4, usuario.getContrasena()); // Recibe la contraseña ya hasheada
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getDireccion());
            pstmt.setString(7, usuario.getDni());
            pstmt.setString(8, usuario.getTipoUsuario().name());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Obtenemos el ID generado para el nuevo usuario
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId_Usuario(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Fallo al crear usuario, no se obtuvo el ID.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        // Lee el tipo de usuario primero para saber qué objeto crear (Cliente o Tecnico)
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipoUsuario"));

        Usuario usuario;
        if (tipoUsuario == TipoUsuario.TECNICO) {
            // Si es un técnico, creamos un objeto Tecnico
            Tecnico tecnico = new Tecnico();

            // --- LÓGICA NUEVA PARA CARGAR DATOS DEL TÉCNICO ---
            // Buscamos los datos adicionales en la tabla 'tecnicos'
            try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement("SELECT especialidad, disponibilidad, perfil_activo FROM tecnicos WHERE id_Usuario = ?")) {

                pstmt.setInt(1, rs.getInt("id_Usuario"));
                try (ResultSet tecnicoRs = pstmt.executeQuery()) {
                    if (tecnicoRs.next()) {
                        tecnico.setEspecialidad(tecnicoRs.getString("especialidad"));
                        tecnico.setDisponibilidad(tecnicoRs.getString("disponibilidad"));
                        tecnico.setPerfilActivo(tecnicoRs.getBoolean("perfil_activo"));
                    }
                }
            }
            usuario = tecnico;

        } else {
            usuario = new Cliente();
        }

        usuario.setId_Usuario(rs.getInt("id_Usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setCorreoElectronico(rs.getString("correoElectronico"));
        usuario.setContrasena(rs.getString("contrasena")); // Importante para la gestión de sesión
        usuario.setTipoUsuario(tipoUsuario);

        // --- AQUÍ LA CORRECCIÓN IMPORTANTE ---
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setDni(rs.getString("dni"));

        // Manejo de la fecha
        Timestamp fechaRegistroTimestamp = rs.getTimestamp("fechaRegistro");
        if (fechaRegistroTimestamp != null) {
            usuario.setFechaRegistro(new Date(fechaRegistroTimestamp.getTime()));
        }

        return usuario;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario {}: {}", id, e.getMessage(), e);
        }
        return false;
    }
}
