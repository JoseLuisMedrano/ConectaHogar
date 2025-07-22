package com.mycompany.conectahogar.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class PerformanceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        // Deja que la solicitud continúe su camino (hacia el servlet, etc.)
        chain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Imprime en el log el tiempo que tardó la solicitud
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        logger.info("PERFORMANCE: Petición a [{}] tardó {} ms.", httpRequest.getRequestURI(), duration);
    }
}
