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
            logger.warn("Solicitud {} no apta para ser aceptada.", idSolicitud);
            return false;
        }
        boolean exitoAsignacion = solicitudDao.asignarTecnicoASolicitud(idSolicitud, idTecnico, precioOfrecido);
        if (exitoAsignacion) {
            return tecnicoDao.actualizarDisponibilidadTecnico(idTecnico, "No Disponible");
        }
        return false;
    }

    public boolean completarSolicitud(int idSolicitud) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null || solicitud.getIdTecnico() == null) {
            logger.warn("Solicitud {} no apta para ser completada.", idSolicitud);
            return false;
        }
        solicitud.setEstado(EstadoSolicitud.COMPLETADA);
        solicitud.setFechaFinalizacion(new Date());
        boolean estadoActualizado = solicitudDao.actualizarSolicitud(solicitud);
        if (estadoActualizado) {
            return tecnicoDao.actualizarDisponibilidadTecnico(solicitud.getIdTecnico(), "Disponible");
        }
        return false;
    }

    public boolean hacerContraoferta(int idSolicitud, Double nuevoPrecio) {
        SolicitudTrabajo solicitud = solicitudDao.obtenerSolicitudPorId(idSolicitud);
        if (solicitud == null) {
            return false;
        }
        solicitud.setPrecioFinal(nuevoPrecio);
        solicitud.setEstado(EstadoSolicitud.CONTRAOFERTA);
        return solicitudDao.actualizarSolicitud(solicitud);
    }

    public List<SolicitudTrabajo> listarSolicitudesPorCliente(int idCliente) {
        return solicitudDao.obtenerSolicitudesPorCliente(idCliente);
    }
    public List<SolicitudTrabajo> listarSolicitudesPendientesPorEspecialidad(String especialidad) {
    return solicitudDao.obtenerSolicitudesPendientesPorEspecialidad(especialidad);
}
    public List<SolicitudTrabajo> listarSolicitudesPorTecnico(int idTecnico) {
    return solicitudDao.obtenerSolicitudesPorTecnico(idTecnico);
}
}