package com.mycompany.conectahogar.model;

import java.util.Date;

public class Tecnico extends Usuario {
    private String especialidad;
    private String disponibilidad;
    private String certificaciones;
    private double calificacionPromedio;

    public Tecnico() {
        super();
    }
    
    // Constructor para un nuevo registro de técnico
    public Tecnico(String correoElectronico, String contrasena, String nombre, String apellido, String telefono, String direccion, String dni, int edad, String sexo, String especialidad, String disponibilidad, String certificaciones, double calificacionPromedio) {
        // Llama al constructor de Usuario para nuevos registros
        super(correoElectronico, contrasena, nombre, apellido, telefono, direccion, dni, TipoUsuario.TECNICO, edad, sexo);
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
        this.certificaciones = certificaciones;
        this.calificacionPromedio = calificacionPromedio;
    }

    // (Aquí van todos los Getters y Setters de los campos específicos de Tecnico)
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }
    public String getCertificaciones() { return certificaciones; }
    public void setCertificaciones(String certificaciones) { this.certificaciones = certificaciones; }
    public double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }
}