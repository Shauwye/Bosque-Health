package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.List;
import co.edu.unbosque.model.Examen;

public class ExamenDAO implements DAO<Examen, Integer> {
	private final String filePath = "data/examenes.dat";
	private final FileHandler<Examen> fileHandler;

	public ExamenDAO() {
		this.fileHandler = new FileHandler<>();
	}

	@Override
	public Examen save(Examen examen) throws IOException {
		fileHandler.save(filePath, examen);
		return examen;
	}

	@Override
	public void saveAll(List<Examen> examenes) throws IOException {
		fileHandler.saveAll(filePath, examenes);
	}

	@Override
	public Examen findById(Integer idExamen) {
		return findAll().stream().filter(examen -> examen.getIdExamen() == idExamen).findFirst().orElse(null);
	}

	@Override
	public List<Examen> findAll() {
		List<Examen> examenesList = fileHandler.loadAll(filePath);
		return examenesList;
	}

	@Override
	public void delete(Examen examen) {
		try {
			fileHandler.delete(filePath, examen);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Error al borrar el examen");
		}
	}

	@Override
	public void update(Examen examen) throws IOException {
		  System.out.println("Actualizando  examen "+ examen.getIdExamen());		
	    	List<Examen> data =  findAll();
	    	for(int i=0 ; i<data.size(); i++) {
	    		if(data.get(i).getIdExamen() == examen.getIdExamen()) {
	    			data.set(i, examen);
	    		}
	    	}
		saveAll(data);
	}
}
