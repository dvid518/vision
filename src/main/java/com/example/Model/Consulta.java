package com.example.Model;

public class Consulta {
    private final int idConsulta;
    private Paciente paciente;
    private String motivo;
    private String diagnostico;
    private String tratamiento;

    public Consulta(Paciente paciente, String motivo, String diagnostico, String tratamiento) {
        this.idConsulta=0;
        this.paciente=paciente;
        this.motivo=motivo;
        this.diagnostico=diagnostico;
        this.tratamiento=tratamiento;
    }

    public int getIdConsulta() {
        return idConsulta;
    }
    
    public Paciente getPaciente() {
        return paciente;
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
    
    public void setPaciente(Paciente paciente) {
        this.paciente=paciente;
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