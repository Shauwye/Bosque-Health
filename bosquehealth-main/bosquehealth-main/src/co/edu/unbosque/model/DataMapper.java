package co.edu.unbosque.model;

public class DataMapper {

	// Convertir de DTO a entidad Cita
	public static Cita convertirCitaDTOaEntidad(CitaDTO citaDTO) {
		Especialista especialista = convertirEspecialistaDTOaEntidad(citaDTO.getEspecialistadto());
		Paciente paciente = convertirPacienteDTOaEntidad(citaDTO.getPacientedto());
		return new Cita(citaDTO.getIdCita(), citaDTO.getFechaCita(), citaDTO.getCorreo(), paciente, especialista, citaDTO.getEstado());
	}

	// Convertir de entidad a DTO Cita
	public static CitaDTO convertirCitaEntidadADTO(Cita cita) {
		EspecialistaDTO especialistaDTO = convertirEspecialistaEntidadADTO(cita.getEspecialista());
		PacienteDTO pacienteDTO = convertirPacienteEntidadADTO(cita.getPaciente());
		return new CitaDTO(cita.getIdCita(), cita.getFechaCita(), cita.getCorreo(), pacienteDTO, especialistaDTO, cita.getEstado());
	}

	// Convertir de DTO a entidad Especialista
	public static Especialista convertirEspecialistaDTOaEntidad(EspecialistaDTO especialistaDTO) {
		return new Especialista(especialistaDTO.getNombre(), especialistaDTO.getIdentificacion(),
				especialistaDTO.getEmail(), especialistaDTO.getEspecialidad(), especialistaDTO.isDisponibilidad(),
				especialistaDTO.getContrasena() // Incluimos la contraseña aquí
		);
	}

	// Convertir de entidad a DTO Especialista
	public static EspecialistaDTO convertirEspecialistaEntidadADTO(Especialista especialista) {
		return new EspecialistaDTO(especialista.getNombre(), especialista.getIdentificacion(), especialista.getEmail(),
				especialista.getEspecialidad(), especialista.isDisponibilidad(), especialista.getContrasena() // Incluimos
		// aquí
		);
	}

	// convertir de DTO a entidad Examen
	public static Examen convertirExamenDTOaEntidad(ExamenDTO examenDTO) {
		return new Examen(examenDTO.getIdExamen(), examenDTO.getTipoExamen(), examenDTO.getFechaSolicitud(),
				examenDTO.getFechaResultados(), examenDTO.getResultadoExamen(),
				convertirPacienteDTOaEntidad(examenDTO.getPacienteDto()));
	}

	public static ExamenDTO convertirExamenEntidadADTO(Examen examen) {
		return new ExamenDTO(examen.getIdExamen(), examen.getTipoExamen(), examen.getFechaSolicitud(),
				examen.getFechaResultados(), examen.getResultadoExamen(),
				convertirPacienteEntidadADTO(examen.getPaciente()));
	}

	// convertir de DTO a entidad Notificacion
	public static Notificacion convertirNotificacionDTOaEntidad(NotificacionDTO notificacionDTO) {
		return new Notificacion(notificacionDTO.getDestinatario(), notificacionDTO.getAsunto(),
				notificacionDTO.getMensaje());
	}

	// convertir de entidad a DTO Notificacion
	public static NotificacionDTO convertirNotificacionEntidadADTO(Notificacion notificacion) {
		return new NotificacionDTO(notificacion.getDestinatario(), notificacion.getAsunto(), notificacion.getMensaje());
	}

	public static Paciente convertirPacienteDTOaEntidad(PacienteDTO pacienteDTO) {
		return new Paciente(pacienteDTO.getNombre(), pacienteDTO.getIdentificacion(), pacienteDTO.getEmail(),
				pacienteDTO.getHistorialMedico(), pacienteDTO.getContrasena() // Incluye la contraseña
		);
	}

	// convertir de entidad a DTO Paciente
	public static PacienteDTO convertirPacienteEntidadADTO(Paciente paciente) {
		return new PacienteDTO(paciente.getNombre(), paciente.getIdentificacion(), paciente.getEmail(),
				paciente.getHistorialMedico(), paciente.getContrasena() // Incluye la contraseña
		);
	}

