package co.edu.unbosque.model;

public class EspecialistaDTO extends PersonaDTO {
	private String especialidad;
	private boolean disponibilidad;
	private String contrasena;

	public EspecialistaDTO(String nombre, int identificacion, String email, String especialidad, boolean disponibilidad,
			String contrasena) {
		super(nombre, identificacion, email);
		this.especialidad = especialidad;
		this.disponibilidad = disponibilidad;
		this.contrasena = contrasena;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}
	

	public String getContrasena() {
		return contrasena;
	}


	@Override
	public String toString() {
		return nombre + "->"+ especialidad;
	}


}
