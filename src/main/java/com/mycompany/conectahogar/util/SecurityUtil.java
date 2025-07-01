// Archivo: com/mycompany/conectahogar/util/SecurityUtil.java
package com.mycompany.conectahogar.util;

// ¡CAMBIO IMPORTANTE! Se usan las clases de la librería at.favre.lib
import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityUtil {

    /**
     * Crea un hash de una contraseña usando la librería at.favre.lib.bcrypt.
     * @param plainPassword La contraseña en texto plano.
     * @return La contraseña hasheada.
     */
    public static String hashPassword(String plainPassword) {
        // Se usa el método de esta librería. El 12 es el "cost factor", un buen valor por defecto.
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash guardado.
     * @param plainPassword La contraseña en texto plano a verificar.
     * @param hashedPassword El hash guardado en la base de datos.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Se usa el método de verificación de esta librería.
            BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
            return result.verified;
        } catch (Exception e) {
            // Maneja el caso en que el hash no tenga el formato correcto
            return false;
        }
    }
}