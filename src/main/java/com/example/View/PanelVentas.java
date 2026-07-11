package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.example.Model.Paciente;
import com.example.Model.Producto;

public class PanelVentas extends JPanel {

    private final JTextField txtIdVenta;
    private final JComboBox<Paciente> cbPaciente;
    private final JComboBox<Producto> cbProducto;
    private final JTextField txtCantidad;

    private final JButton btnRegistrar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnBuscar;

    private final JTable tabla;

    public PanelVentas() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(4, 2));

        formulario.add(new JLabel("ID Venta"));
        txtIdVenta = new JTextField();
        txtIdVenta.setEditable(false);
        formulario.add(txtIdVenta);

        formulario.add(new JLabel("Paciente"));
        cbPaciente = new JComboBox<>();
        formulario.add(cbPaciente);

        formulario.add(new JLabel("Producto"));
        cbProducto = new JComboBox<>();
        formulario.add(cbProducto);

        formulario.add(new JLabel("Cantidad"));
        txtCantidad = new JTextField();
        formulario.add(txtCantidad);

        JPanel botones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");

        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);

        tabla = new JTable();

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    public JTextField getTxtIdVenta() {
        return txtIdVenta;
    }

    public JComboBox<Paciente> getCbPaciente() {
        return cbPaciente;
    }

    public JComboBox<Producto> getCbProducto() {
        return cbProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
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