package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudTrabajoDAO {

    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        String sql = "INSERT INTO solicitudes_trabajo (id_cliente, servicio, descripcion, precio_sugerido, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, solicitud.getIdCliente());
            pstmt.setString(2, solicitud.getServicio().name());
            pstmt.setString(3, solicitud.getDescripcion());
            pstmt.setDouble(4, solicitud.getPrecioSugerido());
            pstmt.setString(5, solicitud.getEstado().name());
            pstmt.setTimestamp(6, new Timestamp(solicitud.getFechaCreacion().getTime()));

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SolicitudTrabajo> obtenerSolicitudesPorEstado(EstadoSolicitud estado) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE estado = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, estado.name());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                solicitudes.add(mapearSolicitud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public List<SolicitudTrabajo> obtenerSolicitudesPorCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_cliente = ? ORDER BY fecha_creacion DESC";

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

    public List<SolicitudTrabajo> obtenerSolicitudesPorTecnico(int idTecnico) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        // Esta consulta busca todas las solicitudes donde el id_tecnico coincida
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_tecnico = ? ORDER BY fecha_creacion DESC";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTecnico);
            ResultSet rs = pstmt.executeQuery();

            // Reutilizamos el método 'mapearSolicitud' que ya existe
            while (rs.next()) {
                solicitudes.add(mapearSolicitud(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    private SolicitudTrabajo mapearSolicitud(ResultSet rs) throws SQLException {
        SolicitudTrabajo sol = new SolicitudTrabajo();
        sol.setId(rs.getInt("id"));
        sol.setIdCliente(rs.getInt("id_cliente"));
        // El id_tecnico puede ser nulo, hay que manejarlo
        if (rs.getObject("id_tecnico") != null) {
            sol.setIdTecnico(rs.getInt("id_tecnico"));
        }
        sol.setServicio(Servicio.valueOf(rs.getString("servicio")));
        sol.setDescripcion(rs.getString("descripcion"));
        sol.setPrecioSugerido(rs.getDouble("precio_sugerido"));
        // El precio_final puede ser nulo
        if (rs.getObject("precio_final") != null) {
            sol.setPrecioFinal(rs.getDouble("precio_final"));
        }
        sol.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
        sol.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        sol.setFechaFinalizacion(rs.getTimestamp("fecha_finalizacion"));
        return sol;
    }

    public boolean hacerContraoferta(int idSolicitud, double nuevoPrecio) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ?, precio_final = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, EstadoSolicitud.CONTRAOFERTA.name());
            pstmt.setDouble(2, nuevoPrecio);
            pstmt.setInt(3, idSolicitud);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SolicitudTrabajo> obtenerContraofertasParaCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_cliente = ? AND estado = 'CONTRAOFERTA'";

        // El try-with-resources se encarga de cerrar la conexión y el statement
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // --- LÍNEA CORREGIDA ---
            // Usamos la variable correcta que recibe el método: idCliente
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

    public boolean asignarTecnicoASolicitud(int idSolicitud, int idTecnico, double precioFinal) {
        // Esta consulta actualiza el estado, asigna el técnico y fija el precio final.
        String sql = "UPDATE solicitudes_trabajo SET estado = ?, id_tecnico = ?, precio_final = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, EstadoSolicitud.ASIGNADA.name()); // Cambiamos el estado
            pstmt.setInt(2, idTecnico);
            pstmt.setDouble(3, precioFinal);
            pstmt.setInt(4, idSolicitud);

            return pstmt.executeUpdate() > 0; // Devuelve true si se actualizó al menos 1 fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean aceptarContraoferta(int idSolicitud) {
        // Esta consulta es la correcta: SOLO cambia el estado a ASIGNADA.
        // NO toca la columna precio_final, conservando el valor de la contraoferta.
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
        // Si el cliente rechaza, podemos considerar la solicitud CANCELADA
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

}
