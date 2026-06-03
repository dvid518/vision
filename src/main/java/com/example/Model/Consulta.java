package com.example.Model;

public class Consulta {
    private String dniPaciente;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private int dia;
    private int mes;
    private int ano;

    public Consulta(String dniPaciente, String motivo, String diagnostico, String tratamiento, int dia, int mes, int ano) {
        this.dniPaciente=dniPaciente;
        this.motivo=motivo;
        this.diagnostico=diagnostico;
        this.tratamiento=tratamiento;
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
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

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public String getFecha() {
        return dia+"-"+mes+"-"+ano;
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

    public void setDia(int dia) {
        this.dia=dia;
    }

    public void setMes(int mes) {
        this.mes=mes;
    }

    public void setAno(int ano) {
        this.ano=ano;
    }
}