package com.example.Model;

public class Historia {
    private int idHistoria;
    private Paciente paciente;
    private String antecedentes;
    private String alergias;
    private String graduacion;
    private String observaciones;

    public Historia(Paciente paciente, String antecedentes, String alergias, String graduacion, String observaciones) {
        this.paciente=paciente;
        this.antecedentes=antecedentes;
        this.alergias=alergias;
        this.graduacion=graduacion;
        this.observaciones=observaciones;
    }

    public int getIdHistoria() {
        return idHistoria;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getGraduacion() {
        return graduacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdHistoria(int idHistoria) {
        this.idHistoria=idHistoria;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente=paciente;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes=antecedentes;
    }

    public void setAlergias(String alergias) {
        this.alergias=alergias;
    }

    public void setGraduacion(String graduacion) {
        this.graduacion=graduacion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones=observaciones;
    }
}