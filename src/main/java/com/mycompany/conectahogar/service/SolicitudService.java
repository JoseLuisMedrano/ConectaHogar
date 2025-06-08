
package com.mycompany.conectahogar.service;


import com.mycompany.conectahogar.dao.SolicitudTrabajoDAO;
import com.mycompany.conectahogar.dao.TecnicoDAO;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import java.util.List;

public class SolicitudService {
    private final SolicitudTrabajoDAO solicitudDao;
    private final TecnicoDAO tecnicoDao;
    
    public SolicitudService() {
        this.solicitudDao = new SolicitudTrabajoDAOImpl();
        this.tecnicoDao = new TecnicoDAOImpl();
    }
    
    public boolean crearSolicitud(SolicitudTrabajo solicitud) {
        return solicitudDao.guardar(solicitud);
    }
    
    public List<Tecnico> buscarTecnicosDisponibles(String especialidad, String ubicacion) {
        return tecnicoDao.buscarPorEspecialidadYDisponibilidad(especialidad);
    }
    
    public boolean aceptarSolicitud(int idTecnico, int idSolicitud) {
        // Lógica para aceptar solicitud
    }
    
    public boolean rechazarSolicitud(int idTecnico, int idSolicitud) {
        // Lógica para rechazar solicitud
    }
    
    public boolean hacerContraoferta(int idTecnico, int idSolicitud, double nuevoPrecio) {
        // Lógica para contraoferta
    }
}
