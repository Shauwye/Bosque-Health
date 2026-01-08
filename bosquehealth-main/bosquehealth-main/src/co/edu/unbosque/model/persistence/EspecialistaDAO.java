package co.edu.unbosque.model.persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.unbosque.model.Especialista;

public class EspecialistaDAO implements DAO<Especialista, Integer> {
    private final String filePath = "data/especialistas.dat";
    private final FileHandler<Especialista> fileHandler;

    public EspecialistaDAO() {
        this.fileHandler = new FileHandler<>();
    }

    @Override
    public Especialista save(Especialista especialista) throws IOException {
        fileHandler.save(filePath, especialista);
        return especialista;
    }

    @Override
    public void saveAll(List<Especialista> especialistas) throws IOException {
       fileHandler.saveAll(filePath, especialistas);
    }

    @Override
    public Especialista findById(Integer id) throws IOException  {
        return findAll().stream()
            .filter(e -> e.getIdentificacion() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Especialista> findAll() throws IOException {
    	    List<Especialista> especialistasList = fileHandler.loadAll(filePath);  // Cargar desde el archivo binario
    	    return especialistasList;
    	}
    public Especialista buscarEspecialistaPorCredenciales(String nombreUsuario, String contrasena) throws IOException {
        List<Especialista> especialistas = findAll();
        for (Especialista esp : especialistas) {
            // Asegúrate de que la contraseña no sea null
            if (esp.getNombre().equals(nombreUsuario) && contrasena.equals(esp.getContrasena() != null ? esp.getContrasena() : "")) {
                return esp;
            }
        }
        return null; // Retorna null si no encuentra una coincidencia
    }

    @Override
    public void delete(Especialista especialista) {
        try {
            fileHandler.delete(filePath, especialista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Especialista especialista) throws IOException {
    	  System.out.println("Actualizando  especialista "+ especialista.getNombre());		
    	List<Especialista> data =  findAll();
    	for(int i=0 ; i<data.size(); i++) {
    		if(data.get(i).getIdentificacion() == especialista.getIdentificacion()) {
    			data.set(i, especialista);
    		}
    	}
    	saveAll(data);
    	
    }
    

    public void updateDisponibilidad(Especialista especialista) throws IOException {
    	  System.out.println("Quitando disponibilidad  especialista "+ especialista.getNombre());		
    	List<Especialista> data =  findAll();
    	for(int i=0 ; i<data.size(); i++) {
    		Especialista especialistaEncontrado = data.get(i);
    		if(especialistaEncontrado.getIdentificacion() == especialista.getIdentificacion()) {
    			especialistaEncontrado.setDisponibilidad(false);
    		}
    	}
    	saveAll(data);
    	
    }
   
}