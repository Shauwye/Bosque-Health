package co.edu.unbosque.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import co.edu.unbosque.model.persistence.CitaDAO;
import co.edu.unbosque.model.persistence.EspecialistaDAO;
import co.edu.unbosque.model.persistence.ExamenDAO;
import co.edu.unbosque.model.persistence.NotificacionDAO;
import co.edu.unbosque.model.persistence.PacienteDAO;
import co.edu.unbosque.model.persistence.TratamientoDAO;
import co.edu.unbosque.model.persistence.TurnoDAO;
import co.edu.unbosque.util.exception.CambioDeTurnoNoAutorizadoException;
import co.edu.unbosque.util.exception.CitaNoDisponibleException;
import co.edu.unbosque.util.exception.EspecialistaNoDisponibleException;
import co.edu.unbosque.util.exception.ExcesoDeTurnosException;
import co.edu.unbosque.util.exception.NotificacionFallidaException;
import co.edu.unbosque.util.exception.PersistenciaNoExitosaException;
import co.edu.unbosque.util.exception.ReporteNoGeneradoException;
import co.edu.unbosque.util.exception.TurnoNoDisponibleException;

public class GestionClinicaFachada {

	private PacienteDAO pacienteDAO;
	private EspecialistaDAO especialistaDAO;
	private CitaDAO citaDAO;
	private TurnoDAO turnoDAO;
	private TratamientoDAO tratamientoDAO;
	private ExamenDAO examenDAO;
	private NotificacionDAO notificacionDAO;

	public GestionClinicaFachada() {
		this.pacienteDAO = new PacienteDAO();
		this.especialistaDAO = new EspecialistaDAO();
		this.citaDAO = new CitaDAO();
		this.turnoDAO = new TurnoDAO();
		this.tratamientoDAO = new TratamientoDAO();
		this.examenDAO = new ExamenDAO();
		this.notificacionDAO = new NotificacionDAO();
	}

	// Métodos de carga de datos para inicializar en DTOs
	public List<PacienteDTO> obtenerPacientesDTO() throws IOException {
		List<Paciente> pacientes = pacienteDAO.findAll();
		List<PacienteDTO> pacientesDTO = new ArrayList<>();
		for (Paciente paciente : pacientes) {
			pacientesDTO.add(DataMapper.convertirPacienteEntidadADTO(paciente));
		}
		return pacientesDTO;
	}

	// Método para obtener y convertir la lista de EspecialistaDTO desde
	// EspecialistaDAO
	public List<EspecialistaDTO> obtenerEspecialistas() throws IOException {
		List<Especialista> especialistas = especialistaDAO.findAll();
		List<EspecialistaDTO> especialistasDTO = new ArrayList<>();
		for (Especialista especialista : especialistas) {
			especialistasDTO.add(DataMapper.convertirEspecialistaEntidadADTO(especialista));
		}
		return especialistasDTO;
	}

	// Gestión de Pacientes
	public Paciente registrarPaciente(Paciente paciente) throws IOException {
		return pacienteDAO.save(paciente);
	}

	public Paciente buscarPacientePorId(int id) {
		return pacienteDAO.findById(id);
	}

	public void eliminarPaciente(Paciente paciente) throws IOException {
		pacienteDAO.delete(paciente);
	}

	// Gestión de Especialistas
	public Especialista registrarEspecialista(Especialista especialista) throws IOException {
		return especialistaDAO.save(especialista);
	}

	public Especialista buscarEspecialistaPorId(int id) throws IOException {
		return especialistaDAO.findById(id);
	}

	public void eliminarEspecialista(Especialista especialista) throws IOException {
		especialistaDAO.delete(especialista);
	}

	// Gestión de Turnos
	public TurnoDTO asignarTurno(EspecialistaDTO especialistaDTO, LocalDateTime fechaInicioTurno) throws EspecialistaNoDisponibleException, ExcesoDeTurnosException, TurnoNoDisponibleException, PersistenciaNoExitosaException {
        try {
            if (!verificarDisponibilidad(especialistaDTO, fechaInicioTurno)) {
                throw new ExcesoDeTurnosException("El especialista tiene un exceso de turnos asignados.");
            }
            LocalDateTime fechaFinTurno = fechaInicioTurno.plusHours(24);
            Turno turno = new Turno(turnoDAO.findAll().size() + 1, fechaInicioTurno, fechaFinTurno, "Asignado",
                                     DataMapper.convertirEspecialistaDTOaEntidad(especialistaDTO), null);
            turnoDAO.save(turno);
            return DataMapper.convertirTurnoEntidadADTO(turno);
        } catch (IOException e) {
            throw new PersistenciaNoExitosaException("Error al guardar el turno en la base de datos.");
        }
    }

