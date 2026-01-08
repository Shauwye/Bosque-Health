package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EleccionRegistro extends JPanel {
	private JButton especialistaButton;
	private JButton pacienteButton;

	public EleccionRegistro() {
		setLayout(new BorderLayout());

		JLabel mensajeLinea1 = new JLabel("¡HOLA! Antes de registrarte, dinos qué tipo de usuario eres:");
		mensajeLinea1.setHorizontalAlignment(SwingConstants.CENTER);
		mensajeLinea1.setFont(new Font("Arial", Font.PLAIN, 16));

		JPanel mensajePanel = new JPanel(new BorderLayout());
		mensajePanel.add(mensajeLinea1, BorderLayout.CENTER);

		especialistaButton = new JButton("Especialista");
		pacienteButton = new JButton("Paciente");

		especialistaButton.setActionCommand("ESPECIALISTA");
		pacienteButton.setActionCommand("PACIENTE");

		JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		botonesPanel.add(especialistaButton);
		botonesPanel.add(pacienteButton);

		add(mensajePanel, BorderLayout.NORTH);
		add(botonesPanel, BorderLayout.SOUTH);
	}

	public JButton getEspecialistaButton() {
		return especialistaButton;
	}

	public JButton getPacienteButton() {
		return pacienteButton;
	}
}