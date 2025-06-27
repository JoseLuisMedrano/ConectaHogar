package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidar sesión previa al mostrar login
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Simulación de autenticación
        Usuario usuario = null;

        if ("cliente@test.com".equals(correo) && "123456".equals(contrasena)) {
            Cliente cliente = new Cliente();
            cliente.setCorreoElectronico(correo);
            cliente.setNombre("Juan");
            cliente.setApellido("Pérez");
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);
            usuario = cliente;
        } else if ("tecnico@test.com".equals(correo) && "123456".equals(contrasena)) {
            Tecnico tecnico = new Tecnico();
            tecnico.setCorreoElectronico(correo);
            tecnico.setNombre("Ana");
            tecnico.setApellido("García");
            tecnico.setTipoUsuario(TipoUsuario.TECNICO);
            usuario = tecnico;
        }

        if (usuario == null) {
            request.setAttribute("mensajeError", "Credenciales inválidas.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        // Crear sesión y redirigir según el rol
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);

        switch (usuario.getTipoUsuario()) {
            case CLIENTE:
                response.sendRedirect(request.getContextPath() + "/panelCliente");
                break;
            case TECNICO:
                response.sendRedirect(request.getContextPath() + "/panelTecnico");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }
}
