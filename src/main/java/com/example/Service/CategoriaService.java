package com.example.Service;

import java.util.List;

import com.example.DAO.CategoriaDAO;
import com.example.Model.Categoria;

public class CategoriaService {
    private final CategoriaDAO catDAO=new CategoriaDAO();

    public List<Categoria> listCategorias() {
        return catDAO.list();
    }

    public Categoria searchCategoriaId(int id) {
        if (id<=0) {
            return null;
        }
        return catDAO.searchId(id);
    }

    public Categoria searchCategoriaNombre(String nombre) {
        if (nombre==null || nombre.isBlank()) {
            return null;
        }
        return catDAO.searchNombre(nombre);
    }
}