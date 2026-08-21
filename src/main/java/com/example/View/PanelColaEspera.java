package com.example.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.example.Model.Paciente;
import com.example.Model.PacientePrioridad;

public final class PanelColaEspera extends JPanel {
    private final JTextField txtBuscar;
    private final JButton btnBuscar;
    private final JTable tablaResultados;
    private final DefaultTableModel modeloTabla;
    
    private final JComboBox<PacientePrioridad.Prioridad> cbPrioridad;
    private final JButton btnAgregarCola;
    
    private final JList<String> listaCola;
    private final DefaultListModel<String> modeloLista;
    private final JButton btnAtender;
    private final JButton btnEliminarCola;
    private final JLabel lblEstadoCola;
    
    public PanelColaEspera() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Paciente en Base de Datos"));
        
        JPanel panelBusquedaInput = new JPanel(new GridLayout(1, 3, 5, 5));
        panelBusquedaInput.add(new JLabel("Buscar (DNI, nombre o apellido):"));
        txtBuscar = new JTextField(20);
        panelBusquedaInput.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusquedaInput.add(btnBuscar);
        
        panelBusqueda.add(panelBusquedaInput, BorderLayout.NORTH);
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "DNI", "Nombre", "Apellidos", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollResultados = new JScrollPane(tablaResultados);
        scrollResultados.setPreferredSize(new java.awt.Dimension(0, 120));
        panelBusqueda.add(scrollResultados, BorderLayout.CENTER);
        
        JPanel panelAgregar = new JPanel(new GridLayout(1, 3, 5, 5));
        panelAgregar.add(new JLabel("Prioridad:"));
        cbPrioridad = new JComboBox<>(PacientePrioridad.Prioridad.values());
        panelAgregar.add(cbPrioridad);
        btnAgregarCola = new JButton("Agregar a Cola");
        panelAgregar.add(btnAgregarCola);
        panelBusqueda.add(panelAgregar, BorderLayout.SOUTH);
        
        add(panelBusqueda, BorderLayout.NORTH);
        
        JPanel panelCola = new JPanel(new BorderLayout(5, 5));
        panelCola.setBorder(BorderFactory.createTitledBorder("Cola de Espera"));
        
        modeloLista = new DefaultListModel<>();
        listaCola = new JList<>(modeloLista);
        listaCola.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollCola = new JScrollPane(listaCola);
        panelCola.add(scrollCola, BorderLayout.CENTER);

        JPanel panelControlCola = new JPanel(new BorderLayout(5, 5));
        
        lblEstadoCola = new JLabel("Pacientes en cola: 0");
        panelControlCola.add(lblEstadoCola, BorderLayout.WEST);
        
        JPanel panelBotonesCola = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAtender = new JButton("Atender");
        btnEliminarCola = new JButton("Eliminar");
        panelBotonesCola.add(btnAtender);
        panelBotonesCola.add(btnEliminarCola);
        panelControlCola.add(panelBotonesCola, BorderLayout.EAST);
        
        panelCola.add(panelControlCola, BorderLayout.SOUTH);
        
        add(panelCola, BorderLayout.CENTER);
        
        actualizarEstadoCola();
    }
    
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    
    public JTable getTablaResultados() {
        return tablaResultados;
    }
    
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public JComboBox<PacientePrioridad.Prioridad> getCbPrioridad() {
        return cbPrioridad;
    }
    
    public JButton getBtnAgregarCola() {
        return btnAgregarCola;
    }
    
    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }

    public JList<String> getListaCola() {
        return listaCola;
    }
    
    public JButton getBtnAtender() {
        return btnAtender;
    }
    
    public JButton getBtnEliminarCola() {
        return btnEliminarCola;
    }
    
    public JLabel getLblEstadoCola() {
        return lblEstadoCola;
    }
    
    public void actualizarEstadoCola() {
        lblEstadoCola.setText("Pacientes en cola: " + modeloLista.size());
    }

    public void limpiarResultados() {
        modeloTabla.setRowCount(0);
    }
    
    public void agregarResultado(Paciente p) {
        modeloTabla.addRow(new Object[]{
            p.getIdPaciente(),
            p.getDni(),
            p.getNombre(),
            p.getApellidos(),
            p.getTelefono()
        });
    }

    public Paciente getPacienteSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila == -1) {
            return null;
        }
        
        try {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String dni = (String) modeloTabla.getValueAt(fila, 1);
            String nombre = (String) modeloTabla.getValueAt(fila, 2);
            String apellidos = (String) modeloTabla.getValueAt(fila, 3);
            String telefono = (String) modeloTabla.getValueAt(fila, 4);
            
            Paciente p = new Paciente();
            p.setIdPaciente(id);
            p.setDni(dni);
            p.setNombre(nombre);
            p.setApellidos(apellidos);
            p.setTelefono(telefono);
            return p;
        } catch (Exception e) {
            return null;
        }
    }
}