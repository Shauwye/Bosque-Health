package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Tratamiento implements Serializable {

	private static final long serialVersionUID = 1L;
	private String descripcion;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Paciente paciente;

	public Tratamiento(String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Paciente paciente) {
		super();
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.paciente = paciente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return "Tratamiento [descripcion=" + descripcion + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", paciente=" + paciente + "]";
	}

	public static Tratamiento fromString(String data) {
		String[] parts = data.substring(data.indexOf("[") + 1, data.length() - 1).split(", ");

		String descripcion = parts[0].split("=")[1].trim();
		LocalDateTime fechaInicio = LocalDateTime.parse(parts[1].split("=")[1].trim());
		LocalDateTime fechaFin = LocalDateTime.parse(parts[2].split("=")[1].trim());

		return new Tratamiento(descripcion, fechaInicio, fechaFin, null);
	}
}
