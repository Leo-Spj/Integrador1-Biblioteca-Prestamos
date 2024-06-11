package com.utp.biblioteca.resources.modelo;

import java.sql.Date;

public class Prestamo {

    private int prestamo_id;
    private int usuario_id;
    private int libro_id;
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

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
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
                ", usuario_id=" + usuario_id +
                ", libro_id=" + libro_id +
                ", fecha_prestamo=" + fecha_prestamo +
                ", fecha_limite=" + fecha_limite +
                ", fecha_devolucion=" + fecha_devolucion +
                ", devuelto=" + devuelto +
                '}';
    }
}