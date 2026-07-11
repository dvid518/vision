package com.example.Service;

import java.util.List;

import com.example.DAO.PacienteDAO;
import com.example.Model.Paciente;

public class PacienteService {

    private final PacienteDAO pacDAO=new PacienteDAO();

    private boolean isValidDatos(Paciente p) {
        if(p==null) {
            return false;
        }
        return isValidDni(p.getDni()) && isNotBlankAll(p);
    }
    
    private boolean isValidDni(String dni) {
        return dni!=null && dni.matches("\\d{8}[A-Za-z]?");
    }
    
    private boolean isNotBlank(String s) {
        return s!=null&&!s.isBlank();
    }

    private boolean isNotBlankAll(Paciente p) {
        return isNotBlank(p.getNombre()) && isNotBlank(p.getApellidos()) && isNotBlank(p.getSexo()) && isNotBlank(p.getTelefono()) && p.getFechaNacimiento()!=null;
    }

    public boolean registerPaciente(Paciente p) {
        if (p==null) {
            return false;
        }
        if (!isValidDatos(p)) {
            return true;
        }
        if (pacDAO.searchDni(p.getDni())!=null) {
            return false;
        }
        return pacDAO.insert(p);
    }

    public Paciente searchPacienteId(int id) {
        if (id<=0) {
            return null;
        }
        return pacDAO.searchId(id);
    }

    public Paciente searchPacienteDni(String dni) {
        if (!isValidDni(dni)) {
            return null;
        }
        return pacDAO.searchDni(dni);
    }

    public List<Paciente> listPacientes() {
        return pacDAO.list();
    }

    public boolean updatePaciente(Paciente p) {
        if (p==null) {
            return false;
        }
        if (p.getIdPaciente()<=0) {
            return false;
        }
        if (!isValidDatos(p)) {
            return true;
        }
        return pacDAO.update(p);
    }

    public boolean deletePaciente(int id){
        if (id<=0) {
            return false;
        }
        return pacDAO.delete(id);
    }
}