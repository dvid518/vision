package com.example.Model;

public class Producto {
    private int idProducto;
    private String nombre;
    private Categoria categoria;
    private double precio;
    private int stock;

    public Producto(String nombre, Categoria categoria, double precio, int stock) {
        this.nombre=nombre;
        this.categoria=categoria;
        this.precio=precio;
        this.stock=stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int idProducto) {
        this.idProducto=idProducto;
    }
    
    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria=categoria;
    }

    public void setPrecio(double precio) {
        this.precio=precio;
    }

    public void setStock(int stock) {
        this.stock=stock;
    }
}