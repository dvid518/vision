package com.example.View;

import com.example.Model.Consulta;
import com.example.Model.Historia;
import com.example.Model.Paciente;
import com.example.Model.Producto;
import com.example.Model.Venta;

public class VistaConsola {
    public void msg(String msg) {
        System.out.println(msg);
    }
    
    public void adminMsg(String msg, String c) {
        System.out.println(c.toUpperCase()+": "+msg);
    }

    public void adminErr(String err, String c) {
        System.err.println(c.toUpperCase()+" - ERROR: "+err);
    }

    // mensajes admin predeterminados
    public void adminMsgTabla(String c) {
        adminMsg("Tabla cargada correctamente", c);
    }

    public void adminClearConsulta(String c) {
        adminMsg("Consulta reestablecida correctamente", c);
    }

    public void adminClearProducto(String c) {
        adminMsg("Producto reestablecido correctamente", c);
    }
    
    public void adminClearVenta(String c) {
        adminMsg("Venta reestablecida correctamente", c);
    }
    
    public void adminStart(String c) {
        adminMsg("Cargados módulos del controlador", c);
    }

    public void adminGetArrayList(String c) {
        adminMsg("Se obtuvo el arreglo de objetos", c);
    }

    // mensajes error predeterminados
    public void errorValidateModel(String c) {
        adminErr("No se creó el objeto", c);
    }
    
    // impresión de objetos
    public void printConsulta(Consulta c) {
        msg("DNI: "+c.getPaciente().getDni());
        msg("Motivo: "+c.getMotivo());
        msg("Diagnóstico: "+c.getDiagnostico());
        msg("Tratamiento: "+c.getTratamiento());
    }

    public void printHistoria(Historia h) {
        msg("DNI: "+h.getPaciente().getDni());
        msg("Antecedentes: "+h.getAntecedentes());
        msg("Alergias: "+h.getAlergias());
        msg("Graduación: "+h.getGraduacion());
        msg("Observaciones: "+h.getObservaciones());
    }

    public void printPaciente(Paciente p) {
        msg("DNI: "+p.getDni());
        msg("Nombre: "+p.getNombre());
        msg("Apellidos: "+p.getApellidos());
        msg("Sexo: "+p.getSexo());
        msg("Telefono: "+p.getTelefono());
    }

    public void printProducto(Producto p) {
    }

    public void printVenta(Venta v) {
    }
}