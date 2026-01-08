package co.edu.unbosque.model;

public class PacienteDTO extends PersonaDTO {
	private String historialMedico;
	private String contrasena;

	public PacienteDTO(String nombre, int identificacion, String email, String historialMedico, String contrasena) {
		super(nombre, identificacion, email);
		this.historialMedico = historialMedico;
		this.contrasena = contrasena;
	}

	public String getHistorialMedico() {
		return historialMedico;
	}
	

	public String getContrasena() {
		return contrasena;
	}

	@Override
	public String toString() {
		return  nombre;
	}
	 public String getCorreo() {
	        return getEmail();  // Llama al método heredado de PersonaDTO
	    }

}
