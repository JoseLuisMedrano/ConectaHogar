// Archivo: src/main/java/com/mycompany/conectahogar/model/Cliente.java
package com.mycompany.conectahogar.model;

import java.util.Date; // Necesario si usas fechaRegistro en el constructor de super

public class Cliente extends Usuario {
    // No necesitamos una 'direccion' aquí si ya la tenemos en Usuario,
    // a menos que sea una dirección específica del Cliente diferente a la del Usuario.
    // Si es la misma, la hereda de Usuario. Si es diferente, mantendríamos este campo.
    // Por ahora, asumiré que la hereda y esta línea se puede eliminar o comentar:
    // private String direccion;

    public Cliente() {
        super();
    }
    
    // Este constructor original está intentando asignar un rol (setRol) que ya no existe.
    // También el constructor de super() vacío no tiene TipoUsuario.
    // Lo comento para dar uno más apropiado.
    /*
    public Cliente(String direccion) {
        super();
        this.direccion = direccion;
        this.setRol(TipoUsuario.CLIENTE); // CAMBIAR a setTipoUsuario
    }
    */
    
    // Este constructor no tiene un campo para 'fechaRegistro' y el orden de los parámetros
    // no coincide con el constructor de Usuario que toma id_Usuario.
    // Lo comento y propongo una versión actualizada.
    /*
    public Cliente(String direccion, int id_Usuario, String nombre, String apellido, 
                   String correoElectronico, String contrasena, String telefono, String dni) {
        super(id_Usuario, nombre, apellido, correoElectronico, contrasena, telefono, dni, TipoUsuario.CLIENTE);
        this.direccion = direccion;
    }
    */
    
    // Constructor recomendado para Cliente, haciendo uso de los constructores de Usuario
    // Este constructor recibe todos los datos que Usuario necesita, incluyendo la dirección
    // y el DNI, y pasa el TipoUsuario directamente al super.
    public Cliente(int id_Usuario, String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni, Date fechaRegistro) { // Añadido fechaRegistro
        super(id_Usuario, correoElectronico, contrasena, nombre, apellido, telefono, 
              direccion, dni, fechaRegistro, TipoUsuario.CLIENTE);
        // this.direccion = direccion; // Si Usuario ya la tiene, esta línea es redundante aquí
    }

    // Constructor para un nuevo registro de cliente (sin ID ni fechaRegistro inicial)
    public Cliente(String correoElectronico, String contrasena, 
                   String nombre, String apellido, String telefono, String direccion, 
                   String dni) {
        super(correoElectronico, contrasena, nombre, apellido, telefono, 
              direccion, dni, TipoUsuario.CLIENTE);
        // this.direccion = direccion; // Si Usuario ya la tiene, esta línea es redundante aquí
    }
    
    // Si Cliente tenía una 'direccion' propia y diferente a la de Usuario,
    // mantendrías los getters y setters aquí. Si no, puedes eliminarlos.
    // Si la 'direccion' del Cliente es la misma que la del Usuario,
    // la obtienes y la estableces a través de los métodos de Usuario.
    /*
    @Override // Si esta direccion es la misma que la de Usuario, anota como @Override
    public String getDireccion() {
        return super.getDireccion(); // Retorna la dirección del Usuario padre
    }
    
    @Override // Si esta direccion es la misma que la de Usuario, anota como @Override
    public void setDireccion(String direccion) {
        super.setDireccion(direccion); // Establece la dirección en el Usuario padre
    }
    */
    // Si `Cliente` ya no tiene su propio campo `direccion`, los métodos `getDireccion()` y `setDireccion()`
    // se heredan directamente de `Usuario` y no necesitan ser redefinidos aquí, a menos que quieras
    // añadir lógica específica.
    // Para simplificar, asumiremos que se heredan directamente de Usuario.
}