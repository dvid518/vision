package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Historia;
import com.example.View.PanelHistorias;
import com.example.View.VentanaPrincipal;

public class ControladorHistorias {
    private final PanelHistorias ph;
    private final VentanaPrincipal vp;
    private final ArrayList<Historia> historias;
    public ControladorHistorias(VentanaPrincipal vp) {
        this.ph=vp.getPanelHistorias();
        this.vp=vp;
        historias=new ArrayList<>();
    }

    public void start() {
        eventos();
        showHistorias();
    }

    // eventos
    public void eventos() {
        ph.getBtnRegistrar().addActionListener(e->createHistoria());
        ph.getBtnEditar().addActionListener(e->editHistoria());
        ph.getBtnEliminar().addActionListener(e->deleteHistoria());
        ph.getBtnBuscar().addActionListener(e->searchHistoria());
    }

    // historias
    public void createHistoria() {
        String dni=ph.getTxtDniPaciente().getText();
        String antecedentes=ph.getTxtAntecedentes().getText();
        String alergias=ph.getTxtAlergias().getText();
        String graduacion=ph.getTxtGraduacion().getText();
        String observaciones=ph.getTxtObservaciones().getText();
        if (targetHistoria(dni)!=null) {
            return;
        }
        Historia h=new Historia(dni, antecedentes, alergias, graduacion, observaciones);
        historias.add(h);
        showHistorias();
        clearHistoria();
        vp.showExito("Historia creada correctamente");
    }

    public void editHistoria() {
        Historia h=targetHistoria(ph.getTxtDniPaciente().getText());
        if (h==null) {
            return;
        }
        h.setAntecedentes(ph.getTxtAntecedentes().getText());
        h.setAlergias(ph.getTxtAlergias().getText());
        h.setGraduacion(ph.getTxtGraduacion().getText());
        h.setObservaciones(ph.getTxtObservaciones().getText());
        showHistorias();
        clearHistoria();
        vp.showExito("Historia editada correctamente");
    }

    public void deleteHistoria() {
        Historia h=targetHistoria(ph.getTxtDniPaciente().getText());
        if (h==null) {
            return;
        }
        historias.remove(h);
        showHistorias();
        clearHistoria();
        vp.showExito("Historia eliminada correctamente");
    }

    public void searchHistoria() {
        Historia h=targetHistoria(ph.getTxtDniPaciente().getText());
        if (h==null) {
            return;
        }
        ph.getTxtAntecedentes().setText(h.getAntecedentes());
        ph.getTxtAlergias().setText(h.getAlergias());
        ph.getTxtGraduacion().setText(h.getGraduacion());
        ph.getTxtObservaciones().setText(h.getObservaciones());
    }

    // mostrar
    public void showHistorias() {
        DefaultTableModel m=new DefaultTableModel();
        m.addColumn("DNI");
        m.addColumn("Antecedentes");
        m.addColumn("Alergias");
        m.addColumn("Graduación");
        m.addColumn("Observaciones");
        for (Historia h:historias) {
            m.addRow(new Object[]{h.getDniPaciente(), h.getAntecedentes(), h.getAlergias(), h.getGraduacion(), h.getObservaciones()});
        }
        ph.getTabla().setModel(m);
    }

    // búsqueda
    public Historia targetHistoria(String dni) {
        for (Historia h:historias) {
            if (h.getDniPaciente().equals(dni)) {
                vp.showExito("Historia encontrada correctamente");
                return h;
            }
        }
        vp.showError("No se encontró la historia");
        return null;
    }

    // utilidades
    public void clearHistoria() {
        ph.getTxtDniPaciente().setText("");
        ph.getTxtAntecedentes().setText("");
        ph.getTxtAlergias().setText("");
        ph.getTxtGraduacion().setText("");
        ph.getTxtObservaciones().setText("");
    }

    public ArrayList<Historia> getHistorias() {
        return historias;
    }
}