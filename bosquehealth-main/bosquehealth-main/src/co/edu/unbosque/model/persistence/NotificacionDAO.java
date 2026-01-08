package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.Notificacion;

public class NotificacionDAO implements DAO<Notificacion, String> {
	private final String filePath = "data/notificaciones.dat";
	private final FileHandler<Notificacion> fileHandler;
// NO REQUIERE DE DAO
	public NotificacionDAO() {
		this.fileHandler = new FileHandler<>();
	}

	@Override
	public Notificacion save(Notificacion notificacion) throws IOException {
		fileHandler.save(filePath, notificacion);
		return notificacion;
	}

	@Override
	public void saveAll(List<Notificacion> notificaciones) throws IOException {
		fileHandler.saveAll(filePath, notificaciones);
	}

	@Override
	public Notificacion findById(String destinatario) {
		return findAll().stream().filter(notificacion -> notificacion.getDestinatario().equals(destinatario))
				.findFirst().orElse(null);
	}

	@Override
	public ArrayList<Notificacion> findAll() {
		return new ArrayList<>(fileHandler.loadAll(filePath));
	}

	@Override
	public void delete(Notificacion notificacion) {
		try {
			fileHandler.delete(filePath, notificacion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Notificacion notificacion) throws IOException {
		fileHandler.update(filePath, notificacion,
				(original, updated) -> original.getDestinatario().equals(updated.getDestinatario()));
	}
}