package co.edu.unbosque.model;

public abstract class PersonaDTO {
	protected String nombre;
	protected int identificacion;
	protected String email;
	
	public PersonaDTO(String nombre, int identificacion, String email) {
		this.nombre = nombre;
		this.identificacion = identificacion;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public int getIdentificacion() {
		return identificacion;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "PersonaDTO [nombre=" + nombre + ", identificacion=" + identificacion + ", email=" + email + "]";
	}

}
