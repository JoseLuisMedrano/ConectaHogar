// Archivo: com/mycompany/conectahogar/model/SolicitudTrabajo.java
package com.mycompany.conectahogar.model;

import java.util.Date;

public class SolicitudTrabajo {

    private int id; // ID de la solicitud (autoincremental en DB)
    private int idCliente; // FK a usuarios (cliente)
    private Integer idTecnico; // FK a usuarios (tecnico), puede ser null
    private String descripcion;
    private Servicio servicio; // Enum Servicio (Ej: ELECTRICIDAD, FONTANERIA)
    private Double precioSugerido; // ¡CAMBIAR A DOUBLE!
    private Double precioFinal; // ¡CAMBIAR A DOUBLE! (También para consistencia)
    private EstadoSolicitud estado; // Enum EstadoSolicitud (Ej: PENDIENTE, ACEPTADA)
    private Date fechaCreacion;
    private Date fechaFinalizacion; // Puede ser null
    private String nombreTecnico;

    public SolicitudTrabajo(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    // Constructores
    public SolicitudTrabajo() {
        this.estado = EstadoSolicitud.PENDIENTE; // Estado por defecto
        this.fechaCreacion = new Date(); // Fecha de creación por defecto
    }

    public SolicitudTrabajo(int idCliente, String descripcion, Servicio servicio, Double precioSugerido) {
        this(); // Llama al constructor por defecto para establecer estado y fechaCreacion
        this.idCliente = idCliente;
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.precioSugerido = precioSugerido;
    }

    // --- Getters y Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdTecnico() { // Integer porque puede ser null
        return idTecnico;
    }

    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Double getPrecioSugerido() { // ¡CAMBIAR A DOUBLE!
        return precioSugerido;
    }

    public void setPrecioSugerido(Double precioSugerido) { // ¡CAMBIAR A DOUBLE!
        this.precioSugerido = precioSugerido;
    }

    public Double getPrecioFinal() { // ¡CAMBIAR A DOUBLE!
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) { // ¡CAMBIAR A DOUBLE!
        this.precioFinal = precioFinal;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
}
