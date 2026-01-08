package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import co.edu.unbosque.controller.Controller;
import co.edu.unbosque.model.CitaDTO;
import co.edu.unbosque.model.EspecialistaDTO;
import co.edu.unbosque.model.PacienteDTO;
import co.edu.unbosque.model.TurnoDTO;

/**
 * Ventana Principal de la aplicacion
 * @author team
 *
 */
public class VentanaPrincipal extends JFrame {
	private BotonesVentanaPrincipal bvp;
	private JPanel panelCentral;
	private CardLayout cardLayout;
	private PanelTablaTurno tablaTurnos;
	private PanelGestionTurno gestionTurnos;
	private PanelCita panelCita;
	private PanelGestionTratamiento gestionTratamientos;
	private PanelReporte reportes;
	private InicioUsuario inicioUsuario;
	private EleccionRegistro eleccionRegistro;
	private RegistroEspecialistaPanel registroEspecialista;
	private PanelRegistrarPaciente registroPaciente;
	private JDialog dialogoRegistroEspecialista;
	private JDialog dialogoRegistroPaciente;
	private PanelAsignarExamen asiganrExamen;

	public VentanaPrincipal(int ancho, int alto, Controller listener) {
		this.setSize(ancho, alto);
		this.setTitle("Bosque Health");
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eleccionRegistro = new EleccionRegistro();

		mostrarLogin(listener);
		this.setVisible(true);
		reportes = new PanelReporte(listener);
	}

	private void mostrarLogin(Controller listener) {
		inicioUsuario = new InicioUsuario(listener);
		this.setLayout(new BorderLayout());
		this.add(inicioUsuario, BorderLayout.CENTER);
	}

	public void mostrarVistaPrincipal(Controller listener) {
		// Remover el login si está presente
		if (inicioUsuario != null) {
			this.remove(inicioUsuario);
			inicioUsuario = null;
		}
		iniciarComponentes(listener);

		this.revalidate();
		this.repaint();
	}

