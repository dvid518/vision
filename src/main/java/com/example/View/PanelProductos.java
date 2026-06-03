package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelProductos extends JPanel {
    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtCategoria;
    private final JTextField txtPrecio;
    private final JTextField txtStock;

    private final JButton btnRegistrar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnBuscar;

    private final JTable tabla;

    public PanelProductos() {
        setLayout(new BorderLayout());

        JPanel formulario=new JPanel(new GridLayout(5,2));

        formulario.add(new JLabel("Código"));
        txtCodigo=new JTextField();
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nombre"));
        txtNombre=new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Categoría"));
        txtCategoria=new JTextField();
        formulario.add(txtCategoria);

        formulario.add(new JLabel("Precio"));
        txtPrecio=new JTextField();
        formulario.add(txtPrecio);

        formulario.add(new JLabel("Stock"));
        txtStock=new JTextField();
        formulario.add(txtStock);

        JPanel botones=new JPanel();

        btnRegistrar=new JButton("Registrar");
        btnEditar=new JButton("Editar");
        btnEliminar=new JButton("Eliminar");
        btnBuscar=new JButton("Buscar");

        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);

        tabla=new JTable();

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }
    
    public JTextField getTxtCategoria() {
        return txtCategoria;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTabla() {
        return tabla;
    }

}