package com.example.Service;

import java.util.List;

import com.example.DAO.PacienteDAO;
import com.example.Model.Paciente;

public final class PacienteService {

    private final PacienteDAO pacDAO = new PacienteDAO();
    private final BuscadorPacientes buscador = new BuscadorPacientes();

    public PacienteService() {
        recargarDatos();
    }

    public void recargarDatos() {
        List<Paciente> pacientes = pacDAO.list();
        buscador.cargarDatos(pacientes);
    }

    public Paciente buscarPorIdOptimizado(int id) {
        return buscador.buscarPorId(id);
    }

    public Paciente buscarPorDniOptimizado(String dni) {
        return buscador.buscarPorDni(dni);
    }

    public List<Paciente> buscarPorNombreOptimizado(String nombre) {
        return buscador.buscar(nombre, BuscadorPacientes.TipoBusqueda.POR_NOMBRE);
    }

    public List<Paciente> buscarPorApellidoOptimizado(String apellido) {
        return buscador.buscar(apellido, BuscadorPacientes.TipoBusqueda.POR_APELLIDO);
    }

    public List<Paciente> buscarPorPrefijoNombre(String prefijo) {
        return buscador.buscarPorPrefijoNombre(prefijo);
    }

    public List<Paciente> buscarPorPrefijoApellido(String prefijo) {
        return buscador.buscarPorPrefijoApellido(prefijo);
    }

    private boolean isValidDatos(Paciente p) {
        if (p == null) {
            return false;
        }
        return isValidDni(p.getDni()) && isNotBlankAll(p);
    }

    private boolean isValidDni(String dni) {
        return dni != null && dni.matches("\\d{8}[A-Za-z]?");
    }

    private boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }

    private boolean isNotBlankAll(Paciente p) {
        return isNotBlank(p.getNombre()) && isNotBlank(p.getApellidos())
                && isNotBlank(p.getSexo()) && isNotBlank(p.getTelefono())
                && p.getFechaNacimiento() != null;
    }

    public boolean registerPaciente(Paciente p) {
        if (p == null) {
            return false;
        }
        if (!isValidDatos(p)) {
            return false;
        }
        if (pacDAO.searchDni(p.getDni()) != null) {
            return false;
        }
        boolean resultado = pacDAO.insert(p);
        if (resultado) {
            recargarDatos();
        }
        return resultado;
    }

    public Paciente searchPacienteId(int id) {
        if (id <= 0) {
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
        if (p == null) {
            return false;
        }
        if (p.getIdPaciente() <= 0) {
            return false;
        }
        if (!isValidDatos(p)) {
            return false;
        }
        boolean resultado = pacDAO.update(p);
        if (resultado) {
            recargarDatos();
        }
        return resultado;
    }

    public boolean deletePaciente(int id) {
        if (id <= 0) {
            return false;
        }
        boolean resultado = pacDAO.delete(id);
        if (resultado) {
            recargarDatos();
        }
        return resultado;
    }

    public List<Paciente> buscarPorNombreOApellido(String texto) {
        if (texto == null || texto.isBlank()) {
            return new java.util.ArrayList<>();
        }
        return pacDAO.buscarPorNombreOApellido(texto);
    }

    public List<Paciente> buscarPorNombreYApellidos(String texto) {
        if (texto == null || texto.isBlank()) {
            return new java.util.ArrayList<>();
        }
        return pacDAO.buscarPorNombreYApellidos(texto);
    }

    public List<Paciente> buscarPorNombreExacto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return new java.util.ArrayList<>();
        }
        return pacDAO.buscarPorNombreExacto(nombre);
    }
}