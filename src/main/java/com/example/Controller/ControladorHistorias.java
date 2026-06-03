package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.HistoriaClinica;
import com.example.View.PanelHistorias;
import com.example.View.VentanaPrincipal;

public class ControladorHistorias {
    private final PanelHistorias p;

    private final ArrayList<HistoriaClinica> historias;

    public ControladorHistorias(VentanaPrincipal v) {
        this.p=v.getPanelHistorias();

        historias=new ArrayList<>();
    }

    public void start() {
        eventos();
        showHistorias();
    }

    // eventos
    public void eventos() {
        p.getBtnRegistrar().addActionListener(e->createHistoria());
        p.getBtnEditar().addActionListener(e->editHistoria());
        p.getBtnEliminar().addActionListener(e->deleteHistoria());
        p.getBtnBuscar().addActionListener(e->searchHistoria());
    }

    // historias
    public void createHistoria() {
        String dni=p.getTxtDniPaciente().getText();
        String antecedentes=p.getTxtAntecedentes().getText();
        String alergias=p.getTxtAlergias().getText();
        String graduacion=p.getTxtGraduacion().getText();
        String observaciones=p.getTxtObservaciones().getText();

        if (targetHistoria(dni)!=null) {
            return;
        }

        HistoriaClinica his=new HistoriaClinica(dni, antecedentes, alergias, graduacion, observaciones);

        historias.add(his);
        showHistorias();
        clearHistoria();
    }

    public void editHistoria() {
        HistoriaClinica his=targetHistoria(p.getTxtDniPaciente().getText());

        if (his==null) {
            return;
        }

        his.setAntecedentes(p.getTxtAntecedentes().getText());
        his.setAlergias(p.getTxtAlergias().getText());
        his.setGraduacion(p.getTxtGraduacion().getText());
        his.setObservaciones(p.getTxtObservaciones().getText());

        showHistorias();
        clearHistoria();
    }

    public void deleteHistoria() {
        HistoriaClinica his=targetHistoria(p.getTxtDniPaciente().getText());

        if (his==null) {
            return;
        }

        historias.remove(his);

        showHistorias();
        clearHistoria();
    }

    public void searchHistoria() {
        HistoriaClinica his=targetHistoria(p.getTxtDniPaciente().getText());

        if (his==null) {
            return;
        }

        p.getTxtAntecedentes().setText(his.getAntecedentes());
        p.getTxtAlergias().setText(his.getAlergias());
        p.getTxtGraduacion().setText(his.getGraduacion());
        p.getTxtObservaciones().setText(his.getObservaciones());
    }

    // mostrar
    public void showHistorias() {
        DefaultTableModel modelo=new DefaultTableModel();

        modelo.addColumn("DNI");
        modelo.addColumn("Antecedentes");
        modelo.addColumn("Alergias");
        modelo.addColumn("Graduación");
        modelo.addColumn("Observaciones");

        for (HistoriaClinica his:historias) {
            modelo.addRow(new Object[]{his.getDniPaciente(), his.getAntecedentes(), his.getAlergias(), his.getGraduacion(), his.getObservaciones()});
        }

        p.getTabla().setModel(modelo);
    }

    // búsqueda
    public HistoriaClinica targetHistoria(String dni) {
        for (HistoriaClinica his:historias) {
            if (his.getDniPaciente().equals(dni)) {
                return his;
            }
        }

        return null;
    }

    // utilidades
    public void clearHistoria() {
        p.getTxtDniPaciente().setText("");
        p.getTxtAntecedentes().setText("");
        p.getTxtAlergias().setText("");
        p.getTxtGraduacion().setText("");
        p.getTxtObservaciones().setText("");
    }

    public ArrayList<HistoriaClinica> getHistorias() {
        return historias;
    }
}