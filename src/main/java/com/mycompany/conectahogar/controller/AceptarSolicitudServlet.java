package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AceptarSolicitudServlet", urlPatterns = {"/aceptar-solicitud"})
public class AceptarSolicitudServlet extends HttpServlet {

    private final SolicitudService solicitudService = new SolicitudService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null || !(session.getAttribute("usuario") instanceof Tecnico)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Tecnico tecnico = (Tecnico) session.getAttribute("usuario");
            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
            double precioFinal = Double.parseDouble(request.getParameter("precioSugerido"));

            boolean exito = solicitudService.aceptarSolicitud(tecnico.getId_Usuario(), idSolicitud, precioFinal);

            if (exito) {
                session.setAttribute("mensajeExito", "¡Has aceptado el trabajo #" + idSolicitud + "!");
            } else {
                session.setAttribute("mensajeError", "No se pudo aceptar el trabajo. Es posible que ya haya sido tomado.");
            }
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Ocurrió un error al procesar la solicitud.");
        }
        
        response.sendRedirect(request.getContextPath() + "/panelTecnico");
    }
}