package co.edu.unbosque.model;

public class NotificacionDTO {
	private String destinatario;
	private String asunto;
	private String mensaje;

	public NotificacionDTO(String destinatario, String asunto, String mensaje) {
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
	}

	protected String getDestinatario() {
		return destinatario;
	}

	protected String getAsunto() {
		return asunto;
	}

	protected String getMensaje() {
		return mensaje;
	}

	@Override
	public String toString() {
		return "NotificacionDTO [destinatario=" + destinatario + ", asunto=" + asunto + ", mensaje=" + mensaje + "]";
	}

}
