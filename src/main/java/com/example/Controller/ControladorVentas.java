package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Venta;
import com.example.View.PanelVentas;
import com.example.View.VentanaPrincipal;

public class ControladorVentas {
    private final PanelVentas p;

    private final ArrayList<Venta> ventas;

    public ControladorVentas(VentanaPrincipal v) {
        p=v.getPanelVentas();

        ventas=new ArrayList<>();
    }

    public void start() {
        eventos();
        showVentas();
    }

    // eventos
    public void eventos() {
        p.getBtnRegistrar().addActionListener(e->createVenta());
        p.getBtnBuscar().addActionListener(e->searchVenta());
    }

    // ventas
    public void createVenta() {
        String dni=p.getTxtDniPaciente().getText();
        String codigo=p.getTxtCodigoProducto().getText();

        int cantidad;
        int dia;
        int mes;
        int ano;

        try {
            cantidad=Integer.parseInt(p.getTxtCantidad().getText());
            dia=Integer.parseInt(p.getTxtDia().getText());
            mes=Integer.parseInt(p.getTxtMes().getText());
            ano=Integer.parseInt(p.getTxtAno().getText());
        } catch (NumberFormatException e) {
            return;
        }

        if (!validateCantidad(cantidad)) {
            return;
        }

        Venta ven=new Venta(dni, codigo, cantidad, dia, mes, ano);

        ventas.add(ven);
        showVentas();
        clearVenta();
    }

    public void searchVenta() {
        Venta ven=targetVenta(p.getTxtDniPaciente().getText());

        if (ven==null) {
            return;
        }

        p.getTxtCodigoProducto().setText(ven.getCodigoProducto());
        p.getTxtCantidad().setText(String.valueOf(ven.getCantidad()));
        p.getTxtDia().setText(String.valueOf(ven.getDia()));
        p.getTxtMes().setText(String.valueOf(ven.getMes()));
        p.getTxtAno().setText(String.valueOf(ven.getAno()));
    }

    // mostrar
    public void showVentas() {
        DefaultTableModel modelo=new DefaultTableModel();

        modelo.addColumn("DNI");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Fecha");

        for (Venta ven:ventas) {
            modelo.addRow(new Object[]{ven.getDniPaciente(), ven.getCodigoProducto(), ven.getCantidad(), ven.getFecha()});
        }

        p.getTabla().setModel(modelo);
    }

    // búsqueda
    public Venta targetVenta(String dni) {
        for (Venta ven:ventas) {
            if (ven.getDniPaciente().equals(dni)) {
                return ven;
            }
        }

        return null;
    }

    // validación
    public boolean validateCantidad(int cantidad) {
        return cantidad>0;
    }

    // utilidades
    public void clearVenta() {
        p.getTxtDniPaciente().setText("");
        p.getTxtCodigoProducto().setText("");
        p.getTxtCantidad().setText("");
        p.getTxtDia().setText("");
        p.getTxtMes().setText("");
        p.getTxtAno().setText("");
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }
}