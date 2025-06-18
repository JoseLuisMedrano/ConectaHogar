// Archivo: com/mycompany/conectahogar/dao/TecnicoDAO.java
package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TecnicoDAO {

    private static final Logger logger = LoggerFactory.getLogger(TecnicoDAO.class);
    private UsuarioDAO usuarioDAO;

    public TecnicoDAO() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean crearTecnico(Tecnico tecnico) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            tecnico.setTipoUsuario(TipoUsuario.TECNICO);
            
            if (!usuarioDAO.crearUsuario(tecnico)) {
                logger.error("Fallo al crear el usuario base para el técnico.");
                return false;
            }

            if (tecnico.getId_Usuario() == 0) {
                logger.error("La ID de usuario no fue generada después de crear el usuario base para el técnico.");
                return false;
            }

            String sql = "INSERT INTO tecnicos (id_Usuario, especialidad, disponibilidad, certificaciones, calificacionPromedio) VALUES (?, ?, ?, ?, ?)";
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, tecnico.getId_Usuario());
            stmt.setString(2, tecnico.getEspecialidad());
            stmt.setString(3, tecnico.getDisponibilidad()); // Es un String
            stmt.setString(4, tecnico.getCertificaciones()); // Añadido
            stmt.setDouble(5, tecnico.getCalificacionPromedio()); // Añadido

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    logger.info("Técnico con id_Usuario {} y especialidad {} creado exitosamente.", tecnico.getId_Usuario(), tecnico.getEspecialidad());
                    exito = true;
                }
                rs.close();
            }

        } catch (SQLException e) {
            logger.error("Error al crear técnico: " + e.getMessage(), e);
            usuarioDAO.eliminarUsuario(tecnico.getId_Usuario()); // Rollback si falla
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexionBD.cerrarConexion(conn);
            } catch (SQLException e) {
                logger.error("Error al cerrar recursos en crearTecnico: " + e.getMessage(), e);
            }
        }
        return exito;
    }

    public Tecnico obtenerTecnicoPorIdUsuario(int idUsuario) {
        String sql = "SELECT u.id_Usuario, u.nombre, u.apellido, u.correoElectronico, u.contrasena, u.telefono, u.direccion, u.dni, " +
                     "t.especialidad, t.disponibilidad, t.certificaciones, t.calificacionPromedio " +
                     "FROM usuarios u JOIN tecnicos t ON u.id_Usuario = t.id_Usuario WHERE u.id_Usuario = ? AND u.rol = ?";
        Tecnico tecnico = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, TipoUsuario.TECNICO.getRolBD());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tecnico = new Tecnico();
                    tecnico.setId_Usuario(rs.getInt("id_Usuario"));
                    tecnico.setNombre(rs.getString("nombre"));
                    tecnico.setApellido(rs.getString("apellido"));
                    tecnico.setCorreoElectronico(rs.getString("correoElectronico"));
                    tecnico.setContrasena(rs.getString("contrasena"));
                    tecnico.setTelefono(rs.getString("telefono"));
                    tecnico.setDireccion(rs.getString("direccion"));
                    tecnico.setDni(rs.getString("dni"));
                    tecnico.setTipoUsuario(TipoUsuario.TECNICO); 
                    tecnico.setEspecialidad(rs.getString("especialidad"));
                    tecnico.setDisponibilidad(rs.getString("disponibilidad"));
                    tecnico.setCertificaciones(rs.getString("certificaciones"));
                    tecnico.setCalificacionPromedio(rs.getDouble("calificacionPromedio"));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener técnico por ID de usuario {}: {}", idUsuario, e.getMessage(), e);
        }
        return tecnico;
    }

    public boolean actualizarTecnico(Tecnico tecnico) {
        boolean exitoUsuario = false;
        boolean exitoTecnico = false;

        exitoUsuario = usuarioDAO.actualizarUsuario(tecnico);

        if (exitoUsuario) {
            String sql = "UPDATE tecnicos SET especialidad = ?, disponibilidad = ?, certificaciones = ?, calificacionPromedio = ? WHERE id_Usuario = ?";
            try (Connection conn = ConexionBD.obtenerConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, tecnico.getEspecialidad());
                stmt.setString(2, tecnico.getDisponibilidad());
                stmt.setString(3, tecnico.getCertificaciones());
                stmt.setDouble(4, tecnico.getCalificacionPromedio());
                stmt.setInt(5, tecnico.getId_Usuario());

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    logger.info("Información específica del técnico con id_Usuario {} actualizada exitosamente.", tecnico.getId_Usuario());
                    exitoTecnico = true;
                }
            } catch (SQLException e) {
                logger.error("Error al actualizar información del técnico con id_Usuario {}: {}", tecnico.getId_Usuario(), e.getMessage(), e);
            }
        } else {
            logger.error("No se pudo actualizar el usuario base para el técnico con id_Usuario {}.", tecnico.getId_Usuario());
        }
        return exitoUsuario && exitoTecnico;
    }

    public boolean eliminarTecnico(int idUsuario) {
        boolean exitoTecnico = false;
        boolean exitoUsuario = false;

        String sql = "DELETE FROM tecnicos WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Información específica del técnico con id_Usuario {} eliminada exitosamente.", idUsuario);
                exitoTecnico = true;
            } else {
                logger.warn("No se encontró información de técnico para eliminar con id_Usuario {}.", idUsuario);
                exitoTecnico = true; // Consideramos éxito si no hay técnico pero sí usuario
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar información específica del técnico con id_Usuario {}: {}", idUsuario, e.getMessage(), e);
            return false;
        }

        if (exitoTecnico) {
            exitoUsuario = usuarioDAO.eliminarUsuario(idUsuario);
            if (!exitoUsuario) {
                logger.error("Fallo al eliminar el usuario base con ID {} después de eliminar su información de técnico.", idUsuario);
            }
        }
        return exitoTecnico && exitoUsuario;
    }
    
    public boolean actualizarDisponibilidadTecnico(int idUsuario, String disponibilidad) {
        String sql = "UPDATE tecnicos SET disponibilidad = ? WHERE id_Usuario = ?";
        boolean exito = false;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, disponibilidad);
            stmt.setInt(2, idUsuario);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Disponibilidad del técnico con id_Usuario {} actualizada a {}.", idUsuario, disponibilidad);
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar disponibilidad del técnico con id_Usuario {}: {}", idUsuario, e.getMessage(), e);
        }
        return exito;
    }

    public List<Tecnico> obtenerTecnicosDisponiblesPorEspecialidad(String especialidad) {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT u.id_Usuario, u.nombre, u.apellido, u.correoElectronico, u.telefono, u.direccion, u.dni, " +
                     "t.especialidad, t.disponibilidad, t.certificaciones, t.calificacionPromedio " +
                     "FROM usuarios u JOIN tecnicos t ON u.id_Usuario = t.id_Usuario " +
                     "WHERE t.especialidad = ? AND t.disponibilidad = 'Disponible' AND u.rol = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad);
            stmt.setString(2, TipoUsuario.TECNICO.getRolBD());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tecnico tecnico = new Tecnico();
                    tecnico.setId_Usuario(rs.getInt("id_Usuario"));
                    tecnico.setNombre(rs.getString("nombre"));
                    tecnico.setApellido(rs.getString("apellido"));
                    tecnico.setCorreoElectronico(rs.getString("correoElectronico"));
                    tecnico.setTelefono(rs.getString("telefono"));
                    tecnico.setDireccion(rs.getString("direccion"));
                    tecnico.setDni(rs.getString("dni"));
                    tecnico.setTipoUsuario(TipoUsuario.TECNICO);
                    tecnico.setEspecialidad(rs.getString("especialidad"));
                    tecnico.setDisponibilidad(rs.getString("disponibilidad"));
                    tecnico.setCertificaciones(rs.getString("certificaciones"));
                    tecnico.setCalificacionPromedio(rs.getDouble("calificacionPromedio"));
                    tecnicos.add(tecnico);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener técnicos disponibles por especialidad {}: {}", especialidad, e.getMessage(), e);
        }
        return tecnicos;
    }
}