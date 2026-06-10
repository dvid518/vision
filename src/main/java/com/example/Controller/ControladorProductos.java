package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Producto;
import com.example.View.PanelProductos;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorProductos {
    private final VentanaPrincipal vp;
    private final PanelProductos pp;
    private final ArrayList<Producto> productos;
    private final VistaConsola vc;
    private final String controlador="ControladorProductos";
    
    public ControladorProductos(VentanaPrincipal vp) {
        this.pp=vp.getPanelProductos();
        this.vp=vp;
        productos=new ArrayList<>();
        vc=new VistaConsola();
    }

    public void start() {
        eventos();
        showProductos();
        vc.adminStart(controlador);
    }

    // eventos
    public void eventos() {
        pp.getBtnRegistrar().addActionListener(e->createProducto());
        pp.getBtnEditar().addActionListener(e->editProducto());
        pp.getBtnEliminar().addActionListener(e->deleteProducto());
        pp.getBtnBuscar().addActionListener(e->searchProducto());
    }

    // productos
    public void createProducto() {
        String id=pp.getTxtId().getText();
        String nombre=pp.getTxtNombre().getText();
        String categoria=pp.getTxtCategoria().getText();
        double precio;
        int stock;
        try {
            precio=Double.parseDouble(pp.getTxtPrecio().getText());
            stock=Integer.parseInt(pp.getTxtStock().getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (targetProducto(id)!=null) {
            return;
        }
        Producto p=new Producto(id, nombre, categoria, precio, stock);
        productos.add(p);
        showProductos();
        clearProducto();
        vp.showExitoCreateModel(id);
    }

    public void editProducto() {
        Producto p=targetProducto(pp.getTxtId().getText());
        if (p==null) {
            return;
        }
        p.setNombre(pp.getTxtNombre().getText());
        p.setCategoria(pp.getTxtCategoria().getText());
        try {
            p.setPrecio(Double.parseDouble(pp.getTxtPrecio().getText()));
            p.setStock(Integer.parseInt(pp.getTxtStock().getText()));
        } catch (NumberFormatException e) {
            return;
        }
        showProductos();
        clearProducto();
        vp.showExitoEditModel(controlador);
    }

    public void deleteProducto() {
        Producto p=targetProducto(pp.getTxtId().getText());
        if (p==null) {
            return;
        }
        productos.remove(p);
        showProductos();
        clearProducto();
        vp.showExitoDeleteModel(controlador);
    }

    public void searchProducto() {
        Producto p=targetProducto(pp.getTxtId().getText());
        if (p==null) {
            vp.showErrorBusqueda(controlador);
            return;
        }
        pp.getTxtNombre().setText(p.getNombre());
        pp.getTxtCategoria().setText(p.getCategoria());
        pp.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
        pp.getTxtStock().setText(String.valueOf(p.getStock()));
        vp.showExitoBusqueda(controlador);
    }

    // mostrar
    public void showProductos() {
        DefaultTableModel m=new DefaultTableModel();
        m.addColumn("Código");
        m.addColumn("Nombre");
        m.addColumn("Categoría");
        m.addColumn("Precio");
        m.addColumn("Stock");
        for (Producto p:productos) {
            m.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
        pp.getTabla().setModel(m);
        vc.adminMsgTabla(controlador);
    }

    // búsqueda
    public Producto targetProducto(String id) {
        for (Producto p:productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // utilidades
    public void clearProducto() {
        pp.getTxtId().setText("");
        pp.getTxtNombre().setText("");
        pp.getTxtCategoria().setText("");
        pp.getTxtPrecio().setText("");
        pp.getTxtStock().setText("");
    }

    public ArrayList<Producto> getProductos() {
        vc.adminGetArrayList(controlador);
        return productos;
    }
}