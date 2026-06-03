package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Paciente;
import com.example.View.PanelPacientes;
import com.example.View.VentanaPrincipal;

public class ControladorPacientes {
    private final PanelPacientes pp;
    private final ArrayList<Paciente> pacientes;
    private final VentanaPrincipal v;

    public ControladorPacientes(VentanaPrincipal v) {
        this.pp=v.getPanelPacientes();
        this.v=v;
        pacientes=new ArrayList<>();
    }

    public void start() {
        eventos();
        showPacientes();
    }

    // eventos
    public void eventos() {
        pp.getBtnRegistrar().addActionListener(e->createPaciente());
        pp.getBtnEditar().addActionListener(e->editPaciente());
        pp.getBtnEliminar().addActionListener(e->deletePaciente());
        pp.getBtnBuscar().addActionListener(e->searchPaciente());
    }

    // pacientes
    public void createPaciente() {
        String dni=pp.getTxtDni().getText();
        String nombre=pp.getTxtNombre().getText();
        String apellidos=pp.getTxtApellidos().getText();
        String sexo=pp.getTxtSexo().getText();
        String telefono=pp.getTxtTelefono().getText();
        int edad;
        try {
            edad=Integer.parseInt(pp.getTxtEdad().getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (!validatePaciente(dni, sexo, telefono, edad)) {
            return;
        }
        Paciente p=new Paciente(dni, nombre, apellidos, sexo, telefono, edad);
        pacientes.add(p);
        showPacientes();
        clearPaciente();
        v.showExito("Paciente creado correctamente");
    }

    public void editPaciente() {
        Paciente p=targetPaciente(pp.getTxtDni().getText());
        if (p==null) {
            return;
        }
        p.setNombres(pp.getTxtNombre().getText());
        p.setTelefono(pp.getTxtTelefono().getText());
        try {
            p.setEdad(Integer.parseInt(pp.getTxtEdad().getText()));
        } catch (NumberFormatException e) {
            return;
        }
        showPacientes();
        clearPaciente();
        v.showExito("Paciente editado correctamente");
    }

    public void deletePaciente() {
        Paciente p=targetPaciente(pp.getTxtDni().getText());
        if (p==null) {
            return;
        }
        pacientes.remove(p);
        showPacientes();
        clearPaciente();
        v.showExito("Paciente eliminado correctamente");
    }

    public void searchPaciente() {
        Paciente p=targetPaciente(pp.getTxtDni().getText());
        if (p==null) {
            return;
        }
        pp.getTxtNombre().setText(p.getNombre());
        pp.getTxtApellidos().setText(p.getApellidos());
        pp.getTxtSexo().setText(p.getSexo());
        pp.getTxtTelefono().setText(p.getTelefono());
        pp.getTxtEdad().setText(String.valueOf(p.getEdad()));
        v.showExito("Paciente encontrado correctamente");
    }

    // mostrar
    public void showPacientes() {
        DefaultTableModel m=new DefaultTableModel();
        m.addColumn("DNI");
        m.addColumn("Nombre");
        m.addColumn("Apellidos");
        m.addColumn("Sexo");
        m.addColumn("Teléfono");
        m.addColumn("Edad");
        for (Paciente p:pacientes) {
            m.addRow(new Object[]{p.getDni(), p.getNombre(), p.getApellidos(), p.getSexo(), p.getTelefono(), p.getEdad()});
        }
        pp.getTabla().setModel(m);
    }

    // búsqueda
    public Paciente targetPaciente(String dni) {
        for (Paciente p:pacientes) {
            if (p.getDni().equals(dni)) {
                v.showExito("Paciente encontrado correctamente");
                return p;
            }
        }
        v.showError("No se encontró el paciente");
        return null;
    }

    // validaciones
    public boolean validatePaciente(String dni, String sexo, String telefono, int edad) {
        boolean val=true;
        if (!validateDni(dni)) {
            v.showError("El DNI no es correcto (8 dígitos)");
            val=false;
        }
        if (!validateSexo(sexo)) {
            v.showError("El sexo no es correcto (M/F)");
            val=false;
        }
        if (!validateTelefono(telefono)) {
            v.showError("El teléfono no es correcto (9 dígitos)");
            val=false;
        }
        if (!validateEdad(edad)) {
            v.showError("La edad no es correcta (Sólo números positivos)");
            val=false;
        }
        if (targetPaciente(dni)!=null) {
            v.showError("Ya existe un paciente con ese dni");
            val=false;
        }
        return val;
    }

    public boolean validateDni(String dni) {
        return dni.length()==8;
    }
    
    public boolean validateSexo(String sexo) {
        boolean s=sexo.equalsIgnoreCase("m")||sexo.equalsIgnoreCase("f");
        return sexo.length()==1&&s;
    }

    public boolean validateTelefono(String telefono) {
        return telefono.length()>=9;
    }

    public boolean validateEdad(int edad) {
        return edad>0;
    }

    // utilidades
    public void clearPaciente() {
        pp.getTxtDni().setText("");
        pp.getTxtNombre().setText("");
        pp.getTxtApellidos().setText("");
        pp.getTxtSexo().setText("");
        pp.getTxtTelefono().setText("");
        pp.getTxtEdad().setText("");
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }
}