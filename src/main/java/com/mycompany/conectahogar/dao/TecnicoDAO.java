package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TecnicoDAO {
    private static final Logger logger = LoggerFactory.getLogger(TecnicoDAO.class);

    public boolean crearRegistroTecnico(Tecnico tecnico) {
        if (tecnico.getId_Usuario() == 0) {
            logger.error("No se puede crear el registro de técnico sin un ID de usuario base.");
            return false;
        }
        String sql = "INSERT INTO tecnicos (id_Usuario, especialidad, disponibilidad, certificaciones, calificacionPromedio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tecnico.getId_Usuario());
            stmt.setString(2, tecnico.getEspecialidad());
            stmt.setString(3, tecnico.getDisponibilidad());
            stmt.setString(4, tecnico.getCertificaciones());
            stmt.setDouble(5, tecnico.getCalificacionPromedio());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al crear registro de técnico: {}", e.getMessage(), e);
        }
        return false;
    }

    public List<Tecnico> obtenerTecnicosDisponiblesPorEspecialidad(String especialidad) {
        List<Tecnico> tecnicosDisponibles = new ArrayList<>();
        String sql = "SELECT * FROM usuarios u JOIN tecnicos t ON u.id_Usuario = t.id_Usuario " +
                     "WHERE u.tipoUsuario = 'TECNICO' AND t.especialidad = ? AND t.disponibilidad = 'Disponible'";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tecnicosDisponibles.add(mapearTecnico(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener técnicos disponibles por especialidad '{}'", especialidad, e);
        }
        return tecnicosDisponibles;
    }

    public boolean actualizarDisponibilidadTecnico(int idUsuario, String nuevaDisponibilidad) {
        String sql = "UPDATE tecnicos SET disponibilidad = ? WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaDisponibilidad);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar la disponibilidad del técnico {}: {}", idUsuario, e.getMessage(), e);
        }
        return false;
    }

    private Tecnico mapearTecnico(ResultSet rs) throws SQLException {
        Tecnico tecnico = new Tecnico();
        tecnico.setId_Usuario(rs.getInt("u.id_Usuario"));
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
        return tecnico;    
    }
    
    public void cargarDatosEspecificos(Tecnico tecnico) {
        String sql = "SELECT especialidad, disponibilidad, certificaciones, calificacionPromedio FROM tecnicos WHERE id_Usuario = ?";
        
        // Log para saber qué estamos buscando
        logger.info("Cargando datos específicos para el técnico ID: {}", tecnico.getId_Usuario());

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tecnico.getId_Usuario());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tecnico.setEspecialidad(rs.getString("especialidad"));
                    tecnico.setDisponibilidad(rs.getString("disponibilidad"));
                    tecnico.setCertificaciones(rs.getString("certificaciones"));
                    tecnico.setCalificacionPromedio(rs.getDouble("calificacionPromedio"));
                    
                    // Log para confirmar que se encontraron y asignaron los datos
                    logger.info("Datos cargados para técnico ID {}: Especialidad='{}'", tecnico.getId_Usuario(), tecnico.getEspecialidad());
                } else {
                    logger.warn("No se encontraron datos específicos en la tabla 'tecnicos' para el ID: {}", tecnico.getId_Usuario());
                }
            }
        } catch (SQLException e) {
            logger.error("Error al cargar datos específicos para el técnico {}", tecnico.getId_Usuario(), e);
        }
    }
}
