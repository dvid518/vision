package com.example.Model;

public class PacientePrioridad extends Paciente implements Comparable<PacientePrioridad> {
    
    public enum Prioridad {
        NORMAL(2), URGENTE(1);
        
        private final int valor;
        
        Prioridad(int valor) {
            this.valor = valor;
        }
        
        public int getValor() {
            return valor;
        }
    }
    
    private Prioridad prioridad;
    
    public PacientePrioridad(Paciente p, Prioridad prioridad) {
        super(p.getDni(), p.getNombre(), p.getApellidos(), p.getSexo(), 
              p.getTelefono(), p.getFechaNacimiento());
        this.setIdPaciente(p.getIdPaciente());
        this.prioridad = prioridad;
    }
    
    public PacientePrioridad(String dni, String nombre, String apellidos, 
                             String sexo, String telefono, java.time.LocalDate fechaNacimiento, 
                             Prioridad prioridad) {
        super(dni, nombre, apellidos, sexo, telefono, fechaNacimiento);
        this.prioridad = prioridad;
    }
    
    public Prioridad getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    
    @Override
    public int compareTo(PacientePrioridad otro) {
        return Integer.compare(this.prioridad.getValor(), otro.prioridad.getValor());
    }
    
    @Override
    public String toString() {
        return prioridad + " - " + getNombre() + " " + getApellidos() + " (DNI: " + getDni() + ")";
    }
}
