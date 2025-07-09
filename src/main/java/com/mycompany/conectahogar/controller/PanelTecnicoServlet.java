package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.SolicitudService;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PanelTecnicoServlet extends HttpServlet {

    // En PanelTecnicoServlet.java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");
        String action = request.getParameter("accion");

        if ("actualizarPerfil".equals(action)) {
            String especialidad = request.getParameter("especialidad");
            String disponibilidad = request.getParameter("disponibilidad");

            UsuarioService usuarioService = new UsuarioService();
            boolean exito = usuarioService.actualizarPerfilTecnico(tecnico.getId_Usuario(), especialidad, disponibilidad);

            if (exito) {
                // Actualizar también el objeto en la sesión para que se refleje inmediatamente
                tecnico.setEspecialidad(especialidad);
                tecnico.setDisponibilidad(disponibilidad);
                session.setAttribute("usuario", tecnico);
                request.setAttribute("mensajeExito", "Perfil actualizado con éxito.");
            } else {
                request.setAttribute("mensajeError", "Error al actualizar el perfil.");
            }
        }

        // Recargamos la página para mostrar el mensaje y los datos actualizados
        doGet(request, response);
    }

// En PanelTecnicoServlet.java
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Validación de sesión
        if (session == null || session.getAttribute("usuario") == null
                || !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.TECNICO)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        // Lógica para el perfil inactivo
        if (!tecnico.isPerfilActivo()) {
            request.setAttribute("mensajeAdvertencia", "¡Tu perfil no está activo! Completa tus datos para empezar a recibir trabajos.");
        }

        // --- CORRECCIÓN DE ARQUITECTURA ---
        // 2. Creamos una instancia del servicio de solicitudes
        SolicitudService solicitudService = new SolicitudService();

        // 3. Usamos el servicio para obtener las listas de solicitudes
        List<SolicitudTrabajo> solicitudesPendientes = solicitudService.listarSolicitudesPendientes();
        List<SolicitudTrabajo> solicitudesAsignadas = solicitudService.listarSolicitudesPorTecnico(tecnico.getId_Usuario());

        // 4. Pasamos todas las listas y objetos al JSP
        request.setAttribute("tecnico", tecnico);
        request.setAttribute("serviciosDisponibles", Arrays.asList(Servicio.values()));
        request.setAttribute("solicitudesPendientes", solicitudesPendientes);
        request.setAttribute("solicitudesAsignadas", solicitudesAsignadas);

        request.getRequestDispatcher("/WEB-INF/views/tecnico/panelTecnico.jsp").forward(request, response);
    }
}
