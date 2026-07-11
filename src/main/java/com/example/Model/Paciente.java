package com.example.Model;

import java.time.LocalDate;

public class Paciente {
    private int idPaciente;
    private String dni;
    private String nombre;
    private String apellidos;
    private String sexo;
    private String telefono;
    private LocalDate fechaNacimiento;

    public Paciente() {}

    public Paciente(String dni, String nombre, String apellidos, String sexo, String telefono, LocalDate fechaNacimiento) {
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.sexo=sexo;
        this.telefono=telefono;
        this.fechaNacimiento=fechaNacimiento;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    
    public String getSexo() {
        return sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente=idPaciente;
    }

    public void setDni(String dni) {
        this.dni=dni;
    }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos=apellidos;
    }
    
    public void setSexo(String sexo) {
        this.sexo=sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento=fechaNacimiento;
    }
}