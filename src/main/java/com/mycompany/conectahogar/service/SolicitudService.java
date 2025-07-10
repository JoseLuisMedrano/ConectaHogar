package com.mycompany.conectahogar.service;

import com.mycompany.conectahogar.dao.SolicitudTrabajoDAO;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import java.util.List;

/**
 * Esta clase contiene la lógica de negocio para las solicitudes. Actúa como
 * intermediario entre los Servlets y el DAO.
 */
public class SolicitudService {

    // El Service tiene una instancia del DAO para hablar con la base de datos.
    private SolicitudTrabajoDAO solicitudDAO;

    public SolicitudService() {
        // Cada vez que se crea un servicio, se crea también su DAO.
        this.solicitudDAO = new SolicitudTrabajoDAO();
    }

    /**
     * Lógica de negocio para crear una nueva solicitud. Por ahora, solo llama
     * al DAO, pero en el futuro podría hacer más (ej. enviar un email de
     * confirmación).
     */
    public boolean crearNuevaSolicitud(SolicitudTrabajo solicitud) {
        // En el futuro, aquí puedes añadir más lógica.
        return solicitudDAO.crearSolicitud(solicitud);
    }

    /**
     * Lógica de negocio para obtener las solicitudes de un cliente.
     */
    public List<SolicitudTrabajo> listarSolicitudesPorCliente(int idCliente) {
        return solicitudDAO.obtenerSolicitudesPorCliente(idCliente);
    }

    /**
     * Lógica de negocio para que un técnico vea los trabajos disponibles.
     */
    public List<SolicitudTrabajo> listarSolicitudesPendientes() {
        return solicitudDAO.obtenerSolicitudesPorEstado(EstadoSolicitud.PENDIENTE);
    }

    public boolean aceptarSolicitud(int idTecnico, int idSolicitud, double precioFinal) {
        // Por ahora, solo llama al DAO. En el futuro podría hacer más.
        return solicitudDAO.asignarTecnicoASolicitud(idSolicitud, idTecnico, precioFinal);
    }

    public boolean hacerContraoferta(int idSolicitud, double nuevoPrecio, int idTecnico) {

        return solicitudDAO.hacerContraoferta(idSolicitud, nuevoPrecio, idTecnico);
    }

    public List<SolicitudTrabajo> listarContraofertasParaCliente(int idCliente) {
        return solicitudDAO.obtenerContraofertasParaCliente(idCliente);
    }

    public boolean aceptarContraoferta(int idSolicitud) {
        return solicitudDAO.aceptarContraoferta(idSolicitud);
    }

    public boolean rechazarContraoferta(int idSolicitud) {
        return solicitudDAO.rechazarContraoferta(idSolicitud);
    }
    // Todos los métodos antiguos que daban error (aceptar, rechazar, etc.) han sido eliminados.
    // Los añadiremos de nuevo cuando necesitemos esa funcionalidad.

    public List<SolicitudTrabajo> listarSolicitudesPorTecnico(int idTecnico) {
        // El servicio simplemente le pasa la petición al DAO
        return solicitudDAO.obtenerSolicitudesPorTecnico(idTecnico);
    }
}
