// Archivo: src/main/java/com/mycompany/conectahogar/model/Tecnico.java
package com.mycompany.conectahogar.model;

import java.util.Date; // Necesario si usas fechaRegistro en el constructor de super

public class Tecnico extends Usuario {
    private String especialidad;
    private boolean disponible;

    public Tecnico() {
        super();
    }

    // Constructor completo para Técnico, pasando todos los datos de Usuario y los propios de Técnico
    public Tecnico(int id_Usuario, String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni, Date fechaRegistro, String especialidad, boolean disponible) {
        super(id_Usuario, correoElectronico, contrasena, nombre, apellido, telefono, 
              direccion, dni, fechaRegistro, TipoUsuario.TECNICO); // El rol es siempre TECNICO
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

    // Constructor para un nuevo registro de técnico (sin ID ni fechaRegistro inicial)
    public Tecnico(String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni, String especialidad, boolean disponible) {
        super(correoElectronico, contrasena, nombre, apellido, telefono, 
              direccion, dni, TipoUsuario.TECNICO); // El rol es siempre TECNICO
        this.especialidad = especialidad;
        this.disponible = disponible;
    }

    // --- Getters y Setters específicos de Tecnico ---

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() {
        return "Tecnico{" +
               "id_Usuario=" + getId_Usuario() + // Heredado de Usuario
               ", correoElectronico='" + getCorreoElectronico() + '\'' + // Heredado de Usuario
               ", nombre='" + getNombre() + '\'' + // Heredado de Usuario
               ", especialidad='" + especialidad + '\'' +
               ", disponible=" + disponible +
               '}';
    }
}