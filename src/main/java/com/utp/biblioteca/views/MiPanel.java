package com.utp.biblioteca.views;

import javax.swing.JPanel;
import javax.swing.JButton;

public class MiPanel extends JPanel {
    public MiPanel() {
        // Constructor donde configuras tu panel y agregas componentes
        JButton boton = new JButton("Presiona aquí");
        this.add(boton);
        // Configura aquí más componentes y el layout si es necesario
    }
}