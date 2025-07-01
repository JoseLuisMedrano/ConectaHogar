package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PanelClienteServlet", urlPatterns = {"/panelCliente"})
public class PanelClienteServlet extends HttpServlet {
    
    private final SolicitudService solicitudService = new SolicitudService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Validar que el usuario esté en sesión y sea un cliente
        if (session == null || session.getAttribute("usuario") == null || !(session.getAttribute("usuario") instanceof Cliente)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuario");

        // 2. Cargar la lista de solicitudes del cliente para mostrar en la tabla
        List<SolicitudTrabajo> listaSolicitudes = solicitudService.listarSolicitudesPorCliente(cliente.getId_Usuario());
        request.setAttribute("listaSolicitudes", listaSolicitudes);
        
        // 3. Mover mensajes de la sesión al request para que se muestren solo una vez
        if (session.getAttribute("mensajeExito") != null) {
            request.setAttribute("mensajeExito", session.getAttribute("mensajeExito"));
            session.removeAttribute("mensajeExito");
        }
        if (session.getAttribute("mensajeError") != null) {
            request.setAttribute("mensajeError", session.getAttribute("mensajeError"));
            session.removeAttribute("mensajeError");
        }

        // 4. Enviar al JSP
        request.getRequestDispatcher("/WEB-INF/views/cliente/panelCliente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        // Lógica para cerrar la sesión
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Invalida y elimina la sesión
            }
            // Redirige a la página de inicio de sesión
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Si la acción no es 'logout', simplemente recarga el panel
            doGet(request, response);
        }
    }
}