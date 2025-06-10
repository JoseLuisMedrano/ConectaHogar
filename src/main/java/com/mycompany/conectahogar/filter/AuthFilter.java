// Archivo: com/mycompany/conectahogar/filter/AuthFilter.java
package com.mycompany.conectahogar.filter;

import jakarta.servlet.Filter; // Cambiado de javax.servlet.Filter
import jakarta.servlet.FilterChain; // Cambiado de javax.servlet.FilterChain
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException; // Cambiado de javax.servlet.ServletException
import jakarta.servlet.ServletRequest; // Cambiado de javax.servlet.ServletRequest
import jakarta.servlet.ServletResponse; // Cambiado de javax.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter; // Cambiado de javax.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest; // Cambiado de javax.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse; // Cambiado de javax.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession; // Cambiado de javax.servlet.http.HttpSession
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter inicializado.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // Rutas que no requieren autenticación
        if (path.startsWith("/recursos/") || path.equals("/login.jsp") || path.equals("/LoginServlet")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("usuario") != null);
        String rol = (loggedIn) ? (String) session.getAttribute("rol") : null;

        if (loggedIn) {
            // Usuario autenticado, verificar acceso según el rol
            if (path.startsWith("/panelCliente.jsp") || path.equals("/panelCliente")) {
                if ("Cliente".equals(rol)) {
                    chain.doFilter(request, response);
                } else {
                    logger.warn("Acceso denegado: Usuario con rol '{}' intentó acceder a panel de cliente.", rol);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=accesoDenegado");
                }
            } else if (path.startsWith("/panelTecnico.jsp") || path.equals("/panelTecnico")) {
                if ("Tecnico".equals(rol)) {
                    chain.doFilter(request, response);
                } else {
                    logger.warn("Acceso denegado: Usuario con rol '{}' intentó acceder a panel de técnico.", rol);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=accesoDenegado");
                }
            } else if (path.startsWith("/panelAdmin.jsp") || path.equals("/panelAdmin")) {
                 if ("Administrador".equals(rol)) {
                    chain.doFilter(request, response);
                } else {
                    logger.warn("Acceso denegado: Usuario con rol '{}' intentó acceder a panel de administrador.", rol);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=accesoDenegado");
                }
            } else {
                // Si el usuario está logeado y no es una página restringida específica
                chain.doFilter(request, response);
            }
        } else {
            // Usuario no autenticado, redirigir a login
            logger.info("Usuario no autenticado intentó acceder a: {}", path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        logger.info("AuthFilter destruido.");
    }
}