package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AceptarContraofertaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));

        SolicitudService service = new SolicitudService();
        if (service.aceptarContraoferta(idSolicitud)) {
            session.setAttribute("mensajeExito", "Contraoferta aceptada. El trabajo ha sido asignado.");
        } else {
            session.setAttribute("mensajeError", "Error al aceptar la contraoferta.");
        }
        response.sendRedirect(request.getContextPath() + "/panelCliente");
    }
}