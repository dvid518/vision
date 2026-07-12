package com.example.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.Model.Paciente;

public class BuscadorPacientes {
    private final Map<Integer, Paciente> cacheId = new HashMap<>();
    private final Map<String, Paciente> cacheDni = new HashMap<>();
    private final TreeMap<String, Paciente> arbolNombre = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final TreeMap<String, Paciente> arbolApellido = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    
    private boolean datosCargados = false;
    
    public void cargarDatos(List<Paciente> pacientes) {
        cacheId.clear();
        cacheDni.clear();
        arbolNombre.clear();
        arbolApellido.clear();
        
        if (pacientes == null || pacientes.isEmpty()) {
            datosCargados = false;
            return;
        }
        
        for (Paciente p : pacientes) {
            cacheId.put(p.getIdPaciente(), p);
            cacheDni.put(p.getDni(), p);
            arbolNombre.put(p.getNombre().toLowerCase(), p);
            arbolApellido.put(p.getApellidos().toLowerCase(), p);
        }
        
        datosCargados = true;
    }
    
    public Paciente buscarPorId(int id) {
        if (!datosCargados) return null;
        return cacheId.get(id);
    }
    
    public Paciente buscarPorDni(String dni) {
        if (!datosCargados || dni == null || dni.isBlank()) return null;
        return cacheDni.get(dni);
    }
    
    public Paciente buscarPorNombreExacto(String nombre) {
        if (!datosCargados || nombre == null || nombre.isBlank()) return null;
        return arbolNombre.get(nombre.toLowerCase());
    }
    
    public Paciente buscarPorApellidoExacto(String apellido) {
        if (!datosCargados || apellido == null || apellido.isBlank()) return null;
        return arbolApellido.get(apellido.toLowerCase());
    }
    
    public List<Paciente> buscarPorPrefijoNombre(String prefijo) {
        if (!datosCargados || prefijo == null || prefijo.isBlank()) {
            return new ArrayList<>();
        }
        String inicio = prefijo.toLowerCase();
        String fin = inicio + "\uffff";
        return new ArrayList<>(arbolNombre.subMap(inicio, fin).values());
    }
    
    public List<Paciente> buscarPorPrefijoApellido(String prefijo) {
        if (!datosCargados || prefijo == null || prefijo.isBlank()) {
            return new ArrayList<>();
        }
        String inicio = prefijo.toLowerCase();
        String fin = inicio + "\uffff";
        return new ArrayList<>(arbolApellido.subMap(inicio, fin).values());
    }
    
    public List<Paciente> buscar(String criterio, TipoBusqueda tipo) {
        if (!datosCargados || criterio == null || criterio.isBlank()) {
            return new ArrayList<>();
        }
        
        List<Paciente> resultados = new ArrayList<>();
        
        switch (tipo) {
            case POR_ID -> {
                try {
                    int id = Integer.parseInt(criterio);
                    Paciente p = cacheId.get(id);
                    if (p != null) resultados.add(p);
                } catch (NumberFormatException ignored) {}
            }
            case POR_DNI -> {
                Paciente p = cacheDni.get(criterio);
                if (p != null) resultados.add(p);
            }   
            case POR_NOMBRE -> {
                if (criterio.contains(" ")) {
                    String[] partes = criterio.split("\\s+");
                    if (partes.length >= 2) {
                        String nom = partes[0].toLowerCase();
                        String ape = partes[1].toLowerCase();
                        for (Map.Entry<String, Paciente> entry : arbolNombre.entrySet()) {
                            if (entry.getKey().contains(nom) &&
                                    entry.getValue().getApellidos().toLowerCase().contains(ape)) {
                                resultados.add(entry.getValue());
                            }
                        }
                        return resultados;
                    }
                }
                return buscarPorPrefijoNombre(criterio);
            }
            case POR_APELLIDO -> {
                return buscarPorPrefijoApellido(criterio);
            }
            default -> {
                return new ArrayList<>();
            }
        }
        return resultados;
    }
    
    public boolean isDatosCargados() {
        return datosCargados;
    }
    
    public int getCantidadPacientes() {
        return cacheId.size();
    }
    
    public void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS DEL BUSCADOR ===");
        System.out.println("Pacientes en caché ID: " + cacheId.size());
        System.out.println("Pacientes en caché DNI: " + cacheDni.size());
        System.out.println("Pacientes en árbol nombre: " + arbolNombre.size());
        System.out.println("Pacientes en árbol apellido: " + arbolApellido.size());
        System.out.println("Datos cargados: " + (datosCargados ? "SÍ" : "NO"));
    }
    
    public enum TipoBusqueda {
        POR_ID,
        POR_DNI,
        POR_NOMBRE,
        POR_APELLIDO
    }
}