package com.mycompany.conectahogar.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Elimina la anotación @WebFilter si estás usando web.xml
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter inicializado correctamente - Versión 2.0");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        logger.info("Procesando petición para: {}", path);

        // Lista de rutas públicas
        if (isPublicPath(path)) {
            logger.debug("Permitiendo acceso público a: {}", path);
            chain.doFilter(request, response);
            return;
        }

        // Verificar sesión
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            logger.debug("Usuario autenticado, acceso permitido a: {}", path);
            chain.doFilter(request, response);
            return;
        }

        // Redirigir a login si no está autenticado
        logger.warn("Acceso no autorizado a: {}, redirigiendo a login", path);
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
    }

    private boolean isPublicPath(String path) {
        return path.equals("/login")
                || path.equals("/LoginServlet")
                || path.equals("/register.jsp")
                || path.equals("/register")
                || path.equals("/RegisterServlet")
                || path.equals("/registro")
                || path.equals("/")
                || path.equals("/index.jsp")
                || path.startsWith("/resources/")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg");
    }

    @Override
    public void destroy() {
        logger.info("AuthFilter finalizado correctamente");
    }
}
