package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Venta;
import com.example.View.PanelVentas;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorVentas {
    private final VentanaPrincipal vp;
    private final PanelVentas pv;
    private final ArrayList<Venta> ventas;
    private final VistaConsola vc;
    private final String controlador="ControladorVentas";

    public ControladorVentas(VentanaPrincipal vp) {
        this.pv=vp.getPanelVentas();
        this.vp=vp;
        ventas=new ArrayList<>();
        vc=new VistaConsola();
    }

    public void start() {
        eventos();
        showVentas();
        vc.adminStart(controlador);
    }

    // eventos
    public void eventos() {
        pv.getBtnRegistrar().addActionListener(e->createVenta());
        pv.getBtnBuscar().addActionListener(e->searchVenta());
    }

    // ventas
    public void createVenta() {
        String dni=pv.getTxtDniPaciente().getText();
        String codigo=pv.getTxtCodigoProducto().getText();
        int cantidad=Integer.parseInt(pv.getTxtCantidad().getText());
        if (!validateCantidad(cantidad)) {
            return;
        }
        Venta v=new Venta(dni, codigo, cantidad);
        ventas.add(v);
        showVentas();
        clearVenta();
        vp.showExitoCreateModel(controlador);
    }

    public void searchVenta() {
        Venta v=targetVenta(pv.getTxtDniPaciente().getText());
        if (v==null) {
            vp.showErrorBusqueda(controlador);
            return;
        }
        pv.getTxtCodigoProducto().setText(v.getCodigoProducto());
        pv.getTxtCantidad().setText(String.valueOf(v.getCantidad()));
        vp.showExitoBusqueda(controlador);
    }

    // mostrar
    public void showVentas() {
        DefaultTableModel m=new DefaultTableModel();
        m.addColumn("DNI");
        m.addColumn("Producto");
        m.addColumn("Cantidad");
        for (Venta v:ventas) {
            m.addRow(new Object[]{v.getDniPaciente(), v.getCodigoProducto(), v.getCantidad()});
        }
        pv.getTabla().setModel(m);
    }

    // búsqueda
    public Venta targetVenta(String dni) {
        for (Venta v:ventas) {
            if (v.getDniPaciente().equals(dni)) {
                return v;
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
        pv.getTxtDniPaciente().setText("");
        pv.getTxtCodigoProducto().setText("");
        pv.getTxtCantidad().setText("");
    }

    public ArrayList<Venta> getVentas() {
        vc.adminGetArrayList(controlador);
        return ventas;
    }
}