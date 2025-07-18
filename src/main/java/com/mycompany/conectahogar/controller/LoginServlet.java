package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.autenticarUsuario(correo, contrasena);

        if (usuario != null) {
            // ÉXITO
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Redirige al panel correspondiente
            switch (usuario.getTipoUsuario()) {
                case CLIENTE:
                    response.sendRedirect(request.getContextPath() + "/panelCliente");
                    break;
                case TECNICO:
                    response.sendRedirect(request.getContextPath() + "/panelTecnico");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login");
                    break;
            }
        } else {
            // FALLO
            request.setAttribute("mensajeError", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simplemente muestra la página de login
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }
}
