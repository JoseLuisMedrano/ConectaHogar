// Archivo: com/mycompany/conectahogar/dao/SolicitudTrabajoDAO.java
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

    /**
     * Crea una nueva solicitud de trabajo en la base de datos.
     * @param solicitud El objeto SolicitudTrabajo a insertar.
     * @return true si la solicitud fue creada exitosamente, false en caso contrario.
     */
    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        String sql = "INSERT INTO solicitudes_trabajo (id_Cliente, descripcion, servicio, precioSugerido, estado, fechaCreacion) VALUES (?, ?, ?, ?, ?, ?)";
        boolean exito = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitud.getIdCliente());
            stmt.setString(2, solicitud.getDescripcion());
            stmt.setString(3, solicitud.getServicio().name());

            if (solicitud.getPrecioSugerido() != null) {
                stmt.setDouble(4, solicitud.getPrecioSugerido());
            } else {
                stmt.setNull(4, java.sql.Types.DOUBLE);
            }

            stmt.setString(5, solicitud.getEstado().name());
            stmt.setTimestamp(6, new Timestamp(solicitud.getFechaCreacion().getTime()));

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        solicitud.setId(rs.getInt(1));
                        logger.info("Solicitud de trabajo creada exitosamente con ID: {}", solicitud.getId());
                        exito = true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear solicitud de trabajo: " + e.getMessage(), e);
        }
        return exito;
    }

    /**
     * Obtiene una solicitud de trabajo por su ID.
     * @param idSolicitud La ID de la solicitud a buscar.
     * @return El objeto SolicitudTrabajo si se encuentra, o null si no existe.
     */
    public SolicitudTrabajo obtenerSolicitudPorId(int idSolicitud) {
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_Solicitud = ?";
        SolicitudTrabajo solicitud = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSolicitud);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    solicitud = mapearResultSetASolicitud(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitud de trabajo por ID {}: {}", idSolicitud, e.getMessage(), e);
        }
        return solicitud;
    }

    /**
     * Asigna un técnico a una solicitud de trabajo y actualiza el precio final y el estado.
     * Este método es el que faltaba y causa el error de compilación.
     * @param idSolicitud La ID de la solicitud a la que se asignará el técnico.
     * @param idTecnico La ID del técnico que acepta la solicitud.
     * @param precioOfrecido El precio final acordado para la solicitud.
     * @return true si la asignación fue exitosa, false en caso contrario.
     */
    public boolean asignarTecnicoASolicitud(int idSolicitud, int idTecnico, Double precioOfrecido) {
        // Asume que la solicitud debe estar PENDIENTE para ser asignada.
        // Si ya tiene un técnico o un estado diferente, esta consulta no hará nada.
        String sql = "UPDATE solicitudes_trabajo SET id_Tecnico = ?, precioFinal = ?, estado = ? WHERE id_Solicitud = ? AND estado = ?";
        boolean exito = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTecnico);
            if (precioOfrecido != null) {
                stmt.setDouble(2, precioOfrecido);
            } else {
                stmt.setNull(2, java.sql.Types.DOUBLE);
            }
            stmt.setString(3, EstadoSolicitud.ACEPTADA.name()); // Cambiar estado a aceptada
            stmt.setInt(4, idSolicitud);
            stmt.setString(5, EstadoSolicitud.PENDIENTE.name()); // Solo si está PENDIENTE

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Solicitud {} asignada a técnico {} con precio {}.", idSolicitud, idTecnico, precioOfrecido);
                exito = true;
            } else {
                logger.warn("No se pudo asignar la solicitud {} al técnico {}. Es posible que no estuviera PENDIENTE.", idSolicitud, idTecnico);
            }
        } catch (SQLException e) {
            logger.error("Error SQL al asignar técnico {} a la solicitud {}: {}", idTecnico, idSolicitud, e.getMessage(), e);
        }
        return exito;
    }

    /**
     * Actualiza el estado de una solicitud de trabajo.
     * @param idSolicitud La ID de la solicitud.
     * @param nuevoEstado El nuevo estado a establecer.
     * @return true si el estado fue actualizado, false en caso contrario.
     */
    public boolean actualizarEstadoSolicitud(int idSolicitud, EstadoSolicitud nuevoEstado) {
        String sql = "UPDATE solicitudes_trabajo SET estado = ? WHERE id_Solicitud = ?";
        boolean exito = false;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, idSolicitud);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Estado de solicitud {} actualizado a {}.", idSolicitud, nuevoEstado.name());
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar estado de solicitud {}: {}", idSolicitud, e.getMessage(), e);
        }
        return exito;
    }

    /**
     * Actualiza una solicitud de trabajo existente.
     * @param solicitud El objeto SolicitudTrabajo con la información actualizada.
     * @return true si la solicitud fue actualizada exitosamente, false en caso contrario.
     */
    public boolean actualizarSolicitud(SolicitudTrabajo solicitud) {
        String sql = "UPDATE solicitudes_trabajo SET id_Cliente = ?, id_Tecnico = ?, descripcion = ?, " +
                     "servicio = ?, precioSugerido = ?, precioFinal = ?, estado = ?, fechaCreacion = ?, fechaFinalizacion = ? " +
                     "WHERE id_Solicitud = ?";
        boolean exito = false;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, solicitud.getIdCliente());
            if (solicitud.getIdTecnico() != null) {
                stmt.setInt(2, solicitud.getIdTecnico());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, solicitud.getDescripcion());
            stmt.setString(4, solicitud.getServicio().name());
            if (solicitud.getPrecioSugerido() != null) {
                stmt.setDouble(5, solicitud.getPrecioSugerido());
            } else {
                stmt.setNull(5, java.sql.Types.DOUBLE);
            }

            if (solicitud.getPrecioFinal() != null) {
                stmt.setDouble(6, solicitud.getPrecioFinal());
            } else {
                stmt.setNull(6, java.sql.Types.DOUBLE);
            }

            stmt.setString(7, solicitud.getEstado().name());
            stmt.setTimestamp(8, new Timestamp(solicitud.getFechaCreacion().getTime()));
            if (solicitud.getFechaFinalizacion() != null) {
                stmt.setTimestamp(9, new Timestamp(solicitud.getFechaFinalizacion().getTime()));
            } else {
                stmt.setNull(9, java.sql.Types.TIMESTAMP);
            }
            stmt.setInt(10, solicitud.getId());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Solicitud de trabajo con ID {} actualizada exitosamente.", solicitud.getId());
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar solicitud de trabajo con ID {}: {}", solicitud.getId(), e.getMessage(), e);
        }
        return exito;
    }

    /**
     * Obtiene una lista de solicitudes de trabajo pendientes (estado PENDIENTE).
     * @return Una lista de objetos SolicitudTrabajo.
     */
    public List<SolicitudTrabajo> obtenerSolicitudesPendientes() {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE estado = ? ORDER BY fechaCreacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, EstadoSolicitud.PENDIENTE.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearResultSetASolicitud(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes pendientes: " + e.getMessage(), e);
        }
        return solicitudes;
    }

    /**
     * Obtiene una lista de solicitudes de trabajo asignadas a un técnico específico.
     * @param idTecnico La ID del técnico.
     * @return Una lista de objetos SolicitudTrabajo.
     */
    public List<SolicitudTrabajo> obtenerSolicitudesAsignadasATecnico(int idTecnico) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_Tecnico = ? ORDER BY fechaCreacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTecnico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearResultSetASolicitud(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes asignadas al técnico {}: {}", idTecnico, e.getMessage(), e);
        }
        return solicitudes;
    }

    /**
     * Obtiene todas las solicitudes de un cliente específico.
     * @param idCliente La ID del cliente.
     * @return Una lista de objetos SolicitudTrabajo.
     */
    public List<SolicitudTrabajo> obtenerSolicitudesPorCliente(int idCliente) {
        List<SolicitudTrabajo> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes_trabajo WHERE id_Cliente = ? ORDER BY fechaCreacion DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    solicitudes.add(mapearResultSetASolicitud(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes para el cliente {}: {}", idCliente, e.getMessage(), e);
        }
        return solicitudes;
    }

    /**
     * Elimina una solicitud de trabajo por su ID.
     * @param idSolicitud La ID de la solicitud a eliminar.
     * @return true si la solicitud fue eliminada, false en caso contrario.
     */
    public boolean eliminarSolicitud(int idSolicitud) {
        String sql = "DELETE FROM solicitudes_trabajo WHERE id_Solicitud = ?";
        boolean exito = false;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Solicitud de trabajo con ID {} eliminada exitosamente.", idSolicitud);
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar solicitud de trabajo con ID {}: {}", idSolicitud, e.getMessage(), e);
        }
        return exito;
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto SolicitudTrabajo y evitar código repetido.
     * @param rs El ResultSet a mapear.
     * @return Un objeto SolicitudTrabajo poblado.
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
     */
    private SolicitudTrabajo mapearResultSetASolicitud(ResultSet rs) throws SQLException {
        SolicitudTrabajo solicitud = new SolicitudTrabajo();
        try {
            solicitud.setId(rs.getInt("id_Solicitud"));
            solicitud.setIdCliente(rs.getInt("id_Cliente"));

            int idTecnico = rs.getInt("id_Tecnico");
            if (!rs.wasNull()) { // Verifica si el valor de la columna 'id_Tecnico' en la BD es NULL
                solicitud.setIdTecnico(idTecnico);
            }

            solicitud.setDescripcion(rs.getString("descripcion"));
            solicitud.setServicio(Servicio.valueOf(rs.getString("servicio")));
            solicitud.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
            solicitud.setFechaCreacion(rs.getTimestamp("fechaCreacion"));

            double precioSugerido = rs.getDouble("precioSugerido");
            if (!rs.wasNull()) {
                solicitud.setPrecioSugerido(precioSugerido);
            }

            double precioFinal = rs.getDouble("precioFinal");
            if (!rs.wasNull()) {
                solicitud.setPrecioFinal(precioFinal);
            }

            Timestamp fechaFinalizacionTs = rs.getTimestamp("fechaFinalizacion");
            if (fechaFinalizacionTs != null) {
                solicitud.setFechaFinalizacion(new Date(fechaFinalizacionTs.getTime()));
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error de mapeo de enum al leer solicitud: {}", e.getMessage(), e);
            // Podrías retornar null o lanzar una excepción personalizada si un enum no es válido
        }
        return solicitud;
    }
}