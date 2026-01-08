package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Turno implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idTurno;
	private LocalDateTime fechaInicioTurno;
	private LocalDateTime fechaFinTurno;
	private String estadoTurno;
	private Especialista especialista;
	private Especialista especialistaCambio;

	public Turno() {
	}

	public Turno(int idTurno, LocalDateTime fechaInicioTurno, LocalDateTime fechaFinTurno, String estadoTurno,
			Especialista especialista, Especialista especialistaCambio) {
		this.idTurno = idTurno;
		this.fechaInicioTurno = fechaInicioTurno;
		this.fechaFinTurno = fechaFinTurno;
		this.estadoTurno = estadoTurno;
		this.especialista = especialista;
		this.especialistaCambio = especialistaCambio;
	}

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public LocalDateTime getFechaInicioTurno() {
		return fechaInicioTurno;
	}

	public void setFechaInicioTurno(LocalDateTime fechaInicioTurno) {
		this.fechaInicioTurno = fechaInicioTurno;
	}

	public LocalDateTime getFechaFinTurno() {
		return fechaFinTurno;
	}

	public void setFechaFinTurno(LocalDateTime fechaFinTurno) {
		this.fechaFinTurno = fechaFinTurno;
	}

	public String getEstadoTurno() {
		return estadoTurno;
	}

	public void setEstadoTurno(String estadoTurno) {
		this.estadoTurno = estadoTurno;
	}

	public Especialista getEspecialista() {
		return especialista;
	}

	public void setEspecialista(Especialista especialista) {
		this.especialista = especialista;
	}

	public void setEspecialistaCambio(Especialista especialista) {
		this.especialistaCambio = especialista;
	}
	
	
	public Especialista getEspecialistaCambio() {
		return especialistaCambio;
	}
	
	@Override
	public String toString() {
		if(especialistaCambio != null) {
			return "Turno [idTurno=" + idTurno + ", fechaInicioTurno=" + fechaInicioTurno + ", fechaFinTurno="
					+ fechaFinTurno + ", estadoTurno=" + estadoTurno + ", especialista=" + especialista
					+ ", especialistaCambio=" + especialistaCambio + "]";
		}else {
			return "Turno [idTurno=" + idTurno + ", fechaInicioTurno=" + fechaInicioTurno + ", fechaFinTurno="
					+ fechaFinTurno + ", estadoTurno=" + estadoTurno + ", especialista=" + especialista
					+  "]";
		}

	}

	public static Turno fromString(String data) {
		String[] parts = data.substring(data.indexOf("[") + 1, data.length() - 1).split(", ");

		int idTurno = Integer.parseInt(parts[0].split("=")[1].trim());
		LocalDateTime fechaInicioTurno = LocalDateTime.parse(parts[1].split("=")[1].trim());
		LocalDateTime fechaFinTurno = LocalDateTime.parse(parts[2].split("=")[1].trim());
		String estadoTurno = parts[3].split("=")[1].trim();

		String especialistaData = "Especialista ["
				+ data.substring(data.indexOf("especialista=") + "especialista=".length());
		Especialista especialista = Especialista.fromString(especialistaData);
		
		Especialista especialistaCambio = null;
		//valida si existe el especialista cambio
		if(data.indexOf("especialistaCambio=")!= -1){
		String especialistaCambioData = "Especialista ["
				+ data.substring(data.indexOf("especialistaCambio=") + "especialistaCambio=".length());
		 especialistaCambio = Especialista.fromString(especialistaCambioData);
		}

		return new Turno(idTurno, fechaInicioTurno, fechaFinTurno, estadoTurno, especialista, especialistaCambio);
	}
}
