package com.example.Controller;

import java.util.List;

import javax.swing.DefaultListModel;

import com.example.Model.Paciente;
import com.example.Model.PacientePrioridad;
import com.example.Service.ColaPacientesService;
import com.example.Service.PacienteService;
import com.example.View.PanelColaEspera;
import com.example.View.VentanaPrincipal;

public class ControladorCola {

    private static final String CONTROLLER_NAME = "ControladorCola";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelColaEspera panelCola;
    private final ColaPacientesService colaService;
    private final PacienteService pacienteService;

    public ControladorCola(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panelCola = ventanaPrincipal.getPanelColaEspera();
        this.colaService = new ColaPacientesService();
        this.pacienteService = new PacienteService();
    }

    public void start() {
        eventos();
        actualizarListaCola();
        ventanaPrincipal.showAdminStart(CONTROLLER_NAME);
    }

    private void eventos() {
        panelCola.getBtnBuscar().addActionListener(e -> buscarPacientes());
        panelCola.getBtnAgregarCola().addActionListener(e -> agregarACola());
        panelCola.getBtnAtender().addActionListener(e -> atenderSiguiente());
        panelCola.getBtnEliminarCola().addActionListener(e -> eliminarSeleccionado());
        panelCola.getTxtBuscar().addActionListener(e -> buscarPacientes());
    }

    private void buscarPacientes() {
        String texto = panelCola.getTxtBuscar().getText().trim();
        if (texto.isEmpty()) {
            ventanaPrincipal.showError("Ingrese un término de búsqueda", CONTROLLER_NAME);
            panelCola.limpiarResultados();
            return;
        }

        panelCola.limpiarResultados();
        List<Paciente> resultados;

        if (texto.matches("\\d+")) {
            try {
                int id = Integer.parseInt(texto);
                Paciente p = pacienteService.buscarPorIdOptimizado(id);
                if (p != null) {
                    panelCola.agregarResultado(p);
                }
            } catch (NumberFormatException ignored) {}
        } else if (texto.matches("\\d{8}")) {
            Paciente p = pacienteService.buscarPorDniOptimizado(texto);
            if (p != null) {
                panelCola.agregarResultado(p);
            }
        } else {
            resultados = pacienteService.buscarPorNombreOptimizado(texto);
            for (Paciente p : resultados) {
                panelCola.agregarResultado(p);
            }
        }

        if (panelCola.getModeloTabla().getRowCount() == 0) {
            ventanaPrincipal.showError("No se encontraron pacientes", CONTROLLER_NAME);
        } else {
            ventanaPrincipal.showExitoBusqueda(CONTROLLER_NAME);
        }
    }

    private void agregarACola() {
        Paciente p = panelCola.getPacienteSeleccionado();
        if (p == null) {
            ventanaPrincipal.showError("Seleccione un paciente de la tabla", CONTROLLER_NAME);
            return;
        }

        Paciente pacienteCompleto = pacienteService.searchPacienteId(p.getIdPaciente());
        if (pacienteCompleto == null) {
            ventanaPrincipal.showError("El paciente no existe en la base de datos", CONTROLLER_NAME);
            return;
        }

        if (colaService.buscarPorDni(pacienteCompleto.getDni()) != null) {
            ventanaPrincipal.showError("El paciente ya está en la cola de espera", CONTROLLER_NAME);
            return;
        }

        PacientePrioridad.Prioridad prioridad = (PacientePrioridad.Prioridad) panelCola.getCbPrioridad().getSelectedItem();
        PacientePrioridad pacientePrioridad = new PacientePrioridad(pacienteCompleto, prioridad);

        if (colaService.encolar(pacientePrioridad)) {
            actualizarListaCola();
            ventanaPrincipal.showExito("Paciente agregado a la cola con prioridad " + prioridad, CONTROLLER_NAME);
        } else {
            ventanaPrincipal.showError("Error al agregar a la cola", CONTROLLER_NAME);
        }
    }

    private void atenderSiguiente() {
        if (colaService.estaVacia()) {
            ventanaPrincipal.showError("La cola está vacía", CONTROLLER_NAME);
            return;
        }

        PacientePrioridad atendido = colaService.desencolar();
        if (atendido != null) {
            actualizarListaCola();
            ventanaPrincipal.showExito("Atendiendo a: " + atendido.getNombre() + " " + atendido.getApellidos() +
                                      " (Prioridad: " + atendido.getPrioridad() + ")", CONTROLLER_NAME);
        } else {
            ventanaPrincipal.showError("Error al atender", CONTROLLER_NAME);
        }
    }

    private void eliminarSeleccionado() {
        int indice = panelCola.getListaCola().getSelectedIndex();
        if (indice == -1) {
            ventanaPrincipal.showError("Seleccione un paciente de la cola", CONTROLLER_NAME);
            return;
        }
        String texto = panelCola.getModeloLista().getElementAt(indice);
        String dni = "";
        try {
            int inicio = texto.lastIndexOf("DNI: ") + 5;
            int fin = texto.lastIndexOf(")");
            if (inicio > 0 && fin > inicio) {
                dni = texto.substring(inicio, fin);
            }
        } catch (Exception e) {
            ventanaPrincipal.showError("Error al procesar el DNI", CONTROLLER_NAME);
            return;
        }

        if (dni.isEmpty()) {
            ventanaPrincipal.showError("No se pudo identificar el DNI", CONTROLLER_NAME);
            return;
        }

        if (colaService.eliminarPorDni(dni)) {
            actualizarListaCola();
            ventanaPrincipal.showExito("Paciente eliminado de la cola", CONTROLLER_NAME);
        } else {
            ventanaPrincipal.showError("Error al eliminar", CONTROLLER_NAME);
        }
    }

    private void actualizarListaCola() {
        DefaultListModel<String> modelo = panelCola.getModeloLista();
        modelo.clear();
        for (PacientePrioridad p : colaService.listarCola()) {
            modelo.addElement(p.toString());
        }
        panelCola.actualizarEstadoCola();
    }

    public ColaPacientesService getColaService() {
        return colaService;
    }
}