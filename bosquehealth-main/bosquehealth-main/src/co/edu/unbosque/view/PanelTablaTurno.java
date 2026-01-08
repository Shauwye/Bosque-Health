package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import co.edu.unbosque.model.CitaDTO;
import co.edu.unbosque.model.TurnoDTO;

public class PanelTablaTurno extends JPanel {

	private JTable tablaTurnos;
	private DefaultTableModel estructura;

	public PanelTablaTurno() {
		this.setLayout(new BorderLayout());

		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout());

		String[] columnas = { "TURNO", "FECHA", "ESPECIALISTA", "PACIENTE", "EXAMEN" };
		estructura = new DefaultTableModel(columnas, 0);
		tablaTurnos = new JTable(estructura);

		JScrollPane scrollPane = new JScrollPane(tablaTurnos);
		panelTabla.add(scrollPane, BorderLayout.CENTER);
		this.add(panelTabla, BorderLayout.CENTER);
	}

	public void actualizarTabla(List<TurnoDTO> turnos, List<CitaDTO> citas) {
		estructura.setRowCount(0); 

		for (TurnoDTO turno : turnos) {
			String especialistaNombre = turno.getEspecialista().getNombre();
			CitaDTO citaCorrespondiente = citas.stream()
					.filter(cita -> cita.getEspecialistadto().equals(turno.getEspecialista())).findFirst().orElse(null);
			String paciente = (citaCorrespondiente != null) ? citaCorrespondiente.getPacientedto().getNombre()
					: "Sin cita";
			String correoPaciente = (citaCorrespondiente != null) ? citaCorrespondiente.getCorreo() : "N/A";
			Object[] rowData = { turno.getIdTurno(), turno.getFechaInicioTurno(), especialistaNombre, paciente,
					correoPaciente };
			estructura.addRow(rowData);
		}
	}
}