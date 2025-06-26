package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidar sesión previa al mostrar login
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    // Simulación de autenticación MEJORADA
    Usuario usuario = null;

    if ("cliente@test.com".equals(correo) && "123456".equals(contrasena)) {
        // ¡CORRECCIÓN! Ahora creamos un objeto Cliente directamente.
        Cliente cliente = new Cliente();
        cliente.setId_Usuario(1);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setCorreoElectronico(correo);
        cliente.setTipoUsuario(TipoUsuario.CLIENTE); // El tipo sigue siendo importante
        cliente.setDireccion("Av. de Prueba 123"); // Añadimos un dato específico de cliente
        usuario = cliente; // Asignamos el objeto Cliente a la variable usuario
        System.out.println("Simulando login exitoso para CLIENTE.");
    } else if ("tecnico@test.com".equals(correo) && "123456".equals(contrasena)) {
        // ¡CORRECCIÓN! Ahora creamos un objeto Tecnico directamente.
        Tecnico tecnico = new Tecnico();
        tecnico.setId_Usuario(2);
        tecnico.setNombre("Ana");
        tecnico.setApellido("García");
        tecnico.setCorreoElectronico(correo);
        tecnico.setTipoUsuario(TipoUsuario.TECNICO);
        usuario = tecnico; // Asignamos el objeto Tecnico a la variable usuario
        System.out.println("Simulando login exitoso para TECNICO.");
    }

    if (usuario == null) {
        request.setAttribute("mensajeError", "Credenciales de prueba inválidas.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

        // Crear sesión y redirigir según rol
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario); // 

        // ¡¡AQUÍ CORREGIMOS LAS RUTAS!! (Ver Paso 2)
        switch (usuario.getTipoUsuario()) {
            
            // En LoginServlet.java, dentro del switch
            case CLIENTE:
                // La URL correcta que coincide con @WebServlet("/panelCliente")
                response.sendRedirect(request.getContextPath() + "/panelCliente");
                break;
            case TECNICO:
                // La URL correcta que coincide con @WebServlet("/panelTecnico")
                response.sendRedirect(request.getContextPath() + "/panelTecnico");
                break;
            case ADMINISTRADOR:
                // Ajustar cuando tengas el panel de admin
                response.sendRedirect(request.getContextPath() + "/admin/panel"); // 
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }
}
