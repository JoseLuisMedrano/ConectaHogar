// Archivo: src/main/java/com/mycompany/conectahogar/model/Cliente.java
package com.mycompany.conectahogar.model;

import java.util.Date; // Necesario si usas fechaRegistro en el constructor de super

public class Cliente extends Usuario {

    public Cliente() {
        super();
    }

    public Cliente(int id_Usuario, String correoElectronico, String contrasena,
            String nombre, String apellido, String telefono, String direccion,
            String dni, Date fechaRegistro, int edad, String sexo) {
        super(id_Usuario, correoElectronico, contrasena, nombre, apellido, telefono,
                direccion, dni, fechaRegistro, TipoUsuario.CLIENTE, edad, sexo);
        // this.direccion = direccion; // Si Usuario ya la tiene, esta línea es redundante aquí
    }

    // Constructor para un nuevo registro de cliente (sin ID ni fechaRegistro inicial)
    public Cliente(String correoElectronico, String contrasena,
            String nombre, String apellido, String telefono, String direccion,
            String dni,int edad, String sexo) {
        super(correoElectronico, contrasena, nombre, apellido, telefono,
                direccion, dni, TipoUsuario.CLIENTE, edad, sexo);
        // this.direccion = direccion; // Si Usuario ya la tiene, esta línea es redundante aquí
    }

}
