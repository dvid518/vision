package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelVentas extends JPanel {
    private final JTextField txtDniPaciente;
    private final JTextField txtCodigoProducto;
    private final JTextField txtCantidad;
    private final JTextField txtDia;
    private final JTextField txtMes;
    private final JTextField txtAno;

    private final JButton btnRegistrar;
    private final JButton btnBuscar;

    private final JTable tabla;

    public PanelVentas() {
        setLayout(new BorderLayout());

        JPanel formulario=new JPanel(new GridLayout(6,2));

        formulario.add(new JLabel("DNI Paciente"));
        txtDniPaciente=new JTextField();
        formulario.add(txtDniPaciente);

        formulario.add(new JLabel("Código Producto"));
        txtCodigoProducto=new JTextField();
        formulario.add(txtCodigoProducto);

        formulario.add(new JLabel("Cantidad"));
        txtCantidad=new JTextField();
        formulario.add(txtCantidad);

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
        btnBuscar=new JButton("Buscar");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);

        tabla=new JTable();

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    public JTextField getTxtDniPaciente() {
        return txtDniPaciente;
    }
    
    public JTextField getTxtCodigoProducto() {
        return txtCodigoProducto;
    }
    
    public JTextField getTxtCantidad() {
        return txtCantidad;
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
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    
    public JTable getTabla() {
        return tabla;
    }
    
}