package com.example.Model;

public class Paciente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String sexo;
    private String telefono;
    private int edad;
    private String direccion;

    public Paciente(String dni, String nombre, String apellidos, String sexo, String telefono, int edad, String direccion) {
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.sexo=sexo;
        this.telefono=telefono;
        this.edad=edad;
        this.direccion=direccion;
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

    public int getEdad() {
        return edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDni(String dni) {
        this.dni=dni;
    }

    public void setNombres(String nombre) {
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

    public void setEdad(int edad) {
        this.edad=edad;
    }

    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }
}