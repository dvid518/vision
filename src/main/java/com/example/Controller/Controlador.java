package com.example.Controller;

import com.example.View.VentanaPrincipal;

public class Controlador {
    private final ControladorPacientes cp;
    private final ControladorConsultas cc;
    private final ControladorHistorias ch;
    private final ControladorProductos cpr;
    private final ControladorVentas cv;

    public Controlador(VentanaPrincipal v) {
        cp=new ControladorPacientes(v);
        cc=new ControladorConsultas(v);
        ch=new ControladorHistorias(v);
        cpr=new ControladorProductos(v);
        cv=new ControladorVentas(v);
    }

    public void start() {
        cp.start();
        cc.start();
        ch.start();
        cpr.start();
        cv.start();
    }
}