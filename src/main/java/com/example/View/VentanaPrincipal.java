package com.example.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class VentanaPrincipal extends JFrame {
    private final PanelColaEspera panelColaEspera; // NUEVO
    private final PanelPacientes panelPacientes;
    private final PanelConsultas panelConsultas;
    private final PanelHistorias panelHistorias;
    private final PanelProductos panelProductos;
    private final PanelVentas panelVentas;
    private final JLabel lblEstado;
    private final VistaConsola vc;

    public VentanaPrincipal() {
        vc = new VistaConsola();

        setTitle("20/20 Visión Excelente");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel menu = new JPanel();
        
        // NUEVO: Botón Cola de Espera (primero)
        JButton btnCola = new JButton("Cola de Espera");
        JButton btnPacientes = new JButton("Pacientes");
        JButton btnConsultas = new JButton("Consultas");
        JButton btnHistorias = new JButton("Historias");
        JButton btnProductos = new JButton("Productos");
        JButton btnVentas = new JButton("Ventas");

        menu.add(btnCola); // NUEVO (primero)
        menu.add(btnPacientes);
        menu.add(btnConsultas);
        menu.add(btnHistorias);
        menu.add(btnProductos);
        menu.add(btnVentas);

        CardLayout cards = new CardLayout();
        JPanel contenido = new JPanel(cards);

        // NUEVO: Panel Cola de Espera (primero)
        panelColaEspera = new PanelColaEspera();
        panelPacientes = new PanelPacientes();
        panelConsultas = new PanelConsultas();
        panelHistorias = new PanelHistorias();
        panelProductos = new PanelProductos();
        panelVentas = new PanelVentas();

        contenido.add(panelColaEspera, "COLA_ESPERA"); // NUEVO (primero)
        contenido.add(panelPacientes, "PACIENTES");
        contenido.add(panelConsultas, "CONSULTAS");
        contenido.add(panelHistorias, "HISTORIAS");
        contenido.add(panelProductos, "PRODUCTOS");
        contenido.add(panelVentas, "VENTAS");

        // NUEVO: Evento para el botón Cola
        btnCola.addActionListener(e -> cards.show(contenido, "COLA_ESPERA"));
        btnPacientes.addActionListener(e -> cards.show(contenido, "PACIENTES"));
        btnConsultas.addActionListener(e -> cards.show(contenido, "CONSULTAS"));
        btnHistorias.addActionListener(e -> cards.show(contenido, "HISTORIAS"));
        btnProductos.addActionListener(e -> cards.show(contenido, "PRODUCTOS"));
        btnVentas.addActionListener(e -> cards.show(contenido, "VENTAS"));

        lblEstado = new JLabel("", SwingConstants.LEFT);
        lblEstado.setOpaque(false);
        lblEstado.setVisible(false);
        lblEstado.setBackground(Color.WHITE);
        lblEstado.setBounds(850, 620, 300, 30);
        getLayeredPane().add(lblEstado, JLayeredPane.POPUP_LAYER);
        setLayout(new BorderLayout());
        add(menu, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }

    // métodos de mensajes en tiempo real
    public void msg(String msg) {
        lblEstado.setText(msg);
        lblEstado.setVisible(true);
        lblEstado.setOpaque(true);
        cleanLuego();
    }

    public void cleanLuego() {
        Timer t = new Timer(5000, e -> {
            lblEstado.setText("");
            lblEstado.setOpaque(false);
        });
        t.setRepeats(false);
        t.start();
    }

    public void showExito(String msg, String c) {
        vc.adminMsg(msg, c);
        lblEstado.setForeground(Color.decode("#0d8200"));
        msg(msg);
    }

    public void showError(String msg, String c) {
        vc.adminErr(msg, c);
        lblEstado.setForeground(Color.RED);
        msg(msg);
    }

    // métodos de mensajes predeterminados de éxito
    public void showExitoBusqueda(String c) {
        showExito("Objeto encontrado correctamente", c);
    }

    public void showExitoCreateModel(String c) {
        showExito("Objeto creado correctamente", c);
    }

    public void showExitoEditModel(String c) {
        showExito("Objeto editado correctamente", c);
    }

    public void showExitoDeleteModel(String c) {
        showExito("Objeto eliminado correctamente", c);
    }

    // métodos de mensajes predeterminados de error
    public void showErrorBusqueda(String c) {
        showError("Objeto no encontrado", c);
    }

    public void showErrorDato(String c, String dato) {
        showError(dato + " inválido", c);
    }

    public void showErrorFecha(String c) {
        showError("Fecha inválida", c);
    }

    public PanelColaEspera getPanelColaEspera() {
        return panelColaEspera;
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