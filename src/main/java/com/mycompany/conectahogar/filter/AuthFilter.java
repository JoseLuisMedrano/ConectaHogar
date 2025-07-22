package com.mycompany.conectahogar.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filtro de seguridad que intercepta todas las peticiones para verificar la
 * autenticación del usuario.
 */
public class AuthFilter implements Filter {

    // Lista de rutas públicas que no requieren iniciar sesión.
    private static final List<String> RUTAS_PUBLICAS = Arrays.asList("/login", "/register", "/", "/index.jsp", "/health");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Obtenemos la ruta solicitada (ej: /login, /panelCliente)
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // 1. Permitir siempre el acceso a recursos estáticos (CSS, JS, imágenes, etc.)
        if (path.startsWith("/static/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Comprobar si la ruta está en nuestra lista de rutas públicas
        boolean esRutaPublica = RUTAS_PUBLICAS.stream().anyMatch(ruta -> path.equalsIgnoreCase(ruta));
        
        if (esRutaPublica) {
            chain.doFilter(request, response); // Si es pública, dejamos pasar al usuario.
            return;
        }
        
        // 3. Si no es pública, comprobamos si hay una sesión activa con un usuario
        HttpSession session = httpRequest.getSession(false); // 'false' para no crear una sesión si no existe

        if (session != null && session.getAttribute("usuario") != null) {
            // Si hay un usuario en la sesión, el acceso está permitido.
            
            // Añadimos cabeceras para evitar que el navegador guarde la página en caché.
            // Esto soluciona el problema del "botón atrás" después de cerrar sesión.
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            httpResponse.setDateHeader("Expires", 0); // Proxies.
            
            chain.doFilter(request, response); // Dejamos pasar al usuario.
        } else {
            // 4. Si la ruta es privada y no hay sesión, redirigimos al login.
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método de inicialización (puede dejarse vacío).
    }

    @Override
    public void destroy() {
        // Método de destrucción (puede dejarse vacío).
    }
}