	private boolean verificarDisponibilidad(EspecialistaDTO especialista, LocalDateTime fechaInicioTurno) {
		List<Turno> turnos = turnoDAO.findAll();
		Especialista especialistaEntidad = DataMapper.convertirEspecialistaDTOaEntidad(especialista);
		int contadorTurnos = 0;
		LocalDateTime inicioSemana = fechaInicioTurno.with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
		LocalDateTime finSemana = inicioSemana.plusDays(6).withHour(23).withMinute(59).withSecond(59);

		for (Turno turno : turnos) {
			if (turno.getEspecialista().getNombre().equals(especialistaEntidad.getNombre())) {
				if (turno.getFechaInicioTurno().isEqual(fechaInicioTurno)) {
					return false;
				}
				if (turno.getFechaInicioTurno().isAfter(inicioSemana)
						&& turno.getFechaInicioTurno().isBefore(finSemana)) {
					contadorTurnos++;
				}

			}
		}
		return contadorTurnos < 2;
	}

	public boolean eliminarTurno(int idTurno) throws IOException {
		Turno turno = turnoDAO.findById(idTurno);
		if (turno != null) {
			turnoDAO.delete(turno);
			return true;
		}
		return false;
	}

	 public boolean cambiarTurno(TurnoDTO turnoDTO, EspecialistaDTO nuevoEspecialista) throws CambioDeTurnoNoAutorizadoException, EspecialistaNoDisponibleException, PersistenciaNoExitosaException {
	        if (!nuevoEspecialista.isDisponibilidad()) {
	            throw new EspecialistaNoDisponibleException("El especialista no está disponible.");
	        }
	        if (!verificarDisponibilidad(nuevoEspecialista, turnoDTO.getFechaInicioTurno())) {
	            throw new CambioDeTurnoNoAutorizadoException("El especialista tiene el máximo de turnos permitidos.");
	        }
	        Turno turno = DataMapper.convertirTurnoDTOaEntidad(turnoDTO);
	        turno.setEspecialista(DataMapper.convertirEspecialistaDTOaEntidad(nuevoEspecialista));
	        try {
	            turnoDAO.update(turno);
	            return true;
	        } catch (IOException e) {
	            throw new PersistenciaNoExitosaException("No se pudo actualizar el turno en la base de datos.");
	        }
	    }

	private void notificarCambioTurno(Especialista antiguoEspecialista, Especialista nuevoEspecialista, Turno turno) {
		String mensaje = "Cambio de turno realizado.\n" + "Turno anterior: " + antiguoEspecialista.getNombre() + "\n"
				+ "Nuevo especialista: " + nuevoEspecialista.getNombre();
		Notificacion notificacion = new Notificacion("direccion@bosquehealth.com", "Cambio de Turno", mensaje);
		try {
			notificacionDAO.save(notificacion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Turno generarTurnoAutomatico(Especialista especialista) throws IOException {
		int idTurno = new Random().nextInt(10000);
		LocalDateTime fechaInicio = LocalDateTime.now().plusDays(1).withHour(7).withMinute(0);
		LocalDateTime fechaFin = fechaInicio.plusHours(24);

		Turno nuevoTurno = new Turno(idTurno, fechaInicio, fechaFin, "Asignado", especialista, null);
		turnoDAO.save(nuevoTurno);
		especialista.setDisponibilidad(false);
		especialistaDAO.update(especialista);

		return nuevoTurno;
	}

	// Gestión de Tratamientos

	// Notificación de correos
	 private void enviarCorreoNotificacion(String destinatario, String asunto, String mensaje) throws NotificacionFallidaException, IOException {
		Notificacion notificacion = new Notificacion(destinatario, asunto, mensaje);
		notificacionDAO.save(notificacion);
	}

	public List<String> obtenerEspecialistaConMayorNumeroDeCitas() throws IOException {
		List<Cita> citas = citaDAO.findAll();
		Map<String, Long> contadorCitasPorEspecialista = new HashMap<>();
		for (Cita cita : citas) {
			String especialista = cita.getEspecialista().getNombre();
			contadorCitasPorEspecialista.put(especialista,
					contadorCitasPorEspecialista.getOrDefault(especialista, 0L) + 1);
		}
		List<String> especialistasOrdenados = contadorCitasPorEspecialista.entrySet().stream()
				.sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue())).map(Map.Entry::getKey)
				.collect(Collectors.toList());

		return especialistasOrdenados;
	}

	public List<String> obtenerEspecialidadesConMayorConsulta() throws IOException {
		List<Cita> citas = citaDAO.findAll();

		Map<String, Long> contadorCitasPorEspecialidad = new HashMap<>();

		for (Cita cita : citas) {
			String especialidad = cita.getEspecialista().getEspecialidad();
			contadorCitasPorEspecialidad.put(especialidad,
					contadorCitasPorEspecialidad.getOrDefault(especialidad, 0L) + 1);
		}

		List<String> especialidadesOrdenadas = contadorCitasPorEspecialidad.entrySet().stream()
				.sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue())).map(Map.Entry::getKey)
				.collect(Collectors.toList());

