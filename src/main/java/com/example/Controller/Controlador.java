package com.example.Controller;

import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class Controlador {
    private final ControladorPacientes cp;
    private final ControladorConsultas cc;
    private final ControladorHistorias ch;
    private final ControladorProductos cpr;
    private final ControladorVentas cv;
    private final VistaConsola vc;

    public Controlador(VentanaPrincipal v) {
        cp=new ControladorPacientes(v);
        cc=new ControladorConsultas(v);
        ch=new ControladorHistorias(v);
        cpr=new ControladorProductos(v);
        cv=new ControladorVentas(v);
        vc=new VistaConsola();
    }

    public void start() {
        vc.adminMsg("Programa iniciado", "Controlador");
        cp.start();
        cc.start();
        ch.start();
        cpr.start();
        cv.start();
    }
}