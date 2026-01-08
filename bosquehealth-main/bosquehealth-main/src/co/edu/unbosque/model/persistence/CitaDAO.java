package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.unbosque.model.Cita;

public class CitaDAO implements DAO<Cita, Integer> {
	private final String filePath = "data/citas.dat";
	private final FileHandler<Cita> fileHandler;

	public CitaDAO() {
		this.fileHandler = new FileHandler<>();
	}

	@Override
	public Cita save(Cita cita) throws IOException {
		fileHandler.save(filePath, cita);
		return cita;
	}

	@Override
	public void saveAll(List<Cita> citas) throws IOException {
	    fileHandler.saveAll(filePath, citas);
	}

	@Override
	public Cita findById(Integer idCita) {
		return findAll().stream().filter(c -> c.getIdCita() == idCita).findFirst().orElse(null);
	}

	@Override
	public List<Cita> findAll() {
		List<Cita> citas = new ArrayList<>();
	    try {
	    	citas = fileHandler.loadAll(filePath);
		    } catch (Exception e) {
	        System.out.println("ERROR al leer archivo de Citas");
	    }
	    return citas;
	}

	@Override
	public void delete(Cita cita) {
		try {
			fileHandler.delete(filePath, cita);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Cita cita) throws IOException {
		List<Cita> data = findAll();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getIdCita() == cita.getIdCita()) {
				data.set(i, cita);
			}
		}
		saveAll(data);
	}
	
	public List<Cita> obtenerCitas() {
	    return findAll();
	}
	
	public void deleteById(int idCita) throws IOException {
        List<Cita> citas = findAll();
        citas = citas.stream()
                .filter(c -> c.getIdCita() != idCita)  
                .collect(Collectors.toList());
        fileHandler.saveAll(filePath, citas);           
    }
}
