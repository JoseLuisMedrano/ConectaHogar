package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.model.TipoUsuario;
import com.mycompany.conectahogar.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {

    @Test
    void testCrearYObtenerUsuario() {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario nuevoUsuario = new Usuario();
        
        String correoPrueba = "test" + System.currentTimeMillis() + "@test.com";
        
        nuevoUsuario.setCorreoElectronico(correoPrueba);
        nuevoUsuario.setContrasena("hash_de_prueba");
        nuevoUsuario.setNombre("Test");
        nuevoUsuario.setApellido("User");
        nuevoUsuario.setTipoUsuario(TipoUsuario.CLIENTE);

        boolean creado = dao.crearUsuarioBase(nuevoUsuario);
        assertTrue(creado, "El usuario base debería haberse creado en la BD.");

        
        Usuario usuarioObtenido = dao.obtenerUsuarioPorCorreo(correoPrueba);
        assertNotNull(usuarioObtenido, "El usuario obtenido no debería ser nulo.");
        assertEquals("Test", usuarioObtenido.getNombre(), "El nombre del usuario no coincide.");
    }
}