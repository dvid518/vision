package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Consulta;
import com.example.View.PanelConsultas;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorConsultas {
    private final VentanaPrincipal vp;
    private final PanelConsultas pc;
    private final ArrayList<Consulta> consultas;
    private final VistaConsola vc;
    private final String controlador="ControladorConsutas";

    public ControladorConsultas(VentanaPrincipal vp) {
        this.pc=vp.getPanelConsultas();
        this.vp=vp;
        consultas=new ArrayList<>();
        vc=new VistaConsola();
    }

    public void start() {
        eventos();
        showConsultas();
        vc.adminStart(controlador);
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
        vp.showExitoCreateModel(controlador);
        vc.printConsulta(c);
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
        vp.showExitoEditModel(controlador);
        vc.printConsulta(c);
    }

    public void deleteConsulta() {
        Consulta c=targetConsulta(pc.getTxtDniPaciente().getText());
        if (c==null) {
            return;
        }
        consultas.remove(c);
        showConsultas();
        clearConsulta();
        vp.showExitoDeleteModel(controlador);
    }

    public void searchConsulta() {
        Consulta c=targetConsulta(pc.getTxtDniPaciente().getText());
        if (c==null) {
            vp.showErrorBusqueda(controlador);
            return;
        }
        pc.getTxtMotivo().setText(c.getMotivo());
        pc.getTxtDiagnostico().setText(c.getDiagnostico());
        pc.getTxtTratamiento().setText(c.getTratamiento());
        vp.showExitoBusqueda(controlador);
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
        vc.adminMsgTabla(controlador);
    }

    // búsqueda
    public Consulta targetConsulta(String dni) {
        for (Consulta c:consultas) {
            if (c.getDniPaciente().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    // utilidades
    public void clearConsulta() {
        pc.getTxtDniPaciente().setText("");
        pc.getTxtMotivo().setText("");
        pc.getTxtDiagnostico().setText("");
        pc.getTxtTratamiento().setText("");
        vc.adminClearConsulta(controlador);
    }

    public ArrayList<Consulta> getConsultas() {
        vc.adminGetArrayList(controlador);
        return consultas;
    }
}