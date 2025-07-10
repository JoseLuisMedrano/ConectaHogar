package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class ContraofertaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Obtenemos el técnico de la sesión para saber QUIÉN hace la oferta
        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        try {
            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
            double nuevoPrecio = Double.parseDouble(request.getParameter("nuevoPrecio"));

            SolicitudService service = new SolicitudService();
            // Le pasamos el ID del técnico al servicio
            boolean exito = service.hacerContraoferta(idSolicitud, nuevoPrecio, tecnico.getId_Usuario());

            if (exito) {
                session.setAttribute("mensajeExito", "Contraoferta enviada con éxito.");
            } else {
                session.setAttribute("mensajeError", "No se pudo enviar la contraoferta.");
            }
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Datos de contraoferta inválidos.");
        }
        response.sendRedirect(request.getContextPath() + "/panelTecnico");
    }
}
