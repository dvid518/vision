package com.example.Service;

import java.util.List;

import com.example.DAO.ProductoDAO;
import com.example.Model.Categoria;
import com.example.Model.Producto;

public class ProductoService {

    private final ProductoDAO proDAO=new ProductoDAO();
    private final CategoriaService catService=new CategoriaService();

    private boolean isValidDatos(Producto p) {
        if (p==null) return false;
        return isNotBlank(p.getNombre()) && p.getCategoria()!=null && p.getCategoria().getIdCategoria()>0 && p.getPrecio()>0 && p.getStock()>=0;
    }

    private boolean isNotBlank(String s) {
        return s!=null && !s.isBlank();
    }

    public boolean registerProducto(Producto p) {
        if (p==null) {
            return false;
        }
        if (!isValidDatos(p)) {
            return false;
        }
        if (catService.searchCategoriaId(p.getCategoria().getIdCategoria())==null) {
            return false;
        }
        if (proDAO.searchNombre(p.getNombre())!=null) {
            return false;
        }
        
        return proDAO.insert(p);
    }

    public Producto searchProductoId(int id) {
        if (id <= 0) {
            return null;
        }
        return proDAO.searchId(id);
    }

    public Producto searchProductoNombre(String nombre) {
        if (!isNotBlank(nombre)) {
            return null;
        }
        return proDAO.searchNombre(nombre);
    }

    public List<Producto> searchProductosByCategoria(int idCategoria) {
        if (idCategoria<=0) {
            return null;
        }
        return proDAO.searchByCategoria(idCategoria);
    }

    public List<Producto> searchProductosByCategoriaNombre(String nombreCategoria) {
        if (!isNotBlank(nombreCategoria)) {
            return null;
        }
        Categoria c=catService.searchCategoriaNombre(nombreCategoria);
        if (c==null) {
            return null;
        }
        return proDAO.searchByCategoria(c.getIdCategoria());
    }

    public List<Producto> listProductos() {
        return proDAO.list();
    }

    public boolean updateProducto(Producto p) {
        if (p==null) {
            return false;
        }
        if (p.getIdProducto() <= 0) {
            return false;
        }
        if (!isValidDatos(p)) {
            return false;
        }
        if (catService.searchCategoriaId(p.getCategoria().getIdCategoria())==null) {
            return false;
        }
        
        return proDAO.update(p);
    }

    public boolean deleteProducto(int id) {
        if (id<=0) {
            return false;
        }
        return proDAO.delete(id);
    }

    public boolean actualizarStock(int id, int cant) {
        if (id<=0) {
            return false;
        }
        Producto p=proDAO.searchId(id);
        if (p==null) {
            return false;
        }
        int nStock=p.getStock()+cant;
        if (nStock<0) {
            return false;
        }
        
        return proDAO.updateStock(id, nStock);
    }

    public boolean tieneStock(int id, int cant) {
        if (id<=0 || cant<=0) {
            return false;
        }
        Producto p=proDAO.searchId(id);
        if (p==null) {
            return false;
        }
        return p.getStock()>=cant;
    }
}