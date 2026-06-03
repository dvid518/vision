package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelPacientes extends JPanel {
    private final JTextField txtDni;
    private final JTextField txtNombre;
    private final JTextField txtApellidos;
    private final JTextField txtSexo;
    private final JTextField txtTelefono;
    private final JTextField txtEdad;
    private final JButton btnRegistrar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnBuscar;
    private final JTable tabla;

    public PanelPacientes() {
        setLayout(new BorderLayout());
        JPanel formulario=new JPanel(new GridLayout(6,2));

        formulario.add(new JLabel("DNI"));
        txtDni=new JTextField();
        formulario.add(txtDni);

        formulario.add(new JLabel("Nombre"));
        txtNombre=new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Apellidos"));
        txtApellidos=new JTextField();
        formulario.add(txtApellidos);

        formulario.add(new JLabel("Sexo"));
        txtSexo=new JTextField();
        formulario.add(txtSexo);

        formulario.add(new JLabel("Teléfono"));
        txtTelefono=new JTextField();
        formulario.add(txtTelefono);

        formulario.add(new JLabel("Edad"));
        txtEdad=new JTextField();
        formulario.add(txtEdad);

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

    public JTextField getTxtDni() {
        return txtDni;
    }
    
    public JTextField getTxtNombre() {
        return txtNombre;
    }
    
    public JTextField getTxtApellidos() {
        return txtApellidos;
    }
    
    public JTextField getTxtSexo() {
        return txtSexo;
    }
    
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }
    
    public JTextField getTxtEdad() {
        return txtEdad;
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