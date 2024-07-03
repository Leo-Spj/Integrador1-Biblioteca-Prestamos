package com.utp.biblioteca.resources.modelo;

import java.sql.Date;

public class Prestamo {

    private int prestamo_id;
    private Usuario usuario;
    private Libro libro;
    private Date fecha_prestamo;
    private Date fecha_limite;
    private Date fecha_devolucion;
    private boolean devuelto;

    public int getPrestamo_id() {
        return prestamo_id;
    }

    public void setPrestamo_id(int prestamo_id) {
        this.prestamo_id = prestamo_id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(Date fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "prestamo_id=" + prestamo_id +
                ", usuario=" + usuario+
                ", libro=" + libro +
                ", fecha_prestamo=" + fecha_prestamo +
                ", fecha_limite=" + fecha_limite +
                ", fecha_devolucion=" + fecha_devolucion +
                ", devuelto=" + devuelto +
                '}';
    }
}