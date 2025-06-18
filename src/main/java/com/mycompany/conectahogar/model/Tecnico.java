// Archivo: src/main/java/com/mycompany/conectahogar/model/Tecnico.java
package com.mycompany.conectahogar.model;

import java.util.Date;

public class Tecnico extends Usuario {
    private String especialidad;
    private String disponibilidad; // Aseguramos que es String
    private String certificaciones;
    private double calificacionPromedio;

    public Tecnico() {
        super();
    }

    public Tecnico(String especialidad, String disponibilidad, String certificaciones, double calificacionPromedio, 
                   int id_Usuario, String correoElectronico, String contrasena, String nombre, 
                   String apellido, String telefono, String direccion, String dni, Date fechaRegistro) {
        super(id_Usuario, correoElectronico, contrasena, nombre, apellido, telefono, direccion, dni, fechaRegistro, TipoUsuario.TECNICO);
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
        this.certificaciones = certificaciones;
        this.calificacionPromedio = calificacionPromedio;
    }

    public Tecnico(String especialidad, String disponibilidad, String certificaciones, double calificacionPromedio, 
                   String correoElectronico, String contrasena, String nombre, String apellido, 
                   String telefono, String direccion, String dni) {
        super(correoElectronico, contrasena, nombre, apellido, telefono, direccion, dni, TipoUsuario.TECNICO);
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
        this.certificaciones = certificaciones;
        this.calificacionPromedio = calificacionPromedio;
    }

    // Getters y Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(String certificaciones) {
        this.certificaciones = certificaciones;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "especialidad='" + especialidad + '\'' +
                ", disponibilidad='" + disponibilidad + '\'' +
                ", certificaciones='" + certificaciones + '\'' +
                ", calificacionPromedio=" + calificacionPromedio +
                "} " + super.toString();
    }
}