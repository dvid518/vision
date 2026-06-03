package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Producto;
import com.example.View.PanelProductos;
import com.example.View.VentanaPrincipal;

public class ControladorProductos {
    private final PanelProductos p;

    private final ArrayList<Producto> productos;

    public ControladorProductos(VentanaPrincipal v) {
        this.p=v.getPanelProductos();

        productos=new ArrayList<>();
    }

    public void start() {
        eventos();
        showProductos();
    }

    // eventos
    public void eventos() {
        p.getBtnRegistrar().addActionListener(e->createProducto());
        p.getBtnEditar().addActionListener(e->editProducto());
        p.getBtnEliminar().addActionListener(e->deleteProducto());
        p.getBtnBuscar().addActionListener(e->searchProducto());
    }

    // productos
    public void createProducto() {
        String codigo=p.getTxtCodigo().getText();
        String nombre=p.getTxtNombre().getText();
        String categoria=p.getTxtCategoria().getText();

        double precio;
        int stock;

        try {
            precio=Double.parseDouble(p.getTxtPrecio().getText());
            stock=Integer.parseInt(p.getTxtStock().getText());
        } catch (NumberFormatException e) {
            return;
        }

        if (targetProducto(codigo)!=null) {
            return;
        }

        Producto pro=new Producto(codigo, nombre, categoria, precio, stock);

        productos.add(pro);
        showProductos();
        clearProducto();
    }

    public void editProducto() {
        Producto pro=targetProducto(p.getTxtCodigo().getText());

        if (pro==null) {
            return;
        }

        pro.setNombre(p.getTxtNombre().getText());
        pro.setCategoria(p.getTxtCategoria().getText());

        try {
            pro.setPrecio(Double.parseDouble(p.getTxtPrecio().getText()));
            pro.setStock(Integer.parseInt(p.getTxtStock().getText()));
        } catch (NumberFormatException e) {
            return;
        }

        showProductos();
        clearProducto();
    }

    public void deleteProducto() {
        Producto pro=targetProducto(p.getTxtCodigo().getText());

        if (pro==null) {
            return;
        }

        productos.remove(pro);

        showProductos();
        clearProducto();
    }

    public void searchProducto() {
        Producto pro=targetProducto(p.getTxtCodigo().getText());

        if (pro==null) {
            return;
        }

        p.getTxtNombre().setText(pro.getNombre());
        p.getTxtCategoria().setText(pro.getCategoria());
        p.getTxtPrecio().setText(String.valueOf(pro.getPrecio()));
        p.getTxtStock().setText(String.valueOf(pro.getStock()));
    }

    // mostrar
    public void showProductos() {
        DefaultTableModel modelo=new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        for (Producto pro:productos) {
            modelo.addRow(new Object[]{pro.getCodigo(), pro.getNombre(), pro.getCategoria(), pro.getPrecio(), pro.getStock()});
        }

        p.getTabla().setModel(modelo);
    }

    // búsqueda
    public Producto targetProducto(String codigo) {
        for (Producto pro:productos) {
            if (pro.getCodigo().equals(codigo)) {
                return pro;
            }
        }

        return null;
    }

    // utilidades
    public void clearProducto() {
        p.getTxtCodigo().setText("");
        p.getTxtNombre().setText("");
        p.getTxtCategoria().setText("");
        p.getTxtPrecio().setText("");
        p.getTxtStock().setText("");
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}