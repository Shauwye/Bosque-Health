package co.edu.unbosque.model;

import java.io.Serializable;

public class Especialista extends Persona  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String especialidad;
	private boolean disponibilidad;
	private String contrasena;
	
	public Especialista(String nombre, int identificacion, String email, String especialidad, boolean disponibilidad,
			String contrasena) {
		super(nombre, identificacion, email);
		this.especialidad = especialidad;
		this.disponibilidad = disponibilidad;
		this.contrasena = contrasena;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "Especialista [especialidad=" + especialidad + ", disponibilidad=" + disponibilidad + ", nombre="
				+ nombre + ", identificacion=" + identificacion + ", email=" + email + "]";
	}
	
	public static Especialista fromString(String data) {
	    String[] parts = data.split(",");

	    String especialidad = parts[0].split("=")[1];
	    String disponibilidad = parts[1].split("=")[1];
	    String nombre = parts[2].split("=")[1];
	    String identificacion = parts[3].split("=")[1];
	    String email = parts[4].split("=")[1].replace("]", "");

	    Especialista esp = new Especialista(nombre, Integer.parseInt(identificacion), email, especialidad, Boolean.parseBoolean(disponibilidad), "");
	    return esp;
	}
}
