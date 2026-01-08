package co.edu.unbosque.model;

public class Paciente extends Persona {
	private static final long serialVersionUID = 1L;
	private String historialMedico;
	private String contrasena;

	public Paciente(String nombre, int identificacion, String email, String historialMedico, String contrasena) {
		super(nombre, identificacion, email);
		this.historialMedico = historialMedico;
		this.contrasena = contrasena;
	}

	public String getHistorialMedico() {
		return historialMedico;
	}

	public void setHistorialMedico(String historialMedico) {
		this.historialMedico = historialMedico;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	 public String getCorreo() {
	        return getEmail(); 
	    }

	@Override
	public String toString() {
		return "Paciente [historialMedico=" + historialMedico + ", nombre=" + nombre + ", identificacion="
				+ identificacion + ", email=" + email + "]";
	}

	public static Paciente fromString(String data) {
        String[] parts = data.substring(data.indexOf("[") + 1, data.length() - 1).split(", ");

        String historialMedico = parts[0].split("=")[1].trim();
        String nombre = parts[1].split("=")[1].trim();
        int identificacion = Integer.parseInt(parts[2].split("=")[1].trim());
        String email = parts[3].split("=")[1].trim();
        String contrasena = parts[4].split("=")[1].trim();

        return new Paciente(nombre, identificacion, email, historialMedico, contrasena);
    }
}

