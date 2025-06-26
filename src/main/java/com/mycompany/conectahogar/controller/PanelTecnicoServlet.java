package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import jakarta.servlet.http.HttpSession; 
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/panelTecnico")
public class PanelTecnicoServlet extends HttpServlet {

    private SolicitudService solicitudService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.solicitudService = new SolicitudService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        
        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.TECNICO) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        List<SolicitudTrabajo> solicitudesPendientes = solicitudService.listarSolicitudesPendientes();
        List<SolicitudTrabajo> solicitudesAsignadas = solicitudService.listarSolicitudesPorTecnico(tecnico.getId_Usuario());

        request.setAttribute("solicitudesPendientes", solicitudesPendientes);
        request.setAttribute("solicitudesAsignadas", solicitudesAsignadas);
        
        request.getRequestDispatcher("/WEB-INF/views/tecnico/panelTecnico.jsp").forward(request, response);
    }
}
