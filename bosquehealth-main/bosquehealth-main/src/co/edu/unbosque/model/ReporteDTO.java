package co.edu.unbosque.model;

public class ReporteDTO {
	private EspecialistaDTO nombreEspecialista;
	private PacienteDTO nombrePaciente;
	private TratamientoDTO diagnostico;
	private boolean cancelacionCita;
	
	public ReporteDTO(EspecialistaDTO nombreEspecialista, PacienteDTO nombrePaciente, TratamientoDTO diagnosticoDTO,
			boolean cancelacionCita) {
		this.nombreEspecialista = nombreEspecialista;
		this.nombrePaciente = nombrePaciente;
		this.diagnostico = diagnosticoDTO;
		this.cancelacionCita = cancelacionCita;
	}



	public EspecialistaDTO getNombreEspecialista() {
		return nombreEspecialista;
	}



	public PacienteDTO getNombrePaciente() {
		return nombrePaciente;
	}



	public TratamientoDTO getDiagnostico() {
		return diagnostico;
	}



	public boolean isCancelacionCita() {
		return cancelacionCita;
	}



	@Override
	public String toString() {
		return "ReporteDTO [nombreEspecialista=" + nombreEspecialista + ", nombrePaciente=" + nombrePaciente
				+ ", diagnostico=" + diagnostico + ", cancelacionCita=" + cancelacionCita + "]";
	}

}
