package com.example.Controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import com.example.Model.Paciente;
import com.example.View.PanelPacientes;
import com.example.View.VentanaPrincipal;
import com.example.View.VistaConsola;

public class ControladorPacientes {
    private final VentanaPrincipal vp;
    private final PanelPacientes pp;
    private final ArrayList<Paciente> pacientes;
    private final VistaConsola vc;
    private final String controlador="ControladorPacientes";

    public ControladorPacientes(VentanaPrincipal vp) {
        this.pp=vp.getPanelPacientes();
        this.vp=vp;
        pacientes=new ArrayList<>();
        vc=new VistaConsola();
    }

    public void start() {
        eventos();
        showPacientes();
        vc.adminStart(controlador);
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
        vp.showExitoCreateModel(controlador);
        vc.printPaciente(p);
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
        vp.showExitoEditModel(controlador);
        vc.printPaciente(p);
    }

    public void deletePaciente() {
        Paciente p=targetPaciente(pp.getTxtDni().getText());
        if (p==null) {
            return;
        }
        pacientes.remove(p);
        showPacientes();
        clearPaciente();
        vp.showExitoDeleteModel(controlador);
    }

    public void searchPaciente() {
        Paciente p=targetPaciente(pp.getTxtDni().getText());
        if (p==null) {
            vp.showErrorBusqueda(controlador);
            return;
        }
        pp.getTxtNombre().setText(p.getNombre());
        pp.getTxtApellidos().setText(p.getApellidos());
        pp.getTxtSexo().setText(p.getSexo());
        pp.getTxtTelefono().setText(p.getTelefono());
        pp.getTxtEdad().setText(String.valueOf(p.getEdad()));
        vp.showExitoBusqueda(controlador);
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
                return p;
            }
        }
        return null;
    }

    // validaciones
    public boolean validatePaciente(String dni, String sexo, String telefono, int edad) {
        boolean val=true;
        if (!validateDni(dni)) {
            vp.showError("El DNI no es correcto (8 dígitos)", controlador);
            val=false;
        }
        if (targetPaciente(dni)!=null) {
            vp.showError("Ya existe un paciente con ese dni", controlador);
            val=false;
        }
        if (!validateSexo(sexo)) {
            vp.showError("El sexo no es correcto (M/F)", controlador);
            val=false;
        }
        if (!validateTelefono(telefono)) {
            vp.showError("El teléfono no es correcto (9 dígitos)", controlador);
            val=false;
        }
        if (!validateEdad(edad)) {
            vp.showError("La edad no es correcta (Sólo números positivos)", controlador);
            val=false;
        }
        vc.errorValidateModel(controlador);
        return val;
    }

    // validaciones
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
        vc.adminGetArrayList(controlador);
        return pacientes;
    }
}