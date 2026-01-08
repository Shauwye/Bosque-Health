package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.edu.unbosque.model.PacienteDTO;

public class PanelGestionTratamiento extends JPanel {
	private JTextField textIndicaciones;
	private JTextField textDiagnostico;
	private JTextField textTratamiento;
	private JComboBox<PacienteDTO> comboNombrePaciente;

	public PanelGestionTratamiento(ActionListener listener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 20, 20, 20);

		comboNombrePaciente = new JComboBox<>(new PacienteDTO[] {});
		crearComponentes(gbc, listener);
	}

	private void crearComponentes(GridBagConstraints gbc, ActionListener listener) {

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

		JLabel titulo = new JLabel("GESTION TRATAMIENTOS", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridy = 1;
		add(titulo, gbc);

		gbc.gridy = 2;
		add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

		gbc.gridwidth = 1;

		agregarEtiquetaYComponente(gbc, "Nombre del paciente:", comboNombrePaciente, 3);
		agregarEtiquetaYComponente(gbc, "Tratamiento", textDiagnostico = new JTextField(20), 4);
		agregarEtiquetaYComponente(gbc, "Diagnostico:", textTratamiento = new JTextField(20), 5);
		agregarEtiquetaYComponente(gbc, "Indicaciones:", textIndicaciones = new JTextField(20), 6);

		agregarBotones(gbc, listener);
	}

	private void agregarEtiquetaYComponente(GridBagConstraints gbc, String texto, JTextField componente, int y) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setForeground(Color.BLACK);
		etiqueta.setFont(new Font("Arial", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = y;
		add(etiqueta, gbc);

		componente.setFont(new Font("Arial", Font.PLAIN, 18));
		componente.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 1;
		add(componente, gbc);
	}

	private void agregarEtiquetaYComponente(GridBagConstraints gbc, String texto, JComboBox<PacienteDTO> componente,
			int y) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setForeground(Color.BLACK);
		etiqueta.setFont(new Font("Arial", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = y;
		add(etiqueta, gbc);

		componente.setFont(new Font("Arial", Font.PLAIN, 18));
		componente.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 1;
		add(componente, gbc);
	}

	private void agregarBotones(GridBagConstraints gbc, ActionListener listener) {
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

		JButton btnGuardarTratamiento = new JButton("GUARDAR TRATAMIENTO");
		btnGuardarTratamiento.setFont(new Font("Arial", Font.BOLD, 16));
		btnGuardarTratamiento.setPreferredSize(new Dimension(240, 45));
		btnGuardarTratamiento.setActionCommand("GUARDAR_TRATAMIENTO");
		btnGuardarTratamiento.addActionListener(listener);
		btnGuardarTratamiento.putClientProperty("JButton.buttonType", "roundRect");

		JButton btnAsignarExamen = new JButton("ASIGNAR EXAMEN");
		btnAsignarExamen.setFont(new Font("Arial", Font.BOLD, 16));
		btnAsignarExamen.setPreferredSize(new Dimension(240, 45));
		btnAsignarExamen.setActionCommand("ASIGNAR_EXAMEN");
		btnAsignarExamen.addActionListener(listener);
		btnAsignarExamen.putClientProperty("JButton.buttonType", "roundRect");

		JButton btnConsultar = new JButton("CONSULTAR TRATAMIENTOS Y EXÁMENES");
		btnConsultar.setFont(new Font("Arial", Font.BOLD, 16));
		btnConsultar.setPreferredSize(new Dimension(300, 45));
		btnConsultar.setActionCommand("CONSULTAR_TRATAMIENTOS");
		btnConsultar.addActionListener(listener);
		btnConsultar.putClientProperty("JButton.buttonType", "roundRect");

		panelBotones.add(btnGuardarTratamiento);
		panelBotones.add(btnAsignarExamen);
		panelBotones.add(btnConsultar);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		add(panelBotones, gbc);
	}

	public void mostrarDialogoTratamientos(List<String[]> tratamientosData) {
		JDialog dialogo = new JDialog();
		dialogo.setTitle("Tratamientos y Exámenes");
		dialogo.setSize(700, 400);
		dialogo.setLocationRelativeTo(this);
		dialogo.setLayout(new BorderLayout());

		String[] columnNames = { "Paciente", "Descripción/Diagnóstico", "Examen/Indicaciones" };
		DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);

		for (String[] fila : tratamientosData) {
			modeloTabla.addRow(fila);
		}

		JTable tabla = new JTable(modeloTabla);
		tabla.setPreferredScrollableViewportSize(new Dimension(650, 300));
		tabla.setFillsViewportHeight(true);

		dialogo.add(new JScrollPane(tabla), BorderLayout.CENTER);
		dialogo.setVisible(true);
	}

	public void actualizarPacientes(List<PacienteDTO> pacientes) {
		if (pacientes != null) {
			comboNombrePaciente.removeAllItems();
			for (PacienteDTO paciente : pacientes) {
				comboNombrePaciente.addItem(paciente);
			}
		}
	}

	public JTextField getTextIndicaciones() {
		return textIndicaciones;
	}

	public JTextField getTextDiagnostico() {
		return textDiagnostico;
	}

	public JTextField getTextTratamiento() {
		return textTratamiento;
	}

	public JComboBox<PacienteDTO> getComboNombrePaciente() {
		return comboNombrePaciente;
	}

}
