package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Consulta;
import com.example.View.PanelConsultas;
import com.example.View.VentanaPrincipal;

public class ControladorConsultas {
    private final PanelConsultas pc;
    private final VentanaPrincipal v;
    private final ArrayList<Consulta> consultas;
    public ControladorConsultas(VentanaPrincipal v) {
        this.pc=v.getPanelConsultas();
        this.v=v;
        consultas=new ArrayList<>();
    }

    public void start() {
        eventos();
        showConsultas();
    }

    // eventos
    public void eventos() {
        pc.getBtnRegistrar().addActionListener(e->createConsulta());
        pc.getBtnEditar().addActionListener(e->editConsulta());
        pc.getBtnEliminar().addActionListener(e->deleteConsulta());
        pc.getBtnBuscar().addActionListener(e->searchConsulta());
    }

    // consultas
    public void createConsulta() {
        String dni=pc.getTxtDniPaciente().getText();
        String motivo=pc.getTxtMotivo().getText();
        String diagnostico=pc.getTxtDiagnostico().getText();
        String tratamiento=pc.getTxtTratamiento().getText();
        Consulta c=new Consulta(dni, motivo, diagnostico, tratamiento);
        consultas.add(c);
        showConsultas();
        clearConsulta();
        v.showExito("Consulta creada correctamente");
    }

    public void editConsulta() {
        Consulta c=targetConsulta(pc.getTxtDniPaciente().getText());
        if (c==null) {
            return;
        }
        c.setMotivo(pc.getTxtMotivo().getText());
        c.setDiagnostico(pc.getTxtDiagnostico().getText());
        c.setTratamiento(pc.getTxtTratamiento().getText());
        showConsultas();
        clearConsulta();
        v.showExito("Consulta editada correctamente");
    }

    public void deleteConsulta() {
        Consulta c=targetConsulta(pc.getTxtDniPaciente().getText());
        if (c==null) {
            return;
        }
        consultas.remove(c);
        showConsultas();
        clearConsulta();
        v.showExito("Consulta eliminada correctamente");
    }

    public void searchConsulta() {
        Consulta c=targetConsulta(pc.getTxtDniPaciente().getText());
        if (c==null) {
            return;
        }
        pc.getTxtMotivo().setText(c.getMotivo());
        pc.getTxtDiagnostico().setText(c.getDiagnostico());
        pc.getTxtTratamiento().setText(c.getTratamiento());
    }

    // mostrar
    public void showConsultas() {
        DefaultTableModel m=new DefaultTableModel();
        m.addColumn("DNI");
        m.addColumn("Motivo");
        m.addColumn("Diagnóstico");
        m.addColumn("Tratamiento");
        for (Consulta c:consultas) {
            m.addRow(new Object[]{c.getDniPaciente(), c.getMotivo(), c.getDiagnostico(), c.getTratamiento()});
        }
        pc.getTabla().setModel(m);
    }

    // búsqueda
    public Consulta targetConsulta(String dni) {
        for (Consulta c:consultas) {
            if (c.getDniPaciente().equals(dni)) {
                v.showExito("Consulta encontrada correctamente");
                return c;
            }
        }
        v.showError("No se encontró la consulta");
        return null;
    }

    // utilidades
    public void clearConsulta() {
        pc.getTxtDniPaciente().setText("");
        pc.getTxtMotivo().setText("");
        pc.getTxtDiagnostico().setText("");
        pc.getTxtTratamiento().setText("");
    }

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
}