	// convertir de DTO a entidad Tratamiento
	public static Tratamiento convertirTratamientoDTOaEntidad(TratamientoDTO tratamientoDTO) {
		return new Tratamiento(tratamientoDTO.getDescripcion(), tratamientoDTO.getFechaInicio(),
				tratamientoDTO.getFechaFin(), convertirPacienteDTOaEntidad(tratamientoDTO.getPacienteDto()));
	}

	public static TratamientoDTO convertirTratamientoEntidadADTO(Tratamiento tratamiento) {
		return new TratamientoDTO(tratamiento.getDescripcion(), tratamiento.getFechaInicio(), tratamiento.getFechaFin(),
				convertirPacienteEntidadADTO(tratamiento.getPaciente()));
	}

	// convertir de DTO a entidad Turno
	public static Turno convertirTurnoDTOaEntidad(TurnoDTO turnoDTO) {
		return new Turno(turnoDTO.getIdTurno(), turnoDTO.getFechaInicioTurno(), turnoDTO.getFechaFinTurno(),
				turnoDTO.getEstadoTurno(), turnoDTO.getEspecialista(), turnoDTO.getEspecialistaCambio());
	}

	// convertir de entidad a DTO Turno
	public static TurnoDTO convertirTurnoEntidadADTO(Turno turno) {
		return new TurnoDTO(turno.getIdTurno(), turno.getFechaInicioTurno(), turno.getFechaFinTurno(),
				turno.getEstadoTurno(), turno.getEspecialista(), turno.getEspecialistaCambio());
	}

	public static ReporteDTO convertirReporteEntidadADTO(Reporte reporte) {
		Especialista especialista = reporte.getNombreEspecialista();
		Paciente paciente = reporte.getNombrePaciente();
		Tratamiento tratamiento = reporte.getDiagnostico();
		boolean cancelacionCita = reporte.isCancelacionCita();

		// Conversión a EspecialistaDTO, PacienteDTO y TratamientoDTO
		EspecialistaDTO especialistaDTO = new EspecialistaDTO(especialista.getNombre(),
				especialista.getIdentificacion(), especialista.getEmail(), especialista.getEspecialidad(),
				especialista.isDisponibilidad(), especialista.getContrasena());

		PacienteDTO pacienteDTO = new PacienteDTO(paciente.getNombre(), paciente.getIdentificacion(),
				paciente.getCorreo(), paciente.getHistorialMedico(), paciente.getContrasena());

		TratamientoDTO tratamientoDTO = (tratamiento != null)
				? new TratamientoDTO(tratamiento.getDescripcion(), tratamiento.getFechaInicio(),
						tratamiento.getFechaFin(), pacienteDTO)
				: null;

		return new ReporteDTO(especialistaDTO, pacienteDTO, tratamientoDTO, cancelacionCita);
	}

	// Convierte de ReporteDTO a Reporte
	public static Reporte convertirReporteDTOAEntidad(ReporteDTO reporteDTO) {
		EspecialistaDTO especialistaDTO = reporteDTO.getNombreEspecialista();
		PacienteDTO pacienteDTO = reporteDTO.getNombrePaciente();
		TratamientoDTO tratamientoDTO = reporteDTO.getDiagnostico();
		boolean cancelacionCita = reporteDTO.isCancelacionCita();

		// Conversión de DTOs a entidades
		Especialista especialista = new Especialista(especialistaDTO.getNombre(), especialistaDTO.getIdentificacion(),
				especialistaDTO.getEmail(), especialistaDTO.getEspecialidad(), especialistaDTO.isDisponibilidad(),
				especialistaDTO.getContrasena());

		Paciente paciente = new Paciente(pacienteDTO.getNombre(), pacienteDTO.getIdentificacion(),
				pacienteDTO.getCorreo(), pacienteDTO.getHistorialMedico(), pacienteDTO.getContrasena());

		Tratamiento tratamiento = (tratamientoDTO != null)
				? new Tratamiento(tratamientoDTO.getDescripcion(), tratamientoDTO.getFechaInicio(),
						tratamientoDTO.getFechaFin(), paciente)
				: null;

		return new Reporte(especialista, paciente, tratamiento, cancelacionCita);
	}
}
