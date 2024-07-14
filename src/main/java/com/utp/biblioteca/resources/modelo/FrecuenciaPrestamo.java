package com.utp.biblioteca.resources.modelo;

public class FrecuenciaPrestamo {
    private String titulo;
    private int frecuencia;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    @Override
    public String toString() {
        return "FrecuenciaPrestamo{" +
                "titulo='" + titulo + '\'' +
                ", frecuencia=" + frecuencia +
                '}';
    }
}