package com.example.Model;

public class Venta {
    private String dniPaciente;
    private String codigoProducto;
    private int cantidad;

    public Venta(String dniPaciente, String codigoProducto, int cantidad) {
        this.dniPaciente=dniPaciente;
        this.codigoProducto=codigoProducto;
        this.cantidad=cantidad;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente=dniPaciente;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto=codigoProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad=cantidad;
    }
}