package com.mycompany.conectahogar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Este servlet escucha en la URL /logout
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual (sin crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        
        // 2. Si existe una sesión, invalidarla
        if (session != null) {
            session.invalidate();
        }
        
        // 3. Redirigir a la página de login
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hacemos que tanto GET como POST hagan lo mismo para mayor flexibilidad
        doPost(request, response);
    }
}