	private void iniciarComponentes(Controller listener) {
		this.setLayout(new BorderLayout());

		bvp = new BotonesVentanaPrincipal(listener);
		bvp.setBackground(new Color(209, 209, 209));
		this.add(bvp, BorderLayout.WEST);

		JPanel panelTitulo = new JPanel();
		JLabel labelTitulo = new JLabel("Bosque Health");
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));
		panelTitulo.setBackground(new Color(165, 231, 135));
		panelTitulo.add(labelTitulo);
		this.add(panelTitulo, BorderLayout.NORTH);

		panelCentral = new JPanel();
		cardLayout = new CardLayout();
		panelCentral.setLayout(cardLayout);

		FondoPanel fondoPanel = new FondoPanel("img/menu.jpg");
		fondoPanel.setLayout(new BorderLayout());

		// Agregar el fondoPanel al card layout
		panelCentral.add(fondoPanel, "MENU");

		gestionTurnos = new PanelGestionTurno(listener);
		panelCita = new PanelCita(listener);
		gestionTratamientos = new PanelGestionTratamiento(listener);
		reportes = new PanelReporte(listener);
		asiganrExamen = new PanelAsignarExamen(listener);

		panelCentral.add(gestionTurnos, "GESTION_TURNOS");
		panelCentral.add(panelCita, "GESTION_CITAS");
		panelCentral.add(gestionTratamientos, "GESTION_TRATAMIENTOS");
		if (listener.esDirector) {
			panelCentral.add(reportes, "REPORTES");
		}

		this.add(panelCentral, BorderLayout.CENTER);
	}

	public void cargarEspecialistas(List<EspecialistaDTO> datos) {
		gestionTurnos.setEspecialistas(datos);
		panelCita.setEspecialistas(datos);
		asiganrExamen.actualizarEspecialistas(datos);
	}

	public void cargarPacientes(List<PacienteDTO> pacientes) {
		System.out.println("Pacientes cargados en la interfaz: " + pacientes.size());
		panelCita.actualizarPacientes(pacientes);
		gestionTratamientos.actualizarPacientes(pacientes);
		asiganrExamen.actualizarPacientes(pacientes);
	}

	public void mostrarPanel(String panelName) {
		cardLayout.show(panelCentral, panelName);
	}

	private void mostrarDialogo(JPanel panel) {
		JDialog dialog = new JDialog(this, null, true);
		dialog.setSize(700, 550);
		dialog.setLocationRelativeTo(this);
		dialog.add(panel);
		dialog.setVisible(true);
	}

	public void mostrarPanelAsignarExamenDialog() {
		mostrarDialogo(asiganrExamen);
	}

	public void mostrarPanelAsignarExamen(ActionListener listener) {
		PanelAsignarExamen panelAsignarExamen = new PanelAsignarExamen(listener);
		mostrarDialogo(panelAsignarExamen);
	}
	
	public void mostrarPanelReporteMensual(ActionListener listener, List<String> especialistas, List<String> turnos) {
     	PanelReporteMensual panelReporteMensual = new PanelReporteMensual(listener);
		panelReporteMensual.actualizarTabla(especialistas, turnos);
		JDialog dialog = new JDialog(this, "Reporte Mensual de Turnos", true);
		dialog.setSize(500, 400);
		dialog.setLocationRelativeTo(this);
		dialog.add(panelReporteMensual);
		dialog.setVisible(true);

	}

	public void mostrarPanelReporteMensual(ActionListener listener) {
		PanelReporteMensual panelReporteMensual = new PanelReporteMensual(listener);

		mostrarDialogo(panelReporteMensual);
	}

	public void mostrarPanelAsignarExamenDialog(ActionListener listener) {
		PanelAsignarExamen panelAsignarExamen = new PanelAsignarExamen(listener);
		JDialog dialog = new JDialog(this, "Asignar Examen", true);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(this);
		dialog.add(panelAsignarExamen);
		dialog.setVisible(true);
	}

	public void cargarTurnos(List<TurnoDTO> turnos) {
		gestionTurnos.cargarTurnosTabla(turnos);
	}

	public PanelCita getPanelCita() {
		return panelCita;
	}

	public InicioUsuario getInicioUsuario() {
		return inicioUsuario;
	}

	public void mostrarDialogoEleccionRegistro() {
		JDialog dialog = new JDialog(this, "Seleccione el tipo de usuario", true);
		dialog.setSize(600, 150);
		dialog.setLocationRelativeTo(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.add(eleccionRegistro);
		dialog.setVisible(true);
	}

	// Método para registrar el listener en el panel EleccionRegistro
	public void registrarListenerEleccionRegistro(ActionListener listener) {
		eleccionRegistro.getEspecialistaButton().addActionListener(listener);
		eleccionRegistro.getPacienteButton().addActionListener(listener);
	}

	public void mostrarRegistroEspecialistaPanel() {

		if (panelCentral != null) {
			panelCentral.removeAll();
			panelCentral.add(registroEspecialista);
			panelCentral.revalidate();
			panelCentral.repaint();
		} else {
			System.err.println("Error: panelCentral no está inicializado.");
		}
	}

	// Registra el listener en los botones de RegistroEspecialistaPanel
	public void registrarListenerRegistroEspecialista(ActionListener listener) {
		registroEspecialista.getRegistrarseButton().addActionListener(listener);
		registroEspecialista.getVolverButton().addActionListener(listener);
	}

	// Método para mostrar el diálogo de Elección de Registro
	public void mostrarDialogoEleccionRegistro(ActionListener listener) {
		eleccionRegistro.getEspecialistaButton().addActionListener(listener);
		eleccionRegistro.getPacienteButton().addActionListener(listener);

		JDialog dialog = new JDialog(this, "Seleccione el tipo de usuario", true);
		dialog.setSize(600, 150);
		dialog.setLocationRelativeTo(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.add(eleccionRegistro);
		dialog.setVisible(true);
	}

	public RegistroEspecialistaPanel getRegistroEspecialistaPanel() {
		return registroEspecialista;
	}

	public PanelRegistrarPaciente getRegistroPacientePanel() {
		if (registroPaciente == null) {
			System.err.println("El panel de registro de paciente no ha sido inicializado.");
		}
		return registroPaciente;
	}

	public void mostrarPanelRegistroEspecialista(ActionListener listener) {
		if (registroEspecialista == null) {
			registroEspecialista = new RegistroEspecialistaPanel(listener);
		}
		if (dialogoRegistroEspecialista == null) {
			dialogoRegistroEspecialista = new JDialog(this, "Registro Especialista", true);
			dialogoRegistroEspecialista.setSize(700, 500);
			dialogoRegistroEspecialista.setLocationRelativeTo(this);
			dialogoRegistroEspecialista.add(registroEspecialista);
		}
		dialogoRegistroEspecialista.setVisible(true);
	}

	// Método para mostrar el registro de paciente
	public void mostrarPanelRegistroPaciente(ActionListener listener) {
		if (registroPaciente == null) {
			registroPaciente = new PanelRegistrarPaciente(listener);
		}
		if (dialogoRegistroPaciente == null) {
			dialogoRegistroPaciente = new JDialog(this, "Registro Paciente", true);
			dialogoRegistroPaciente.setSize(700, 500);
			dialogoRegistroPaciente.setLocationRelativeTo(this);
			dialogoRegistroPaciente.add(registroPaciente);
		}
		dialogoRegistroPaciente.setVisible(true);
	}

	// Métodos para cerrar los diálogos
	public void cerrarDialogoRegistroEspecialista() {
		if (dialogoRegistroEspecialista != null) {
			dialogoRegistroEspecialista.dispose();
		}
	}

	public void cerrarDialogoRegistroPaciente() {
		if (dialogoRegistroPaciente != null) {
			dialogoRegistroPaciente.dispose();
		}
	}

	public void mostrarPanelEditarCita(ActionListener listener) {
		PanelEditarCita panelEditarCita = new PanelEditarCita(listener);
		mostrarDialogo(panelEditarCita);
	}

	public void mostrarPanelCancelarCita(ActionListener listener) {
		PanelCancelarCita panelCancelarCita = new PanelCancelarCita(listener);
		mostrarDialogo(panelCancelarCita);
	}

	public PanelGestionTratamiento getPanelTratamiento() {
		return gestionTratamientos;
	}

	public PanelAsignarExamen getAsignarExamen() {
		return asiganrExamen;
	}

	// guardar cita
	public Object[] obtenerDatosCita() {
		PanelCita panelCita = getPanelCita();

		String idText = panelCita.getTxtId().getText().trim();
		if (idText.equals("") || idText == null) {
			idText = "0";
		}

		PacienteDTO pacienteSeleccionado = (PacienteDTO) panelCita.getComboPaciente().getSelectedItem();
		EspecialistaDTO especialistaSeleccionado = (EspecialistaDTO) panelCita.getComboEspecialista().getSelectedItem();
		String fechaSeleccionada = panelCita.getFechaSeleccionada();
		String correoPaciente = panelCita.getCorreoPaciente();

		if (pacienteSeleccionado == null || especialistaSeleccionado == null || fechaSeleccionada == null
				|| correoPaciente.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return new Object[] { Integer.parseInt(idText), pacienteSeleccionado, especialistaSeleccionado,
				fechaSeleccionada, correoPaciente };
	}

	public String obtenerIdCita() {
		PanelCita panelCita = getPanelCita();

		return panelCita.getTxtId().getText().trim();
	}

	// tabla citas
	public void cargarCitas(List<CitaDTO> citas) {
		panelCita.setCitas(citas);
	}

	// tabla citas
	public void cargarCitasTabla(List<CitaDTO> citas) {
		panelCita.actualizarTablaCitas(citas);
	}

	public void cargarCita(CitaDTO cita) {
		panelCita.cargarCita(cita);
	}

	public PanelReporte getPanelReporte() {
		return reportes;
	}
	public void VolverAlLogin(Controller listener) {
	    this.getContentPane().removeAll();
	    this.setLayout(new BorderLayout());

	    inicioUsuario = new InicioUsuario(listener);
	    this.add(inicioUsuario, BorderLayout.CENTER);
	    this.revalidate();
	    this.repaint();
	}

}
