package co.edu.unbosque.util.exception;

//Clase para manejar errores de turnos no disponibles
public class TurnoNoDisponibleException extends Exception {
 public TurnoNoDisponibleException(String mensaje) {
     super(mensaje);
 }
}