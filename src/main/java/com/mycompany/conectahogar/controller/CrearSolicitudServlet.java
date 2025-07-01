package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Esta anotación es la que crea el "camino" o URL
@WebServlet(name = "CrearSolicitudServlet", urlPatterns = {"/crear-solicitud"})
public class CrearSolicitudServlet extends HttpServlet {

    private final SolicitudService solicitudService = new SolicitudService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        // 1. Validar que el usuario esté en sesión y sea un cliente
        if (session == null || session.getAttribute("usuario") == null || !(session.getAttribute("usuario") instanceof Cliente)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // 2. Obtener los datos del formulario y de la sesión
            Cliente cliente = (Cliente) session.getAttribute("usuario");
            String descripcion = request.getParameter("descripcion");
            String servicioStr = request.getParameter("servicio");
            double precioSugerido = Double.parseDouble(request.getParameter("precioSugerido"));

            // 3. Crear el objeto SolicitudTrabajo
            SolicitudTrabajo nuevaSolicitud = new SolicitudTrabajo();
            nuevaSolicitud.setIdCliente(cliente.getId_Usuario());
            nuevaSolicitud.setDescripcion(descripcion);
            nuevaSolicitud.setServicio(Servicio.valueOf(servicioStr));
            nuevaSolicitud.setPrecioSugerido(precioSugerido);

            // 4. Llamar al servicio para guardar la solicitud
            boolean exito = solicitudService.crearSolicitud(nuevaSolicitud);

            // 5. Redirigir de vuelta al panel con un mensaje
            if (exito) {
                session.setAttribute("mensajeExito", "¡Tu solicitud ha sido creada exitosamente!");
            } else {
                session.setAttribute("mensajeError", "Hubo un error al crear tu solicitud. Inténtalo de nuevo.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("mensajeError", "El precio sugerido debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            session.setAttribute("mensajeError", "Por favor, selecciona un servicio válido.");
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Ocurrió un error inesperado: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/panelCliente");
    }
}