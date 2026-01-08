package co.edu.unbosque.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cita implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int idCita;
	private LocalDateTime fechaCita;
	private String correo;
	private Paciente paciente;
	private Especialista especialista;
	private String estado;
	
	public Cita(int idCita, LocalDateTime fechaCita, String correo, Paciente paciente, Especialista especialista, String estado) {
		this.idCita = idCita;
		this.fechaCita = fechaCita;
		this.correo = correo;
		this.paciente = paciente;
		this.especialista = especialista;
		this.estado = estado != null ? estado : "CONFIRMADO";
	}


	public int getIdCita() {
		return idCita;
	}


	public void setIdCita(int idCita) {
		this.idCita = idCita;
	}


	public LocalDateTime getFechaCita() {
		return fechaCita;
	}


	public void setFechaCita(LocalDateTime fechaCita) {
		this.fechaCita = fechaCita;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public Paciente getPaciente() {
		return paciente;
	}


	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	public Especialista getEspecialista() {
		return especialista;
	}


	public void setEspecialista(Especialista especialista) {
		this.especialista = especialista;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	@Override
	public String toString() {
		return "Cita [idCita=" + idCita + ", fechaCita=" + fechaCita + ", correo=" + correo + ", paciente=" + paciente
				+ ", especialista=" + especialista +  ", estado=" + estado +   "]";
	}


	public static Cita fromString(String data) {
	    String[] parts = data.substring(data.indexOf("[") + 1, data.length() - 1).split(", ");
	    
	    int idCita = Integer.parseInt(parts[0].split("=")[1]);
	    LocalDateTime fechaCita = LocalDateTime.parse(parts[1].split("=")[1]);
	    String correo = parts[2].split("=")[1];
	    String pacienteData = "Paciente [" + data.substring(data.indexOf("paciente=") + "paciente=".length(), data.indexOf(", especialista=")) + "]";
	    Paciente paciente = Paciente.fromString(pacienteData);
	    String especialistaData = "Especialista [" + data.substring(data.indexOf("especialista=") + "especialista=".length());
	    Especialista especialista = Especialista.fromString(especialistaData);
	    
	    String estado = null;
	    if(parts.length == 6) {
	    	  estado = parts[5].split("=")[1];
	    }

	    return new Cita(idCita, fechaCita, correo, paciente, especialista, estado);
	}
}