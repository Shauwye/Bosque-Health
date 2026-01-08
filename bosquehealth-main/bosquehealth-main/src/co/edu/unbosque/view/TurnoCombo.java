package co.edu.unbosque.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TurnoCombo {
	private int idTurno;
	private LocalDateTime fechaInicioTurno;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	
	public TurnoCombo(int idTurno, LocalDateTime fechaInicioTurno) {
		this.idTurno = idTurno;
		this.fechaInicioTurno = fechaInicioTurno;
	}


	public int getIdTurno() {
		return idTurno;
	}
	
	@Override
	public String toString() {
		return fechaInicioTurno.toLocalDate().format(formatter);
	}
}
