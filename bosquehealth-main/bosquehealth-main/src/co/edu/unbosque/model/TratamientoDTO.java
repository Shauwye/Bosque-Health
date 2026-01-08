package co.edu.unbosque.model;

import java.time.LocalDateTime;

public class TratamientoDTO {
	private String descripcion;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private PacienteDTO pacienteDto;

	public TratamientoDTO(String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin,
			PacienteDTO pacienteDto) {
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.pacienteDto = pacienteDto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public PacienteDTO getPacienteDto() {
		return pacienteDto;
	}
	
	
	
	

}
