// Archivo: com/mycompany/conectahogar/controller/PanelClienteServlet.java
package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Servicio; // Asegúrate de que Servicio esté importado
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException; // Cambiado de javax.servlet.ServletException
import jakarta.servlet.annotation.WebServlet; // Cambiado de javax.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet; // Cambiado de javax.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest; // Cambiado de javax.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse; // Cambiado de javax.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession; // Cambiado de javax.servlet.http.HttpSession
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if (session == null || session.getAttribute("usuario") == null || !"Cliente".equals(session.getAttribute("rol"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuario");
        
        List<SolicitudTrabajo> solicitudesCliente = solicitudService.listarSolicitudesPorCliente(cliente.getId_Usuario());

        request.setAttribute("solicitudesCliente", solicitudesCliente);
        request.getRequestDispatcher("panelCliente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null || !"Cliente".equals(session.getAttribute("rol"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuario");
        String action = request.getParameter("action");
        String mensaje = "";
        boolean exito = false;

        switch (action) {
            case "crearSolicitud":
                String descripcion = request.getParameter("descripcion");
                String servicioStr = request.getParameter("servicio");
                String precioSugeridoStr = request.getParameter("precioSugerido");

                if (descripcion == null || descripcion.trim().isEmpty() ||
                    servicioStr == null || servicioStr.trim().isEmpty() ||
                    precioSugeridoStr == null || precioSugeridoStr.trim().isEmpty()) {
                    mensaje = "Error: Todos los campos de la solicitud son obligatorios.";
                    break;
                }

                try {
                    Servicio servicio = Servicio.valueOf(servicioStr.toUpperCase());
                    Double precioSugerido = Double.parseDouble(precioSugeridoStr);

                    SolicitudTrabajo nuevaSolicitud = new SolicitudTrabajo();
                    nuevaSolicitud.setIdCliente(cliente.getId_Usuario());
                    nuevaSolicitud.setDescripcion(descripcion);
                    nuevaSolicitud.setServicio(servicio);
                    nuevaSolicitud.setPrecioSugerido(precioSugerido);

                    exito = solicitudService.crearSolicitud(nuevaSolicitud);
                    if (exito) {
                        mensaje = "Solicitud de trabajo creada con éxito.";
                    } else {
                        mensaje = "Error al crear la solicitud de trabajo. Intente nuevamente.";
                    }
                } catch (IllegalArgumentException e) {
                    logger.error("Error al parsear servicio o precio sugerido: {}", e.getMessage(), e);
                    mensaje = "Error: Datos de servicio o precio sugerido inválidos.";
                }
                break;
            case "cancelarSolicitud":
                int idSolicitudCancelar = 0;
                try {
                    idSolicitudCancelar = Integer.parseInt(request.getParameter("idSolicitud"));
                } catch (NumberFormatException e) {
                    logger.error("ID de solicitud a cancelar inválida: {}", request.getParameter("idSolicitud"), e);
                    mensaje = "Error: ID de solicitud inválida para cancelar.";
                    break;
                }
                exito = solicitudService.rechazarSolicitud(idSolicitudCancelar);
                if (exito) {
                    mensaje = "Solicitud cancelada con éxito.";
                } else {
                    mensaje = "Error al cancelar la solicitud.";
                }
                break;
            default:
                mensaje = "Acción no reconocida.";
                break;
        }

        request.setAttribute("mensaje", mensaje);
        doGet(request, response);
    }
}