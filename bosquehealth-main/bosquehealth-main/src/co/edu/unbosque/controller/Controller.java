package co.edu.unbosque.controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import co.edu.unbosque.model.CitaDTO;
import co.edu.unbosque.model.EspecialistaDTO;
import co.edu.unbosque.model.ExamenDTO;
import co.edu.unbosque.model.GestionClinicaFachada;
import co.edu.unbosque.model.PacienteDTO;
import co.edu.unbosque.model.ReporteDTO;
import co.edu.unbosque.model.TratamientoDTO;
import co.edu.unbosque.model.TurnoDTO;
import co.edu.unbosque.util.exception.ReporteNoGeneradoException;
import co.edu.unbosque.view.VentanaPrincipal;

//pato
public class Controller implements ActionListener {
	private VentanaPrincipal ventanaPrincipal;
	private GestionClinicaFachada fachada;
	private EspecialistaDTO especialistaLogueado;
	public boolean esDirector = false;

	public void run() {
		fachada = new GestionClinicaFachada();
		ventanaPrincipal = new VentanaPrincipal(1600, 914, this);
	}

	private void cargarDatosIniciales() {
		try {
			List<PacienteDTO> pacientes = fachada.obtenerPacientesDTO();
			List<EspecialistaDTO> especialistas = fachada.obtenerEspecialistas();
			List<TurnoDTO> turnos = fachada.obtenerTurnosAlmacenados();

			ventanaPrincipal.cargarPacientes(pacientes);
			ventanaPrincipal.cargarEspecialistas(especialistas);
			ventanaPrincipal.cargarTurnos(turnos);
			cargarCitas();
		} catch (IOException e) {
			System.err.println("Error al cargar datos iniciales: " + e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		try {
			switch (command) {
			case "INGRESAR":
				realizarIngreso();
				break;
			case "REGISTRARSE":
				ventanaPrincipal.mostrarDialogoEleccionRegistro(this);
				break;
			case "PACIENTE":
				ventanaPrincipal.mostrarPanelRegistroPaciente(this);
				break;
			case "ESPECIALISTA":
				ventanaPrincipal.mostrarPanelRegistroEspecialista(this);
				break;
			case "VOLVER_ESPECIALISTA":
				ventanaPrincipal.cerrarDialogoRegistroEspecialista();
				break;
			case "VOLVER_PACIENTE":
				ventanaPrincipal.cerrarDialogoRegistroPaciente();
				break;
			case "REGISTRARSE_ESPECIALISTA":
				registrarEspecialista();
				break;
			case "REGISTRARSE_PACIENTE":
				registrarPacientes();
				break;
			case "GESTION_CITAS":
				ventanaPrincipal.cargarEspecialistas(fachada.obtenerEspecialistas());
				ventanaPrincipal.mostrarPanel("GESTION_CITAS");
				break;
			case "GESTION_TRATAMIENTOS":
				ventanaPrincipal.mostrarPanel("GESTION_TRATAMIENTOS");
				break;
			case "REPORTES":
				ventanaPrincipal.mostrarPanel("REPORTES");
				mostrarReporteEnTabla();
				break;
			case "EDITAR_CITA_COMMAND":
				editarCita();
				break;
			case "AGREGAR_CITA_COMMAND":
				guardarCita();
				break;
			case "CANCELAR_CITA_COMMAND":
				int opcion = JOptionPane.showConfirmDialog(ventanaPrincipal, "¿Deseas Cancelar esta cita?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (opcion == JOptionPane.YES_OPTION) {
					cancelarCita();
				}
				break;
			case "GESTION_TURNOS":
				ventanaPrincipal.mostrarPanel("GESTION_TURNOS");
				break;
			case "REPORTE_MENSUAL":
				reporteMensual();
				break;
			case "TABLA_TURNOS":
				ventanaPrincipal.mostrarPanel("TABLA_TURNOS");
				break;
			case "GUARDAR_TRATAMIENTO":
				guardarTratamiento();
				break;
			case "ASIGNAREXAMEN":
				ventanaPrincipal.mostrarPanelAsignarExamenDialog(this);
				break;
			case "GUARDAR_EXAMEN":
				guardarExamen();
				break;
			case "ASIGNAR_EXAMEN":
				ventanaPrincipal.mostrarPanelAsignarExamenDialog();
				break;
			case "CONSULTAR_TRATAMIENTOS":
				mostrarTratamientosYExamenes();
				break;
			case "ESPECIALIDADES_DE_MAYOR_CONSULTA":
				mostrarEspecialidadesMayorConsulta();
				break;
			case "ESPECIALISTA_CON_MAYOR_NUMERO_DE_CITAS":
				mostrarEspecialistaConMayorNumeroDeCitas();
				break;
			case "REGRESAR_LOGIN":
				ventanaPrincipal.VolverAlLogin(this);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Funcionalidad en desarrollo para: " + command);
				break;
			}
		} catch (Exception error) {
			System.err.println("Error al procesar el comando: " + error.getMessage());
			error.printStackTrace();
		}
	}

	private void reporteMensual() throws IOException {
		List<TurnoDTO> turnos = fachada.obtenerTurnosAlmacenados();
		Map<String, Integer> numeroDeTurnosPorEspecialista = new HashMap<>();

		for (TurnoDTO turno : turnos) {
			String nombreEspecialista = turno.getEspecialista().getNombre();
			numeroDeTurnosPorEspecialista.put(nombreEspecialista,
					numeroDeTurnosPorEspecialista.getOrDefault(nombreEspecialista, 0) + 1);
		}

		List<String> especialistas = new ArrayList<>(numeroDeTurnosPorEspecialista.keySet());
		List<String> cantidadTurnos = especialistas.stream()
				.map(especialista -> numeroDeTurnosPorEspecialista.get(especialista).toString())
				.collect(Collectors.toList());
		
		if (!especialistas.isEmpty() && !cantidadTurnos.isEmpty()) {
			ventanaPrincipal.mostrarPanelReporteMensual(this, especialistas, cantidadTurnos);
		} else {
			JOptionPane.showMessageDialog(ventanaPrincipal, "No hay datos para mostrar en el reporte mensual.",
					"Reporte Mensual", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void mostrarEspecialistaConMayorNumeroDeCitas() {
		try {
			List<String> especialistas = fachada.obtenerEspecialistaConMayorNumeroDeCitas();
			List<String> especialistaTop = especialistas.stream().limit(1).collect(Collectors.toList());
			ventanaPrincipal.getPanelReporte().mostrarEspecialistaConMayorNumeroDeCitas(especialistaTop);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(ventanaPrincipal,
					"Error al obtener las especialistacon mayor número de citas.", "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	public void mostrarEspecialidadesMayorConsulta() {
		try {
			List<String> especialidades = fachada.obtenerEspecialidadesConMayorConsulta();
			List<String> topEspecialidades = especialidades.stream().limit(5).collect(Collectors.toList());

			ventanaPrincipal.getPanelReporte().mostrarEspecialidadesMayorConsultaDialog(topEspecialidades);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(ventanaPrincipal,
					"Error al obtener las especialidades con mayor número de consultas.", "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void mostrarTratamientosYExamenes() {
		List<TratamientoDTO> tratamientos = fachada.obtenerTratamientos();
		List<ExamenDTO> examenes = fachada.obtenerExamenes();

		List<String[]> datosTabla = new ArrayList<>();

		for (TratamientoDTO tratamiento : tratamientos) {
			String[] fila = { tratamiento.getPacienteDto().getNombre(), tratamiento.getDescripcion(), };
			datosTabla.add(fila);
		}

		for (ExamenDTO examen : examenes) {
			String[] fila = { examen.getPacienteDto().getNombre(), examen.getTipoExamen(), "Examen solicitado" };
			datosTabla.add(fila);
		}

		ventanaPrincipal.getPanelTratamiento().mostrarDialogoTratamientos(datosTabla);
	}

	public TurnoDTO generarTurnoAleatorio(EspecialistaDTO especialista, String jornada) {
		try {

			LocalDateTime ld = LocalDateTime.now();
			LocalDateTime nextDate = ld.plusDays(5);
			TurnoDTO turno = null;
			do {
				nextDate = nextDate.plusDays(1);
				System.out.println("Buscando turno aleatorio Fecha " + nextDate.toString());
				turno = fachada.asignarTurno(especialista, nextDate);

			} while (turno == null);

			JOptionPane.showMessageDialog(ventanaPrincipal, "Turno Creado Exitosamente COD:" + turno.getIdTurno());

			return turno;
		} catch (Exception error) {
			System.err.println(error.getMessage());
		}
		return null;
	}

	public TurnoDTO guardarTurno(EspecialistaDTO especialista, Date fecha, String jornada) {
		System.out.println("Guardando turno ");
		try {
			LocalDateTime ld = Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
			TurnoDTO turno = fachada.asignarTurno(especialista, ld);
			if (turno != null) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Turno Creado Exitosamente COD:" + turno.getIdTurno());
			} else {
				JOptionPane.showMessageDialog(ventanaPrincipal,
						"El Especialista no tiene disponibilidad en las fechas seleccionadas");
			}
			return turno;
		} catch (Exception error) {
			System.err.println(error.getMessage());
		}
		return null;
	}

	public List<EspecialistaDTO> obtenerEspecialistas() {
		try {
			return fachada.obtenerEspecialistas();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void guardarCita() {
		try {
			Object[] datosCita = ventanaPrincipal.obtenerDatosCita();
			if (datosCita == null) {
				return;
			}

			PacienteDTO pacienteSeleccionado = (PacienteDTO) datosCita[1];
			EspecialistaDTO especialistaSeleccionado = (EspecialistaDTO) datosCita[2];
			String fechaSeleccionada = (String) datosCita[3];
			String correoPaciente = (String) datosCita[4];

			DateTimeFormatter formatterConHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			DateTimeFormatter formatterSinHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime fechaCita = null;
			try {
				fechaCita = LocalDateTime.parse(fechaSeleccionada, formatterConHora);
			} catch (DateTimeParseException e) {
				LocalDate fecha = LocalDate.parse(fechaSeleccionada, formatterSinHora);
				fechaCita = fecha.atStartOfDay();
			}

			CitaDTO nuevaCita = new CitaDTO(fachada.generarIdCita(), fechaCita, correoPaciente, pacienteSeleccionado,
					especialistaSeleccionado, null);
			fachada.guardarCita(nuevaCita);
			ventanaPrincipal.cargarCita(nuevaCita);

			JOptionPane.showMessageDialog(ventanaPrincipal, String.format(
					"La cita ha sido guardada exitosamente para el paciente %s con el especialista %s en la fecha %s.",
					pacienteSeleccionado.getNombre(), especialistaSeleccionado.getNombre(),
					fechaCita.format(formatterConHora)));

			if (correoPaciente != null) {
				enviarEmail(correoPaciente, "Confirmación de Cita", String.format(
						"Hola %s,\n\nSe ha confirmado su cita con el especialista %s para el día %s.\n\nGracias por confiar en nuestro servicio.\n\nEquipo Bosque Health",
						pacienteSeleccionado.getNombre(), especialistaSeleccionado.getNombre(),
						fechaCita.format(formatterConHora)));
			}

			cargarCitas();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar la cita: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void editarCita() {
		try {
			Object[] datosCita = ventanaPrincipal.obtenerDatosCita();
			if (datosCita == null) {
				return;
			}
			int idCita = (int) datosCita[0];

			PacienteDTO pacienteSeleccionado = (PacienteDTO) datosCita[1];
			EspecialistaDTO especialistaSeleccionado = (EspecialistaDTO) datosCita[2];
			String fechaSeleccionada = (String) datosCita[3];
			String correoPaciente = (String) datosCita[4];
			DateTimeFormatter formatterConHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			DateTimeFormatter formatterSinHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime fechaCita = null;
			try {
				fechaCita = LocalDateTime.parse(fechaSeleccionada, formatterConHora);
			} catch (DateTimeParseException e) {
				LocalDate fecha = LocalDate.parse(fechaSeleccionada, formatterSinHora);
				fechaCita = fecha.atStartOfDay();
			}

			CitaDTO nuevaCita = new CitaDTO(idCita, fechaCita, correoPaciente, pacienteSeleccionado,
					especialistaSeleccionado, null);
			fachada.actualizarCita(nuevaCita);
			List<CitaDTO> citas = fachada.obtenerCitasDTO();
			ventanaPrincipal.cargarCitasTabla(citas);
			JOptionPane.showMessageDialog(ventanaPrincipal, "Cita actualizada exitosamente.");
			if (correoPaciente != null) {
				enviarEmail(correoPaciente, "Cita Actualziada", "Hola " + pacienteSeleccionado.getNombre()
						+ " \n\n Hemos confirmado tu cita con el especialista " + especialistaSeleccionado.getNombre());
			}
			cargarCitas();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar la cita: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void cancelarCita() {
		try {
			String idCita = ventanaPrincipal.obtenerIdCita();
			int citaId = Integer.parseInt(idCita);

			boolean cancelada = fachada.cancelarCita(citaId);
			if (cancelada) {
				cargarCitas();
				JOptionPane.showMessageDialog(ventanaPrincipal, "Se ha cancelado la cita exitosamente");
			} else {
				JOptionPane.showMessageDialog(ventanaPrincipal, "La cita no pudo ser cancelada", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al cancelar la cita: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void realizarIngreso() {
		String tipoUsuario = ventanaPrincipal.getInicioUsuario().getTipoUsuarioSeleccionado();
		String usuario = ventanaPrincipal.getInicioUsuario().getUsuario();
		String contrasena = ventanaPrincipal.getInicioUsuario().getContrasena();
		boolean loginValido = false;

		if (usuario.isEmpty() || contrasena.isEmpty()) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, complete todos los campos.", "Error de ingreso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		esDirector = false;
		switch (tipoUsuario) {
		case "Especialista":
			try {
				EspecialistaDTO especialista = fachada.verificarCredencialesEspecialista(usuario, contrasena);
				if (especialista != null) {
					JOptionPane.showMessageDialog(null, "Bienvenido especialista");
					loginValido = true;
					especialistaLogueado = especialista;

				} else {
					JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error al verificar credenciales");
				e.printStackTrace();
			}
			break;

		case "Director Medico":


			if (usuario.equalsIgnoreCase("Andres Rey") && contrasena.equals("12345")) {
				JOptionPane.showMessageDialog(null, "Bienvenido director medico");
				loginValido = true;
				esDirector = true;
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos para Director Medico.");
			}
			break;
		default:
			JOptionPane.showMessageDialog(null, "Tipo de usuario desconocido");
			break;
		}
		if (loginValido == true) {
			ventanaPrincipal.mostrarVistaPrincipal(this);
			cargarDatosIniciales();
		}
	}

	public void registrarEspecialista() {
		try {
			String nombre = ventanaPrincipal.getRegistroEspecialistaPanel().getNombreField().getText();
			int identificacion = Integer
					.parseInt(ventanaPrincipal.getRegistroEspecialistaPanel().getIdentificacionField().getText());
			String email = ventanaPrincipal.getRegistroEspecialistaPanel().getCorreoField().getText();
			String especialidad = (String) ventanaPrincipal.getRegistroEspecialistaPanel().getEspecialidadComboBox()
					.getSelectedItem();
			boolean disponibilidad = true;
			String codigoSeguridad = ventanaPrincipal.getRegistroEspecialistaPanel().getCodigoSeguridadField()
					.getText();
			String contrasena = new String(
					ventanaPrincipal.getRegistroEspecialistaPanel().getContrasenaField().getPassword());

			if (!"123".equals(codigoSeguridad)) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Error: Código de seguridad incorrecto.");
				return;
			}

			EspecialistaDTO especialistaDTO = new EspecialistaDTO(nombre, identificacion, email, especialidad,
					disponibilidad, contrasena);
			fachada.registrarEspecialista(especialistaDTO);
			JOptionPane.showMessageDialog(ventanaPrincipal, "Especialista registrado exitosamente.");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error: La identificación debe ser un número.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar el especialista.");
			e.printStackTrace();
		}
	}

	public void registrarPacientes() {
		try {
			String nombre = ventanaPrincipal.getRegistroPacientePanel().getNombreField().getText();
			int identificacion = Integer
					.parseInt(ventanaPrincipal.getRegistroPacientePanel().getIdentificacionField().getText());
			String email = ventanaPrincipal.getRegistroPacientePanel().getCorreoField().getText();
			String contrasena = new String(
					ventanaPrincipal.getRegistroPacientePanel().getContrasenaField().getPassword());

			PacienteDTO pacienteDTO = new PacienteDTO(nombre, identificacion, email, "historial_medico", contrasena);
			fachada.registrarPaciente(pacienteDTO);

			JOptionPane.showMessageDialog(ventanaPrincipal, "Paciente registrado exitosamente.");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error: La identificación debe ser un número.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al guardar el paciente.");
			e.printStackTrace();
		}
	}

// mandar correo
	public void guardarTratamiento() {
		try {
			String indicaciones = ventanaPrincipal.getPanelTratamiento().getTextIndicaciones().getText();
			String diagnostico = ventanaPrincipal.getPanelTratamiento().getTextDiagnostico().getText();
			String tratamiento = ventanaPrincipal.getPanelTratamiento().getTextTratamiento().getText();

			PacienteDTO paciente = (PacienteDTO) ventanaPrincipal.getPanelTratamiento().getComboNombrePaciente()
					.getSelectedItem();

			// Validación de campos vacíos
			if (paciente == null) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, seleccione un paciente.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (diagnostico.isEmpty()) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, ingrese el diagnóstico.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (tratamiento.isEmpty()) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, ingrese el tratamiento.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (indicaciones.isEmpty()) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, ingrese las indicaciones.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Crear el objeto TratamientoDTO con los datos ingresados
			TratamientoDTO tratamientoDTO = new TratamientoDTO(tratamiento, LocalDateTime.now(), null, paciente);

			boolean tratamientoGuardado = fachada.guardarTratamiento(tratamientoDTO);
			if (tratamientoGuardado) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Tratamiento guardado exitosamente.");

				if (paciente.getEmail() != null && !paciente.getEmail().isEmpty()) {
					String asunto = "Confirmación de tratamiento médico";
					String mensaje = String.format(
							"Estimado(a) %s,\n\nSe ha registrado un nuevo tratamiento para usted en nuestro sistema.\n\n"
									+ "Tratamiento: %s\nDiagnostico: %s\nIndicaciones: %s\n\n"
									+ "Gracias por confiar en nosotros.\n\nSaludos,\nEquipo Bosque Health",
							paciente.getNombre(), diagnostico, tratamiento, indicaciones);

					enviarEmail(paciente.getEmail(), asunto, mensaje);
				} else {
					JOptionPane.showMessageDialog(ventanaPrincipal,
							"No se ha proporcionado un correo electrónico válido para el paciente.",
							"Correo no enviado", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(ventanaPrincipal, "No se pudo guardar el tratamiento.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al intentar guardar el tratamiento.");
			e.printStackTrace();
		}
	}

	public List<TurnoDTO> getTurnos() {
		return fachada.obtenerTurnosAlmacenados();
	}

// aca debe enviar correo 
	public void guardarExamen() {
		try {
			PacienteDTO pacienteSeleccionado = ventanaPrincipal.getAsignarExamen().getPacienteSeleccionado();
			EspecialistaDTO especialistaSeleccionado = ventanaPrincipal.getAsignarExamen()
					.getEspecialistaSeleccionado();
			String tipoExamen = ventanaPrincipal.getAsignarExamen().getTipoExamenSeleccionado();
			Date fechaSeleccionada = ventanaPrincipal.getAsignarExamen().getFechaSeleccionada();
			String correoPaciente = ventanaPrincipal.getAsignarExamen().getCorreoPaciente();

			// Validaciones de campos obligatorios
			if (pacienteSeleccionado == null) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, seleccione un paciente.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (especialistaSeleccionado == null) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, seleccione un especialista.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (tipoExamen == null || tipoExamen.isEmpty()) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, seleccione un tipo de examen.",
						"Campo vacío", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (fechaSeleccionada == null) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, seleccione una fecha.", "Campo vacío",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (correoPaciente == null || correoPaciente.isEmpty()) {
				JOptionPane.showMessageDialog(ventanaPrincipal, "Por favor, ingrese el correo del paciente.",
						"Campo vacío", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Convertir fecha a LocalDate
			LocalDate fechaSolicitud = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int id = fachada.generarIdExamen();
			ExamenDTO examen = new ExamenDTO(id, tipoExamen, fechaSolicitud, null, null, pacienteSeleccionado);

			// Guardar el examen y mostrar mensaje de éxito
			fachada.asignarExamen(examen);
			JOptionPane.showMessageDialog(ventanaPrincipal, "Examen guardado exitosamente.");

			// Enviar correo de confirmación al paciente
			if (pacienteSeleccionado.getEmail() != null && !pacienteSeleccionado.getEmail().isEmpty()) {
				String asunto = "Confirmación de examen médico";
				String mensaje = String.format(
						"Estimado(a) %s,\n\nSe ha registrado un nuevo examen en nuestro sistema.\n\n"
								+ "Tipo de Examen: %s\nEspecialista: %s\nFecha: %s\n\n"
								+ "Gracias por confiar en nosotros.\n\nSaludos,\nEquipo Bosque Health",
						pacienteSeleccionado.getNombre(), tipoExamen, especialistaSeleccionado.getNombre(),
						fechaSolicitud.toString());

				enviarEmail(correoPaciente, asunto, mensaje);
			} else {
				JOptionPane.showMessageDialog(ventanaPrincipal,
						"No se ha proporcionado un correo electrónico válido para el paciente.", "Correo no enviado",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Error al intentar guardar el examen.");
			e.printStackTrace();
		}
	}

	// para la tabla de citas
	private void cargarCitas() {
		try {
			List<CitaDTO> citas = fachada.obtenerCitasDTO();
			ventanaPrincipal.cargarCitas(citas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EspecialistaDTO getUsuario() {
		return especialistaLogueado;
	}

	public void solicitarCambioTurno(EspecialistaDTO especialistaSeleccionado, int idTurno) {
		try {
			TurnoDTO turnoCambiado = fachada.solicitarCambioTurno(especialistaSeleccionado, idTurno);
			if (turnoCambiado != null && especialistaSeleccionado.getEmail() != null) {
				enviarEmail(especialistaSeleccionado.getEmail(), "Solicitud de Cambio de Turno",
						"Estimado/a " + especialistaSeleccionado.getNombre() + ",\n\n"
								+ "Se ha solicitado un cambio de turno que requiere su confirmación. "
								+ "Por favor, confirme si puede aceptar el turno en la siguiente fecha y hora:\n\n"
								+ "Fecha de inicio del turno: " + turnoCambiado.getFechaInicioTurnoString() + "\n\n"
								+ "Agradecemos su colaboración.\n\n" + "Saludos,\n" + "Equipo Bosque Health");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TurnoDTO aceptarCambioTurno(int idTurno) {
		try {
			TurnoDTO dto = fachada.aceptarCambioTurno(idTurno);
			if (dto != null) {
				enviarEmail(dto.getEspecialista().getEmail(), "Solicitud Cambio de Turno Aceptada",
						"Hola " + dto.getEspecialista().getNombre() + ", \n \n "
								+ "SE HA ACEPTADO EL CAMBIO DE TURNO POR EL OTRO ESPECIALISTA  ");
			}
			return dto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// metodos para tema de emails
	public Properties leerPropiedades() {
		Properties propiedades = new Properties();
		try (InputStream entrada = new FileInputStream(new File("app_config.properties"))) {
			// Carga el archivo de propiedades
			propiedades.load(entrada);
			return propiedades;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void enviarEmail(String destinatario, String asunto, String mensaje) {
		// Configuración de las propiedades para la conexión SMTP de Gmail
		Properties props = leerPropiedades();

		String username = props.getProperty("email.username");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, props.getProperty("email.password"));
			}
		});

		try {
			Message mensajeEmail = new MimeMessage(session);
			mensajeEmail.setFrom(new InternetAddress(username));
			mensajeEmail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
			mensajeEmail.setSubject(asunto);
			mensajeEmail.setText(mensaje);
			Transport.send(mensajeEmail);

			JOptionPane.showMessageDialog(ventanaPrincipal, "Correo enviado exitosamente.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mostrarReporteEnTabla() throws ReporteNoGeneradoException, IOException {
		List<ReporteDTO> reportes = fachada.obtenerReportes();

		ventanaPrincipal.getPanelReporte().cargarDatos(reportes);
		ventanaPrincipal.mostrarPanel("REPORTES"); // Muestra el panel de reportes
	}
}