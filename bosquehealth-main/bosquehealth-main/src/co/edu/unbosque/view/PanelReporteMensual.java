package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelReporteMensual extends JPanel {

	private JTable tablaTurnos;
	private DefaultTableModel estructura;

	public PanelReporteMensual(ActionListener listener) {
		setLayout(new BorderLayout());

		String[] columnas = { "ESPECIALISTA", "NUMERO DE TURNOS" };
		estructura = new DefaultTableModel(columnas, 0);
		tablaTurnos = new JTable(estructura);

		JScrollPane scrollPane = new JScrollPane(tablaTurnos);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void actualizarTabla(List<String> especialistas, List<String> turnos) {
    	estructura.setRowCount(0);
    	for (int i = 0; i < especialistas.size(); i++) {
            String especialista = especialistas.get(i);
            String numeroTurnos = turnos.get(i);

            Object[] rowData = { especialista, numeroTurnos };
            estructura.addRow(rowData);
    
    }
	}   	
}