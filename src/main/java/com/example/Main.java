package com.example;

import com.example.Controller.Controlador;
import com.example.View.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        VentanaPrincipal v=new VentanaPrincipal();
        Controlador c=new Controlador(v);

        v.setVisible(true);
        c.start();
    }
}