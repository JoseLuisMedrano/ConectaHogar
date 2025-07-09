package com.mycompany.conectahogar.controller;

import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.service.UsuarioService;
import com.mycompany.conectahogar.util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    // El GET está perfecto, decide qué página mostrar.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");

        if (role == null || role.isEmpty()) {
            // Si no hay rol, muestra la página para elegir
            request.getRequestDispatcher("/WEB-INF/views/auth/register_role_selection.jsp").forward(request, response);
        } else {
            // Si ya eligió rol, pasa el rol al JSP y muestra el formulario de detalles
            request.setAttribute("role", role);
            request.getRequestDispatcher("/WEB-INF/views/auth/register_details_form.jsp").forward(request, response);
        }
    }

    // El POST procesa el formulario de detalles
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recoger todos los datos del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String dni = request.getParameter("dni");
        String tipoUsuarioStr = request.getParameter("tipoUsuario");

        if (contrasena == null || contrasena.trim().isEmpty()) {
            request.setAttribute("mensajeError", "La contraseña no puede estar vacía.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register_details_form.jsp").forward(request, response);
            return;
        }

        // 2. Crear el objeto de usuario correspondiente
        Usuario nuevoUsuario;
        TipoUsuario tipo = TipoUsuario.valueOf(tipoUsuarioStr.toUpperCase());

        if (tipo == TipoUsuario.CLIENTE) {
            nuevoUsuario = new Cliente();
        } else {
            nuevoUsuario = new Tecnico();
        }

        // 3. Asignar TODOS los datos comunes al objeto
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setCorreoElectronico(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setTipoUsuario(tipo);

        // --- ESTA ES LA LÍNEA CLAVE Y LA ÚNICA CORRECCIÓN NECESARIA ---
        // Le pasamos la contraseña en TEXTO PLANO al objeto.
        // El servicio se encargará de encriptarla.
        nuevoUsuario.setContrasena(contrasena);

        // 4. Guardar el usuario usando el servicio
        try {
            UsuarioService usuarioService = new UsuarioService();
            boolean exito = usuarioService.registrarUsuario(nuevoUsuario);
            if (exito) {
                request.getSession().setAttribute("mensajeExito", "¡Registro exitoso! Ya puedes iniciar sesión.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("mensajeError", "No se pudo completar el registro. El correo electrónico ya podría estar en uso.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register_details_form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Ocurrió un error inesperado en el servidor.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register_details_form.jsp").forward(request, response);
        }
    }
}
