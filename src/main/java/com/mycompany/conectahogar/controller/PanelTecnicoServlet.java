package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PanelTecnicoServlet", urlPatterns = {"/panelTecnico"})
public class PanelTecnicoServlet extends HttpServlet {

    private final SolicitudService solicitudService = new SolicitudService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null || !(session.getAttribute("usuario") instanceof Tecnico)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        // Cargar datos reales usando el nuevo método
        List<SolicitudTrabajo> solicitudesPendientes = solicitudService.listarSolicitudesPendientesPorEspecialidad(tecnico.getEspecialidad());
        List<SolicitudTrabajo> solicitudesAsignadas = solicitudService.listarSolicitudesPorTecnico(tecnico.getId_Usuario());
        
        request.setAttribute("solicitudesPendientes", solicitudesPendientes);
        request.setAttribute("solicitudesAsignadas", solicitudesAsignadas);
        
        if (session.getAttribute("mensajeExito") != null) {
            request.setAttribute("mensajeExito", session.getAttribute("mensajeExito"));
            session.removeAttribute("mensajeExito");
        }
        if (session.getAttribute("mensajeError") != null) {
            request.setAttribute("mensajeError", session.getAttribute("mensajeError"));
            session.removeAttribute("mensajeError");
        }

        request.getRequestDispatcher("/WEB-INF/views/tecnico/panelTecnico.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("logout".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            doGet(request, response);
        }
    }
}