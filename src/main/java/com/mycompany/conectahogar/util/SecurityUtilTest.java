package com.mycompany.conectahogar.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {

    @Test
    void testHashYVerifyPassword() {
        String passwordPlana = "admin123";
        String passwordHasheada = SecurityUtil.hashPassword(passwordPlana);

        assertNotNull(passwordHasheada, "El hash no debería ser nulo.");
        assertTrue(SecurityUtil.verifyPassword(passwordPlana, passwordHasheada), "La verificación debería ser exitosa.");
        assertFalse(SecurityUtil.verifyPassword("mala_password", passwordHasheada), "La verificación con contraseña incorrecta debería fallar.");
    }
}