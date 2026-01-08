package co.edu.unbosque.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import co.edu.unbosque.model.CitaDTO;
import co.edu.unbosque.model.ReporteDTO;

public class PanelReporte extends JPanel {
	private JTable tabla;
	private DefaultTableModel tableModel;

	public PanelReporte(ActionListener listener) {
		this.setLayout(new BorderLayout());

		// Panel superior para el título
		JPanel panelTitulo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);

		JSeparator lineaSuperior = new JSeparator(SwingConstants.HORIZONTAL);
		lineaSuperior.setPreferredSize(new Dimension(600, 2));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelTitulo.add(lineaSuperior, gbc);

		JLabel lblListadoPacientes = new JLabel("LISTADO DE PACIENTES ATENDIDOS", JLabel.CENTER);
		lblListadoPacientes.setForeground(Color.BLACK);
		lblListadoPacientes.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridy = 1;
		panelTitulo.add(lblListadoPacientes, gbc);

		JSeparator lineaInferior = new JSeparator(SwingConstants.HORIZONTAL);
		lineaInferior.setPreferredSize(new Dimension(600, 2));
		gbc.gridy = 2;
		panelTitulo.add(lineaInferior, gbc);

		this.add(panelTitulo, BorderLayout.NORTH);

		// Configuración de la tabla
		String[] columnNames = { "PACIENTE", "ESPECIALISTA", "DIAGNOSTICO", "¿CITA CANCELADA?" };
		tableModel = new DefaultTableModel(columnNames, 0);
		tabla = new JTable(tableModel);

		JTableHeader header = tabla.getTableHeader();
		header.setBackground(new Color(165, 231, 135));
		header.setForeground(Color.BLACK);
		header.setFont(header.getFont().deriveFont(Font.BOLD));
		header.setReorderingAllowed(false);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.setDefaultRenderer(Object.class, cellRenderer);

		JScrollPane scrollPane = new JScrollPane(tabla);
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		JButton btn1 = new JButton("ESPECIALISTA CON MAYOR NUMERO DE CITAS");
		btn1.setPreferredSize(new Dimension(300, 60));
		btn1.setActionCommand("ESPECIALISTA_CON_MAYOR_NUMERO_DE_CITAS");
		btn1.addActionListener(listener);
		panelBotones.add(btn1);

		JButton btn2 = new JButton("ESPECIALIDADES DE MAYOR CONSULTA");
		btn2.setPreferredSize(new Dimension(300, 60));
		btn2.setActionCommand("ESPECIALIDADES_DE_MAYOR_CONSULTA");
		btn2.addActionListener(listener);
		panelBotones.add(btn2);

		JButton btn3 = new JButton("REPORTE MENSUAL");
		btn3.setPreferredSize(new Dimension(300, 60));
		btn3.setActionCommand("REPORTE_MENSUAL");
		btn3.addActionListener(listener);
		panelBotones.add(btn3);

		this.add(panelBotones, BorderLayout.SOUTH);
	}

	// Cargar datos en la tabla con información de cancelación de citas
	public void cargarDatos(List<ReporteDTO> reportes) {
	    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    model.setRowCount(0); 

	    for (ReporteDTO reporte : reportes) {
	        Object[] row = {
	            reporte.getNombrePaciente().getNombre(),
	            reporte.getNombreEspecialista().getNombre(),
	            reporte.getDiagnostico().getDescripcion(),
	            reporte.isCancelacionCita() ? "Sí" : "No"  
	        };
	        model.addRow(row);
	    }
	}

	public void mostrarEspecialidadesMayorConsultaDialog(List<String> topEspecialidades) {
		StringBuilder mensaje = new StringBuilder("Top 5 Especialidades con Mayor Número de Consultas:\n\n");
		for (int i = 0; i < topEspecialidades.size(); i++) {
			mensaje.append(i + 1).append(". ").append(topEspecialidades.get(i)).append("\n");
		}

		JOptionPane.showMessageDialog(this, mensaje.toString(), "Especialidades con Mayor Número de Consultas",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void mostrarEspecialistaConMayorNumeroDeCitas(List<String> especialista) {
		StringBuilder mensaje = new StringBuilder("Especialista con mayor número de citas: \n\n");
		for (int i = 0; i < especialista.size(); i++) {
			mensaje.append(i + 1).append(". ").append(especialista.get(i)).append("\n");
		}
		JOptionPane.showMessageDialog(this, mensaje.toString(), "Especialista con mayor número de citas",
				JOptionPane.INFORMATION_MESSAGE);
	}
}