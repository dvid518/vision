package com.example.Service;

import java.util.LinkedList;
import java.util.List;
import com.example.Model.PacientePrioridad;

public class ColaPacientesService {
    
    private final LinkedList<PacientePrioridad> cola;
    
    public ColaPacientesService() {
        this.cola = new LinkedList<>();
    }
    
    public boolean encolar(PacientePrioridad paciente) {
        if (paciente == null) {
            return false;
        }
        if (cola.isEmpty()) {
            cola.add(paciente);
            return true;
        }
        int posicion = 0;
        for (PacientePrioridad p : cola) {
            if (paciente.getPrioridad().getValor() < p.getPrioridad().getValor()) {
                cola.add(posicion, paciente);
                return true;
            }
            posicion++;
        }
        cola.add(paciente);
        return true;
    }

    public PacientePrioridad desencolar() {
        if (cola.isEmpty()) {
            return null;
        }
        return cola.removeFirst();
    }

    public PacientePrioridad verSiguiente() {
        if (cola.isEmpty()) {
            return null;
        }
        return cola.getFirst();
    }
    
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamaño() {
        return cola.size();
    }
    
    public List<PacientePrioridad> listarCola() {
        return new LinkedList<>(cola);
    }
    
    public PacientePrioridad buscarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            return null;
        }
        for (PacientePrioridad p : cola) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }
    
    public boolean eliminarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            return false;
        }
        return cola.removeIf(p -> p.getDni().equals(dni));
    }
    
    public void vaciarCola() {
        cola.clear();
    }
}