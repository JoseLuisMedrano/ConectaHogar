package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.dao.SolicitudTrabajoDAO;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.util.GestorDeSolicitudes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PanelTecnicoServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("usuario") == null ||
        !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.TECNICO)) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    Tecnico tecnico = (Tecnico) session.getAttribute("usuario");
    
    // --- CÓDIGO NUEVO: Leer desde la Base de Datos ---
    SolicitudTrabajoDAO dao = new SolicitudTrabajoDAO();
    List<SolicitudTrabajo> solicitudesPendientes = dao.obtenerSolicitudesPorEstado(EstadoSolicitud.PENDIENTE);
    // Para el futuro: aquí llamarías a un método para obtener las solicitudes asignadas a este técnico
    // List<SolicitudTrabajo> solicitudesAsignadas = dao.obtenerSolicitudesPorTecnico(tecnico.getId_Usuario());
    
    request.setAttribute("tecnico", tecnico);
    request.setAttribute("solicitudesPendientes", solicitudesPendientes);
    // request.setAttribute("solicitudesAsignadas", solicitudesAsignadas); // Descomentar cuando lo implementes
    
    request.getRequestDispatcher("/WEB-INF/views/tecnico/panelTecnico.jsp").forward(request, response);
}
}