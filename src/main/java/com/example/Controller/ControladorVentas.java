package com.example.Controller;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.example.Model.Paciente;
import com.example.Model.Producto;
import com.example.Model.Venta;
import com.example.Service.PacienteService;
import com.example.Service.ProductoService;
import com.example.Service.VentaService;
import com.example.View.PanelVentas;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorVentas {

    private static final String CONTROLLER_NAME = "ControladorVentas";
    
    private final VentanaPrincipal ventanaPrincipal;
    private final PanelVentas panelVentas;
    private final VentaService ventaService;
    private final PacienteService pacienteService;
    private final ProductoService productoService;
    private final VistaConsola vistaConsola;

    public ControladorVentas(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panelVentas = ventanaPrincipal.getPanelVentas();
        this.ventaService = new VentaService();
        this.pacienteService = new PacienteService();
        this.productoService = new ProductoService();
        this.vistaConsola = new VistaConsola();
    }

    public void start() {
        eventos();
        cargarPacientes();
        cargarProductos();
        showVentas();
        ventanaPrincipal.showAdminStart(CONTROLLER_NAME);
    }

    public void eventos() {
        panelVentas.getBtnRegistrar().addActionListener(e -> createVenta());
        panelVentas.getBtnEditar().addActionListener(e -> editVenta());
        panelVentas.getBtnEliminar().addActionListener(e -> deleteVenta());
        panelVentas.getBtnBuscar().addActionListener(e -> searchVenta());
    }

    private void cargarPacientes() {
        DefaultComboBoxModel<Paciente> model = new DefaultComboBoxModel<>();
        for (Paciente p : pacienteService.listPacientes()) {
            model.addElement(p);
        }
        panelVentas.getCbPaciente().setModel(model);
    }

    private void cargarProductos() {
        DefaultComboBoxModel<Producto> model = new DefaultComboBoxModel<>();
        for (Producto p:productoService.listProductos()) {
            model.addElement(p);
        }
        panelVentas.getCbProducto().setModel(model);
    }

    public void createVenta() {
        Paciente paciente = (Paciente) panelVentas.getCbPaciente().getSelectedItem();
        if (paciente == null) {
            ventanaPrincipal.showError("Debe seleccionar un paciente", CONTROLLER_NAME);
            return;
        }

        Producto producto = (Producto) panelVentas.getCbProducto().getSelectedItem();
        if (producto == null) {
            ventanaPrincipal.showError("Debe seleccionar un producto", CONTROLLER_NAME);
            return;
        }

        String cantidadStr = panelVentas.getTxtCantidad().getText().trim();
        if (cantidadStr.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar la cantidad", CONTROLLER_NAME);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            ventanaPrincipal.showError("La cantidad debe ser un número válido", CONTROLLER_NAME);
            return;
        }

        if (cantidad <= 0) {
            ventanaPrincipal.showError("La cantidad debe ser mayor a 0", CONTROLLER_NAME);
            return;
        }

        // Verificar stock disponible
        if (!productoService.tieneStock(producto.getIdProducto(), cantidad)) {
            ventanaPrincipal.showError("Stock insuficiente. Stock disponible: " + 
                productoService.searchProductoId(producto.getIdProducto()).getStock(), CONTROLLER_NAME);
            return;
        }

        Venta v = new Venta(paciente, producto, cantidad);
        
        if (!ventaService.registerVenta(v)) {
            ventanaPrincipal.showError("No se pudo registrar la venta", CONTROLLER_NAME);
            return;
        }
        
        showVentas();
        clearVenta();
        ventanaPrincipal.showExitoCreateModel(CONTROLLER_NAME);
        vistaConsola.printVenta(v);
    }

    public void editVenta() {
        int filaSeleccionada = panelVentas.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione una venta de la tabla para editar", CONTROLLER_NAME);
            return;
        }

        int idVenta = (int) panelVentas.getTabla().getValueAt(filaSeleccionada, 0);
        Venta v = ventaService.searchVentaId(idVenta);
        if (v == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        Paciente paciente = (Paciente) panelVentas.getCbPaciente().getSelectedItem();
        if (paciente == null) {
            ventanaPrincipal.showError("Debe seleccionar un paciente", CONTROLLER_NAME);
            return;
        }

        Producto producto = (Producto) panelVentas.getCbProducto().getSelectedItem();
        if (producto == null) {
            ventanaPrincipal.showError("Debe seleccionar un producto", CONTROLLER_NAME);
            return;
        }

        String cantidadStr = panelVentas.getTxtCantidad().getText().trim();
        if (cantidadStr.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar la cantidad", CONTROLLER_NAME);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            ventanaPrincipal.showError("La cantidad debe ser un número válido", CONTROLLER_NAME);
            return;
        }

        if (cantidad <= 0) {
            ventanaPrincipal.showError("La cantidad debe ser mayor a 0", CONTROLLER_NAME);
            return;
        }

        v.setPaciente(paciente);
        v.setProducto(producto);
        v.setCantidad(cantidad);

        if (!ventaService.updateVenta(v)) {
            ventanaPrincipal.showError("No se pudo actualizar la venta", CONTROLLER_NAME);
            return;
        }

        showVentas();
        clearVenta();
        ventanaPrincipal.showExitoEditModel(CONTROLLER_NAME);
        vistaConsola.printVenta(v);
    }

    public void deleteVenta() {
        int filaSeleccionada = panelVentas.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione una venta de la tabla para eliminar", CONTROLLER_NAME);
            return;
        }

        int idVenta = (int) panelVentas.getTabla().getValueAt(filaSeleccionada, 0);
        
        if (!ventaService.deleteVenta(idVenta)) {
            ventanaPrincipal.showError("No se pudo eliminar la venta", CONTROLLER_NAME);
            return;
        }

        showVentas();
        clearVenta();
        ventanaPrincipal.showExitoDeleteModel(CONTROLLER_NAME);
    }

    public void searchVenta() {
        String idStr = panelVentas.getTxtIdVenta().getText().trim();
        if (idStr.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un ID para buscar", CONTROLLER_NAME);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            ventanaPrincipal.showError("El ID debe ser un número válido", CONTROLLER_NAME);
            return;
        }

        Venta v = ventaService.searchVentaId(id);
        if (v == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        panelVentas.getCbPaciente().setSelectedItem(v.getPaciente());
        panelVentas.getCbProducto().setSelectedItem(v.getProducto());
        panelVentas.getTxtCantidad().setText(String.valueOf(v.getCantidad()));
        ventanaPrincipal.showExitoBusqueda(CONTROLLER_NAME);
    }

    public void showVentas() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Paciente");
        model.addColumn("DNI");
        model.addColumn("Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio Unit.");
        model.addColumn("Total");

        for (Venta v : ventaService.listVentas()) {
            model.addRow(new Object[]{
                v.getIdVenta(),
                v.getPaciente().getNombre() + " " + v.getPaciente().getApellidos(),
                v.getPaciente().getDni(),
                v.getProducto().getNombre(),
                v.getCantidad(),
                v.getProducto().getPrecio(),
                v.getCantidad() * v.getProducto().getPrecio()
            });
        }
        panelVentas.getTabla().setModel(model);
    }

    public void clearVenta() {
        panelVentas.getTxtIdVenta().setText("");
        panelVentas.getCbPaciente().setSelectedIndex(0);
        panelVentas.getCbProducto().setSelectedIndex(0);
        panelVentas.getTxtCantidad().setText("");
        vistaConsola.adminClearVenta(CONTROLLER_NAME);
    }
}