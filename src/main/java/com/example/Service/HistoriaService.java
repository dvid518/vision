package com.example.Service;

import java.util.List;

import com.example.DAO.HistoriaDAO;
import com.example.DAO.PacienteDAO;
import com.example.Model.Historia;
import com.example.Model.Paciente;

public class HistoriaService {

    private final HistoriaDAO hisDAO=new HistoriaDAO();
    private final PacienteDAO pacDAO=new PacienteDAO();

    private boolean isValidDatos(Historia h) {
        if (h==null) {
            return false;
        }
        return h.getPaciente()!=null && h.getPaciente().getIdPaciente()>0 && pacDAO.searchId(h.getPaciente().getIdPaciente())!=null;
    }

    public boolean registerHistoria(Historia h) {
        if (h==null) {
            return false;
        }
        if (!isValidDatos(h)) {
            return false;
        }
        if (hisDAO.searchByPaciente(h.getPaciente().getIdPaciente())!=null) {
            return false;
        }
        return hisDAO.insert(h);
    }

    public Historia searchHistoriaId(int id) {
        if (id<=0) {
            return null;
        }
        return hisDAO.searchId(id);
    }

    public Historia searchHistoriaByPaciente(int idPaciente) {
        if (idPaciente<=0) {
            return null;
        }
        return hisDAO.searchByPaciente(idPaciente);
    }

    public Historia searchHistoriaByPacienteDni(String dni) {
        if (dni==null || dni.isBlank()) {
            return null;
        }
        Paciente p=pacDAO.searchDni(dni);
        if (p==null) {
            return null;
        }
        return hisDAO.searchByPaciente(p.getIdPaciente());
    }

    public List<Historia> listHistorias() {
        return hisDAO.list();
    }

    public boolean updateHistoria(Historia h) {
        if (h==null) return false;
        if (h.getIdHistoria()<=0) {
            return false;
        }
        if (!isValidDatos(h)) {
            return false;
        }
        return hisDAO.update(h);
    }

    public boolean deleteHistoria(int id) {
        if (id<=0) {
            return false;
        }
        return hisDAO.delete(id);
    }

    public boolean existeHistoria(int idPaciente) {
        if (idPaciente<=0) {
            return false;
        }
        return hisDAO.searchByPaciente(idPaciente)!=null;
    }
}