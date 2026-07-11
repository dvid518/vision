package com.example.Model;

public class Categoria {
    private final int idCategoria;
    private final String nombre;

    public Categoria(int idCategoria, String nombre) {
        this.idCategoria=idCategoria;
        this.nombre=nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }
}