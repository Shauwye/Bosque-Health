package co.edu.unbosque.model;

import java.io.Serializable;

public class Notificacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String destinatario;
	private String asunto;
	private String mensaje;

	public Notificacion(String destinatario, String asunto, String mensaje) {
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "Notificacion [destinatario=" + destinatario + ", asunto=" + asunto + ", mensaje=" + mensaje + "]";
	}

}
