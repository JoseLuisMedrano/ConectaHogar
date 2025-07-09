package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    // En LoginServlet.java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // 1. Llama al servicio de autenticación
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.autenticarUsuario(correo, contrasena);

        // 2. Comprueba el resultado
        if (usuario != null) {
            // ÉXITO: El usuario y la contraseña son correctos.
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Redirige al panel correspondiente según el rol
            switch (usuario.getTipoUsuario()) {
                case CLIENTE:
                    response.sendRedirect(request.getContextPath() + "/panelCliente");
                    break;
                case TECNICO:
                    response.sendRedirect(request.getContextPath() + "/panelTecnico");
                    break;
                case ADMINISTRADOR:
                    response.sendRedirect(request.getContextPath() + "/panelAdmin"); // O la ruta que uses
                    break;
            }
        } else {
            // FALLO: El correo no existe o la contraseña es incorrecta.
            request.setAttribute("mensajeError", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
}
