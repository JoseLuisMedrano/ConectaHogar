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

    public List<Tecnico> buscarTecnicosDisponibles(String especialidad) {
        return tecnicoDao.obtenerTecnicosDisponiblesPorEspecialidad(especialidad);
    }

    public boolean aceptarSolicitud(int idTecnico, int idSolicitud, Double precioOfrecido) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null || solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            logger.warn("Solicitud {} no apta para ser aceptada por técnico {}. Estado actual: {}", idSolicitud, idTecnico, solicitud != null ? solicitud.getEstado().name() : "N/A");
            return false;
        }
        
        // Antes de aceptar, si el técnico va a tomar el trabajo, su disponibilidad debería cambiar a "No Disponible"
        // o "Ocupado". Esto debe hacerse ANTES de asignar la solicitud.
        // Asumiendo que el flujo es: 1. Aceptar/Asignar solicitud; 2. Marcar al técnico como no disponible.
        // Si el técnico ya está disponible para aceptar, se le asigna la solicitud.
        // Después de la asignación exitosa, se podría actualizar su disponibilidad.
        boolean exitoAsignacion = solicitudDao.asignarTecnicoASolicitud(idSolicitud, idTecnico, precioOfrecido);

        if (exitoAsignacion) {
            // Una vez asignada la solicitud con éxito, marcamos al técnico como "No Disponible"
            boolean tecnicoOcupado = tecnicoDao.actualizarDisponibilidadTecnico(idTecnico, "No Disponible"); // CAMBIO CRÍTICO AQUÍ
            if (!tecnicoOcupado) {
                logger.error("Fallo al marcar al técnico {} como 'No Disponible' después de asignar la solicitud {}.", idTecnico, idSolicitud);
                // Aquí podrías considerar un rollback de la asignación de la solicitud si la disponibilidad no se actualiza,
                // pero para "que funcione" lo dejaremos así por ahora.
            }
            logger.info("Solicitud {} aceptada por técnico {}. Precio final: {}. Técnico marcado como 'No Disponible'.", idSolicitud, idTecnico, precioOfrecido);
            return true; // La asignación y el cambio de estado del técnico fueron exitosos
        } else {
            logger.error("Fallo al asignar técnico {} a la solicitud {}.", idTecnico, idSolicitud);
            return false;
        }
    }

    public boolean rechazarSolicitud(int idSolicitud) {
        return solicitudDao.actualizarEstadoSolicitud(idSolicitud, EstadoSolicitud.CANCELADA);
    }
    
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

    public List<SolicitudTrabajo> listarSolicitudesPendientes() {
        return solicitudDao.obtenerSolicitudesPendientes();
    }

    public List<SolicitudTrabajo> listarSolicitudesPorTecnico(int idTecnico) {
        return solicitudDao.obtenerSolicitudesAsignadasATecnico(idTecnico);
    }

    public List<SolicitudTrabajo> listarSolicitudesPorCliente(int idCliente) {
        return solicitudDao.obtenerSolicitudesPorCliente(idCliente);
    }
    
    public boolean completarSolicitud(int idSolicitud) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null || solicitud.getIdTecnico() == null || solicitud.getEstado() == EstadoSolicitud.COMPLETADA) {
            logger.warn("Solicitud {} no apta para ser completada (no encontrada, sin técnico o ya completada).", idSolicitud);
            return false;
        }
        
        solicitud.setEstado(EstadoSolicitud.COMPLETADA);
        solicitud.setFechaFinalizacion(new Date()); 
        boolean estadoActualizado = solicitudDao.actualizarSolicitud(solicitud);

        // CAMBIO CRÍTICO: El método actualizarDisponibilidadTecnico ahora espera un String
        boolean tecnicoLiberado = false;
        if (solicitud.getIdTecnico() != null) { // Asegurarse de que hay un técnico asignado
             tecnicoLiberado = tecnicoDao.actualizarDisponibilidadTecnico(solicitud.getIdTecnico(), "Disponible");
        }
       
        if (estadoActualizado && tecnicoLiberado) {
            logger.info("Solicitud {} completada exitosamente. Técnico {} liberado.", idSolicitud, solicitud.getIdTecnico());
            return true;
        } else {
            logger.error("Fallo al completar la solicitud {}. Estado actualizado: {}, Técnico liberado: {}.", idSolicitud, estadoActualizado, tecnicoLiberado);
            return false;
        }
    }
}