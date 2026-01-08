package co.edu.unbosque.model;

import java.io.Serializable;

public abstract class Persona  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String nombre;
    protected int identificacion;
    protected String email;

    public Persona(String nombre, int identificacion, String email) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", identificacion=" + identificacion + ", email=" + email + "]";
    }
}