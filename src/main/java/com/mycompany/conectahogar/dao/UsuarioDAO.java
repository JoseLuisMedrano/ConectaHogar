package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
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
    
    public Usuario obtenerUsuarioPorCredenciales(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correoElectronico = ? AND contrasena = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            
            logger.info("Ejecutando consulta de autenticación para: {}", correo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por credenciales: {}", e.getMessage(), e);
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
            logger.error("Error al obtener usuario por ID: {}", e.getMessage(), e);
        }
        return null;
    }
    
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (correoElectronico, contrasena, nombre, apellido, telefono, direccion, dni, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, usuario.getCorreoElectronico());
            pstmt.setString(2, usuario.getContrasena());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getDireccion());
            pstmt.setString(7, usuario.getDni());
            pstmt.setString(8, usuario.getTipoUsuario().name());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId_Usuario(generatedKeys.getInt(1));
                    }
                }
                logger.info("Usuario creado exitosamente: {}", usuario.getCorreoElectronico());
                
                // Si es técnico, crear registro adicional en tabla tecnicos
                if (usuario instanceof Tecnico) {
                    return crearTecnico((Tecnico) usuario);
                }
                
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error al crear usuario: {}", e.getMessage(), e);
        }
        return false;
    }
    
    private boolean crearTecnico(Tecnico tecnico) {
        String sql = "INSERT INTO tecnicos (id_Usuario, especialidad, disponibilidad, certificaciones, calificacionPromedio) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tecnico.getId_Usuario());
            pstmt.setString(2, tecnico.getEspecialidad());
            pstmt.setString(3, tecnico.getDisponibilidad());
            pstmt.setString(4, tecnico.getCertificaciones());
            pstmt.setDouble(5, tecnico.getCalificacionPromedio());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.error("Error al crear técnico: {}", e.getMessage(), e);
        }
        return false;
    }
    
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET correoElectronico = ?, contrasena = ?, nombre = ?, apellido = ?, telefono = ?, direccion = ?, dni = ?, tipoUsuario = ? WHERE id_Usuario = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getCorreoElectronico());
            pstmt.setString(2, usuario.getContrasena());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());
            pstmt.setString(5, usuario.getTelefono());
            pstmt.setString(6, usuario.getDireccion());
            pstmt.setString(7, usuario.getDni());
            pstmt.setString(8, usuario.getTipoUsuario().name());
            pstmt.setInt(9, usuario.getId_Usuario());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.error("Error al actualizar usuario: {}", e.getMessage(), e);
        }
        return false;
    }
    
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id_Usuario = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario: {}", e.getMessage(), e);
        }
        return false;
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
        } catch (SQLException e) {
            logger.error("Error al obtener todos los usuarios: {}", e.getMessage(), e);
        }
        return usuarios;
    }
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_Usuario");
        String correo = rs.getString("correoElectronico");
        String contrasena = rs.getString("contrasena");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String telefono = rs.getString("telefono");
        String direccion = rs.getString("direccion");
        String dni = rs.getString("dni");
        java.util.Date fechaRegistro = rs.getTimestamp("fechaRegistro");
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipoUsuario"));
        
        Usuario usuario;
        
        switch (tipoUsuario) {
            case TECNICO:
                usuario = new Tecnico();
                // Cargar datos específicos del técnico si es necesario
                cargarDatosTecnico((Tecnico) usuario, id);
                break;
            case CLIENTE:
                usuario = new Cliente();
                break;
            default:
                usuario = new Usuario();
                break;
        }
        
        usuario.setId_Usuario(id);
        usuario.setCorreoElectronico(correo);
        usuario.setContrasena(contrasena);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setDni(dni);
        usuario.setFechaRegistro(fechaRegistro);
        usuario.setTipoUsuario(tipoUsuario);
        
        return usuario;
    }
    
    private void cargarDatosTecnico(Tecnico tecnico, int idUsuario) {
        String sql = "SELECT * FROM tecnicos WHERE id_Usuario = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tecnico.setEspecialidad(rs.getString("especialidad"));
                    tecnico.setDisponibilidad(rs.getString("disponibilidad"));
                    tecnico.setCertificaciones(rs.getString("certificaciones"));
                    tecnico.setCalificacionPromedio(rs.getDouble("calificacionPromedio"));
                } else {
                    // Si no hay datos específicos del técnico, establecer valores por defecto
                    tecnico.setEspecialidad("No especificada");
                    tecnico.setDisponibilidad("No especificada");
                    tecnico.setCertificaciones("No especificadas");
                    tecnico.setCalificacionPromedio(0.0);
                    logger.warn("No se encontraron datos específicos para el técnico con ID: {}", idUsuario);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al cargar datos del técnico ID {}: {}", idUsuario, e.getMessage(), e);
            // Establecer valores por defecto en caso de error
            tecnico.setEspecialidad("Error al cargar");
            tecnico.setDisponibilidad("Error al cargar");
            tecnico.setCertificaciones("Error al cargar");
            tecnico.setCalificacionPromedio(0.0);
        }
    }
}