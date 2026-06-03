package com.example.Model;

public class HistoriaClinica {
    private String dniPaciente;
    private String antecedentes;
    private String alergias;
    private String graduacion;
    private String observaciones;

    public HistoriaClinica(String dniPaciente, String antecedentes, String alergias, String graduacion, String observaciones) {
        this.dniPaciente=dniPaciente;
        this.antecedentes=antecedentes;
        this.alergias=alergias;
        this.graduacion=graduacion;
        this.observaciones=observaciones;
    }

    public String getDniPaciente() {
        return dniPaciente;
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

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente=dniPaciente;
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