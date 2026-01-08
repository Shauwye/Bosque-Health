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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.management.loading.PrivateClassLoader;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import co.edu.unbosque.model.TurnoDTO;

public class PanelGestionTurno extends JPanel implements ActionListener, MouseListener {
	private JTextField textTurnoAuto;
	private JComboBox<EspecialistaDTO> comboBoxEspecialistas;
	private JComboBox<String> comboBoxJornada;
	private JComboBox<String> comboBoxEspecialidades;
	private JXDatePicker datePicker;
	private Controller controller;
	private EspecialistaDTO especialistaSeleccionado;
	private String jornadaSeleccionada;
	private Date fechaSeleccionada;

	List<EspecialistaDTO> especialistas;

	private JTable tablaTurnos;
	private DefaultTableModel estructuraTabla;

	public PanelGestionTurno(Controller listener) {
		setLayout(new BorderLayout());

		JPanel panelGestion = crearPanelGestion(listener);
		JPanel panelTabla = crearPanelTabla();

		this.add(panelGestion, BorderLayout.NORTH);
		this.add(panelTabla, BorderLayout.CENTER);
	}

	private JPanel crearPanelGestion(Controller listener) {
		JPanel panelGestion = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);

		controller = listener;

		// Línea superior y título ajustado
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelGestion.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

		JLabel tituloGestion = new JLabel("GESTION DE TURNOS", JLabel.CENTER);
		tituloGestion.setFont(new Font("Arial", Font.BOLD, 14));
		gbc.gridy = 1;
		panelGestion.add(tituloGestion, gbc);

		gbc.gridy = 2;
		panelGestion.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
		gbc.gridwidth = 1;

		gbc.gridy = 3;
		panelGestion.add(Box.createVerticalStrut(5), gbc);

		// Especialidad
		JLabel lblEspecialidad = new JLabel("Especialidad:");
		lblEspecialidad.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 4;
		panelGestion.add(lblEspecialidad, gbc);

		comboBoxEspecialidades = new JComboBox<>(new String[] { "Cirugía", "Oncología", "Dermatología", "Neumología",
				"Cardiología", "Medicina Interna" });
		comboBoxEspecialidades.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxEspecialidades.setPreferredSize(new Dimension(250, 30));
		comboBoxEspecialidades.setActionCommand("COMBO_ESPECIALIDADES");
		comboBoxEspecialidades.addActionListener(this);
		gbc.gridx = 1;
		panelGestion.add(comboBoxEspecialidades, gbc);

		// Especialista
		comboBoxEspecialistas = new JComboBox<>();
		agregarEtiquetaYComboBox(gbc, panelGestion, "Especialista:", comboBoxEspecialistas, 5);

		// Disponibilidad del especialista
		JLabel lblDispEspecialista = new JLabel("Disponibilidad Especialista:");
		lblDispEspecialista.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 6;
		panelGestion.add(lblDispEspecialista, gbc);

		datePicker = new JXDatePicker();
		datePicker.setDate(Calendar.getInstance().getTime());
		datePicker.setFormats("dd/MM/yyyy");
		datePicker.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 1;
		panelGestion.add(datePicker, gbc);

		// Turno
		comboBoxJornada = new JComboBox<>(new String[] { "Mañana", "Tarde", "Noche" });
		agregarEtiquetaYComboBox(gbc, panelGestion, "Elegir Turno:", comboBoxJornada, 7);

		// Botón de turno automático
		JLabel lblTurnoAuto = new JLabel("Turno Automáticamente:");
		lblTurnoAuto.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = 8;
		panelGestion.add(lblTurnoAuto, gbc);

		JButton btnTurnoAuto = new JButton("Generar turno automatico");
		btnTurnoAuto.setActionCommand("GENERAR_TURNO");
		btnTurnoAuto.addActionListener(this);
		btnTurnoAuto.setFont(new Font("Arial", Font.BOLD, 14));
		btnTurnoAuto.setPreferredSize(new Dimension(200, 35));
		gbc.gridx = 1;
		panelGestion.add(btnTurnoAuto, gbc);

		agregarBotones(gbc, panelGestion, listener);

