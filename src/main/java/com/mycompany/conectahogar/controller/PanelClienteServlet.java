// Archivo: com/mycompany/conectahogar/controller/PanelClienteServlet.java
package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@WebServlet("/panelCliente")
public class PanelClienteServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PanelClienteServlet.class);

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

        // Verificamos que el usuario exista en la sesión y sea un Cliente
        if (session == null || session.getAttribute("usuario") == null
                || !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.CLIENTE)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ¡CORRECCIÓN! Ahora el casting es seguro porque creamos un Cliente en el LoginServlet.
        Cliente cliente = (Cliente) session.getAttribute("usuario");

        // --- SIMULACIÓN DE DATOS (sin base de datos) ---
        // Comentamos la línea que usa el servicio de base de datos
        // List<SolicitudTrabajo> solicitudesCliente = solicitudService.listarSolicitudesPorCliente(cliente.getId_Usuario());
        // En su lugar, creamos datos de prueba para que el JSP funcione
        request.setAttribute("serviciosDisponibles", Arrays.asList(Servicio.values())); //
        request.setAttribute("solicitudes", new java.util.ArrayList<SolicitudTrabajo>()); // Enviamos una lista vacía por ahora
        request.setAttribute("clienteActual", cliente); // Pasamos el cliente al JSP

        // Mostramos mensajes de éxito/error si existen
        if (request.getAttribute("mensajeExito") != null) {
            request.setAttribute("mensajeExito", request.getAttribute("mensajeExito"));
        }
        if (request.getAttribute("mensajeError") != null) {
            request.setAttribute("mensajeError", request.getAttribute("mensajeError"));
        }
        // --- FIN DE SIMULACIÓN ---

        request.getRequestDispatcher("/panelCliente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("accion"); //

        // Simulamos la creación de una solicitud para que veas el mensaje
        if ("crearSolicitud".equals(action)) {
            String descripcion = request.getParameter("descripcion");
            if (descripcion != null && !descripcion.trim().isEmpty()) {
                System.out.println("Simulación: Solicitud creada con descripción: " + descripcion);
                request.setAttribute("mensajeExito", "Solicitud de trabajo creada con éxito (simulación).");
            } else {
                request.setAttribute("mensajeError", "Error: La descripción es obligatoria.");
            }
        }

        // Finalmente, llamamos a doGet para recargar la página con los datos y mensajes actualizados.
        doGet(request, response);
    }
}

