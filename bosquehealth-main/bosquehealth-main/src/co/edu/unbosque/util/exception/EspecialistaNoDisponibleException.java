package co.edu.unbosque.util.exception;

//Clase para manejar cuando un especialista no está disponible
public class EspecialistaNoDisponibleException extends Exception {
	public EspecialistaNoDisponibleException(String mensaje) {
		super(mensaje);
	}
}