package co.edu.unbosque.util.exception;

//Clase para manejar errores de envío de notificaciones
public class NotificacionFallidaException extends Exception {
	public NotificacionFallidaException(String mensaje) {
		super(mensaje);
	}
}