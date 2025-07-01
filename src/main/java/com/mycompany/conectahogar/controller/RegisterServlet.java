package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Recoger todos los parámetros, incluyendo los nuevos
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String edadStr = request.getParameter("edad"); // ¡NUEVO!
        String sexo = request.getParameter("sexo"); // ¡NUEVO!
        String tipoUsuarioStr = request.getParameter("tipoUsuario");

        try {
            TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioStr.toUpperCase());
            int edad = Integer.parseInt(edadStr); // Convertir edad a número

            Usuario nuevoUsuario = (tipoUsuario == TipoUsuario.TECNICO) ? new Tecnico() : new Cliente();

            // Establecer todos los datos
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setCorreoElectronico(correo);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setTipoUsuario(tipoUsuario);
            nuevoUsuario.setEdad(edad); // ¡NUEVO!
            nuevoUsuario.setSexo(sexo); // ¡NUEVO!
            
            // DNI y Dirección son opcionales por ahora, se pueden añadir después
            // nuevoUsuario.setDni("...");
            // nuevoUsuario.setDireccion("...");

            boolean exito = usuarioService.registrarUsuario(nuevoUsuario);
            
            if (exito) {
                HttpSession session = request.getSession();
                session.setAttribute("mensajeExito", "¡Registro exitoso! Por favor, inicia sesión.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("mensajeError", "No se pudo completar el registro. Inténtalo de nuevo.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error en los datos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }
}