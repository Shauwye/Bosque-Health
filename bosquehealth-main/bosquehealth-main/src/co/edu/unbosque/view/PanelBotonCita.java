package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotonCita extends JPanel {
	
	 public PanelBotonCita(ActionListener listener) {
	        setLayout(new GridBagLayout()); 
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.HORIZONTAL; 
	        gbc.weightx = 1.0; 
	        gbc.insets = new Insets(20, 20, 20, 20); 

	        // Configurar botón "Agregar Cita"
	        JButton btnAgregar = new JButton("AGREGAR CITA");
	        btnAgregar.setActionCommand("AGREGAR_CITA_COMMAND");
	        btnAgregar.addActionListener(listener);
	        btnAgregar.setFont(new Font("Arial", Font.BOLD, 16)); 
	        btnAgregar.setPreferredSize(new Dimension(200, 50)); 
	        gbc.gridx = 0;
	        add(btnAgregar, gbc);

	        // Configurar botón "Editar Cita"
	        JButton btnEditar = new JButton("EDITAR CITA");
	        btnEditar.setActionCommand("EDITAR_CITA_COMMAND");
	        btnEditar.addActionListener(listener);
	        btnEditar.setFont(new Font("Arial", Font.BOLD, 16));
	        btnEditar.setPreferredSize(new Dimension(200, 40));
	        gbc.gridx = 1;
	        add(btnEditar, gbc);

	        // Configurar botón "Cancelar Cita"
	        JButton btnCancelar = new JButton("CANCELAR CITA");
	        btnCancelar.setActionCommand("CANCELAR_CITA_COMMAND");
	        btnCancelar.addActionListener(listener);
	        btnCancelar.setFont(new Font("Arial", Font.BOLD, 16));
	        btnCancelar.setPreferredSize(new Dimension(200, 40));
	        gbc.gridx = 2;
	        add(btnCancelar, gbc);
	    }
	}