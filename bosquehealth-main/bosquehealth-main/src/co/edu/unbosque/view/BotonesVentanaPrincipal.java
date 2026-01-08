package co.edu.unbosque.view;

import com.formdev.flatlaf.FlatLightLaf;

import co.edu.unbosque.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BotonesVentanaPrincipal extends JPanel {

	public BotonesVentanaPrincipal(Controller listener) {
        FlatLightLaf.setup(); // Configura el tema de FlatLaf

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(25, 25, 25, 25);

        Font fontGrande = new Font("Arial", Font.PLAIN, 17);
        Dimension buttonSize = new Dimension(200, 60);
        Color buttonColor = Color.WHITE;

        // Botón Gestion de Citas
        JButton btnGestionCitas = new JButton("Gestion de citas");
        btnGestionCitas.setActionCommand("GESTION_CITAS");
        btnGestionCitas.setFont(fontGrande);
        btnGestionCitas.setPreferredSize(buttonSize);
        btnGestionCitas.setBackground(buttonColor);
        btnGestionCitas.setForeground(Color.BLACK);
        btnGestionCitas.putClientProperty("JButton.buttonType", "roundRect");
        btnGestionCitas.addActionListener(listener);

        // Botón Gestion de Turnos
        JButton btnGestionTurnos = new JButton("Gestion de turnos");
        btnGestionTurnos.setActionCommand("GESTION_TURNOS");
        btnGestionTurnos.setFont(fontGrande);
        btnGestionTurnos.setPreferredSize(buttonSize);
        btnGestionTurnos.setBackground(buttonColor);
        btnGestionTurnos.setForeground(Color.BLACK);
        btnGestionTurnos.putClientProperty("JButton.buttonType", "roundRect");
        btnGestionTurnos.addActionListener(listener);

        // Botón Gestion de Tratamientos
        JButton btnSeguimiento = new JButton("Gestion tratamientos");
        btnSeguimiento.setActionCommand("GESTION_TRATAMIENTOS");
        btnSeguimiento.setFont(fontGrande);
        btnSeguimiento.setPreferredSize(buttonSize);
        btnSeguimiento.setBackground(buttonColor);
        btnSeguimiento.setForeground(Color.BLACK);
        btnSeguimiento.putClientProperty("JButton.buttonType", "roundRect");
        btnSeguimiento.addActionListener(listener);

        // Botón Reportes
        JButton btnReportes = new JButton("Reportes");
        btnReportes.setActionCommand("REPORTES");
        btnReportes.setFont(fontGrande);
        btnReportes.setPreferredSize(buttonSize);
        btnReportes.setBackground(buttonColor);
        btnReportes.setForeground(Color.BLACK);
        btnReportes.putClientProperty("JButton.buttonType", "roundRect");
        btnReportes.addActionListener(listener);

        // Botón Regresar al Login
        JButton btnRegresarLogin = new JButton("Regresar al login");
        btnRegresarLogin.setActionCommand("REGRESAR_LOGIN");
        btnRegresarLogin.setFont(fontGrande);
        btnRegresarLogin.setPreferredSize(buttonSize);
        btnRegresarLogin.setBackground(buttonColor);
        btnRegresarLogin.setForeground(Color.BLACK);
        btnRegresarLogin.putClientProperty("JButton.buttonType", "roundRect");
        btnRegresarLogin.addActionListener(listener);

        // Agregar los botones al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(btnGestionCitas, gbc);

        gbc.gridy = 1;
        this.add(btnGestionTurnos, gbc);

        gbc.gridy = 2;
        this.add(btnSeguimiento, gbc);

        if (listener.esDirector) {
            gbc.gridy = 3;
            this.add(btnReportes, gbc);
        }

        // Añadir el botón Regresar al login
        gbc.gridy = 4;
        this.add(btnRegresarLogin, gbc);
    }
}

