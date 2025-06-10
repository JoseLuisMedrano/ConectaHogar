// Archivo: com/mycompany/conectahogar/controller/LoginServlet.java
package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario; // ¡Importación correcta para TipoUsuario!
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        logger.info("Redirigiendo a login.jsp después de logout o acceso directo.");
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        logger.info("Intento de login para correo: {}", correo);

        if (correo == null || correo.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            request.setAttribute("mensaje", "Por favor, ingrese correo y contraseña.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            logger.warn("Intento de login con campos vacíos para correo: {}", correo);
            return;
        }

        Usuario usuario = usuarioService.autenticarUsuario(correo, contrasena);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            // Esto asume que usuario.getTipoUsuario() retorna un objeto TipoUsuario
            session.setAttribute("rol", usuario.getTipoUsuario().name()); 
            
            logger.info("Login exitoso para usuario: {} con rol: {}", usuario.getCorreoElectronico(), usuario.getTipoUsuario().name());

            // Redirigir según el tipo de usuario
            // ¡CORRECCIÓN CRÍTICA AQUÍ: USAR TipoUsuario.CLIENTE, TipoUsuario.TECNICO, TipoUsuario.ADMINISTRADOR!
            switch (usuario.getTipoUsuario()) {
                case CLIENTE: // Antes era 'case TipoUsuario.CLIENTE:', pero el compilador ahora soporta esto si es un enum
                    response.sendRedirect("panelCliente.jsp");
                    break;
                case TECNICO:
                    response.sendRedirect("panelTecnico.jsp");
                    break;
                case ADMINISTRADOR:
                    response.sendRedirect("panelAdmin.jsp");
                    break;
                default:
                    response.sendRedirect("login.jsp?error=rolDesconocido");
                    break;
            }
        } else {
            request.setAttribute("mensaje", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            logger.warn("Login fallido para correo: {}", correo);
        }
    }
}