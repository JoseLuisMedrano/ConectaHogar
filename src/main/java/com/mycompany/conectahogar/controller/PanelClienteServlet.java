package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.dao.SolicitudTrabajoDAO;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.EstadoSolicitud;
import com.mycompany.conectahogar.model.Servicio;
import com.mycompany.conectahogar.model.SolicitudTrabajo;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
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

        // 1. Validar que el usuario en sesión exista y sea un Cliente
        if (session == null || session.getAttribute("usuario") == null
                || !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.CLIENTE)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Obtener el objeto Cliente de la sesión
        Cliente cliente = (Cliente) session.getAttribute("usuario");

        // Llama al DAO para obtener las solicitudes SOLO de este cliente
        SolicitudTrabajoDAO dao = new SolicitudTrabajoDAO();
        List<SolicitudTrabajo> misSolicitudes = dao.obtenerSolicitudesPorCliente(cliente.getId_Usuario());

        // 4. Pasar los datos a la vista (JSP)
        request.setAttribute("solicitudes", misSolicitudes);
        request.setAttribute("serviciosDisponibles", Arrays.asList(Servicio.values()));

        // 5. Enviar la petición al JSP para que se renderice
        request.getRequestDispatcher("/WEB-INF/views/cliente/panelCliente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        // Validar que la sesión y el usuario existan antes de procesar
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Cliente cliente = (Cliente) session.getAttribute("usuario");

        String action = request.getParameter("accion");

        // Procesar la acción de crear una nueva solicitud
        if ("crearSolicitud".equals(action)) {
            try {
                // 1. Recoger los datos del formulario
                String descripcion = request.getParameter("descripcion");
                Servicio servicio = Servicio.valueOf(request.getParameter("servicio"));
                double precioSugerido = Double.parseDouble(request.getParameter("precioSugerido"));

                // 2. Crear un nuevo objeto SolicitudTrabajo
                SolicitudTrabajo nuevaSolicitud = new SolicitudTrabajo();
                nuevaSolicitud.setIdCliente(cliente.getId_Usuario());
                nuevaSolicitud.setDescripcion(descripcion);
                nuevaSolicitud.setServicio(servicio);
                nuevaSolicitud.setPrecioSugerido(precioSugerido);
                nuevaSolicitud.setFechaCreacion(new Date());
                nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

                SolicitudTrabajoDAO dao = new SolicitudTrabajoDAO();
                boolean exito = dao.crearSolicitud(nuevaSolicitud);

                if (exito) {
                    request.setAttribute("mensajeExito", "¡Solicitud guardada en la base de datos!");
                } else {
                    request.setAttribute("mensajeError", "No se pudo guardar la solicitud.");
                }

            } catch (Exception e) {
                // En caso de error (ej. datos de formulario incorrectos)
                request.setAttribute("mensajeError", "Error al crear la solicitud. Verifique los datos.");
                e.printStackTrace(); // Es buena idea registrar el error en la consola del servidor
            }
        }

        // 5. Después de procesar el POST, llamar a doGet para recargar la página con los datos actualizados
        doGet(request, response);
    }
}
