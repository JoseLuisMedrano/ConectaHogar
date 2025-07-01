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
        if (email == null || password == null) return null;
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(email);
        if (usuario != null && SecurityUtil.verifyPassword(password, usuario.getContrasena())) {
            logger.info("Autenticación exitosa para: {}", email);
            return usuario;
        }
        logger.warn("Fallo de autenticación para: {}", email);
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioDAO.obtenerUsuarioPorCorreo(usuario.getCorreoElectronico()) != null) {
            throw new Exception("El correo electrónico ya está en uso.");
        }
        boolean exitoUsuarioBase = usuarioDAO.crearUsuario(usuario);
        if (!exitoUsuarioBase) {
            return false;
        }
        if (usuario instanceof Tecnico) {
            return tecnicoDAO.crearRegistroTecnico((Tecnico) usuario);
        } else if (usuario instanceof Cliente) {
            return clienteDAO.crearRegistroCliente((Cliente) usuario);
        }
        return true;
    }
}