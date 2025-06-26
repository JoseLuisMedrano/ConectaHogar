package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
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
import java.util.Arrays;

@WebServlet("/panelCliente")
public class PanelClienteServlet extends HttpServlet {

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
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.CLIENTE) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuario");
        request.setAttribute("clienteActual", cliente);
        request.setAttribute("serviciosDisponibles", Arrays.asList("Servicio1", "Servicio2", "Servicio3"));
        request.setAttribute("solicitudes", new java.util.ArrayList<>());
        
        request.getRequestDispatcher("/WEB-INF/views/cliente/panelCliente.jsp").forward(request, response);
    }
}
