package co.edu.unbosque.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CitaDTO {
	private int idCita;
	private LocalDateTime fechaCita;
	private String correo;
	private PacienteDTO paciente;
	private EspecialistaDTO especialista;
	private String estado;
	
	public CitaDTO(int idCita, LocalDateTime fechaCita, String correo, PacienteDTO paciente,
			EspecialistaDTO especialista, String estado) {
		this.idCita = idCita;
		this.fechaCita = fechaCita;
		this.correo = correo;
		this.paciente= paciente;
		this.especialista = especialista;
		this.estado = estado;
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
	
	public String getFechaCitaString() {
        DateTimeFormatter formatterConHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatterSinHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		   try {
	            if (fechaCita.toLocalTime().equals(LocalDateTime.of(fechaCita.toLocalDate(), LocalDateTime.MIN.toLocalTime()).toLocalTime())) {
	                return fechaCita.toLocalDate().format(formatterSinHora);
	            }else {
	                // Si tiene hora, usar el formato completo
	                return fechaCita.format(formatterConHora);
	            }
	       
	        } catch (DateTimeParseException e) {
	          
	                System.err.println("Formato de fecha no válido: " + fechaCita.toString());
	                return null;
	        }
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
	public PacienteDTO getPacientedto() {
		return paciente;
	}
	public void setPacientedto(PacienteDTO pacientedto) {
		this.paciente = pacientedto;
	}
	public EspecialistaDTO getEspecialistadto() {
		return especialista;
	}
	public void setEspecialistadto(EspecialistaDTO especialistadto) {
		this.especialista = especialistadto;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "CitaDTO [idCita=" + idCita + ", fechaCita=" + fechaCita + ", correo=" + correo + ", pacientedto="
				+ paciente + ", especialistadto=" + especialista + ", estado=" + estado + "]";
	}
}
