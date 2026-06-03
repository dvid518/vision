package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelHistorias extends JPanel {
    private final JTextField txtDniPaciente;
    private final JTextField txtAntecedentes;
    private final JTextField txtAlergias;
    private final JTextField txtGraduacion;
    private final JTextField txtObservaciones;
    private final JButton btnRegistrar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnBuscar;
    private final JTable tabla;

    public PanelHistorias() {
        setLayout(new BorderLayout());
        JPanel formulario=new JPanel(new GridLayout(5,2));

        formulario.add(new JLabel("DNI Paciente"));
        txtDniPaciente=new JTextField();
        formulario.add(txtDniPaciente);

        formulario.add(new JLabel("Antecedentes"));
        txtAntecedentes=new JTextField();
        formulario.add(txtAntecedentes);

        formulario.add(new JLabel("Alergias"));
        txtAlergias=new JTextField();
        formulario.add(txtAlergias);

        formulario.add(new JLabel("Graduación"));
        txtGraduacion=new JTextField();
        formulario.add(txtGraduacion);

        formulario.add(new JLabel("Observaciones"));
        txtObservaciones=new JTextField();
        formulario.add(txtObservaciones);

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

    public JTextField getTxtDniPaciente() {
        return txtDniPaciente;
    }
    
    public JTextField getTxtAntecedentes() {
        return txtAntecedentes;
    }
    
    public JTextField getTxtAlergias() {
        return txtAlergias;
    }
    
    public JTextField getTxtGraduacion() {
        return txtGraduacion;
    }
    
    public JTextField getTxtObservaciones() {
        return txtObservaciones;
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