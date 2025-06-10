package com.mycompany.conectahogar.model;

import java.time.LocalDateTime;

public class Resena {

    private int idResena;
    private Cliente cliente;
    private Servicio servicio;
    private double calificacion; // 1-5 estrellas
    private String comentario;
    private LocalDateTime fechaResena;

    public Resena() {
    }

    public Resena(int idResena, Cliente cliente, Servicio servicio, double calificacion, String comentario, LocalDateTime fechaResena) {
        this.idResena = idResena;
        this.cliente = cliente;
        this.servicio = servicio;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fechaResena = fechaResena;
    }

    public int getIdResena() {
        return idResena;
    }

    public void setIdResena(int idResena) {
        this.idResena = idResena;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDateTime fechaResena) {
        this.fechaResena = fechaResena;
    }

}
