package co.edu.unbosque.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelRegistrarPaciente extends JPanel {
	private JTextField identificacionField;
	private JTextField nombreField;
	private JPasswordField contrasenaField;
	private JTextField correoField;
	private JButton registrarseButton;
	private JButton volverButton;

	public PanelRegistrarPaciente(ActionListener listener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Línea superior
		JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		topSeparator.setPreferredSize(new Dimension(600, 1));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(topSeparator, gbc);

		// Título del panel
		JLabel titulo = new JLabel("Registrar Paciente", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		add(titulo, gbc);

		// Línea inferior debajo del título
		JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		bottomSeparator.setPreferredSize(new Dimension(600, 1));
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(bottomSeparator, gbc);

		// Mensaje de bienvenida
		JLabel mensaje = new JLabel("Por favor, complete la información para registrarse como paciente",
				JLabel.CENTER);
		mensaje.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		add(mensaje, gbc);

		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;

		// Campo de identificación
		JLabel identificacionLabel = new JLabel("Identificación:");
		identificacionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy = 4;
		gbc.gridx = 0;
		add(identificacionLabel, gbc);

		identificacionField = new JTextField(20);
		identificacionField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(identificacionField, gbc);

		// Campo de nombre
		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy = 5;
		gbc.gridx = 0;
		add(nombreLabel, gbc);

		nombreField = new JTextField(20);
		nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(nombreField, gbc);

		// Campo de contraseña
		JLabel contrasenaLabel = new JLabel("Contraseña:");
		contrasenaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy = 6;
		gbc.gridx = 0;
		add(contrasenaLabel, gbc);

		contrasenaField = new JPasswordField(20);
		contrasenaField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(contrasenaField, gbc);

		// Campo de correo electrónico
		JLabel correoLabel = new JLabel("Correo Electrónico:");
		correoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy = 7;
		gbc.gridx = 0;
		add(correoLabel, gbc);

		correoField = new JTextField(20);
		correoField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(correoField, gbc);

		// Panel para los botones
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

		// Botones de Registrarse y Volver
		registrarseButton = new JButton("Registrarse");
		volverButton = new JButton("Volver");

		// Asignar ActionCommand a los botones
		registrarseButton.setActionCommand("REGISTRARSE_PACIENTE");
		registrarseButton.addActionListener(listener);
		volverButton.setActionCommand("VOLVER_PACIENTE");
		volverButton.addActionListener(listener);

		// Ajustar tamaño y fuente de los botones
		registrarseButton.setFont(new Font("Arial", Font.BOLD, 16));
		volverButton.setFont(new Font("Arial", Font.BOLD, 16));
		registrarseButton.setPreferredSize(new Dimension(140, 35));
		volverButton.setPreferredSize(new Dimension(140, 35));

		// Quitar colores de fondo
		registrarseButton.setContentAreaFilled(false);
		volverButton.setContentAreaFilled(false);

		// Agregar los botones al panel de botones
		buttonPanel.add(registrarseButton);
		buttonPanel.add(volverButton);

		// Agregar el panel de botones al panel principal
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(buttonPanel, gbc);
	}

	// Métodos getter para acceder a los componentes desde fuera de la clase
	public JTextField getIdentificacionField() {
		return identificacionField;
	}

	public JTextField getNombreField() {
		return nombreField;
	}

	public JPasswordField getContrasenaField() {
		return contrasenaField;
	}

	public JTextField getCorreoField() {
		return correoField;
	}

	public JButton getRegistrarseButton() {
		return registrarseButton;
	}

	public JButton getVolverButton() {
		return volverButton;
	}
	
}

