package com.mycompany.conectahogar.service;

import com.mycompany.conectahogar.dao.ClienteDAO;
import com.mycompany.conectahogar.dao.TecnicoDAO;
import com.mycompany.conectahogar.dao.UsuarioDAO;
import com.mycompany.conectahogar.model.Cliente;
import com.mycompany.conectahogar.model.Tecnico;
import com.mycompany.conectahogar.model.Usuario;
import com.mycompany.conectahogar.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioDAO usuarioDAO;
    private final ClienteDAO clienteDAO;
    private final TecnicoDAO tecnicoDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.clienteDAO = new ClienteDAO();
        this.tecnicoDAO = new TecnicoDAO();
    }

    public Usuario autenticarUsuario(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            logger.warn("Intento de autenticación con campos vacíos.");
            return null;
        }

        logger.info("--- INICIO DE AUTENTICACIÓN PARA: {} ---", email);
        
        // 1. Obtener el usuario por su correo
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(email);

        if (usuario == null) {
            logger.warn("Usuario no encontrado en la base de datos con el correo: {}", email);
            logger.info("--- FIN DE AUTENTICACIÓN ---");
            return null;
        }

        // --- SECCIÓN DE DEPURACIÓN ---
        String plainPasswordFromForm = password;
        String hashedPasswordFromDB = usuario.getContrasena();
        
        logger.info("Contraseña del formulario (texto plano): '{}'", plainPasswordFromForm);
        logger.info("Hash de la contraseña desde la BD: '{}'", hashedPasswordFromDB);
        
        // 2. Verificar si la contraseña coincide con el hash
        boolean passwordsMatch = SecurityUtil.verifyPassword(plainPasswordFromForm, hashedPasswordFromDB);
        
        logger.info("¿Las contraseñas coinciden? -> {}", passwordsMatch);
        // --- FIN DE SECCIÓN DE DEPURACIÓN ---

        if (passwordsMatch) {
            logger.info("¡ÉXITO! Autenticación correcta para: {}", email);
            logger.info("--- FIN DE AUTENTICACIÓN ---");
            return usuario; // Devuelve el usuario si todo es correcto
        } else {
            logger.warn("¡FALLO! La contraseña proporcionada no coincide con el hash de la BD.");
            logger.info("--- FIN DE AUTENTICACIÓN ---");
            return null; // Devuelve null si la contraseña es incorrecta
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Crea el registro en la tabla 'usuarios' y luego en la tabla específica ('clientes' o 'tecnicos').
     * @param usuario El objeto Usuario a registrar.
     * @return true si el registro fue completamente exitoso.
     * @throws Exception si el correo ya existe o hay un error.
     */
    public boolean registrarUsuario(Usuario usuario) throws Exception {
        // 1. Verificar si el correo ya existe
        if (usuarioDAO.obtenerUsuarioPorCorreo(usuario.getCorreoElectronico()) != null) {
            throw new Exception("El correo electrónico ya está en uso.");
        }

        // 2. Crear el usuario base en la tabla 'usuarios'
        boolean exitoUsuarioBase = usuarioDAO.crearUsuario(usuario);
        
        // Si no se pudo crear el usuario base, detenemos el proceso
        if (!exitoUsuarioBase) {
            logger.error("No se pudo crear el registro base para el usuario: {}", usuario.getCorreoElectronico());
            return false;
        }
        
        logger.info("Registro base creado para usuario ID: {}", usuario.getId_Usuario());

        // 3. Crear el registro específico en 'clientes' o 'tecnicos'
        if (usuario instanceof Tecnico) {
            logger.info("El usuario es un Técnico. Creando registro específico...");
            return tecnicoDAO.crearRegistroTecnico((Tecnico) usuario);
        } else if (usuario instanceof Cliente) {
            logger.info("El usuario es un Cliente. Creando registro específico...");
            return clienteDAO.crearRegistroCliente((Cliente) usuario);
        }
        
        // Si es un tipo de usuario que no tiene tabla específica (ej: Administrador)
        return true;
    }
}