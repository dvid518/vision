package com.example.Model;

public class Consulta {
    private String dniPaciente;
    private String motivo;
    private String diagnostico;
    private String tratamiento;

    public Consulta(String dniPaciente, String motivo, String diagnostico, String tratamiento) {
        this.dniPaciente=dniPaciente;
        this.motivo=motivo;
        this.diagnostico=diagnostico;
        this.tratamiento=tratamiento;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente=dniPaciente;
    }

    public void setMotivo(String motivo) {
        this.motivo=motivo;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico=diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento=tratamiento;
    }
}