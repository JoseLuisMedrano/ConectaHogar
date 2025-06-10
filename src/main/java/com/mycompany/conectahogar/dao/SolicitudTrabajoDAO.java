package com.mycompany.conectahogar.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.config.ConexionBD;

public class SolicitudTrabajoDAO {

    public void registrarSolicitud(SolicitudTrabajo solicitud) {
        String sql = "INSERT INTO solicitud_trabajo (id_cliente, descripcion, servicio, precio_sugerido, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, solicitud.getIdCliente());
            ps.setString(2, solicitud.getDescripcion());
            ps.setString(3, solicitud.getServicio().name());
            ps.setDouble(4, solicitud.getPrecioSugerido());
            ps.setString(5, EstadoSolicitud.PENDIENTE.name());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SolicitudTrabajo> listarSolicitudesPendientes() {
        List<SolicitudTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_trabajo WHERE estado = ?";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, EstadoSolicitud.PENDIENTE.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SolicitudTrabajo s = new SolicitudTrabajo();
                s.setId(rs.getInt("id"));
                s.setDescripcion(rs.getString("descripcion"));
                s.setServicio(Servicio.valueOf(rs.getString("servicio")));
                s.setPrecioSugerido(rs.getDouble("precio_sugerido"));
                s.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
                s.setIdCliente(rs.getInt("id_cliente"));
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void actualizarEstado(int idSolicitud, EstadoSolicitud estado) {
        String sql = "UPDATE solicitud_trabajo SET estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado.name());
            ps.setInt(2, idSolicitud);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean asignarTecnico(int idSolicitud, int idTecnico) {
        String sql = "UPDATE solicitud_trabajo SET id_tecnico = ?, estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTecnico);
            ps.setString(2, EstadoSolicitud.ACEPTADA.name());
            ps.setInt(3, idSolicitud);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarPrecio(int idSolicitud, double nuevoPrecio) {
        String sql = "UPDATE solicitud_trabajo SET precio_final = ?, estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, EstadoSolicitud.CONTRAOFERTA.name());
            ps.setInt(3, idSolicitud);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
