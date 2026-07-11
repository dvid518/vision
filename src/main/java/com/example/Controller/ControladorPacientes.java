package com.example.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Paciente;
import com.example.Service.PacienteService;
import com.example.View.PanelPacientes;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorPacientes {

    private static final String CONTROLLER_NAME = "ControladorPacientes";
    
    private final VentanaPrincipal ventanaPrincipal;
    private final PanelPacientes panelPacientes;
    private final PacienteService pacienteService;
    private final VistaConsola vistaConsola;

    public ControladorPacientes(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panelPacientes = ventanaPrincipal.getPanelPacientes();
        this.pacienteService = new PacienteService();
        this.vistaConsola = new VistaConsola();
    }

    public void start() {
        eventos();
        showPacientes();
        vistaConsola.adminStart(CONTROLLER_NAME);
    }

    public void eventos() {
        panelPacientes.getBtnRegistrar().addActionListener(e -> createPaciente());
        panelPacientes.getBtnEditar().addActionListener(e -> editPaciente());
        panelPacientes.getBtnEliminar().addActionListener(e -> deletePaciente());
        panelPacientes.getBtnBuscar().addActionListener(e -> searchPaciente());
    }

    public void createPaciente() {
        String dni = panelPacientes.getTxtDni().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("El DNI es obligatorio", CONTROLLER_NAME);
            return;
        }

        // Validar DNI (8 dígitos)
        if (!dni.matches("\\d{8}")) {
            ventanaPrincipal.showError("DNI inválido. Debe tener 8 dígitos", CONTROLLER_NAME);
            return;
        }

        // Verificar si el DNI ya existe
        if (pacienteService.searchPacienteDni(dni) != null) {
            ventanaPrincipal.showError("Ya existe un paciente con ese DNI", CONTROLLER_NAME);
            return;
        }

        String nombre = panelPacientes.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            ventanaPrincipal.showError("El nombre es obligatorio", CONTROLLER_NAME);
            return;
        }

        String apellidos = panelPacientes.getTxtApellidos().getText().trim();
        if (apellidos.isEmpty()) {
            ventanaPrincipal.showError("Los apellidos son obligatorios", CONTROLLER_NAME);
            return;
        }

        String sexo = (String) panelPacientes.getCbSexo().getSelectedItem();
        if (sexo == null || sexo.isEmpty()) {
            ventanaPrincipal.showError("Debe seleccionar un sexo", CONTROLLER_NAME);
            return;
        }

        String telefono = panelPacientes.getTxtTelefono().getText().trim();
        if (telefono.isEmpty()) {
            ventanaPrincipal.showError("El teléfono es obligatorio", CONTROLLER_NAME);
            return;
        }

        // Validar teléfono (9 dígitos)
        if (!telefono.matches("\\d{9}")) {
            ventanaPrincipal.showError("Teléfono inválido. Debe tener 9 dígitos", CONTROLLER_NAME);
            return;
        }

        LocalDate fechaNacimiento;
        Date date = panelPacientes.getChooserFechaNacimiento().getDate();
        if (date == null) {
            ventanaPrincipal.showError("Debe seleccionar una fecha de nacimiento", CONTROLLER_NAME);
            return;
        }
        fechaNacimiento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Paciente p = new Paciente(dni, nombre, apellidos, sexo, telefono, fechaNacimiento);
        
        if (!pacienteService.registerPaciente(p)) {
            ventanaPrincipal.showError("No se pudo registrar el paciente", CONTROLLER_NAME);
            return;
        }
        
        showPacientes();
        clearPaciente();
        ventanaPrincipal.showExitoCreateModel(CONTROLLER_NAME);
        vistaConsola.printPaciente(p);
    }

    public void editPaciente() {
        int filaSeleccionada = panelPacientes.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione un paciente de la tabla para editar", CONTROLLER_NAME);
            return;
        }

        int idPaciente = (int) panelPacientes.getTabla().getValueAt(filaSeleccionada, 0);
        Paciente p = pacienteService.searchPacienteId(idPaciente);
        if (p == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        String dni = panelPacientes.getTxtDni().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("El DNI es obligatorio", CONTROLLER_NAME);
            return;
        }

        if (!dni.matches("\\d{8}")) {
            ventanaPrincipal.showError("DNI inválido. Debe tener 8 dígitos", CONTROLLER_NAME);
            return;
        }

        // Verificar si el DNI ya existe en otro paciente
        Paciente existente = pacienteService.searchPacienteDni(dni);
        if (existente != null && existente.getIdPaciente() != idPaciente) {
            ventanaPrincipal.showError("Ya existe otro paciente con ese DNI", CONTROLLER_NAME);
            return;
        }

        String nombre = panelPacientes.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            ventanaPrincipal.showError("El nombre es obligatorio", CONTROLLER_NAME);
            return;
        }

        String apellidos = panelPacientes.getTxtApellidos().getText().trim();
        if (apellidos.isEmpty()) {
            ventanaPrincipal.showError("Los apellidos son obligatorios", CONTROLLER_NAME);
            return;
        }

        String sexo = (String) panelPacientes.getCbSexo().getSelectedItem();
        if (sexo == null || sexo.isEmpty() || sexo.equals("-")) {
            ventanaPrincipal.showError("Debe seleccionar un sexo", CONTROLLER_NAME);
            return;
        }

        String telefono = panelPacientes.getTxtTelefono().getText().trim();
        if (telefono.isEmpty()) {
            ventanaPrincipal.showError("El teléfono es obligatorio", CONTROLLER_NAME);
            return;
        }

        if (!telefono.matches("\\d{9}")) {
            ventanaPrincipal.showError("Teléfono inválido. Debe tener 9 dígitos", CONTROLLER_NAME);
            return;
        }

        LocalDate fechaNacimiento;
        Date date = panelPacientes.getChooserFechaNacimiento().getDate();
        if (date == null) {
            ventanaPrincipal.showError("Debe seleccionar una fecha de nacimiento", CONTROLLER_NAME);
            return;
        }
        fechaNacimiento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        p.setDni(dni);
        p.setNombre(nombre);
        p.setApellidos(apellidos);
        p.setSexo(sexo);
        p.setTelefono(telefono);
        p.setFechaNacimiento(fechaNacimiento);

        if (!pacienteService.updatePaciente(p)) {
            ventanaPrincipal.showError("No se pudo actualizar el paciente", CONTROLLER_NAME);
            return;
        }

        showPacientes();
        clearPaciente();
        ventanaPrincipal.showExitoEditModel(CONTROLLER_NAME);
        vistaConsola.printPaciente(p);
    }

    public void deletePaciente() {
        int filaSeleccionada = panelPacientes.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione un paciente de la tabla para eliminar", CONTROLLER_NAME);
            return;
        }

        int idPaciente = (int) panelPacientes.getTabla().getValueAt(filaSeleccionada, 0);
        
        if (!pacienteService.deletePaciente(idPaciente)) {
            ventanaPrincipal.showError("No se pudo eliminar el paciente", CONTROLLER_NAME);
            return;
        }

        showPacientes();
        clearPaciente();
        ventanaPrincipal.showExitoDeleteModel(CONTROLLER_NAME);
    }

    public void searchPaciente() {
        String dni = panelPacientes.getTxtDni().getText().trim();
        if (dni.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un DNI para buscar", CONTROLLER_NAME);
            return;
        }

        Paciente p = pacienteService.searchPacienteDni(dni);
        if (p == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        panelPacientes.getTxtIdPaciente().setText(String.valueOf(p.getIdPaciente()));
        panelPacientes.getTxtDni().setText(p.getDni());
        panelPacientes.getTxtNombre().setText(p.getNombre());
        panelPacientes.getTxtApellidos().setText(p.getApellidos());
        panelPacientes.getCbSexo().setSelectedItem(p.getSexo());
        panelPacientes.getTxtTelefono().setText(p.getTelefono());
        panelPacientes.getChooserFechaNacimiento().setDate(java.sql.Date.valueOf(p.getFechaNacimiento()));
        ventanaPrincipal.showExitoBusqueda(CONTROLLER_NAME);
    }

    public void showPacientes() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("DNI");
        model.addColumn("Nombre");
        model.addColumn("Apellidos");
        model.addColumn("Sexo");
        model.addColumn("Teléfono");
        model.addColumn("Fecha Nacimiento");

        for (Paciente p : pacienteService.listPacientes()) {
            model.addRow(new Object[]{
                p.getIdPaciente(),
                p.getDni(),
                p.getNombre(),
                p.getApellidos(),
                p.getSexo(),
                p.getTelefono(),
                p.getFechaNacimiento()
            });
        }
        panelPacientes.getTabla().setModel(model);
        vistaConsola.adminMsgTabla(CONTROLLER_NAME);
    }

    public void clearPaciente() {
        panelPacientes.getTxtIdPaciente().setText("");
        panelPacientes.getTxtDni().setText("");
        panelPacientes.getTxtNombre().setText("");
        panelPacientes.getTxtApellidos().setText("");
        panelPacientes.getCbSexo().setSelectedIndex(0);
        panelPacientes.getTxtTelefono().setText("");
        panelPacientes.getChooserFechaNacimiento().setDate(null);
    }
}