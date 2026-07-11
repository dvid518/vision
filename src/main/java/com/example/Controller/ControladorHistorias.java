package com.example.Controller;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Historia;
import com.example.Model.Paciente;
import com.example.Service.HistoriaService;
import com.example.Service.PacienteService;
import com.example.View.PanelHistorias;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorHistorias {

    private static final String CONTROLLER_NAME = "ControladorHistorias";
    
    private final VentanaPrincipal vp;
    private final PanelHistorias ph;
    private final HistoriaService hs;
    private final PacienteService ps;
    private final VistaConsola vc;

    public ControladorHistorias(VentanaPrincipal vp) {
        this.vp=vp;
        this.ph=vp.getPanelHistorias();
        this.hs=new HistoriaService();
        this.ps=new PacienteService();
        this.vc=new VistaConsola();
    }

    public void start() {
        eventos();
        showHistorias();
        vc.adminStart(CONTROLLER_NAME);
    }

    public void eventos() {
        ph.getBtnRegistrar().addActionListener(e -> createHistoria());
        ph.getBtnEditar().addActionListener(e -> editHistoria());
        ph.getBtnEliminar().addActionListener(e -> deleteHistoria());
        ph.getBtnBuscar().addActionListener(e -> searchHistoria());
    }

    public void createHistoria() {
        String dni=ph.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            vp.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        // Verificar que el paciente existe
        Paciente paciente=ps.searchPacienteDni(dni);
        if (paciente==null) {
            vp.showError("Paciente no encontrado con DNI: "+dni, CONTROLLER_NAME);
            return;
        }

        // Verificar si ya tiene historia
        if (hs.existeHistoria(paciente.getIdPaciente())) {
            vp.showError("El paciente ya tiene una historia clínica", CONTROLLER_NAME);
            return;
        }

        String antecedentes=ph.getTxtAntecedentes().getText().trim();
        String alergias=ph.getTxtAlergias().getText().trim();
        String graduacion=ph.getTxtGraduacion().getText().trim();
        String observaciones=ph.getTxtObservaciones().getText().trim();

        Historia h = new Historia(paciente, antecedentes, alergias, graduacion, observaciones);
        
        if (!hs.registerHistoria(h)) {
            vp.showError("No se pudo registrar la historia clínica", CONTROLLER_NAME);
            return;
        }
        
        showHistorias();
        clearHistoria();
        vp.showExitoCreateModel(CONTROLLER_NAME);
        vc.printHistoria(h);
    }

    public void editHistoria() {
        String dni=ph.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            vp.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente=ps.searchPacienteDni(dni);
        if (paciente == null) {
            vp.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        Historia h=hs.searchHistoriaByPaciente(paciente.getIdPaciente());
        if (h==null) {
            vp.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        h.setAntecedentes(ph.getTxtAntecedentes().getText().trim());
        h.setAlergias(ph.getTxtAlergias().getText().trim());
        h.setGraduacion(ph.getTxtGraduacion().getText().trim());
        h.setObservaciones(ph.getTxtObservaciones().getText().trim());

        if (!hs.updateHistoria(h)) {
            vp.showError("No se pudo actualizar la historia clínica", CONTROLLER_NAME);
            return;
        }

        showHistorias();
        clearHistoria();
        vp.showExitoEditModel(CONTROLLER_NAME);
        vc.printHistoria(h);
    }

    public void deleteHistoria() {
        String dni=ph.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            vp.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente=ps.searchPacienteDni(dni);
        if (paciente==null) {
            vp.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        Historia h = hs.searchHistoriaByPaciente(paciente.getIdPaciente());
        if (h==null) {
            vp.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        if (!hs.deleteHistoria(h.getIdHistoria())) {
            vp.showError("No se pudo eliminar la historia clínica", CONTROLLER_NAME);
            return;
        }

        showHistorias();
        clearHistoria();
        vp.showExitoDeleteModel(CONTROLLER_NAME);
    }

    public void searchHistoria() {
        String dni=ph.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            vp.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente=ps.searchPacienteDni(dni);
        if (paciente==null) {
            vp.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        Historia h=hs.searchHistoriaByPaciente(paciente.getIdPaciente());
        if (h == null) {
            vp.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        ph.getTxtAntecedentes().setText(h.getAntecedentes());
        ph.getTxtAlergias().setText(h.getAlergias());
        ph.getTxtGraduacion().setText(h.getGraduacion());
        ph.getTxtObservaciones().setText(h.getObservaciones());
        vp.showExitoBusqueda(CONTROLLER_NAME);
    }

    public void showHistorias() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("DNI Paciente");
        model.addColumn("Antecedentes");
        model.addColumn("Alergias");
        model.addColumn("Graduación");
        model.addColumn("Observaciones");

        for (Historia h:hs.listHistorias()) {
            model.addRow(new Object[]{
                h.getIdHistoria(),
                h.getPaciente().getDni(),
                h.getAntecedentes(),
                h.getAlergias(),
                h.getGraduacion(),
                h.getObservaciones()
            });
        }
        ph.getTabla().setModel(model);
        vc.adminMsgTabla(CONTROLLER_NAME);
    }

    public void clearHistoria() {
        ph.getTxtDniPaciente().setText("");
        ph.getTxtAntecedentes().setText("");
        ph.getTxtAlergias().setText("");
        ph.getTxtGraduacion().setText("");
        ph.getTxtObservaciones().setText("");
    }
}