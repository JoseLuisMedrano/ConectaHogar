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
        // Simplemente muestra el formulario de registro
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recoger todos los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String dni = request.getParameter("dni");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String edadStr = request.getParameter("edad");
        String sexo = request.getParameter("sexo");
        String tipoUsuarioStr = request.getParameter("tipoUsuario");

        try {
            // 2. Validaciones y conversiones
            if (nombre == null || apellido == null || correo == null || contrasena == null || tipoUsuarioStr == null ||
                nombre.trim().isEmpty() || apellido.trim().isEmpty() || correo.trim().isEmpty() || contrasena.trim().isEmpty()) {
                throw new Exception("Todos los campos marcados son obligatorios.");
            }
            
            TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioStr);
            int edad = Integer.parseInt(edadStr);
            
            Usuario nuevoUsuario;

            // 3. Crear el tipo de objeto correcto (Cliente o Tecnico)
            if (tipoUsuario == TipoUsuario.TECNICO) {
                Tecnico nuevoTecnico = new Tecnico();
                
                // Recoger los campos específicos del técnico
                String especialidad = request.getParameter("especialidad");
                String disponibilidad = request.getParameter("disponibilidad");
                
                nuevoTecnico.setEspecialidad(especialidad);
                nuevoTecnico.setDisponibilidad(disponibilidad);
                // Se pueden establecer valores por defecto para otros campos si se desea
                nuevoTecnico.setCalificacionPromedio(0.0);
                nuevoTecnico.setCertificaciones("Ninguna");

                nuevoUsuario = nuevoTecnico;
            } else {
                nuevoUsuario = new Cliente();
            }

            // 4. Establecer los datos comunes en el objeto
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setCorreoElectronico(correo);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setDni(dni);
            nuevoUsuario.setDireccion(direccion);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setEdad(edad);
            nuevoUsuario.setSexo(sexo);
            nuevoUsuario.setTipoUsuario(tipoUsuario);

            // 5. Llamar al servicio para registrar al usuario
            boolean exito = usuarioService.registrarUsuario(nuevoUsuario);

            // 6. Redirigir con el mensaje apropiado
            if (exito) {
                HttpSession session = request.getSession();
                session.setAttribute("mensajeExito", "¡Registro exitoso! Ya puedes iniciar sesión.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("mensajeError", "No se pudo completar el registro. El correo o DNI podrían ya estar en uso.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Capturar cualquier error (ej. email duplicado, datos inválidos)
            request.setAttribute("mensajeError", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }
}