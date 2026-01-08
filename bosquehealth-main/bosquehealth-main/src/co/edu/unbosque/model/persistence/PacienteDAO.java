package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.unbosque.model.Paciente;

public class PacienteDAO implements DAO<Paciente, Integer> {
    private final String  filePath = "data/pacientes.dat";
    private final FileHandler<Paciente> fileHandler;

    public PacienteDAO() {
        this.fileHandler = new FileHandler<>();
    }

    @Override
    public Paciente save(Paciente paciente) throws IOException {
        fileHandler.save(filePath, paciente); 
        return paciente;
    }

    @Override
    public void saveAll(List<Paciente> pacientes) throws IOException {
        fileHandler.saveAll(filePath, pacientes);
    }

    @Override
    public Paciente findById(Integer id) {
        return findAll().stream()
            .filter(paciente -> paciente.getIdentificacion() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        try {
        	pacientes = fileHandler.loadAll(filePath); 
        } catch (Exception e) {
            System.out.println("ERROR al leer archivo de Pacientes");
        }
        return pacientes;
    }

    @Override
    public void delete(Paciente paciente) {
        try {
            fileHandler.delete(filePath, paciente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Paciente paciente) throws IOException {
        List<Paciente> data = findAll();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIdentificacion() == paciente.getIdentificacion()) {
                data.set(i, paciente);
            }
        }
        saveAll(data);
    }
}
