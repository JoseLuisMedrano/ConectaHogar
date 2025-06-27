package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.SolicitudTrabajo; // Import necesario para la lista vacía
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList; // Import necesario para la lista vacía

public class PanelTecnicoServlet extends HttpServlet {

    // El servicio se puede inicializar, pero no lo usaremos en la simulación
    // private SolicitudService solicitudService = new SolicitudService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 1. Validar que el usuario en sesión exista y sea un Técnico
        if (session == null || session.getAttribute("usuario") == null ||
            !(((Usuario) session.getAttribute("usuario")).getTipoUsuario() == TipoUsuario.TECNICO)) {
            response.sendRedirect(request.getContextPath() + "/login"); // Redirigir a /login
            return;
        }

        // 2. Obtener el objeto Tecnico de la sesión (el casting ahora es seguro)
        Tecnico tecnico = (Tecnico) session.getAttribute("usuario");

        // --- SIMULACIÓN DE DATOS (sin base de datos) ---

        // 3. Pasar el nombre del trabajador al JSP, como lo espera
        request.setAttribute("trabajadorNombre", tecnico.getNombre());

        // 4. Enviar listas vacías para que el JSP no falle (si las usara)
        request.setAttribute("solicitudesPendientes", new ArrayList<SolicitudTrabajo>());
        request.setAttribute("solicitudesAsignadas", new ArrayList<SolicitudTrabajo>());

        // 5. Redirigir al JSP correcto
        request.getRequestDispatcher("/WEB-INF/views/tecnico/panelTecnico.jsp").forward(request, response);
    }
}