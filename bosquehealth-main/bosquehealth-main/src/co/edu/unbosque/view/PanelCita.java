package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdesktop.swingx.JXDatePicker;

import co.edu.unbosque.controller.Controller;
import co.edu.unbosque.model.CitaDTO;
import co.edu.unbosque.model.EspecialistaDTO;
import co.edu.unbosque.model.PacienteDTO;
import co.edu.unbosque.model.TurnoDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanelCita extends JPanel implements ActionListener, MouseListener {
	private JTextField txtId;
	private JComboBox<String> comboEspecialidad;
	private JComboBox<EspecialistaDTO> comboEspecialista;
	private JComboBox<PacienteDTO> comboPaciente;
	private JComboBox<String> datesCombo;
	private JTextField txtCorreo;
	private PanelBotonCita pbc;
	private PanelCancelarCita pcc;
	private PanelEditarCita pec;
	private JTable tablaCitas;
	private Controller controller;

	private List<CitaDTO> citasMemoria;

	private List<EspecialistaDTO> especialistas = new ArrayList<>();

	public PanelCita(Controller listener) {
		controller = listener;
		pbc = new PanelBotonCita(listener);
		initComponents();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Línea superior
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

		// Título del panel
		JLabel titulo = new JLabel("GESTION CITAS", JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridy = 1;
		add(titulo, gbc);

		// Línea inferior debajo del título
		gbc.gridy = 2;
		add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

		// ID debajo de la línea inferior del título
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblId, gbc);

		txtId = new JTextField(20);
		txtId.setFont(new Font("Arial", Font.PLAIN, 18));
		txtId.setPreferredSize(new Dimension(200, 30));
		txtId.setEnabled(false);
		gbc.gridx = 1;
		add(txtId, gbc);

		// Etiqueta y lista para Paciente
		gbc.gridy = 4;
		gbc.gridx = 0;
		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblPaciente, gbc);

		comboPaciente = new JComboBox<>();
		comboPaciente.setActionCommand("COMBO_PACIENTES");
		comboPaciente.addActionListener(this);
		comboPaciente.setFont(new Font("Arial", Font.PLAIN, 18));
		comboPaciente.setPreferredSize(new Dimension(200, 30));
		gbc.gridx = 1;
		add(comboPaciente, gbc);

		// Especialidad
		gbc.gridx = 0;
		gbc.gridy = 5;
		JLabel lblEspecialidad = new JLabel("Especialidad:");
		lblEspecialidad.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblEspecialidad, gbc);

		comboEspecialidad = new JComboBox<>(new String[] { "Cirugía", "Oncología", "Dermatología", "Neumología",
				"Cardiología", "Medicina Interna" });
		comboEspecialidad.setFont(new Font("Arial", Font.PLAIN, 18));
		comboEspecialidad.setPreferredSize(new Dimension(200, 30));
		comboEspecialidad.setActionCommand("COMBO_ESPECIALIDADES");
		comboEspecialidad.addActionListener(this);
		gbc.gridx = 1;
		add(comboEspecialidad, gbc);

		// Especialista
		gbc.gridx = 0;
		gbc.gridy = 6;
		JLabel lblEspecialista = new JLabel("Especialista:");
		lblEspecialista.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblEspecialista, gbc);

		comboEspecialista = new JComboBox<>();
		comboEspecialista.setFont(new Font("Arial", Font.PLAIN, 18));
		comboEspecialista.setPreferredSize(new Dimension(200, 30));
		comboEspecialista.setActionCommand("COMBO_ESPECIALISTA");
		comboEspecialista.addActionListener(this);
		gbc.gridx = 1;
		add(comboEspecialista, gbc);

		// Fecha
		gbc.gridx = 0;
		gbc.gridy = 7;
		JLabel lblFecha = new JLabel("Fecha (DD-MM-YYYY):");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblFecha, gbc);

		datesCombo = new JComboBox<>();
		datesCombo.setFont(new Font("Arial", Font.PLAIN, 18));
		datesCombo.setPreferredSize(new Dimension(200, 30));
		gbc.gridx = 1;
		add(datesCombo, gbc);

		// Correo
		gbc.gridx = 0;
		gbc.gridy = 8;
		JLabel lblCorreo = new JLabel("Correo Paciente:");
		lblCorreo.setFont(new Font("Arial", Font.PLAIN, 18));
		add(lblCorreo, gbc);

		txtCorreo = new JTextField(20);
		txtCorreo.setFont(new Font("Arial", Font.PLAIN, 18));
		txtCorreo.setPreferredSize(new Dimension(200, 30));
		gbc.gridx = 1;
		add(txtCorreo, gbc);

		// Panel de botones
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		add(pbc, gbc);

		// tabla
		gbc.gridy = 10;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		JPanel panelTabla = crearPanelTabla();
		add(panelTabla, gbc);
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public JComboBox<String> getComboEspecialidad() {
		return comboEspecialidad;
	}

	public JComboBox<EspecialistaDTO> getComboEspecialista() {
		return comboEspecialista;
	}

	public JComboBox<PacienteDTO> getComboPaciente() {
		return comboPaciente;
	}


	public JTextField getTxtCorreo() {
		return txtCorreo;
	}

	public PanelBotonCita getPbc() {
		return pbc;
	}

	public List<EspecialistaDTO> getEspecialistas() {
		return especialistas;
	}

	public void actualizarPacientes(List<PacienteDTO> pacientes) {
		if (pacientes != null) {
			comboPaciente.removeAllItems();
			for (PacienteDTO p : pacientes) {
				comboPaciente.addItem(p);
			}
		}
	}

	// Método para establecer la lista de especialistas
	public void setEspecialistas(List<EspecialistaDTO> especialistas) {
		this.especialistas = especialistas;
	}

	private void actualizarEspecialistas(String especialidad) {
		comboEspecialista.removeAllItems();
		for (EspecialistaDTO especialista : especialistas) {
			if (especialista.getEspecialidad().equalsIgnoreCase(especialidad)) {
				comboEspecialista.addItem(especialista);
			}
		}
	}

	public String getPacienteSeleccionado() {
		return (String) comboPaciente.getSelectedItem();
	}

	// Método para obtener la especialidad seleccionada
	public String getEspecialidadSeleccionada() {
		return (String) comboEspecialidad.getSelectedItem();
	}

	// Método para obtener el especialista seleccionado
	public EspecialistaDTO getEspecialistaSeleccionado() {
		return (EspecialistaDTO) comboEspecialista.getSelectedItem();
	}

	// Método para obtener la fecha seleccionada
	public String getFechaSeleccionada() {
		return (String) datesCombo.getSelectedItem();
	}

	// Método para obtener el correo del paciente
	public String getCorreoPaciente() {
		return txtCorreo.getText();

	}

	public PanelCancelarCita getPcc() {
		return pcc;
	}

	public PanelEditarCita getPec() {
		return pec;
	}
	
	public void setCitas(List<CitaDTO> citas) {
		citasMemoria = citas;
	}

	private JPanel crearPanelTabla() {
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
		String[] columnas = { "ID", "Paciente", "Especialidad", "Especialista", "Fecha", "Correo", "Estado" };
		DefaultTableModel estructuraTabla = new DefaultTableModel(columnas, 0);

		// Inicializa tablaCitas con el modelo de datos
		tablaCitas = new JTable(estructuraTabla); // Ahora es un atributo de clase
		JTableHeader header = tablaCitas.getTableHeader();
		tablaCitas.addMouseListener(this);
		header.setBackground(new Color(165, 231, 135));
		header.setForeground(Color.BLACK);
		header.setFont(header.getFont().deriveFont(Font.BOLD));

		// Centrar el contenido de las celdas
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tablaCitas.setDefaultRenderer(Object.class, cellRenderer);

		// Configuración adicional de la tabla
		tablaCitas.setPreferredScrollableViewportSize(new Dimension(500, 150));
		tablaCitas.getTableHeader().setReorderingAllowed(false);

		// Agregar la tabla a un JScrollPane para permitir desplazamiento
		JScrollPane scrollPane = new JScrollPane(tablaCitas);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		panelTabla.add(scrollPane);
		return panelTabla;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if ("COMBO_PACIENTES".equals(e.getActionCommand())) {
	            PacienteDTO paciente = (PacienteDTO) comboPaciente.getSelectedItem();
	            if (paciente != null) { 
	                txtCorreo.setText(paciente.getEmail());
	                if (citasMemoria != null) {
	                    actualizarTablaCitas(
	                        citasMemoria.stream()
	                            .filter(cita -> cita.getPacientedto() != null &&
	                                    cita.getPacientedto().getIdentificacion() == paciente.getIdentificacion())
	                            .toList()
	                    );
	                }
	            }
	        }

	        if ("COMBO_ESPECIALIDADES".equals(e.getActionCommand())) {
	            String especialidadSeleccionada = (String) comboEspecialidad.getSelectedItem();
	            if (especialidadSeleccionada != null) {
	                actualizarEspecialistas(especialidadSeleccionada);
	            }
	        }

	        if ("COMBO_ESPECIALISTA".equals(e.getActionCommand())) {
	            EspecialistaDTO especialista = (EspecialistaDTO) comboEspecialista.getSelectedItem();
	            if (especialista != null) { 
	                datesCombo.removeAllItems();
	                List<TurnoDTO> turnos = controller.getTurnos();
	                for (TurnoDTO t : turnos) {
	                    if (t.getEspecialista() != null && 
	                        t.getEspecialista().getIdentificacion() == especialista.getIdentificacion()) {
	                        datesCombo.addItem(t.getFechaInicioTurnoString());
	                    }
	                }
	            }
	        }
	    }


	public void actualizarTablaCitas(List<CitaDTO> citas) {
		DefaultTableModel modeloTabla = (DefaultTableModel) tablaCitas.getModel();
		modeloTabla.setRowCount(0);

		for (CitaDTO cita : citas) {
			cargarCita(cita);
		}
	}

	public void cargarCita(CitaDTO cita) {
		DefaultTableModel modeloTabla = (DefaultTableModel) tablaCitas.getModel();
		String nombrePaciente = cita.getPacientedto() != null ? cita.getPacientedto().getNombre() : "N/A";
		String especialidad = cita.getEspecialistadto() != null ? cita.getEspecialistadto().getEspecialidad() : "N/A";
		String nombreEspecialista = cita.getEspecialistadto() != null ? cita.getEspecialistadto().getNombre() : "N/A";
		String fechaCita = cita.getFechaCita() != null ? cita.getFechaCitaString() : "N/A";
		String correo = cita.getCorreo() != null ? cita.getCorreo() : "N/A";
		String estado = cita.getEstado() != null ? cita.getEstado() : "";

		modeloTabla.addRow(
				new Object[] { cita.getIdCita(), nombrePaciente, especialidad, nombreEspecialista, fechaCita, correo, estado });
	}

	

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
		int fila = tablaCitas.rowAtPoint(e.getPoint());

        if (fila >= 0) {
        	
        	int id = (int) tablaCitas.getModel().getValueAt(fila, 0);
        	String nombrePaciente = (String) tablaCitas.getModel().getValueAt(fila, 1);
        	String especialidad = (String) tablaCitas.getModel().getValueAt(fila, 2);
        	String nombreEspecialista = (String) tablaCitas.getModel().getValueAt(fila, 3);
        	String fechaCita = (String) tablaCitas.getModel().getValueAt(fila, 4);
        	String correo = (String) tablaCitas.getModel().getValueAt(fila, 5);
        	for(int i =0 ; i< comboEspecialista.getModel().getSize(); i++) {
        		EspecialistaDTO ep = (EspecialistaDTO) comboEspecialista.getItemAt(i);
        		if(ep.getNombre().equals(nombreEspecialista)) {
        			comboEspecialista.setSelectedIndex(i);
        			break;
        		}					
        	}
        	txtId.setText(String.valueOf(id));
        	txtCorreo.setText(correo);
        	datesCombo.setSelectedItem(fechaCita);
        
        }
        
		}catch(IndexOutOfBoundsException error) {
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}