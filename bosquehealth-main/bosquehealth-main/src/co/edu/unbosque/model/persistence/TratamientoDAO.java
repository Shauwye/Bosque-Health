package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.List;
import co.edu.unbosque.model.Tratamiento;

public class TratamientoDAO implements DAO<Tratamiento, String> {
	private final String filePath = "data/tratamientos.txt";
	private final FileHandler<Tratamiento> fileHandler;

	public TratamientoDAO() {
		this.fileHandler = new FileHandler<>();
	}

	@Override
	public Tratamiento save(Tratamiento tratamiento) throws IOException {
		fileHandler.save(filePath, tratamiento);
		return tratamiento;
	}

	@Override
	public void saveAll(List<Tratamiento> tratamientos) throws IOException {
		fileHandler.saveAll(filePath, tratamientos);
	}

	@Override
	public Tratamiento findById(String descripcion) {
		return findAll().stream().filter(tratamiento -> tratamiento.getDescripcion().equals(descripcion)).findFirst()
				.orElse(null);
	}

	@Override
	public List<Tratamiento> findAll() {
		List<Tratamiento> listaTratamientos = fileHandler.loadAll(filePath);
		return listaTratamientos;
	}

	@Override
	public void delete(Tratamiento tratamiento) {
		try {
			fileHandler.delete(filePath, tratamiento);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Error al borrar el tratamiento");
		}
	}

	@Override
	public void update(Tratamiento tratamiento) throws IOException {
		System.out.println("Actualizando  tratamiento " + tratamiento.getDescripcion());
		List<Tratamiento> data = findAll();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getDescripcion() == tratamiento.getDescripcion()) {
				data.set(i, tratamiento);
			}
		}
		saveAll(data);
	}
	
}

