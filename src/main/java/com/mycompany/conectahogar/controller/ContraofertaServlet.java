package com.mycompany.conectahogar.controller;

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
        try {
            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
            double nuevoPrecio = Double.parseDouble(request.getParameter("nuevoPrecio"));

            SolicitudService service = new SolicitudService();
            boolean exito = service.hacerContraoferta(idSolicitud, nuevoPrecio);

            if (exito) {
                session.setAttribute("mensajeExito", "Contraoferta enviada con éxito.");
            } else {
                session.setAttribute("mensajeError", "No se pudo enviar la contraoferta.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "El precio ingresado no es válido.");
        }
        response.sendRedirect(request.getContextPath() + "/panelTecnico");
    }
}
