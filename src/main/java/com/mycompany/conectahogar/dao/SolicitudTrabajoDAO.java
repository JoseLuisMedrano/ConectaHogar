package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitudTrabajoDAO {
    private static final Logger logger = LoggerFactory.getLogger(SolicitudTrabajoDAO.class);

    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        String sql = "INSERT INTO solicitudes_trabajo (id_Cliente, descripcion, servicio, precioSugerido, estado, fechaCreacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, solicitud.getIdCliente());
            stmt.setString(2, solicitud.getDescripcion());
            stmt.setString(3, solicitud.getServicio().name());
            stmt.setDouble(4, solicitud.getPrecioSugerido());
            stmt.setString(5, solicitud.getEstado().name());
            stmt.setTimestamp(6, new Timestamp(solicitud.getFechaCreacion().getTime()));
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        solicitud.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear solicitud de trabajo: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean asignarTecnicoASolicitud(int idSolicitud, int idTecnico, Double precioOfrecido) {
        String sql = "UPDATE solicitudes_trabajo SET id_Tecnico = ?, precioFinal = ?, estado = ? WHERE id_Solicitud = ? AND estado = 'PENDIENTE'";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTecnico);
            stmt.setDouble(2, precioOfrecido);
            stmt.setString(3, EstadoSolicitud.ACEPTADA.name());
            stmt.setInt(4, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error SQL al asignar técnico {} a la solicitud {}: {}", idTecnico, idSolicitud, e.getMessage(), e);
        }
        return false;
    }
    
    public boolean actualizarSolicitud(SolicitudTrabajo solicitud) {
        String sql = "UPDATE solicitudes_trabajo SET id_Tecnico = ?, descripcion = ?, precioFinal = ?, estado = ?, fechaFinalizacion = ? WHERE id_Solicitud = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, solicitud.getIdTecnico());
            stmt.setString(2, solicitud.getDescripcion());
            stmt.setObject(3, solicitud.getPrecioFinal());
            stmt.setString(4, solicitud.getEstado().name());
            stmt.setTimestamp(5, solicitud.getFechaFinalizacion() != null ? new Timestamp(solicitud.getFechaFinalizacion().getTime()) : null);
            stmt.setInt(6, solicitud.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar solicitud de trabajo {}: {}", solicitud.getId(), e.getMessage(), e);
        }
        return false;
    }
    
    public SolicitudTrabajo obtenerSolicitudPorId(int idSolicitud) {
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_Solicitud = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearSolicitud(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitud por ID {}: {}", idSolicitud, e.getMessage(), e);
        }
        return null;
    }
    
    public List<SolicitudTrabajo> obtenerSolicitudesPorCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_Cliente = ? ORDER BY fechaCreacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes para el cliente {}: {}", idCliente, e.getMessage(), e);
        }
        return solicitudes;
    }

    private SolicitudTrabajo mapearSolicitud(ResultSet rs) throws SQLException {
        SolicitudTrabajo solicitud = new SolicitudTrabajo();
        solicitud.setId(rs.getInt("id_Solicitud"));
        solicitud.setIdCliente(rs.getInt("id_Cliente"));
        solicitud.setIdTecnico((Integer) rs.getObject("id_Tecnico"));
        solicitud.setDescripcion(rs.getString("descripcion"));
        solicitud.setServicio(Servicio.valueOf(rs.getString("servicio")));
        solicitud.setPrecioSugerido(rs.getDouble("precioSugerido"));
        solicitud.setPrecioFinal(rs.getDouble("precioFinal"));
        solicitud.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
        solicitud.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
        solicitud.setFechaFinalizacion(rs.getTimestamp("fechaFinalizacion"));
        return solicitud;
    }
}