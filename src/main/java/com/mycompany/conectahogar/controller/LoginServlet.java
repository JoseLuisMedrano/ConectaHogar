package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    private UsuarioService usuarioService = new UsuarioService();

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

        if (correo == null || contrasena == null || correo.trim().isEmpty() || contrasena.trim().isEmpty()) {
            request.setAttribute("error", "Correo y contraseña son obligatorios");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        Usuario usuario = usuarioService.autenticarUsuario(correo, contrasena);

        if (usuario == null) {
            request.setAttribute("error", "Credenciales inválidas");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        // Crear sesión y redirigir según rol
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);

        switch (usuario.getTipoUsuario()) {
            case CLIENTE:
                response.sendRedirect(request.getContextPath() + "/cliente/panel");
                break;
            case TECNICO:
                response.sendRedirect(request.getContextPath() + "/tecnico/panel");
                break;
            case ADMINISTRADOR:
                response.sendRedirect(request.getContextPath() + "/admin/panel");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}