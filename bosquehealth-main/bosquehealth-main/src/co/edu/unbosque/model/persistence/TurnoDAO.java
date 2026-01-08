package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.unbosque.model.Turno;

public class TurnoDAO implements DAO<Turno, Integer> {
	private final String fileTXTPath = "data/turnos.txt";
	private final FileHandler<Turno> fileHandler;

	public TurnoDAO() {
		this.fileHandler = new FileHandler<>();
	}

	@Override
	public Turno save(Turno turno) throws IOException {
		 System.out.println("Almacenando Turno...");		
		fileHandler.saveTXT(fileTXTPath, turno);
		return turno;
	}

	@Override
	public void saveAll(List<Turno> turnos) throws IOException {
		fileHandler.saveAllTXTFile(fileTXTPath, turnos);
	}

	@Override
	public Turno findById(Integer idTurno) {
		return findAll().stream()
				.filter(turno -> turno.getIdTurno() == idTurno)
				.findFirst()
				.orElse(null);
	}

	@Override
	public ArrayList<Turno> findAll() {
		ArrayList<Turno> turnos = new ArrayList<>();
		try {
			List<String> lines = fileHandler.loadAllTXT(fileTXTPath);
			turnos = lines.stream()
					.map(Turno::fromString)
					.collect(Collectors.toCollection(ArrayList::new));
		} catch (Exception e) {
			System.out.println("ERROR al leer archivo de Turnos");
		}
		return turnos;
	}

	@Override
	public void delete(Turno turno) {
		try {
			fileHandler.delete(fileTXTPath, turno);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Turno turno) throws IOException {
		ArrayList<Turno> data = findAll();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getIdTurno() == turno.getIdTurno()) {
				data.set(i, turno);
			}
		}
		saveAll(data);
	}
}
