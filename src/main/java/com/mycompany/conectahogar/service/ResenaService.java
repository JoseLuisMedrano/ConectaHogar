package com.mycompany.conectahogar.service;

import com.mycompany.conectahogar.dao.ResenaDAO;
import com.mycompany.conectahogar.dao.ServicioDAO;
import com.mycompany.conectahogar.model.Resena;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public class ResenaService {
    private final ResenaDAO resenaDao;
    private final ServicioDAO servicioDao;
    
    public ResenaService() {
        this.resenaDao = new ResenaDAOImpl();
        this.servicioDao = new ServicioDAOImpl();
    }
    
    public boolean agregarResena(Usuario cliente, int idServicio, double calificacion, String comentario) {
        // Verificar que el servicio está completado y pertenece al cliente
        Servicio servicio = servicioDao.buscarPorId(idServicio);
        if (servicio == null || !servicio.getCliente().equals(cliente) {
            return false;
        }
        
        Resena resena = new Resena();
        resena.setCliente(cliente);
        resena.setServicio(servicio);
        resena.setCalificacion(calificacion);
        resena.setComentario(comentario);
        resena.setFechaResena(LocalDateTime.now());
        
        return resenaDao.crearResena(resena);
    }
    
    public List<Resena> obtenerResenasTecnico(int idTecnico) {
        return resenaDao.obtenerPorTecnico(idTecnico);
    }
}