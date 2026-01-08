package co.edu.unbosque.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

public class PanelEditarCita extends JPanel {
	private JTextField txtId;


	public PanelEditarCita(ActionListener listener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblCita = new JLabel("Id Cita");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblCita, gbc);
		
		txtId = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(txtId, gbc);
		
		// Etiqueta y lista para Turno
		JLabel lblTurno = new JLabel("Especialidad");
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(lblTurno, gbc);

		JComboBox<String> comboTurno = new JComboBox<>(new String[] { "Cirugía", "Oncología", "Dermatología",
				"Neumología", "Cardiología", "Medicina Interna" });
		gbc.gridx = 1;
		add(comboTurno, gbc);

		// Etiqueta y lista para Especialista
		JLabel lblEspecialista = new JLabel("Especialista:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblEspecialista, gbc);

		JComboBox<String> comboEspecialista = new JComboBox<>(
				new String[] { "Especialista 1", "Especialista 2", "Especialista 3" });
		gbc.gridx = 1;
		add(comboEspecialista, gbc);

		// Etiqueta y picker para Fecha usando JXDatePicker
		JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lblFecha, gbc);

		JXDatePicker datePicker = new JXDatePicker();
		gbc.gridx = 1;
		add(datePicker, gbc);

		// Etiqueta y caja de texto para el correo del paciente
		JLabel lblCorreo = new JLabel("Correo Paciente:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblCorreo, gbc);

		JTextField txtCorreo = new JTextField(20);
		gbc.gridx = 1;
		add(txtCorreo, gbc);

		// Agregar el panel de botones al final
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2; // Ocupa dos columnas
		PanelBotonEditarCita panelBotones = new PanelBotonEditarCita(listener);
		add(panelBotones, gbc);
	}
	public JTextField getTxtId() {
		return txtId;
	}
	
	public int getIdCita() {
		return Integer.parseInt(txtId.getText());

	}
}
