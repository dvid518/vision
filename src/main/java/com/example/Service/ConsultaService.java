package com.example.Service;

import java.util.List;

import com.example.DAO.ConsultaDAO;
import com.example.DAO.PacienteDAO;
import com.example.Model.Consulta;
import com.example.Model.Paciente;

public class ConsultaService {

    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    private boolean isValidDatos(Consulta c) {
        if (c == null) return false;
        return c.getPaciente() != null
            && c.getPaciente().getIdPaciente() > 0
            && pacienteDAO.searchId(c.getPaciente().getIdPaciente()) != null
            && isNotBlank(c.getMotivo());
    }

    private boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }

    public boolean registerConsulta(Consulta c) {
        if (c == null) return false;
        if (!isValidDatos(c)) return false;
        return consultaDAO.insert(c);
    }

    public Consulta searchConsultaId(int id) {
        if (id <= 0) return null;
        return consultaDAO.searchId(id);
    }

    public List<Consulta> searchConsultasByPaciente(int idPaciente) {
        if (idPaciente <= 0) return null;
        return consultaDAO.searchByPaciente(idPaciente);
    }

    public List<Consulta> searchConsultasByPacienteDni(String dni) {
        if (dni == null || dni.isBlank()) return null;
        Paciente p = pacienteDAO.searchDni(dni);
        if (p == null) return null;
        return consultaDAO.searchByPaciente(p.getIdPaciente());
    }

    public List<Consulta> listConsultas() {
        return consultaDAO.list();
    }

    public boolean updateConsulta(Consulta c) {
        if (c == null) return false;
        if (c.getIdConsulta() <= 0) return false;
        if (!isValidDatos(c)) return false;
        return consultaDAO.update(c);
    }

    public boolean deleteConsulta(int id) {
        if (id <= 0) return false;
        return consultaDAO.delete(id);
    }

    public int countConsultasByPaciente(int idPaciente) {
        if (idPaciente <= 0) return 0;
        List<Consulta> lista = consultaDAO.searchByPaciente(idPaciente);
        return lista != null ? lista.size() : 0;
    }
}