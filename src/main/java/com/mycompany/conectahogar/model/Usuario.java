package com.mycompany.conectahogar.model;

import java.util.Date;

public class Usuario {
    private int id_Usuario;
    private String correoElectronico;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String dni; // ¡Nuevo campo para el DNI!
    private Date fechaRegistro;
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    // Constructor completo actualizado para incluir DNI
    public Usuario(int id_Usuario, String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni, Date fechaRegistro, TipoUsuario tipoUsuario) {
        this.id_Usuario = id_Usuario;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni; // Asignación del DNI
        this.fechaRegistro = fechaRegistro;
        this.tipoUsuario = tipoUsuario;
    }
    
    // Constructor para registro (sin id_Usuario y fechaRegistro que se generan en BD/Service)
    public Usuario(String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni, TipoUsuario tipoUsuario) { // Constructor actualizado para DNI
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni; // Asignación del DNI
        this.tipoUsuario = tipoUsuario;
    }

    // --- Getters y Setters ---

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    // ¡Getter y Setter para DNI!
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_Usuario=" + id_Usuario + ", correoElectronico=" + correoElectronico + ", contrasena=" + contrasena + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", direccion=" + direccion + ", dni=" + dni + ", fechaRegistro=" + fechaRegistro + ", tipoUsuario=" + tipoUsuario + '}';
    }
}