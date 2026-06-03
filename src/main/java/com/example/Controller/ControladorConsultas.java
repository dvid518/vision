package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Consulta;
import com.example.View.PanelConsultas;
import com.example.View.VentanaPrincipal;

public class ControladorConsultas {
    private final PanelConsultas p;

    private final ArrayList<Consulta> consultas;

    public ControladorConsultas(VentanaPrincipal v) {
        this.p=v.getPanelConsultas();

        consultas=new ArrayList<>();
    }

    public void start() {
        eventos();
        showConsultas();
    }

    // eventos
    public void eventos() {
        p.getBtnRegistrar().addActionListener(e->createConsulta());
        p.getBtnEditar().addActionListener(e->editConsulta());
        p.getBtnEliminar().addActionListener(e->deleteConsulta());
        p.getBtnBuscar().addActionListener(e->searchConsulta());
    }

    // consultas
    public void createConsulta() {
        String dni=p.getTxtDniPaciente().getText();
        String motivo=p.getTxtMotivo().getText();
        String diagnostico=p.getTxtDiagnostico().getText();
        String tratamiento=p.getTxtTratamiento().getText();

        int dia;
        int mes;
        int ano;

        try {
            dia=Integer.parseInt(p.getTxtDia().getText());
            mes=Integer.parseInt(p.getTxtMes().getText());
            ano=Integer.parseInt(p.getTxtAno().getText());
        } catch (NumberFormatException e) {
            return;
        }

        Consulta con=new Consulta(dni, motivo, diagnostico, tratamiento, dia, mes, ano);

        consultas.add(con);
        showConsultas();
        clearConsulta();
    }

    public void editConsulta() {
        Consulta con=targetConsulta(p.getTxtDniPaciente().getText());

        if (con==null) {
            return;
        }

        con.setMotivo(p.getTxtMotivo().getText());
        con.setDiagnostico(p.getTxtDiagnostico().getText());
        con.setTratamiento(p.getTxtTratamiento().getText());

        try {
            con.setDia(Integer.parseInt(p.getTxtDia().getText()));
            con.setMes(Integer.parseInt(p.getTxtMes().getText()));
            con.setAno(Integer.parseInt(p.getTxtAno().getText()));
        } catch (NumberFormatException e) {
            return;
        }

        showConsultas();
        clearConsulta();
    }

    public void deleteConsulta() {
        Consulta con=targetConsulta(p.getTxtDniPaciente().getText());

        if (con==null) {
            return;
        }

        consultas.remove(con);

        showConsultas();
        clearConsulta();
    }

    public void searchConsulta() {
        Consulta con=targetConsulta(p.getTxtDniPaciente().getText());

        if (con==null) {
            return;
        }

        p.getTxtMotivo().setText(con.getMotivo());
        p.getTxtDiagnostico().setText(con.getDiagnostico());
        p.getTxtTratamiento().setText(con.getTratamiento());

        p.getTxtDia().setText(String.valueOf(con.getDia()));
        p.getTxtMes().setText(String.valueOf(con.getMes()));
        p.getTxtAno().setText(String.valueOf(con.getAno()));
    }

    // mostrar
    public void showConsultas() {
        DefaultTableModel modelo=new DefaultTableModel();

        modelo.addColumn("DNI");
        modelo.addColumn("Motivo");
        modelo.addColumn("Diagnóstico");
        modelo.addColumn("Tratamiento");
        modelo.addColumn("Fecha");

        for (Consulta con:consultas) {
            modelo.addRow(new Object[]{con.getDniPaciente(), con.getMotivo(), con.getDiagnostico(), con.getTratamiento(), con.getFecha()});
        }

        p.getTabla().setModel(modelo);
    }

    // búsqueda
    public Consulta targetConsulta(String dni) {
        for (Consulta con:consultas) {
            if (con.getDniPaciente().equals(dni)) {
                return con;
            }
        }

        return null;
    }

    // utilidades
    public void clearConsulta() {
        p.getTxtDniPaciente().setText("");
        p.getTxtMotivo().setText("");
        p.getTxtDiagnostico().setText("");
        p.getTxtTratamiento().setText("");
        p.getTxtDia().setText("");
        p.getTxtMes().setText("");
        p.getTxtAno().setText("");
    }

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
}