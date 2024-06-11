package com.utp.biblioteca.resources.modelo;

public class Autor {

    private int autor_id;
    private String nombre;

    public int getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(int autor_id) {
        this.autor_id = autor_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "autor_id=" + autor_id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
