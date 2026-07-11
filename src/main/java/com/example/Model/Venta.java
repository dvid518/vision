package com.example.Model;

public class Venta {
    private final int idVenta;
    private Paciente paciente;
    private Producto producto;
    private int cantidad;

    public Venta(Paciente paciente, Producto producto, int cantidad) {
        this.idVenta=0;
        this.paciente=paciente;
        this.producto=producto;
        this.cantidad=cantidad;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setIdPaciente(Paciente paciente) {
        this.paciente=paciente;
    }

    public void setProducto(Producto producto) {
        this.producto=producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad=cantidad;
    }
}