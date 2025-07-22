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

public class AuthFilter implements Filter {

    // Lista de rutas públicas que no necesitan login.
    private static final List<String> RUTAS_PUBLICAS = Arrays.asList("/login", "/register");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Obtenemos la ruta solicitada (sin el nombre de la aplicación)
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // 1. Permitir siempre el acceso a recursos estáticos (CSS, JS, imágenes)
        if (path.startsWith("/static/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Comprobar si la ruta es pública
        boolean esRutaPublica = RUTAS_PUBLICAS.stream().anyMatch(ruta -> path.startsWith(ruta));

        if (esRutaPublica) {
            chain.doFilter(request, response); // Si es pública, dejamos pasar
            return;
        }

        // 3. Si no es pública, comprobamos si hay una sesión activa
        HttpSession session = httpRequest.getSession(false); // false = no crear sesión si no existe

        if (session != null && session.getAttribute("usuario") != null) {
            chain.doFilter(request, response); // Si hay usuario en la sesión, dejamos pasar
        } else {
            // 4. Si no es pública y no hay sesión, redirigimos al login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Método de inicialización (puede estar vacío)
    }

    @Override
    public void destroy() {
        // Método de destrucción (puede estar vacío)
    }
}
