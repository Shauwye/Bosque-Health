package co.edu.unbosque.model;

import java.time.LocalDate;

public class ExamenDTO {
	private int idExamen;
	private String tipoExamen;
	private LocalDate fechaSolicitud;
	private LocalDate fechaResultados;
	private String resultadoExamen;
	private PacienteDTO pacienteDto;
	
	
	
	public ExamenDTO(int idExamen, String tipoExamen, LocalDate fechaSolicitud, LocalDate fechaResultados,
			String resultadoExamen, PacienteDTO pacienteDto) {
		super();
		this.idExamen = idExamen;
		this.tipoExamen = tipoExamen;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaResultados = fechaResultados;
		this.resultadoExamen = resultadoExamen;
		this.pacienteDto = pacienteDto;
	}
	public int getIdExamen() {
		return idExamen;
	}
	public String getTipoExamen() {
		return tipoExamen;
	}
	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}
	public LocalDate getFechaResultados() {
		return fechaResultados;
	}
	public String getResultadoExamen() {
		return resultadoExamen;
	}
	public PacienteDTO getPacienteDto() {
		return pacienteDto;
	}
	
	

	

}
