package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelConsultas extends JPanel {
    private final JTextField txtDniPaciente;
    private final JTextField txtMotivo;
    private final JTextField txtDiagnostico;
    private final JTextField txtTratamiento;
    private final JTextField txtDia;
    private final JTextField txtMes;
    private final JTextField txtAno;

    private final JButton btnRegistrar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JButton btnBuscar;

    private final JTable tabla;

    public PanelConsultas() {
        setLayout(new BorderLayout());

        JPanel formulario=new JPanel(new GridLayout(7,2));

        formulario.add(new JLabel("DNI Paciente"));
        txtDniPaciente=new JTextField();
        formulario.add(txtDniPaciente);

        formulario.add(new JLabel("Motivo"));
        txtMotivo=new JTextField();
        formulario.add(txtMotivo);

        formulario.add(new JLabel("Diagnóstico"));
        txtDiagnostico=new JTextField();
        formulario.add(txtDiagnostico);

        formulario.add(new JLabel("Tratamiento"));
        txtTratamiento=new JTextField();
        formulario.add(txtTratamiento);

        formulario.add(new JLabel("Día"));
        txtDia=new JTextField();
        formulario.add(txtDia);

        formulario.add(new JLabel("Mes"));
        txtMes=new JTextField();
        formulario.add(txtMes);

        formulario.add(new JLabel("Año"));
        txtAno=new JTextField();
        formulario.add(txtAno);

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

    public JTextField getTxtMotivo() {
        return txtMotivo;
    }

    public JTextField getTxtDiagnostico() {
        return txtDiagnostico;
    }

    public JTextField getTxtTratamiento() {
        return txtTratamiento;
    }

    public JTextField getTxtDia() {
        return txtDia;
    }

    public JTextField getTxtMes() {
        return txtMes;
    }

    public JTextField getTxtAno() {
        return txtAno;
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