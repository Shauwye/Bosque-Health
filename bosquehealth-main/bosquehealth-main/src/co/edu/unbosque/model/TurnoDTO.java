package co.edu.unbosque.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TurnoDTO {
	private int idTurno;
	private LocalDateTime fechaInicioTurno;
	private LocalDateTime fechaFinTurno;
	private String estadoTurno;
	private Especialista especialista;
	private Especialista especialistaCambio;
	
	public TurnoDTO(int idTurno, LocalDateTime fechaInicioTurno, LocalDateTime fechaFinTurno, String estadoTurno,
			Especialista especialista, Especialista especialistaCambio) {
		super();
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

	public LocalDateTime getFechaInicioTurno() {
		return fechaInicioTurno;
	}
	
	public String getFechaInicioTurnoString() {
        DateTimeFormatter formatterConHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatterSinHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		   try {
	            if (fechaInicioTurno.toLocalTime().equals(LocalDateTime.of(fechaInicioTurno.toLocalDate(), LocalDateTime.MIN.toLocalTime()).toLocalTime())) {
	                return fechaInicioTurno.toLocalDate().format(formatterSinHora);
	            }else {
	                // Si tiene hora, usar el formato completo
	                return fechaInicioTurno.format(formatterConHora);
	            }
	       
	        } catch (DateTimeParseException e) {
	          
	                System.err.println("Formato de fecha no válido: " + fechaInicioTurno.toString());
	                return null;
	        }
	}


	public LocalDateTime getFechaFinTurno() {
		return fechaFinTurno;
	}

	public String getEstadoTurno() {
		return estadoTurno;
	}

	public Especialista getEspecialista() {
		return especialista;
	}
	
	public Especialista getEspecialistaCambio() {
		return especialistaCambio;
	}
	
	

}
