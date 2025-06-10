// Archivo: src/main/java/com/mycompany/conectahogar/model/TipoUsuario.java
package com.mycompany.conectahogar.model;

public enum TipoUsuario {
    CLIENTE("Cliente"), // Asocia un String a cada enum
    TECNICO("Tecnico"),
    ADMINISTRADOR("Administrador");

    private final String rolBD; // Campo para el valor en la base de datos

    // Constructor para el enum
    TipoUsuario(String rolBD) {
        this.rolBD = rolBD;
    }

    // Getter para obtener el valor que se guardaría en la BD
    public String getRolBD() {
        return rolBD;
    }

    // Método estático para convertir un String (de la BD) a un TipoUsuario
    public static TipoUsuario fromString(String text) {
        for (TipoUsuario b : TipoUsuario.values()) {
            if (b.rolBD.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún TipoUsuario con el texto: " + text);
    }
}