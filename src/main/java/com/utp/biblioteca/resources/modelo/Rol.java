package com.utp.biblioteca.resources.modelo;

public class Rol {

    private int rol_id;
    private String nombre;

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "rol_id=" + rol_id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
