package com.example.Model;

public class Producto {
    private String id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, String categoria, double precio, int stock) {
        this.id=id;
        this.nombre=nombre;
        this.categoria=categoria;
        this.precio=precio;
        this.stock=stock;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setId(String id) {
        this.id=id;
    }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria=categoria;
    }

    public void setPrecio(double precio) {
        this.precio=precio;
    }

    public void setStock(int stock) {
        this.stock=stock;
    }
}