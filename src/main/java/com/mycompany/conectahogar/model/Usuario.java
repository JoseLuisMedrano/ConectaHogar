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
    private String dni;
    private Date fechaRegistro;
    private TipoUsuario tipoUsuario;
    private int edad;
    private String sexo;

    public Usuario() {
    }

    // Constructor completo para ser llamado por las clases hijas
    public Usuario(int id_Usuario, String correoElectronico, String contrasena, String nombre, String apellido, String telefono, String direccion, String dni, Date fechaRegistro, TipoUsuario tipoUsuario, int edad, String sexo) {
        this.id_Usuario = id_Usuario;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni;
        this.fechaRegistro = fechaRegistro;
        this.tipoUsuario = tipoUsuario;
        this.edad = edad;
        this.sexo = sexo;
    }
    
    // Constructor para nuevos registros
    public Usuario(String correoElectronico, String contrasena, String nombre, String apellido, String telefono, String direccion, String dni, TipoUsuario tipoUsuario, int edad, String sexo) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.dni = dni;
        this.tipoUsuario = tipoUsuario;
        this.edad = edad;
        this.sexo = sexo;
    }

    // (Aquí van todos tus Getters y Setters, incluyendo los de edad y sexo)
    public int getId_Usuario() { return id_Usuario; }
    public void setId_Usuario(int id_Usuario) { this.id_Usuario = id_Usuario; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}