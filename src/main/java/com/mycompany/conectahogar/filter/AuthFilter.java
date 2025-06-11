package com.mycompany.conectahogar.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger; // O ch.qos.logback.classic.Logger si usas Logback

// Mapea el filtro a todas las URLs (o a las que quieras proteger)
// El valor de urlPatterns debe proteger lo que necesitas
// Si el LoginServlet está en la raíz, o lo quieres proteger,
// el filtro lo interceptará.
@WebFilter(urlPatterns = {"/cliente/*", "/tecnico/*", "/admin/*", "/*"}) // Puedes ajustar esto
public class AuthFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());
    // Si estás usando Logback, cambia la línea anterior por:
    // private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AuthFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("AuthFilter inicializado.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // No crear una nueva sesión si no existe

        String requestURI = httpRequest.getRequestURI(); // Ej: /ConectaHogar/login
        String contextPath = httpRequest.getContextPath(); // Ej: /ConectaHogar
        String path = requestURI.substring(contextPath.length()); // Ej: /login o /

        LOGGER.info("Usuario intentó acceder a: " + path); // Esto es lo que vemos en el log

        // RUTAS PÚBLICAS: URLs a las que se puede acceder sin autenticación
        // Es CRUCIAL que el LoginServlet y sus JSPs (como login.jsp) estén aquí
        if (path.startsWith("/login") || // URL del LoginServlet
            path.startsWith("/LoginServlet") || // URL del LoginServlet (si usas la clase)
            path.equals("/") || // La URL raíz que podría ir al index.jsp
            path.startsWith("/index.jsp") || // Si el index.jsp es accesible directamente
            path.startsWith("/recursos/") || // Si tienes CSS/JS en una carpeta "recursos"
            path.startsWith("/css/") || // Rutas para CSS
            path.startsWith("/js/") || // Rutas para JavaScript
            path.startsWith("/images/") || // Rutas para imágenes
            path.startsWith("/registro") || // Si tienes un servlet de registro
            path.startsWith("/RegistroServlet")) // Si tienes un servlet de registro
            {
            // El recurso es público, permite el acceso
            chain.doFilter(request, response);
            return; // Importante para evitar que se ejecute el resto del código
        }

        // Si la sesión es nula o no tiene un atributo "usuario" (o como lo llames)
        if (session == null || session.getAttribute("usuario") == null) {
            LOGGER.info("Usuario no autenticado, redirigiendo a /login");
            // Redirigir a la página de login (la URL pública que maneja el inicio de sesión)
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login"); // Redirige al LoginServlet o su página
            return; // Detiene el procesamiento posterior de la solicitud
        }

        // Si el usuario está autenticado, permite el acceso al recurso solicitado
        LOGGER.info("Usuario autenticado, permitiendo acceso a: " + path);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("AuthFilter destruido.");
    }
}