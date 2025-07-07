package com.mycompany.conectahogar.controller; // o .servlets, donde lo tengas

import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AceptarSolicitudServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectURL = request.getContextPath() + "/panelTecnico";

        // 1. Validar que el usuario sea un técnico autenticado
        if (session == null || session.getAttribute("usuario") == null ||
            !(((com.mycompany.conectahogar.model.Usuario) session.getAttribute("usuario")).getTipoUsuario() == com.mycompany.conectahogar.model.TipoUsuario.TECNICO)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        try {
            // 2. Obtener los datos del formulario de forma segura
            int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
            double precioFinal = Double.parseDouble(request.getParameter("precioFinal"));

            // 3. Llamar al servicio para procesar la lógica de negocio
            SolicitudService solicitudService = new SolicitudService();
            boolean exito = solicitudService.aceptarSolicitud(tecnico.getId_Usuario(), idSolicitud, precioFinal);

            // 4. Preparar un mensaje para el usuario
            if (exito) {
                session.setAttribute("mensajeExito", "¡Trabajo aceptado con éxito! Ahora puedes verlo en 'Mis Trabajos'.");
            } else {
                session.setAttribute("mensajeError", "No se pudo aceptar el trabajo. Es posible que ya haya sido tomado.");
            }

        } catch (NumberFormatException e) {
            // Esto ocurre si idSolicitud o precioFinal no son números válidos
            session.setAttribute("mensajeError", "Error: Datos de solicitud inválidos.");
            e.printStackTrace();
        } catch (Exception e) {
            // Cualquier otro error inesperado
            session.setAttribute("mensajeError", "Ocurrió un error inesperado al procesar la solicitud.");
            e.printStackTrace();
        }
        
        // 5. Redirigir de vuelta al panel del técnico
        response.sendRedirect(redirectURL);
    }
}