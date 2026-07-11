package com.example.Service;

import java.util.List;

import com.example.DAO.VentaDAO;
import com.example.Model.Paciente;
import com.example.Model.Producto;
import com.example.Model.Venta;

public class VentaService {
    private final VentaDAO venDAO=new VentaDAO();
    private final PacienteService pacService=new PacienteService();
    private final ProductoService proService=new ProductoService();

    private boolean isValidDatos(Venta v) {
        if (v==null) {
            return false;
        }
        return v.getPaciente()!=null && v.getPaciente().getIdPaciente()>0 && v.getProducto()!=null && v.getProducto().getIdProducto()>0 && v.getCantidad()>0;
    }

    public boolean registerVenta(Venta v) {
        if (v==null) {
            return false;
        }
        if (!isValidDatos(v)) {
            return false;
        }
        Paciente paciente=pacService.searchPacienteId(v.getPaciente().getIdPaciente());
        if (paciente==null) {
            return false;
        }
        Producto producto=proService.searchProductoId(v.getProducto().getIdProducto());
        if (producto==null) {
            return false;
        }
        if (!proService.tieneStock(producto.getIdProducto(), v.getCantidad())) {
            return false;
        }
        if (!proService.actualizarStock(producto.getIdProducto(), -v.getCantidad())) {
            return false;
        }
        return venDAO.insert(v);
    }

    public Venta searchVentaId(int id) {
        if (id<=0) {
            return null;
        }
        return venDAO.searchId(id);
    }

    public List<Venta> searchVentasByPaciente(int idPaciente) {
        if (idPaciente<=0) {
            return null;
        }
        return venDAO.searchByPaciente(idPaciente);
    }

    public List<Venta> searchVentasByPacienteDni(String dni) {
        if (dni==null || dni.isBlank()) {
            return null;
        }
        Paciente p=pacService.searchPacienteDni(dni);
        if (p==null) {
            return null;
        }
        return venDAO.searchByPaciente(p.getIdPaciente());
    }

    public List<Venta> searchVentasByProducto(int idProducto) {
        if (idProducto<=0) return null;
        return venDAO.searchByProducto(idProducto);
    }

    public List<Venta> listVentas() {
        return venDAO.list();
    }

    public boolean updateVenta(Venta v) {
        Paciente paciente=v.getPaciente();
        Producto producto=v.getProducto();
        if (v.getIdVenta()<=0 || !isValidDatos(v)) {
            return false;
        }
        Venta venta=venDAO.searchId(v.getIdVenta());
        if (venta==null) {
            return false;
        }
        if (paciente==null) {
            return false;
        }
        if (producto==null) {
            return false;
        }
        int diferencia=v.getCantidad()-venta.getCantidad();
        if (diferencia>0) {
            if (!proService.tieneStock(producto.getIdProducto(), diferencia)) {
                return false;
            }
            proService.actualizarStock(producto.getIdProducto(), -diferencia);
        } else if (diferencia<0) {
            proService.actualizarStock(producto.getIdProducto(), -diferencia);
        }
        return venDAO.update(v);
    }

    public boolean deleteVenta(int id) {
        if (id<=0) {
            return false;
        }
        Venta v=venDAO.searchId(id);
        if (v==null) {
            return false;
        }
        proService.actualizarStock(v.getProducto().getIdProducto(), v.getCantidad());
        return venDAO.delete(id);
    }

    public double calcularTotalVentas() {
        List<Venta> ventas=venDAO.list();
        double total=0;
        for (Venta v:ventas) {
            total+=v.getCantidad()*v.getProducto().getPrecio();
        }
        return total;
    }

    public int contarVentasByPaciente(int idPaciente) {
        if (idPaciente<=0) {
            return 0;
        }
        List<Venta> lista=venDAO.searchByPaciente(idPaciente);
        return lista!=null?lista.size():0;
    }
}