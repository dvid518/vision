package com.example.View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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
        configurarComboPaciente();
        formulario.add(cbPaciente);

        formulario.add(new JLabel("Producto"));
        cbProducto = new JComboBox<>();
        configurarComboProducto();
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

    private void configurarComboPaciente() {
        cbPaciente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Paciente p) {
                    setText(p.getNombre() + " " + p.getApellidos() + " (DNI: " + p.getDni() + ")");
                } else if (value == null) {
                    setText("Seleccione un paciente");
                }
                return this;
            }
        });
    }

    private void configurarComboProducto() {
        cbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto p) {
                    setText(p.getNombre() + " (S/" + String.format("%.2f", p.getPrecio()) + ")");
                } else if (value == null) {
                    setText("Seleccione un producto");
                }
                return this;
            }
        });
    }

    public void setPacientes(Paciente[] pacientes) {
        cbPaciente.removeAllItems();
        if (pacientes != null) {
            for (Paciente p : pacientes) {
                cbPaciente.addItem(p);
            }
        }
    }

    public void setProductos(Producto[] productos) {
        cbProducto.removeAllItems();
        if (productos != null) {
            for (Producto p : productos) {
                cbProducto.addItem(p);
            }
        }
    }

    public Paciente getPacienteSeleccionado() {
        return (Paciente) cbPaciente.getSelectedItem();
    }

    public Producto getProductoSeleccionado() {
        return (Producto) cbProducto.getSelectedItem();
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