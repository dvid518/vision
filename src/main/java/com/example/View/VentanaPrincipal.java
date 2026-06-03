package com.example.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class VentanaPrincipal extends JFrame {
    private final PanelPacientes panelPacientes;
    private final PanelConsultas panelConsultas;
    private final PanelHistorias panelHistorias;
    private final PanelProductos panelProductos;
    private final PanelVentas panelVentas;

    private final JLabel lblEstado;

    public VentanaPrincipal() {
        setTitle("20/20 Visión Excelente");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel menu=new JPanel();

        JButton btnPacientes=new JButton("Pacientes");
        JButton btnConsultas=new JButton("Consultas");
        JButton btnHistorias=new JButton("Historias");
        JButton btnProductos=new JButton("Productos");
        JButton btnVentas=new JButton("Ventas");

        menu.add(btnPacientes);
        menu.add(btnConsultas);
        menu.add(btnHistorias);
        menu.add(btnProductos);
        menu.add(btnVentas);

        CardLayout cards=new CardLayout();
        JPanel contenido=new JPanel(cards);

        panelPacientes=new PanelPacientes();
        panelConsultas=new PanelConsultas();
        panelHistorias=new PanelHistorias();
        panelProductos=new PanelProductos();
        panelVentas=new PanelVentas();

        contenido.add(panelPacientes, "PACIENTES");
        contenido.add(panelConsultas, "CONSULTAS");
        contenido.add(panelHistorias, "HISTORIAS");
        contenido.add(panelProductos, "PRODUCTOS");
        contenido.add(panelVentas, "VENTAS");

        btnPacientes.addActionListener(e -> cards.show(contenido, "PACIENTES"));
        btnConsultas.addActionListener(e -> cards.show(contenido, "CONSULTAS"));
        btnHistorias.addActionListener(e -> cards.show(contenido, "HISTORIAS"));
        btnProductos.addActionListener(e -> cards.show(contenido, "PRODUCTOS"));
        btnVentas.addActionListener(e -> cards.show(contenido, "VENTAS"));

        lblEstado=new JLabel("");
        lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        setLayout(new BorderLayout());

        add(menu, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);
    }

    public void showError(String msg) {
        lblEstado.setForeground(Color.RED);
        lblEstado.setText(msg);
        cleanLuego();
    }

    public void showExito(String msg) {
        lblEstado.setForeground(Color.GREEN);
        lblEstado.setText(msg);
        cleanLuego();
    }

    public void cleanLuego() {
        Timer t=new Timer(3000, e -> {lblEstado.setText("");});
        t.setRepeats(false);
        t.start();
    }
    
    public PanelPacientes getPanelPacientes() {
        return panelPacientes;
    }

    public PanelConsultas getPanelConsultas() {
        return panelConsultas;
    }

    public PanelHistorias getPanelHistorias() {
        return panelHistorias;
    }

    public PanelProductos getPanelProductos() {
        return panelProductos;
    }

    public PanelVentas getPanelVentas() {
        return panelVentas;
    }
}