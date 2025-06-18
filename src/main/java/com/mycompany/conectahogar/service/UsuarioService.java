// Archivo: com/mycompany/conectahogar/service/UsuarioService.java
package com.mycompany.conectahogar.service;

import com.mycompany.conectahogar.dao.UsuarioDAO;
import com.mycompany.conectahogar.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mycompany.conectahogar.util.SecurityUtil;


public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Autentica a un usuario usando su correo electrónico y contraseña.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return El objeto Usuario si las credenciales son válidas, o null si no lo son.
     */
    public Usuario autenticarUsuario(String email, String password) {
    if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        logger.warn("Intento de autenticación con campos vacíos.");
        return null;
    }

    // 1. Obtener el usuario por su correo
    Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(email);

    // 2. Verificar si el usuario existe Y si la contraseña coincide con el hash
    if (usuario != null && SecurityUtil.verifyPassword(password, usuario.getContrasena())) {
        logger.info("Autenticación exitosa para el usuario: {}", email); 
        return usuario; // Devuelve el usuario si todo es correcto
    } else {
        logger.warn("Fallo de autenticación para el usuario: {}", email);
        return null; // Devuelve null si el usuario no existe o la contraseña es incorrecta
    }
}

    // Aquí podrías agregar otros métodos de lógica de negocio relacionados con usuarios
    // Por ejemplo, para el registro de un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getCorreoElectronico() == null || usuario.getContrasena() == null ||
            usuario.getCorreoElectronico().trim().isEmpty() || usuario.getContrasena().trim().isEmpty()) {
            logger.warn("Intento de registro de usuario con datos incompletos o nulos.");
            return false;
        }
        // El método crearUsuario en UsuarioDAO ya se encarga de la encriptación
        return usuarioDAO.crearUsuario(usuario);
    }
}