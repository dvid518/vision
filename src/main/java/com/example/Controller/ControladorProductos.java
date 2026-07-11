package com.example.Controller;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.example.Model.Categoria;
import com.example.Model.Producto;
import com.example.Service.CategoriaService;
import com.example.Service.ProductoService;
import com.example.View.PanelProductos;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorProductos {

    private static final String CONTROLLER_NAME = "ControladorProductos";
    
    private final VentanaPrincipal ventanaPrincipal;
    private final PanelProductos panelProductos;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final VistaConsola vistaConsola;

    public ControladorProductos(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panelProductos = ventanaPrincipal.getPanelProductos();
        this.productoService = new ProductoService();
        this.categoriaService = new CategoriaService();
        this.vistaConsola = new VistaConsola();
    }

    public void start() {
        eventos();
        cargarCategorias();
        showProductos();
        vistaConsola.adminStart(CONTROLLER_NAME);
    }

    public void eventos() {
        panelProductos.getBtnRegistrar().addActionListener(e -> createProducto());
        panelProductos.getBtnEditar().addActionListener(e -> editProducto());
        panelProductos.getBtnEliminar().addActionListener(e -> deleteProducto());
        panelProductos.getBtnBuscar().addActionListener(e -> searchProducto());
    }

    private void cargarCategorias() {
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();
        for (Categoria c : categoriaService.listCategorias()) {
            model.addElement(c);
        }
        panelProductos.getCbCategoria().setModel(model);
    }

    public void createProducto() {
        String nombre = panelProductos.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            ventanaPrincipal.showError("El nombre del producto es obligatorio", CONTROLLER_NAME);
            return;
        }

        Categoria categoria = (Categoria) panelProductos.getCbCategoria().getSelectedItem();
        if (categoria == null) {
            ventanaPrincipal.showError("Debe seleccionar una categoría", CONTROLLER_NAME);
            return;
        }

        String precioStr = panelProductos.getTxtPrecio().getText().trim();
        if (precioStr.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un precio", CONTROLLER_NAME);
            return;
        }

        String stockStr = panelProductos.getTxtStock().getText().trim();
        if (stockStr.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar el stock", CONTROLLER_NAME);
            return;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            ventanaPrincipal.showError("Precio y stock deben ser números válidos", CONTROLLER_NAME);
            return;
        }

        if (precio <= 0) {
            ventanaPrincipal.showError("El precio debe ser mayor a 0", CONTROLLER_NAME);
            return;
        }

        if (stock < 0) {
            ventanaPrincipal.showError("El stock no puede ser negativo", CONTROLLER_NAME);
            return;
        }

        Producto p = new Producto(nombre, categoria, precio, stock);
        
        if (!productoService.registerProducto(p)) {
            ventanaPrincipal.showError("No se pudo registrar el producto", CONTROLLER_NAME);
            return;
        }
        
        showProductos();
        clearProducto();
        ventanaPrincipal.showExitoCreateModel(CONTROLLER_NAME);
        vistaConsola.printProducto(p);
    }

    public void editProducto() {
        int filaSeleccionada = panelProductos.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione un producto de la tabla para editar", CONTROLLER_NAME);
            return;
        }

        int idProducto = (int) panelProductos.getTabla().getValueAt(filaSeleccionada, 0);
        Producto p = productoService.searchProductoId(idProducto);
        if (p == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        String nombre = panelProductos.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            ventanaPrincipal.showError("El nombre del producto es obligatorio", CONTROLLER_NAME);
            return;
        }

        Categoria categoria = (Categoria) panelProductos.getCbCategoria().getSelectedItem();
        if (categoria == null) {
            ventanaPrincipal.showError("Debe seleccionar una categoría", CONTROLLER_NAME);
            return;
        }

        String precioStr = panelProductos.getTxtPrecio().getText().trim();
        String stockStr = panelProductos.getTxtStock().getText().trim();

        if (precioStr.isEmpty() || stockStr.isEmpty()) {
            ventanaPrincipal.showError("Precio y stock son obligatorios", CONTROLLER_NAME);
            return;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            ventanaPrincipal.showError("Precio y stock deben ser números válidos", CONTROLLER_NAME);
            return;
        }

        if (precio <= 0) {
            ventanaPrincipal.showError("El precio debe ser mayor a 0", CONTROLLER_NAME);
            return;
        }

        if (stock < 0) {
            ventanaPrincipal.showError("El stock no puede ser negativo", CONTROLLER_NAME);
            return;
        }

        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setStock(stock);

        if (!productoService.updateProducto(p)) {
            ventanaPrincipal.showError("No se pudo actualizar el producto", CONTROLLER_NAME);
            return;
        }

        showProductos();
        clearProducto();
        ventanaPrincipal.showExitoEditModel(CONTROLLER_NAME);
        vistaConsola.printProducto(p);
    }

    public void deleteProducto() {
        int filaSeleccionada = panelProductos.getTabla().getSelectedRow();
        if (filaSeleccionada == -1) {
            ventanaPrincipal.showError("Seleccione un producto de la tabla para eliminar", CONTROLLER_NAME);
            return;
        }

        int idProducto = (int) panelProductos.getTabla().getValueAt(filaSeleccionada, 0);
        
        if (!productoService.deleteProducto(idProducto)) {
            ventanaPrincipal.showError("No se pudo eliminar el producto", CONTROLLER_NAME);
            return;
        }
        showProductos();
        clearProducto();
        ventanaPrincipal.showExitoDeleteModel(CONTROLLER_NAME);
    }

    public void searchProducto() {
        String nombre = panelProductos.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            ventanaPrincipal.showError("Debe ingresar un nombre para buscar", CONTROLLER_NAME);
            return;
        }

        Producto p = productoService.searchProductoNombre(nombre);
        if (p == null) {
            ventanaPrincipal.showErrorBusqueda(CONTROLLER_NAME);
            return;
        }

        panelProductos.getTxtId().setText(String.valueOf(p.getIdProducto()));
        panelProductos.getTxtNombre().setText(p.getNombre());
        panelProductos.getCbCategoria().setSelectedItem(p.getCategoria());
        panelProductos.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
        panelProductos.getTxtStock().setText(String.valueOf(p.getStock()));
        ventanaPrincipal.showExitoBusqueda(CONTROLLER_NAME);
    }

    public void showProductos() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Categoría");
        model.addColumn("Precio");
        model.addColumn("Stock");

        for (Producto p : productoService.listProductos()) {
            model.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getCategoria().getNombre(),
                p.getPrecio(),
                p.getStock()
            });
        }
        panelProductos.getTabla().setModel(model);
        vistaConsola.adminMsgTabla(CONTROLLER_NAME);
    }

    public void clearProducto() {
        panelProductos.getTxtId().setText("");
        panelProductos.getTxtNombre().setText("");
        panelProductos.getCbCategoria().setSelectedIndex(0);
        panelProductos.getTxtPrecio().setText("");
        panelProductos.getTxtStock().setText("");
        vistaConsola.adminClearProducto(CONTROLLER_NAME);
    }
}