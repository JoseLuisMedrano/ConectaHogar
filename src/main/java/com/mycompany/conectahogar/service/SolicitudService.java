// Archivo: com/mycompany/conectahogar/service/SolicitudService.java
package com.mycompany.conectahogar.service;

import com.mycompany.conectahogar.dao.SolicitudTrabajoDAO;
import com.mycompany.conectahogar.dao.TecnicoDAO;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitudService {
    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    private final SolicitudTrabajoDAO solicitudDao;
    private final TecnicoDAO tecnicoDao;

    public SolicitudService() {
        this.solicitudDao = new SolicitudTrabajoDAO();
        this.tecnicoDao = new TecnicoDAO();
    }

    /**
     * Crea una nueva solicitud de trabajo.
     * @param solicitud El objeto SolicitudTrabajo a crear.
     * @return true si la solicitud fue creada exitosamente.
     */
    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        if (solicitud.getDescripcion() == null || solicitud.getDescripcion().trim().isEmpty() ||
            solicitud.getServicio() == null || solicitud.getPrecioSugerido() == null) {
            logger.warn("Intentando crear solicitud con datos incompletos o inválidos.");
            return false;
        }
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaCreacion(new Date()); 
        return solicitudDao.crearSolicitud(solicitud);
    }

    /**
     * Busca técnicos disponibles por especialidad.
     * @param especialidad La especialidad del técnico.
     * @return Lista de técnicos disponibles.
     */
    public List<Tecnico> buscarTecnicosDisponibles(String especialidad) {
        return tecnicoDao.obtenerTecnicosDisponiblesPorEspecialidad(especialidad);
    }

    /**
     * Permite a un técnico aceptar una solicitud, asignándose a ella.
     * El precio final se establece aquí, el estado cambia a ACEPTADA.
     * @param idTecnico La ID del usuario del técnico que acepta.
     * @param idSolicitud La ID de la solicitud a aceptar.
     * @param precioOfrecido El precio final que el técnico ofrece (puede ser igual al sugerido).
     * @return true si la solicitud fue aceptada y asignada.
     */
    public boolean aceptarSolicitud(int idTecnico, int idSolicitud, Double precioOfrecido) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null || solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            logger.warn("Solicitud {} no apta para ser aceptada por técnico {}. Estado actual: {}", idSolicitud, idTecnico, solicitud != null ? solicitud.getEstado().name() : "N/A");
            return false;
        }
        
        boolean exito = solicitudDao.asignarTecnicoASolicitud(idSolicitud, idTecnico, precioOfrecido);

        if (exito) {
            logger.info("Solicitud {} aceptada por técnico {}. Precio final: {}", idSolicitud, idTecnico, precioOfrecido);
        } else {
            logger.error("Fallo al asignar técnico {} a la solicitud {}.", idTecnico, idSolicitud);
        }
        return exito;
    }

    /**
     * Rechaza una solicitud de trabajo, cambiando su estado a CANCELADA.
     * @param idSolicitud La ID de la solicitud a rechazar.
     * @return true si la solicitud fue rechazada.
     */
    public boolean rechazarSolicitud(int idSolicitud) {
        return solicitudDao.actualizarEstadoSolicitud(idSolicitud, EstadoSolicitud.CANCELADA);
    }
    
    /**
     * Permite a un técnico hacer una contraoferta en una solicitud.
     * Actualiza el precio final y el estado de la solicitud a CONTRAOFERTA.
     * @param idSolicitud La ID de la solicitud.
     * @param nuevoPrecio El nuevo precio contraofertado.
     * @return true si la contraoferta fue exitosa.
     */
    public boolean hacerContraoferta(int idSolicitud, Double nuevoPrecio) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null) {
            logger.warn("No se encontró solicitud con ID {} para contraoferta.", idSolicitud);
            return false;
        }
        solicitud.setPrecioFinal(nuevoPrecio);
        solicitud.setEstado(EstadoSolicitud.CONTRAOFERTA);
        return solicitudDao.actualizarSolicitud(solicitud); 
    }

    /**
     * Lista todas las solicitudes de trabajo pendientes.
     * @return Lista de solicitudes pendientes.
     */
    public List<SolicitudTrabajo> listarSolicitudesPendientes() {
        return solicitudDao.obtenerSolicitudesPendientes();
    }

    /**
     * Lista todas las solicitudes de trabajo asignadas a un técnico específico.
     * @param idTecnico La ID del usuario del técnico.
     * @return Lista de solicitudes asignadas al técnico.
     */
    public List<SolicitudTrabajo> listarSolicitudesPorTecnico(int idTecnico) {
        return solicitudDao.obtenerSolicitudesAsignadasATecnico(idTecnico);
    }

    /**
     * Lista todas las solicitudes de trabajo asociadas a un cliente específico.
     * @param idCliente La ID del usuario del cliente.
     * @return Lista de solicitudes del cliente.
     */
    public List<SolicitudTrabajo> listarSolicitudesPorCliente(int idCliente) {
        // Llama al método correspondiente en el DAO
        return solicitudDao.obtenerSolicitudesPorCliente(idCliente);
    }

    /**
     * Completa una solicitud de trabajo.
     * Cambia el estado de la solicitud a COMPLETADA y libera al técnico.
     * @param idSolicitud La ID de la solicitud a completar.
     * @return true si la solicitud fue completada y el técnico liberado.
     */
    public boolean completarSolicitud(int idSolicitud) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null || solicitud.getIdTecnico() == null || solicitud.getEstado() == EstadoSolicitud.COMPLETADA) {
            logger.warn("Solicitud {} no apta para ser completada (no encontrada, sin técnico o ya completada).", idSolicitud);
            return false;
        }
        
        solicitud.setEstado(EstadoSolicitud.COMPLETADA);
        solicitud.setFechaFinalizacion(new Date()); 
        boolean estadoActualizado = solicitudDao.actualizarSolicitud(solicitud);

        boolean tecnicoLiberado = tecnicoDao.actualizarDisponibilidadTecnico(solicitud.getIdTecnico(), true);
        
        if (estadoActualizado && tecnicoLiberado) {
            logger.info("Solicitud {} completada exitosamente. Técnico {} liberado.", idSolicitud, solicitud.getIdTecnico());
            return true;
        } else {
            logger.error("Fallo al completar la solicitud {}. Estado actualizado: {}, Técnico liberado: {}.", idSolicitud, estadoActualizado, tecnicoLiberado);
            return false;
        }
    }
}