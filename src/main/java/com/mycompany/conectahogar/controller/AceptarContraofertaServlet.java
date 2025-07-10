package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AceptarContraofertaServlet extends HttpServlet {

    // En AceptarContraofertaServlet.java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 1. Obtenemos solo el ID de la solicitud desde el formulario
        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));

        // 2. Llamamos al servicio que ejecuta la lógica correcta
        SolicitudService service = new SolicitudService();
        boolean exito = service.aceptarContraoferta(idSolicitud); // Este método llama al DAO que acabamos de verificar

        if (exito) {
            session.setAttribute("mensajeExito", "Contraoferta aceptada. El trabajo ha sido asignado.");
        } else {
            session.setAttribute("mensajeError", "Error al aceptar la contraoferta.");
        }

        // 3. Redirigimos de vuelta al panel del cliente
        response.sendRedirect(request.getContextPath() + "/panelCliente");
    }
}
