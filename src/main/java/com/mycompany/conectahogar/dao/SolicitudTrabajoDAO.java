package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SolicitudTrabajoDAO {

    private final String BASE_SELECT_SQL
            = "SELECT s.*, u.nombre AS nombre_tecnico, u.apellido AS apellido_tecnico "
            + "FROM solicitudes_trabajo s "
            + "LEFT JOIN usuarios u ON s.id_tecnico = u.id_Usuario ";

    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        // Esta consulta ya está correcta (sin precio_sugerido)
        String sql = "INSERT INTO solicitudes_trabajo (id_cliente, servicio, descripcion, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, solicitud.getIdCliente());
            pstmt.setString(2, solicitud.getServicio().name());
            pstmt.setString(3, solicitud.getDescripcion());
            pstmt.setString(4, solicitud.getEstado().name());
            pstmt.setTimestamp(5, new Timestamp(solicitud.getFechaCreacion().getTime()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean asignarTecnicoASolicitud(int idSolicitud, int idTecnico, double precioFinal) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ?, id_tecnico = ?, precio_final = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, EstadoSolicitud.ASIGNADA.name());
            pstmt.setInt(2, idTecnico);
            pstmt.setDouble(3, precioFinal);
            pstmt.setInt(4, idSolicitud);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hacerContraoferta(int idSolicitud, double nuevoPrecio, int idTecnico) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ?, precio_final = ?, id_tecnico = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, EstadoSolicitud.CONTRAOFERTA.name());
            pstmt.setDouble(2, nuevoPrecio);
            pstmt.setInt(3, idTecnico);
            pstmt.setInt(4, idSolicitud);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean aceptarContraoferta(int idSolicitud) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, EstadoSolicitud.ASIGNADA.name());
            pstmt.setInt(2, idSolicitud);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rechazarContraoferta(int idSolicitud) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, EstadoSolicitud.CANCELADA.name());
            pstmt.setInt(2, idSolicitud);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SolicitudTrabajo> obtenerSolicitudesPorEstado(EstadoSolicitud estado) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE s.estado = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estado.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public List<SolicitudTrabajo> obtenerSolicitudesPorCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE s.id_cliente = ? ORDER BY s.fecha_creacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public List<SolicitudTrabajo> obtenerSolicitudesPorTecnico(int idTecnico) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE s.id_tecnico = ? ORDER BY s.fecha_creacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTecnico);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public List<SolicitudTrabajo> obtenerContraofertasParaCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = BASE_SELECT_SQL + "WHERE s.id_cliente = ? AND s.estado = 'CONTRAOFERTA'";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                solicitudes.add(mapearSolicitud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    // --- MÉTODO HELPER PARA MAPEAR ---
    private SolicitudTrabajo mapearSolicitud(ResultSet rs) throws SQLException {
        SolicitudTrabajo sol = new SolicitudTrabajo();
        sol.setId(rs.getInt("id"));
        sol.setIdCliente(rs.getInt("id_cliente"));
        sol.setDescripcion(rs.getString("descripcion"));
        sol.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        sol.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
        sol.setServicio(Servicio.valueOf(rs.getString("servicio")));

        if (rs.getObject("id_tecnico") != null) {
            sol.setIdTecnico(rs.getInt("id_tecnico"));
        }
        if (rs.getObject("precio_final") != null) {
            sol.setPrecioFinal(rs.getDouble("precio_final"));
        }
        if (rs.getObject("fecha_finalizacion") != null) {
            sol.setFechaFinalizacion(rs.getTimestamp("fecha_finalizacion"));
        }

        String nombreTecnico = rs.getString("nombre_tecnico");
        String apellidoTecnico = rs.getString("apellido_tecnico");
        if (nombreTecnico != null) {
            sol.setNombreTecnico(nombreTecnico + " " + apellidoTecnico);
        }
        return sol;
    }
}