		return especialidadesOrdenadas;
	}

	public int generarIdCita() {
		return new Random().nextInt(10000);
	}

	public boolean asignarCita(CitaDTO citaDTO) throws CitaNoDisponibleException, PersistenciaNoExitosaException, IOException{
		Cita cita = DataMapper.convertirCitaDTOaEntidad(citaDTO);
		List<Cita> listaCitas = citaDAO.findAll();
		boolean citaExiste = listaCitas.stream().anyMatch(c -> c.getFechaCita().isEqual(cita.getFechaCita())
				&& c.getEspecialista().equals(cita.getEspecialista()) && c.getCorreo().equals(cita.getCorreo()));
		if (citaExiste) {
			return false;
		}

		citaDAO.save(cita);
		return true;
	}

	public EspecialistaDTO obtenerEspecialistaPorNombre(String especialistaSeleccionado) throws IOException {
		List<Especialista> listaEspecialistas = especialistaDAO.findAll();
		for (Especialista especialista : listaEspecialistas) {
			if (especialista.getNombre().equalsIgnoreCase(especialistaSeleccionado)) {
				return DataMapper.convertirEspecialistaEntidadADTO(especialista);
			}
		}
		return null;
	}

	public void guardarCita(CitaDTO citaDTO) throws IOException {
		Cita cita = DataMapper.convertirCitaDTOaEntidad(citaDTO);
		citaDAO.save(cita);
	}

	public void actualizarCita(CitaDTO citaDTO) throws IOException {
		Cita cita = DataMapper.convertirCitaDTOaEntidad(citaDTO);
		citaDAO.update(cita);
	}

	public boolean cancelarCita(int id) throws IOException {
	    Cita cita = citaDAO.findById(id);
	    if (cita != null) {
	        cita.setEstado("CANCELADO");
	        citaDAO.update(cita); 
	        return true;
	    }
	    return false;
	}

	public List<TurnoDTO> obtenerTurnosAlmacenados() {
		List<Turno> turnos = turnoDAO.findAll();
		List<TurnoDTO> turnosDTO = new ArrayList<TurnoDTO>();
		for (Turno t : turnos) {
			turnosDTO.add(DataMapper.convertirTurnoEntidadADTO(t));
		}
		return turnosDTO;
	}

	public void registrarEspecialista(EspecialistaDTO especialistaDTO) throws IOException {
		Especialista especialista = DataMapper.convertirEspecialistaDTOaEntidad(especialistaDTO);
		especialistaDAO.save(especialista);
	}

	public EspecialistaDTO verificarCredencialesEspecialista(String nombreUsuario, String contrasena)
			throws IOException {
		Especialista especialista = especialistaDAO.buscarEspecialistaPorCredenciales(nombreUsuario, contrasena);
		return (especialista != null) ? DataMapper.convertirEspecialistaEntidadADTO(especialista) : null;
	}

	public void registrarPaciente(PacienteDTO pacienteDTO) throws IOException {
		Paciente paciente = DataMapper.convertirPacienteDTOaEntidad(pacienteDTO);
		pacienteDAO.save(paciente);
	}

	public boolean guardarTratamiento(TratamientoDTO tratamientoDTO) throws IOException {
		try {
			Tratamiento tratamiento = DataMapper.convertirTratamientoDTOaEntidad(tratamientoDTO);
			tratamientoDAO.save(tratamiento);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	public int generarIdExamen() {
		return new Random().nextInt(10000);
	}

	public boolean asignarExamen(ExamenDTO examenDto) throws IOException {
		try {
			Examen examen = DataMapper.convertirExamenDTOaEntidad(examenDto);
			examenDAO.save(examen);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}


	

	// editar cita
	public void editarCita(CitaDTO citaDTO) throws IOException {
		Cita cita = DataMapper.convertirCitaDTOaEntidad(citaDTO);
		List<Cita> listaCitas = citaDAO.findAll();
		boolean citaExiste = listaCitas.stream().anyMatch(c -> c.getIdCita() == cita.getIdCita());
		if (!citaExiste) {
			return;
		}
		citaDAO.delete(cita);
		citaDAO.save(cita);
	}

	// esto es para la tabla de cita
	public List<CitaDTO> obtenerCitasDTO() throws IOException {
		List<Cita> citas = citaDAO.obtenerCitas();
		return citas.stream().map(DataMapper::convertirCitaEntidadADTO).collect(Collectors.toList());
	}

	public List<TratamientoDTO> obtenerTratamientos() {
		List<Tratamiento> tratamientos = tratamientoDAO.findAll();
		List<TratamientoDTO> tratamientosDTO = new ArrayList<>();
		for (Tratamiento tratamiento : tratamientos) {
			tratamientosDTO.add(DataMapper.convertirTratamientoEntidadADTO(tratamiento));
		}
		return tratamientosDTO;
	}

	public List<ExamenDTO> obtenerExamenes() {
		List<Examen> examenes = examenDAO.findAll();
		List<ExamenDTO> examenesDTO = new ArrayList<>();
		for (Examen examen : examenes) {
			examenesDTO.add(DataMapper.convertirExamenEntidadADTO(examen));
		}
		return examenesDTO;
	}

	public TurnoDTO solicitarCambioTurno(EspecialistaDTO especialistaSeleccionado, int idTurno) throws IOException {
		Turno turno = turnoDAO.findById(idTurno);
		if (turno != null) {
			System.out.println("Actualizando turno en base de datos");
			turno.setEspecialistaCambio(DataMapper.convertirEspecialistaDTOaEntidad(especialistaSeleccionado));
			turnoDAO.update(turno);
			return DataMapper.convertirTurnoEntidadADTO(turno);
		}
		return null;
	
	}

	public TurnoDTO aceptarCambioTurno(int idTurno) throws IOException {
		Turno turno = turnoDAO.findById(idTurno);
		if (turno != null) {
			Especialista nuevoEspecialista = turno.getEspecialistaCambio();
			turno.setEspecialistaCambio(null);
			turno.setEspecialista(nuevoEspecialista);
			turnoDAO.update(turno);
			return DataMapper.convertirTurnoEntidadADTO(turno);
		}
		return null;
	}

	 public List<ReporteDTO> obtenerReportes() throws ReporteNoGeneradoException  {
	    List<Cita> citas = citaDAO.findAll(); 
	    List<Tratamiento> tratamientos = tratamientoDAO.findAll();
	    List<ReporteDTO> reportes = new ArrayList<>();

	    for (Cita cita : citas) {
	        Especialista especialista = cita.getEspecialista();
	        EspecialistaDTO especialistaDTO = new EspecialistaDTO(especialista.getNombre(),
	                especialista.getIdentificacion(), especialista.getEmail(), especialista.getEspecialidad(),
	                especialista.isDisponibilidad(), especialista.getContrasena());

	        Paciente paciente = cita.getPaciente();
	        PacienteDTO pacienteDTO = new PacienteDTO(paciente.getNombre(), paciente.getIdentificacion(),
	                paciente.getCorreo(), "historial_medico_no_definido", "contrasena_no_definida");

	        Tratamiento diagnostico = tratamientos.stream()
	                .filter(tratamiento -> tratamiento.getPaciente().getNombre().equals(paciente.getNombre()))
	                .findFirst().orElse(null);

	        TratamientoDTO diagnosticoDTO = (diagnostico != null)
	                ? new TratamientoDTO(diagnostico.getDescripcion(), diagnostico.getFechaInicio(),
	                        diagnostico.getFechaFin(), pacienteDTO)
	                : new TratamientoDTO("Sin diagnóstico", LocalDateTime.now(), null, pacienteDTO);

	        boolean citaCancelada = "CANCELADO".equalsIgnoreCase(cita.getEstado());
	        ReporteDTO reporte = new ReporteDTO(especialistaDTO, pacienteDTO, diagnosticoDTO, citaCancelada);
	        reportes.add(reporte);
	    }

	    return reportes;
	}
}
