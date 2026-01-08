package co.edu.unbosque.util.exception;

//Clase para manejar excesos de turnos asignados
public class ExcesoDeTurnosException extends Exception {
	public ExcesoDeTurnosException(String mensaje) {
		super(mensaje);
	}
}