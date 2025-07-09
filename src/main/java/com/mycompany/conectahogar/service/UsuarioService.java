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

    public boolean actualizarPerfilTecnico(int idTecnico, String especialidad, String disponibilidad) {
        TecnicoDAO tecnicoDAO = new TecnicoDAO();
        // El servicio simplemente delega la tarea al DAO correspondiente
        return tecnicoDAO.actualizarPerfil(idTecnico, especialidad, disponibilidad);
    }

    // En UsuarioService.java
    public Usuario autenticarUsuario(String email, String plainPassword) {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(email);

        if (usuario != null) {
            System.out.println("-------------------");
            System.out.println("4. [LOGIN] Intentando iniciar sesión como: " + email);
            System.out.println("5. [LOGIN] Contraseña ingresada (texto plano): [" + plainPassword + "]");
            System.out.println("6. [LOGIN] Hash guardado en la BD: [" + usuario.getContrasena() + "]");
            System.out.println("   -> Longitud del hash de la BD: " + usuario.getContrasena().length());

            boolean sonIguales = SecurityUtil.verifyPassword(plainPassword, usuario.getContrasena());
            System.out.println("7. [LOGIN] ¿Las contraseñas coinciden?: " + sonIguales);
            System.out.println("-------------------");

            if (sonIguales) {
                return usuario;
            }
        } else {
            System.out.println("-------------------");
            System.out.println("4. [LOGIN] Usuario no encontrado para el correo: " + email);
            System.out.println("-------------------");
        }

        return null;
    }

    /**
     * Registra un nuevo usuario en el sistema. Crea el registro en la tabla
     * 'usuarios' y luego en la tabla específica ('clientes' o 'tecnicos').
     *
     * @param usuario El objeto Usuario a registrar.
     * @return true si el registro fue completamente exitoso.
     * @throws Exception si el correo ya existe o hay un error.
     */
    public boolean registrarUsuario(Usuario usuario) throws Exception {
        // 1. Verificar si el correo ya existe
        if (usuarioDAO.obtenerUsuarioPorCorreo(usuario.getCorreoElectronico()) != null) {
            logger.warn("Intento de registro con correo ya existente: {}", usuario.getCorreoElectronico());
            return false;
        }

        // 2. Encriptar la contraseña ANTES de cualquier operación de BD
        String plainPassword = usuario.getContrasena();
        System.out.println("2. [REGISTRO] Contraseña que llega al servicio: [" + plainPassword + "]");
        String hashedPassword = SecurityUtil.hashPassword(plainPassword);
        System.out.println("3. [REGISTRO] Contraseña encriptada (hash): [" + hashedPassword + "]");
        System.out.println("   -> Longitud del hash: " + hashedPassword.length()); // Un hash de BCrypt debe tener 60 caracteres
        usuario.setContrasena(hashedPassword);

        // 3. Crear el usuario base en la tabla 'usuarios'
        boolean exitoUsuarioBase = usuarioDAO.crearUsuarioBase(usuario);

        // Si no se pudo crear el usuario base, detenemos el proceso
        if (!exitoUsuarioBase) {
            logger.error("No se pudo crear el registro base para el usuario: {}", usuario.getCorreoElectronico());
            return false;
        }

        logger.info("Registro base creado para usuario ID: {}", usuario.getId_Usuario());

        // 4. Crear el registro específico en 'clientes' o 'tecnicos'
        if (usuario instanceof Tecnico) {
            logger.info("El usuario es un Técnico. Creando registro específico...");
            return tecnicoDAO.crearRegistroTecnico((Tecnico) usuario);
        } else if (usuario instanceof Cliente) {
            logger.info("El usuario es un Cliente. Creando registro específico...");
            return clienteDAO.crearRegistroCliente((Cliente) usuario);
        }

        // Si no es ni Cliente ni Técnico (ej. Admin), el proceso termina aquí exitosamente.
        return true;
    }
}
