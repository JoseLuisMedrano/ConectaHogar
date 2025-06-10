package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudTrabajoDAO {

    public boolean registrarSolicitud(SolicitudTrabajo solicitud) {
        String sql = "INSERT INTO solicitud_trabajo (id_cliente, descripcion, servicio, precio_sugerido, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, solicitud.getIdCliente());
            ps.setString(2, solicitud.getDescripcion());
            ps.setString(3, solicitud.getServicio().name());
            ps.setDouble(4, solicitud.getPrecioSugerido());
            ps.setString(5, EstadoSolicitud.PENDIENTE.name());
            ps.setTimestamp(6, new Timestamp(solicitud.getFechaCreacion().getTime()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SolicitudTrabajo> listarSolicitudesPendientes() {
        List<SolicitudTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_trabajo WHERE estado = ? ORDER BY fecha_creacion DESC";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, EstadoSolicitud.PENDIENTE.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SolicitudTrabajo s = mapearSolicitud(rs);
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<SolicitudTrabajo> listarSolicitudesPorServicio(Servicio servicio) {
        List<SolicitudTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_trabajo WHERE servicio = ? AND estado = ? ORDER BY fecha_creacion DESC";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, servicio.name());
            ps.setString(2, EstadoSolicitud.PENDIENTE.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SolicitudTrabajo s = mapearSolicitud(rs);
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarEstado(int idSolicitud, EstadoSolicitud estado) {
        String sql = "UPDATE solicitud_trabajo SET estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado.name());
            ps.setInt(2, idSolicitud);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean asignarTecnico(int idSolicitud, int idTecnico) {
        String sql = "UPDATE solicitud_trabajo SET id_tecnico = ?, estado = ? WHERE id = ? AND estado = ?";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTecnico);
            ps.setString(2, EstadoSolicitud.ACEPTADA.name());
            ps.setInt(3, idSolicitud);
            ps.setString(4, EstadoSolicitud.PENDIENTE.name());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarPrecio(int idSolicitud, double nuevoPrecio) {
        String sql = "UPDATE solicitud_trabajo SET precio_final = ?, estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, EstadoSolicitud.CONTRAOFERTA.name());
            ps.setInt(3, idSolicitud);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<SolicitudTrabajo> listarSolicitudesPorTecnico(int idTecnico) {
        List<SolicitudTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_trabajo WHERE id_tecnico = ? ORDER BY fecha_creacion DESC";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idTecnico);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                SolicitudTrabajo s = mapearSolicitud(rs);
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<SolicitudTrabajo> listarSolicitudesPorCliente(int idCliente) {
        List<SolicitudTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_trabajo WHERE id_cliente = ? ORDER BY fecha_creacion DESC";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                SolicitudTrabajo s = mapearSolicitud(rs);
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private SolicitudTrabajo mapearSolicitud(ResultSet rs) throws SQLException {
        SolicitudTrabajo s = new SolicitudTrabajo();
        s.setId(rs.getInt("id"));
        s.setIdCliente(rs.getInt("id_cliente"));
        s.setIdTecnico(rs.getObject("id_tecnico") != null ? rs.getInt("id_tecnico") : null);
        s.setDescripcion(rs.getString("descripcion"));
        s.setServicio(Servicio.valueOf(rs.getString("servicio")));
        s.setPrecioSugerido(rs.getDouble("precio_sugerido"));
        s.setPrecioFinal(rs.getObject("precio_final") != null ? rs.getDouble("precio_final") : null);
        s.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
        s.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        s.setFechaFinalizacion(rs.getTimestamp("fecha_finalizacion"));
        return s;
    }

    public SolicitudTrabajo obtenerPorId(int id) {
        String sql = "SELECT * FROM solicitud_trabajo WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearSolicitud(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean completarTrabajo(int idSolicitud) {
        String sql = "UPDATE solicitud_trabajo SET estado = ?, fecha_finalizacion = ? WHERE id = ?";
        
        try (Connection con = ConexionBD.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, EstadoSolicitud.COMPLETADA.name());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, idSolicitud);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}