		return panelGestion;
	}

	private void agregarEtiquetaYComboBox(GridBagConstraints gbc, JPanel panel, String texto, JComboBox comboBox,
			int y) {
		JLabel etiqueta = new JLabel(texto);
		etiqueta.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridx = 0;
		gbc.gridy = y;
		panel.add(etiqueta, gbc);

		comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 1;
		panel.add(comboBox, gbc);
	}

	private void agregarBotones(GridBagConstraints gbc, JPanel panel, ActionListener listener) {
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton btnAsignarTurno = new JButton("ASIGNAR TURNO");
		btnAsignarTurno.setFont(new Font("Arial", Font.BOLD, 14));
		btnAsignarTurno.setPreferredSize(new Dimension(180, 35));
		btnAsignarTurno.setActionCommand("ASIGNAR_TURNO");
		btnAsignarTurno.addActionListener(this);

		JButton btnCambiarTurno = new JButton("CAMBIAR TURNO");
		btnCambiarTurno.setFont(new Font("Arial", Font.BOLD, 14));
		btnCambiarTurno.setPreferredSize(new Dimension(180, 35));
		btnCambiarTurno.setActionCommand("MOSTRAR_CAMBIAR_TURNO");
		btnCambiarTurno.addActionListener(this);

		panelBotones.add(btnAsignarTurno);
		panelBotones.add(btnCambiarTurno);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.SOUTH;
		panel.add(panelBotones, gbc);
	}

	private JPanel crearPanelTabla() {
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
		String[] columnas = { "TURNO", "FECHA", "ESPECIALISTA", "CAMBIO_SOLICITADO" };
		estructuraTabla = new DefaultTableModel(columnas, 0);
		tablaTurnos = new JTable(estructuraTabla);

		JTableHeader header = tablaTurnos.getTableHeader();
		header.setBackground(new Color(165, 231, 135));
		header.setForeground(Color.BLACK);
		header.setFont(header.getFont().deriveFont(Font.BOLD));

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tablaTurnos.setDefaultRenderer(Object.class, cellRenderer);

		tablaTurnos.setPreferredScrollableViewportSize(new Dimension(500, 150));
		tablaTurnos.getTableHeader().setReorderingAllowed(false);
		tablaTurnos.addMouseListener(this);
		JScrollPane scrollPane = new JScrollPane(tablaTurnos);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		panelTabla.add(scrollPane);

		return panelTabla;
	}

	public void setEspecialistas(List<EspecialistaDTO> especialistas) {
		this.especialistas = especialistas;
	}

	public void actualizarTabla(List<TurnoDTO> turnos, List<CitaDTO> citas) {
		estructuraTabla.setRowCount(0);

		for (TurnoDTO turno : turnos) {
			String especialistaNombre = turno.getEspecialista().getNombre();
			CitaDTO citaCorrespondiente = citas.stream()
					.filter(cita -> cita.getEspecialistadto().equals(turno.getEspecialista())).findFirst().orElse(null);
			String paciente = (citaCorrespondiente != null) ? citaCorrespondiente.getPacientedto().getNombre()
					: "Sin cita";
			String correo = (citaCorrespondiente != null) ? citaCorrespondiente.getCorreo() : "N/A";
			String especialistaCambio = (turno.getEspecialistaCambio() != null)
					? turno.getEspecialistaCambio().getEmail()
					: "";
			Object[] rowData = { turno.getIdTurno(), turno.getFechaInicioTurno(), especialistaNombre,
					especialistaCambio};
			estructuraTabla.addRow(rowData);
		}
	}

	public void cargarTurnosTabla(List<TurnoDTO> turnos) {
		for (TurnoDTO turno : turnos) {
			agregarTurnoTabla(turno);
		}
	}

	public void agregarTurnoTabla(TurnoDTO turno) {
		if (turno != null) {
			String especialistaNombre = turno.getEspecialista().getNombre();
			String especialistaCambio = (turno.getEspecialistaCambio() != null)
					? turno.getEspecialistaCambio().getEmail()
					: "";
			Object[] rowData = { turno.getIdTurno(), turno.getFechaInicioTurno(), especialistaNombre,
					especialistaCambio };
			estructuraTabla.addRow(rowData);
		}
	}

	public void leerComponentes() {
		especialistaSeleccionado = (EspecialistaDTO) comboBoxEspecialistas.getSelectedItem();
		fechaSeleccionada = datePicker.getDate();
		jornadaSeleccionada = (String) comboBoxJornada.getSelectedItem();
	}

	public void actualizarEspecialistas(List<EspecialistaDTO> especialistas) {
		comboBoxEspecialistas.removeAllItems();
		for (EspecialistaDTO especialista : especialistas) {
			comboBoxEspecialistas.addItem(especialista);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		leerComponentes();
		TurnoDTO turno = null;
		System.out.println("Evento " + e.getActionCommand());
		switch (e.getActionCommand()) {
		case "GENERAR_TURNO":
			if (especialistaSeleccionado == null) {
				JOptionPane.showMessageDialog(this, "Debe Seleccionar un Especialista");
				break;
			}
			turno = controller.generarTurnoAleatorio(especialistaSeleccionado, jornadaSeleccionada);
			if (turno != null) {
				agregarTurnoTabla(turno);
			}
			break;

		case "ASIGNAR_TURNO":
			if (especialistaSeleccionado == null) {
				JOptionPane.showMessageDialog(this, "Debe Seleccionar un Especialista");
				break;
			}

			turno = controller.guardarTurno(especialistaSeleccionado, fechaSeleccionada, jornadaSeleccionada);
			if (turno != null) {
				agregarTurnoTabla(turno);
			}
			break;
		case "COMBO_ESPECIALIDADES":

			String especialidad = (String) comboBoxEspecialidades.getSelectedItem();

			List<EspecialistaDTO> especialistasXEspecialidad = new ArrayList<EspecialistaDTO>();

			if (especialistas != null) {
				for (EspecialistaDTO especialista : especialistas) {
					if (especialidad.equalsIgnoreCase(especialista.getEspecialidad())) {
						especialistasXEspecialidad.add(especialista);
					}
				}

				actualizarEspecialistas(especialistasXEspecialidad);
			}
			break;

		case "MOSTRAR_CAMBIAR_TURNO":
			PanelCambiarTurno panelCambiarTurno = new PanelCambiarTurno(this);
			List<EspecialistaDTO> especialistasDisponibles = new ArrayList<>();

			if (especialistas != null) {
				if (controller.getUsuario() == null) {
					especialistasDisponibles = especialistas;

				} else {

					String usuario = controller.getUsuario().getNombre();
					System.out.println("usuario =" + usuario);
					for (EspecialistaDTO esp : especialistas) {
						System.out.println("especialista= " + esp.getNombre());
						// Solo carga los especialistas que sean diferentes al logueado
						if (!esp.getNombre().equals(usuario)) {
							especialistasDisponibles.add(esp);
						}
					}
				}
			}

			// Lo convertimos a TurnoCombo para mostrar en el combo solo la fecha pero no
			// perder el id para poder actualizar luego este turno
			List<TurnoCombo> turnos = new ArrayList();
			if (estructuraTabla != null) {
				Vector vector = estructuraTabla.getDataVector();
				for (Object obj : vector) {
					Vector row = (Vector) obj;

					String nombreEsp = (String) row.get(2);

					// Solo carga los turnos del especialista logueado
					if (controller.getUsuario() != null && nombreEsp.equals(controller.getUsuario().getNombre())) {
						turnos.add(new TurnoCombo((int) row.get(0), (LocalDateTime) row.get(1)));
					} else {
						turnos.add(new TurnoCombo((int) row.get(0), (LocalDateTime) row.get(1)));
					}
				}
			}
			panelCambiarTurno.actualizarTurnos(turnos);

			panelCambiarTurno.actualizarEspecialistas(especialistasDisponibles);
			mostrarDialogo(panelCambiarTurno);
			break;
		}
	}

	private void mostrarDialogo(JPanel panel) {
		JDialog dialog = new JDialog();
		dialog.setSize(700, 550);
		dialog.setLocationRelativeTo(this);
		dialog.add(panel);
		dialog.setVisible(true);
	}

	public void solicitarCambioTurno(EspecialistaDTO especialistaSeleccionado, int idTurno) {
		if (estructuraTabla != null) {
			for (int fila = 0; fila < estructuraTabla.getRowCount(); fila++) {
				if ((int) estructuraTabla.getValueAt(fila, 0) == idTurno) {
					estructuraTabla.setValueAt(especialistaSeleccionado.getEmail(), fila, 3);
					controller.solicitarCambioTurno(especialistaSeleccionado, idTurno);
					break;
				}
			}
		}
		JOptionPane.showMessageDialog(this, "Se ha solicitado el cambio de turno,  lo debe confirmar el especialista "
				+ especialistaSeleccionado.getNombre());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Verificar que el clic fue en una celda de la tabla
		int fila = tablaTurnos.rowAtPoint(e.getPoint());
		int columna = tablaTurnos.columnAtPoint(e.getPoint());

		if (fila >= 0 && columna == 3) {

			String email = (String) estructuraTabla.getValueAt(fila, columna);

			// Mostrar cuadro de confirmación
			int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas Aceptar este cambio de turno?",
					"Confirmar cambio", JOptionPane.YES_NO_OPTION);

			// Si el usuario confirma, realiza el cambio en la celda
			if (opcion == JOptionPane.YES_OPTION) {
				int turnoId = (int) estructuraTabla.getValueAt(fila, 0);
				TurnoDTO turno = controller.aceptarCambioTurno(turnoId);
				if (turno != null) {
					JOptionPane.showMessageDialog(this, "Se Ha aceptado la solictud de cambio satisfactoriamente");
					estructuraTabla.setValueAt("", fila, 3);
					estructuraTabla.setValueAt(turno.getEspecialista().getNombre(), fila, 2);
				}
			}
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