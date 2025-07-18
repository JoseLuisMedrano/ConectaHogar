package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.SolicitudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PanelClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Validación de sesión
        if (session == null || session.getAttribute("usuario") == null
                || !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.CLIENTE)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuario");

        if (cliente.getDireccion() == null || cliente.getDireccion().isEmpty()) {
            // Si no tiene dirección, lo mandamos a una página para que la complete
            request.setAttribute("mensajeAdvertencia", "Por favor, completa tu dirección para poder crear una solicitud.");
            // Aquí podrías redirigirlo a una sección de "Editar Perfil"
            request.getRequestDispatcher("/WEB-INF/views/cliente/completarPerfil.jsp").forward(request, response);
            return; // Detenemos la ejecución para que no muestre la página de crear solicitud
        }

        // --- CÓDIGO CORREGIDO ---
        // 2. Creamos UNA instancia del servicio
        SolicitudService service = new SolicitudService();

        // 3. Usamos el servicio para obtener TODA la información necesaria
        List<SolicitudTrabajo> misSolicitudes = service.listarSolicitudesPorCliente(cliente.getId_Usuario());
        List<SolicitudTrabajo> contraofertas = service.listarContraofertasParaCliente(cliente.getId_Usuario());

        // 4. Pasamos los datos a la vista (JSP)
        request.setAttribute("solicitudes", misSolicitudes);
        request.setAttribute("contraofertas", contraofertas);
        request.setAttribute("serviciosDisponibles", Arrays.asList(Servicio.values()));

        request.getRequestDispatcher("/WEB-INF/views/cliente/panelCliente.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    // Es importante validar que el objeto en sesión no sea nulo antes de hacer el casting
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    Cliente cliente = (Cliente) session.getAttribute("usuario");
    String action = request.getParameter("accion");
    String redirectURL = request.getContextPath() + "/panelCliente";

    if ("crearSolicitud".equals(action)) {
        try {
            // 1. Creamos el objeto SolicitudTrabajo con los datos del formulario
            SolicitudTrabajo nuevaSolicitud = new SolicitudTrabajo();
            nuevaSolicitud.setIdCliente(cliente.getId_Usuario());
            nuevaSolicitud.setDescripcion(request.getParameter("descripcion"));
            nuevaSolicitud.setServicio(Servicio.valueOf(request.getParameter("servicio")));
            nuevaSolicitud.setFechaCreacion(new Date());
            
            nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

            // 2. Llamamos al servicio para guardar en la base de datos
            SolicitudService service = new SolicitudService();
            boolean exito = service.crearNuevaSolicitud(nuevaSolicitud);

            if (exito) {
                session.setAttribute("mensajeExito", "¡Solicitud creada y enviada a los técnicos!");
                redirectURL += "?active_tab=misSolicitudes";
            } else {
                session.setAttribute("mensajeError", "No se pudo guardar la solicitud.");
            }
        } catch (Exception e) {
            session.setAttribute("mensajeError", "Error al crear la solicitud. Verifique los datos.");
            e.printStackTrace();
        }
    }
    
    // 3. Redirigimos al usuario
    response.sendRedirect(redirectURL);
}
}
