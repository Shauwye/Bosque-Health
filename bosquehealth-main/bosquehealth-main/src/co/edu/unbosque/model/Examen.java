package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Examen implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idExamen;
	private String tipoExamen;
	private LocalDate fechaSolicitud;
	private LocalDate fechaResultados;
	private String resultadoExamen;
	private Paciente paciente;

	public Examen(int idExamen, String tipoExamen, LocalDate fechaSolicitud, LocalDate fechaResultados,
			String resultadoExamen, Paciente paciente) {
		this.idExamen = idExamen;
		this.tipoExamen = tipoExamen;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaResultados = fechaResultados;
		this.resultadoExamen = resultadoExamen;
		this.paciente = paciente;
	}


	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public LocalDate getFechaResultados() {
		return fechaResultados;
	}

	public void setFechaResultados(LocalDate fechaResultados) {
		this.fechaResultados = fechaResultados;
	}

	public String getResultadoExamen() {
		return resultadoExamen;
	}

	public void setResultadoExamen(String resultadoExamen) {
		this.resultadoExamen = resultadoExamen;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return "Examen [idExamen=" + idExamen + ", tipoExamen=" + tipoExamen + ", fechaSolicitud=" + fechaSolicitud
				+ ", fechaResultados=" + fechaResultados + ", resultadoExamen=" + resultadoExamen + ", paciente="
				+ paciente + "]";
	}

	public static Examen fromString(String data) {
		String[] parts = data.substring(data.indexOf("[") + 1, data.length() - 1).split(", ");

		int idExamen = Integer.parseInt(parts[0].split("=")[1].trim());
		String tipoExamen = parts[1].split("=")[1].trim();
		LocalDate fechaSolicitud = LocalDate.parse(parts[2].split("=")[1].trim());
		LocalDate fechaResultados = LocalDate.parse(parts[3].split("=")[1].trim());
		String resultadoExamen = parts[4].split("=")[1].trim();

		return new Examen(idExamen, tipoExamen, fechaSolicitud, fechaResultados, resultadoExamen, null);
	}
}
