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
        String sql = "SELECT id_Solicitud, id_Cliente, id_Tecnico, descripcion, servicio, " +
                     "precioSugerido, precioFinal, estado, fechaCreacion, fechaFinalizacion " +
                     "FROM solicitudes_trabajo WHERE id_Solicitud = ?";
        SolicitudTrabajo solicitud = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSolicitud);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    solicitud = new SolicitudTrabajo();
                    solicitud.setId(rs.getInt("id_Solicitud"));
                    solicitud.setIdCliente(rs.getInt("id_Cliente"));
                    
                    int idTecnico = rs.getInt("id_Tecnico");
                    if (!rs.wasNull()) {
                        solicitud.setIdTecnico(idTecnico);
                    } else {
                        solicitud.setIdTecnico(null);
                    }
                    
                    solicitud.setDescripcion(rs.getString("descripcion"));
                    solicitud.setServicio(Servicio.valueOf(rs.getString("servicio")));
                    
                    double precioSugerido = rs.getDouble("precioSugerido");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioSugerido(precioSugerido);
                    } else {
                        solicitud.setPrecioSugerido(null);
                    }
                    
                    double precioFinal = rs.getDouble("precioFinal");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioFinal(precioFinal);
                    } else {
                        solicitud.setPrecioFinal(null);
                    }
                    
                    solicitud.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
                    
                    Timestamp fechaCreacionTs = rs.getTimestamp("fechaCreacion");
                    if (fechaCreacionTs != null) {
                        solicitud.setFechaCreacion(new Date(fechaCreacionTs.getTime()));
                    }
                    
                    Timestamp fechaFinalizacionTs = rs.getTimestamp("fechaFinalizacion");
                    if (fechaFinalizacionTs != null) {
                        solicitud.setFechaFinalizacion(new Date(fechaFinalizacionTs.getTime()));
                    } else {
                        solicitud.setFechaFinalizacion(null);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitud de trabajo por ID {}: {}", idSolicitud, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Error de mapeo de enum en SolicitudTrabajo con ID {}: {}", idSolicitud, e.getMessage(), e);
        }
        return solicitud;
    }

    /**
     * Asigna un técnico a una solicitud de trabajo y actualiza el precio final y el estado.
     * @param idSolicitud La ID de la solicitud.
     * @param idTecnico La ID del técnico a asignar.
     * @param precioFinal El precio final acordado para el servicio.
     * @return true si la asignación fue exitosa, false en caso contrario.
     */
    public boolean asignarTecnicoASolicitud(int idSolicitud, int idTecnico, Double precioFinal) {
        String sql = "UPDATE solicitudes_trabajo SET id_Tecnico = ?, precioFinal = ?, estado = ? WHERE id_Solicitud = ?";
        boolean exito = false;
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTecnico);
            if (precioFinal != null) {
                stmt.setDouble(2, precioFinal);
            } else {
                stmt.setNull(2, java.sql.Types.DOUBLE);
            }
            stmt.setString(3, EstadoSolicitud.ACEPTADA.name()); // Cambiar estado a ACEPTADA
            stmt.setInt(4, idSolicitud);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                logger.info("Técnico {} asignado a solicitud {} con precio final {}.", idTecnico, idSolicitud, precioFinal);
                exito = true;
            }
        } catch (SQLException e) {
            logger.error("Error al asignar técnico {} a solicitud {}: {}", idTecnico, idSolicitud, e.getMessage(), e);
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
     * Este método puede ser usado para actualizar cualquier campo de la solicitud.
     * Se recomienda usar métodos específicos para cambios de estado o asignación.
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
        String sql = "SELECT id_Solicitud, id_Cliente, id_Tecnico, descripcion, servicio, " +
                     "precioSugerido, precioFinal, estado, fechaCreacion, fechaFinalizacion " +
                     "FROM solicitudes_trabajo WHERE estado = ? ORDER BY fechaCreacion DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, EstadoSolicitud.PENDIENTE.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SolicitudTrabajo solicitud = new SolicitudTrabajo();
                    solicitud.setId(rs.getInt("id_Solicitud"));
                    solicitud.setIdCliente(rs.getInt("id_Cliente"));
                    
                    int idTecnico = rs.getInt("id_Tecnico");
                    if (!rs.wasNull()) {
                        solicitud.setIdTecnico(idTecnico);
                    } else {
                        solicitud.setIdTecnico(null);
                    }
                    
                    solicitud.setDescripcion(rs.getString("descripcion"));
                    solicitud.setServicio(Servicio.valueOf(rs.getString("servicio")));
                    
                    double precioSugerido = rs.getDouble("precioSugerido");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioSugerido(precioSugerido);
                    } else {
                        solicitud.setPrecioSugerido(null);
                    }
                    
                    double precioFinal = rs.getDouble("precioFinal");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioFinal(precioFinal);
                    } else {
                        solicitud.setPrecioFinal(null);
                    }
                    
                    solicitud.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
                    
                    Timestamp fechaCreacionTs = rs.getTimestamp("fechaCreacion");
                    if (fechaCreacionTs != null) {
                        solicitud.setFechaCreacion(new Date(fechaCreacionTs.getTime()));
                    }
                    
                    Timestamp fechaFinalizacionTs = rs.getTimestamp("fechaFinalizacion");
                    if (fechaFinalizacionTs != null) {
                        solicitud.setFechaFinalizacion(new Date(fechaFinalizacionTs.getTime()));
                    } else {
                        solicitud.setFechaFinalizacion(null);
                    }
                    solicitudes.add(solicitud);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes pendientes: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Error de mapeo de enum al obtener solicitudes pendientes: " + e.getMessage(), e);
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
        String sql = "SELECT id_Solicitud, id_Cliente, id_Tecnico, descripcion, servicio, " +
                     "precioSugerido, precioFinal, estado, fechaCreacion, fechaFinalizacion " +
                     "FROM solicitudes_trabajo WHERE id_Tecnico = ? ORDER BY fechaCreacion DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTecnico);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SolicitudTrabajo solicitud = new SolicitudTrabajo();
                    solicitud.setId(rs.getInt("id_Solicitud"));
                    solicitud.setIdCliente(rs.getInt("id_Cliente"));
                    
                    int idTecnicoRs = rs.getInt("id_Tecnico"); // Usar un nombre diferente para evitar conflicto
                    if (!rs.wasNull()) {
                        solicitud.setIdTecnico(idTecnicoRs);
                    } else {
                        solicitud.setIdTecnico(null);
                    }
                    
                    solicitud.setDescripcion(rs.getString("descripcion"));
                    solicitud.setServicio(Servicio.valueOf(rs.getString("servicio")));
                    
                    double precioSugerido = rs.getDouble("precioSugerido");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioSugerido(precioSugerido);
                    } else {
                        solicitud.setPrecioSugerido(null);
                    }
                    
                    double precioFinal = rs.getDouble("precioFinal");
                    if (!rs.wasNull()) {
                        solicitud.setPrecioFinal(precioFinal);
                    } else {
                        solicitud.setPrecioFinal(null);
                    }
                    
                    solicitud.setEstado(EstadoSolicitud.valueOf(rs.getString("estado")));
                    
                    Timestamp fechaCreacionTs = rs.getTimestamp("fechaCreacion");
                    if (fechaCreacionTs != null) {
                        solicitud.setFechaCreacion(new Date(fechaCreacionTs.getTime()));
                    }
                    
                    Timestamp fechaFinalizacionTs = rs.getTimestamp("fechaFinalizacion");
                    if (fechaFinalizacionTs != null) {
                        solicitud.setFechaFinalizacion(new Date(fechaFinalizacionTs.getTime()));
                    } else {
                        solicitud.setFechaFinalizacion(null);
                    }
                    solicitudes.add(solicitud);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener solicitudes asignadas al técnico {}: {}", idTecnico, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Error de mapeo de enum al obtener solicitudes asignadas al técnico {}: {}", idTecnico, e.getMessage(), e);
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

    public List<SolicitudTrabajo> obtenerSolicitudesPorCliente(int id_Usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}