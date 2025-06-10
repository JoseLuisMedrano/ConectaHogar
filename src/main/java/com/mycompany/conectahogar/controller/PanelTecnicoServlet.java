// Archivo: com/mycompany/conectahogar/controller/PanelTecnicoServlet.java
package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.Tecnico;
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

@WebServlet("/panelTecnico")
public class PanelTecnicoServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PanelTecnicoServlet.class);
    
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
        if (session == null || session.getAttribute("usuario") == null || !"Tecnico".equals(session.getAttribute("rol"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");
        
        List<SolicitudTrabajo> solicitudesPendientes = solicitudService.listarSolicitudesPendientes();
        List<SolicitudTrabajo> solicitudesAsignadas = solicitudService.listarSolicitudesPorTecnico(tecnico.getId_Usuario());

        request.setAttribute("solicitudesPendientes", solicitudesPendientes);
        request.setAttribute("solicitudesAsignadas", solicitudesAsignadas);
        request.getRequestDispatcher("panelTecnico.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null || !"Tecnico".equals(session.getAttribute("rol"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");
        String action = request.getParameter("action");
        int idSolicitud = 0;
        try {
            idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
        } catch (NumberFormatException e) {
            logger.error("ID de solicitud inválida: {}", request.getParameter("idSolicitud"), e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de solicitud inválida.");
            return;
        }

        boolean exito = false;
        String mensaje = "";

        switch (action) {
            case "aceptar":
                Double precioOfrecido = null;
                String precioOfrecidoStr = request.getParameter("precioOfrecido");
                if (precioOfrecidoStr != null && !precioOfrecidoStr.trim().isEmpty()) {
                    try {
                        precioOfrecido = Double.parseDouble(precioOfrecidoStr);
                    } catch (NumberFormatException e) {
                        logger.error("Precio ofrecido inválido para aceptar solicitud {}: {}", idSolicitud, precioOfrecidoStr, e);
                        mensaje = "Error: Precio ofrecido inválido.";
                        break;
                    }
                }

                if (precioOfrecido == null || precioOfrecido <= 0) {
                    mensaje = "Error: El precio ofrecido no puede estar vacío o ser menor o igual a cero.";
                    break;
                }
                
                exito = solicitudService.aceptarSolicitud(tecnico.getId_Usuario(), idSolicitud, precioOfrecido);
                if (exito) {
                    mensaje = "Solicitud aceptada y asignada con éxito.";
                } else {
                    mensaje = "Error al aceptar y asignar solicitud.";
                }
                break;
            case "rechazar":
                exito = solicitudService.rechazarSolicitud(idSolicitud);
                if (exito) {
                    mensaje = "Solicitud rechazada con éxito.";
                } else {
                    mensaje = "Error al rechazar solicitud.";
                }
                break;
            case "completar":
                exito = solicitudService.completarSolicitud(idSolicitud);
                if (exito) {
                    mensaje = "Solicitud marcada como completada con éxito.";
                } else {
                    mensaje = "Error al marcar solicitud como completada.";
                }
                break;
            case "contraoferta":
                Double nuevoPrecio = null;
                String nuevoPrecioStr = request.getParameter("nuevoPrecio");
                if (nuevoPrecioStr != null && !nuevoPrecioStr.trim().isEmpty()) {
                    try {
                        nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                    } catch (NumberFormatException e) {
                        logger.error("Nuevo precio de contraoferta inválido para solicitud {}: {}", idSolicitud, nuevoPrecioStr, e);
                        mensaje = "Error: Nuevo precio inválido para contraoferta.";
                        break;
                    }
                }
                
                if (nuevoPrecio == null || nuevoPrecio <= 0) {
                    mensaje = "Error: El nuevo precio de contraoferta no puede estar vacío o ser menor o igual a cero.";
                    break;
                }
                
                exito = solicitudService.hacerContraoferta(idSolicitud, nuevoPrecio);
                if (exito) {
                    mensaje = "Contraoferta realizada con éxito.";
                } else {
                    mensaje = "Error al realizar contraoferta.";
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