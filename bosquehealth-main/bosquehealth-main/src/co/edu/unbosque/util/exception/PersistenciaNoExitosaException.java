package co.edu.unbosque.util.exception;

//Clase para manejar errores de persistencia de datos
public class PersistenciaNoExitosaException extends Exception {
	public PersistenciaNoExitosaException(String mensaje) {
		super(mensaje);
	}
}