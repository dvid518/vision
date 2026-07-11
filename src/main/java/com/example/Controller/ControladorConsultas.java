package com.example.Controller;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Consulta;
import com.example.Model.Paciente;
import com.example.Service.ConsultaService;
import com.example.Service.PacienteService;
import com.example.View.PanelConsultas;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorConsultas {

    private static final String CONTROLLER_NAME = "ControladorConsultas";
    
    private final VentanaPrincipal ventanaPrincipal;
    private final PanelConsultas panelConsultas;
    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final VistaConsola vistaConsola;

    public ControladorConsultas(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panelConsultas = ventanaPrincipal.getPanelConsultas();
        this.consultaService = new ConsultaService();
        this.pacienteService = new PacienteService();
        this.vistaConsola = new VistaConsola();
    }

    public void start() {
        eventos();
        showConsultas();
        vistaConsola.adminStart(CONTROLLER_NAME);
    }

    public void eventos() {
        panelConsultas.getBtnRegistrar().addActionListener(e -> createConsulta());
        panelConsultas.getBtnEditar().addActionListener(e -> editConsulta());
        panelConsultas.getBtnEliminar().addActionListener(e -> deleteConsulta());
        panelConsultas.getBtnBuscar().addActionListener(e -> searchConsultasByPaciente());
    }

    public void createConsulta() {
        String dni = panelConsultas.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente = pacienteService.searchPacienteDni(dni);
        if (paciente == null) {
            ventanaPrincipal.showError("Paciente no encontrado con DNI: " + dni, CONTROLLER_NAME);
            return;
        }

        String motivo = panelConsultas.getTxtMotivo().getText().trim();
        if (motivo.isEmpty()) {
            ventanaPrincipal.showError("El motivo es obligatorio", CONTROLLER_NAME);
            return;
        }

        String diagnostico = panelConsultas.getTxtDiagnostico().getText().trim();
        String tratamiento = panelConsultas.getTxtTratamiento().getText().trim();

        Consulta c = new Consulta(paciente, motivo, diagnostico, tratamiento);
        
        if (!consultaService.registerConsulta(c)) {
            ventanaPrincipal.showError("No se pudo registrar la consulta", CONTROLLER_NAME);
            return;
        }
        
        showConsultas();
        clearConsulta();
        ventanaPrincipal.showExitoCreateModel(CONTROLLER_NAME);
        vistaConsola.printConsulta(c);
    }

    public void editConsulta() {
        // Primero buscar por DNI para obtener el paciente
        String dni = panelConsultas.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente = pacienteService.searchPacienteDni(dni);
        if (paciente == null) {
            ventanaPrincipal.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        // Buscar consultas del paciente
        java.util.List<Consulta> consultasPaciente = consultaService.searchConsultasByPaciente(paciente.getIdPaciente());
        if (consultasPaciente == null || consultasPaciente.isEmpty()) {
            ventanaPrincipal.showError("El paciente no tiene consultas registradas", CONTROLLER_NAME);
            return;
        }

        // Usar la primera consulta (la más reciente si el DAO ordena DESC)
        Consulta c = consultasPaciente.get(0);
        
        String motivo = panelConsultas.getTxtMotivo().getText().trim();
        if (motivo.isEmpty()) {
            ventanaPrincipal.showError("El motivo es obligatorio", CONTROLLER_NAME);
            return;
        }

        c.setMotivo(motivo);
        c.setDiagnostico(panelConsultas.getTxtDiagnostico().getText().trim());
        c.setTratamiento(panelConsultas.getTxtTratamiento().getText().trim());

        if (!consultaService.updateConsulta(c)) {
            ventanaPrincipal.showError("No se pudo actualizar la consulta", CONTROLLER_NAME);
            return;
        }

        showConsultas();
        clearConsulta();
        ventanaPrincipal.showExitoEditModel(CONTROLLER_NAME);
        vistaConsola.printConsulta(c);
    }

    public void deleteConsulta() {
        String dni = panelConsultas.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un DNI", CONTROLLER_NAME);
            return;
        }

        Paciente paciente = pacienteService.searchPacienteDni(dni);
        if (paciente == null) {
            ventanaPrincipal.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        java.util.List<Consulta> consultasPaciente = consultaService.searchConsultasByPaciente(paciente.getIdPaciente());
        if (consultasPaciente == null || consultasPaciente.isEmpty()) {
            ventanaPrincipal.showError("El paciente no tiene consultas registradas", CONTROLLER_NAME);
            return;
        }

        // Eliminar la primera consulta (la más reciente)
        Consulta c = consultasPaciente.get(0);
        
        if (!consultaService.deleteConsulta(c.getIdConsulta())) {
            ventanaPrincipal.showError("No se pudo eliminar la consulta", CONTROLLER_NAME);
            return;
        }

        showConsultas();
        clearConsulta();
        ventanaPrincipal.showExitoDeleteModel(CONTROLLER_NAME);
    }

    public void searchConsultasByPaciente() {
        String dni = panelConsultas.getTxtDniPaciente().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un DNI para buscar", CONTROLLER_NAME);
            return;
        }

        Paciente paciente = pacienteService.searchPacienteDni(dni);
        if (paciente == null) {
            ventanaPrincipal.showError("Paciente no encontrado", CONTROLLER_NAME);
            return;
        }

        java.util.List<Consulta> consultasPaciente = consultaService.searchConsultasByPaciente(paciente.getIdPaciente());
        if (consultasPaciente == null || consultasPaciente.isEmpty()) {
            ventanaPrincipal.showError("El paciente no tiene consultas registradas", CONTROLLER_NAME);
            return;
        }

        // Mostrar la primera consulta (la más reciente) en los campos de texto
        Consulta c = consultasPaciente.get(0);
        panelConsultas.getTxtMotivo().setText(c.getMotivo());
        panelConsultas.getTxtDiagnostico().setText(c.getDiagnostico());
        panelConsultas.getTxtTratamiento().setText(c.getTratamiento());

        // Actualizar tabla con todas las consultas del paciente
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("DNI Paciente");
        model.addColumn("Nombre");
        model.addColumn("Motivo");
        model.addColumn("Diagnóstico");
        model.addColumn("Tratamiento");

        for (Consulta consulta : consultasPaciente) {
            model.addRow(new Object[]{
                consulta.getIdConsulta(),
                consulta.getPaciente().getDni(),
                consulta.getPaciente().getNombre(),
                consulta.getMotivo(),
                consulta.getDiagnostico(),
                consulta.getTratamiento()
            });
        }
        panelConsultas.getTabla().setModel(model);
        ventanaPrincipal.showExitoBusqueda(CONTROLLER_NAME);
    }

    public void showConsultas() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("DNI Paciente");
        model.addColumn("Nombre");
        model.addColumn("Motivo");
        model.addColumn("Diagnóstico");
        model.addColumn("Tratamiento");

        for (Consulta c : consultaService.listConsultas()) {
            model.addRow(new Object[]{
                c.getIdConsulta(),
                c.getPaciente().getDni(),
                c.getPaciente().getNombre(),
                c.getMotivo(),
                c.getDiagnostico(),
                c.getTratamiento()
            });
        }
        panelConsultas.getTabla().setModel(model);
        vistaConsola.adminMsgTabla(CONTROLLER_NAME);
    }

    public void clearConsulta() {
        panelConsultas.getTxtDniPaciente().setText("");
        panelConsultas.getTxtMotivo().setText("");
        panelConsultas.getTxtDiagnostico().setText("");
        panelConsultas.getTxtTratamiento().setText("");
        vistaConsola.adminClearConsulta(CONTROLLER_NAME);
    }
}