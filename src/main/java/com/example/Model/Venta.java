package com.example.Model;

public class Venta {
    private String dniPaciente;
    private String codigoProducto;
    private int cantidad;
    private int dia;
    private int mes;
    private int ano;

    public Venta(String dniPaciente, String codigoProducto, int cantidad, int dia, int mes, int ano) {
        this.dniPaciente=dniPaciente;
        this.codigoProducto=codigoProducto;
        this.cantidad=cantidad;
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
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

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public String getFecha() {
        return dia+"-"+mes+"-"+ano;
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

    public void setDia(int dia) {
        this.dia=dia;
    }

    public void setMes(int mes) {
        this.mes=mes;
    }

    public void setAno(int ano) {
        this.ano=ano;
    }
}