package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class PanelClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario.getTipoUsuario() != TipoUsuario.CLIENTE) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cliente cliente = (Cliente) usuario;
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("/WEB-INF/views/cliente/panelCliente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        } else {
            doGet(request, response);
        }
    }
}