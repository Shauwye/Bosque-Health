package co.edu.unbosque.model;

public class Reporte {
	private Especialista nombreEspecialista;
	private Paciente nombrePaciente;
	private Tratamiento diagnostico;
	private boolean cancelacionCita;
	
	public Reporte(Especialista nombreEspecialista, Paciente nombrePaciente, Tratamiento diagnostico,
			boolean cancelacionCita) {
		this.nombreEspecialista = nombreEspecialista;
		this.nombrePaciente = nombrePaciente;
		this.diagnostico = diagnostico;
		this.cancelacionCita = cancelacionCita;
	}

	public Especialista getNombreEspecialista() {
		return nombreEspecialista;
	}

	public void setNombreEspecialista(Especialista nombreEspecialista) {
		this.nombreEspecialista = nombreEspecialista;
	}

	public Paciente getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(Paciente nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public Tratamiento getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Tratamiento diagnostico) {
		this.diagnostico = diagnostico;
	}

	public boolean isCancelacionCita() {
		return cancelacionCita;
	}

	public void setCancelacionCita(boolean cancelacionCita) {
		this.cancelacionCita = cancelacionCita;
	}

	@Override
	public String toString() {
		return "Reporte [nombreEspecialista=" + nombreEspecialista + ", nombrePaciente=" + nombrePaciente
				+ ", diagnostico=" + diagnostico + ", cancelacionCita=" + cancelacionCita + "]";
	}
}
