package com.mycompany.conectahogar.util;

import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// Usamos el patrón Singleton para asegurar que solo haya UNA instancia de este gestor.
public class GestorDeSolicitudes {

    private static final GestorDeSolicitudes instancia = new GestorDeSolicitudes();
    
    // Usamos una lista segura para concurrencia y un contador atómico para los IDs.
    private final List<SolicitudTrabajo> listaDeSolicitudes = new CopyOnWriteArrayList<>();
    private final AtomicInteger contadorIds = new AtomicInteger(100); // Empezamos los IDs desde 100

    // El constructor es privado para que no se pueda instanciar desde fuera.
    private GestorDeSolicitudes() {}

    // Método público para obtener la única instancia.
    public static GestorDeSolicitudes getInstancia() {
        return instancia;
    }

    // Método para añadir una nueva solicitud.
    public void agregarSolicitud(SolicitudTrabajo solicitud) {
        solicitud.setId(contadorIds.getAndIncrement()); // Asigna un ID único y lo incrementa.
        solicitud.setEstado(EstadoSolicitud.PENDIENTE); // Toda nueva solicitud nace como PENDIENTE.
        listaDeSolicitudes.add(solicitud);
        System.out.println("Nueva solicitud agregada al gestor. Total de solicitudes: " + listaDeSolicitudes.size());
    }

    // Método para obtener las solicitudes de un cliente específico.
    public List<SolicitudTrabajo> getSolicitudesPorCliente(int idCliente) {
        return listaDeSolicitudes.stream()
                .filter(sol -> sol.getIdCliente() == idCliente)
                .collect(Collectors.toList());
    }

    // Método para que los técnicos vean los trabajos disponibles.
    public List<SolicitudTrabajo> getSolicitudesPendientes() {
        return listaDeSolicitudes.stream()
                .filter(sol -> sol.getEstado() == EstadoSolicitud.PENDIENTE)
                .collect(Collectors.toList());
    }
    
    // Método para que un técnico vea sus trabajos asignados.
    public List<SolicitudTrabajo> getSolicitudesPorTecnico(int idTecnico) {
        return listaDeSolicitudes.stream()
                .filter(sol -> sol.getIdTecnico() != null && sol.getIdTecnico() == idTecnico)
                .collect(Collectors.toList());
    }
}