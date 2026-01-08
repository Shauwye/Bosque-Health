package co.edu.unbosque.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelCancelarCita extends JPanel {
	private JLabel lblId;
	private JTextField txtId;
	private JButton btnCancelarCita;
	private JButton btnVolver;

	public PanelCancelarCita(ActionListener listener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		lblId = new JLabel("Ingrese el ID de su cita:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblId, gbc);

		txtId = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(txtId, gbc);

		btnCancelarCita = new JButton("Cancelar cita");
		btnCancelarCita.setActionCommand("CancelarCita");
		btnCancelarCita.addActionListener(listener);
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(btnCancelarCita, gbc);

		btnVolver = new JButton("Volver");
		btnVolver.setActionCommand("VolverEditarCita");
		btnVolver.addActionListener(listener);
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(btnVolver, gbc);
	}

	public JLabel getLblId() {
		return lblId;
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public JButton getBtnCancelarCita() {
		return btnCancelarCita;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}
	
	public int getIdCita() {
		return Integer.parseInt(txtId.getText());

	}
}
