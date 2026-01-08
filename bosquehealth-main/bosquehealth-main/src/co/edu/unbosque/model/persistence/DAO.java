package co.edu.unbosque.model.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface DAO<T, K> {
	
	T save(T entidad) throws IOException;

	void saveAll(List<T> entidades) throws IOException;

	T findById(K id)  throws IOException;

	List<T> findAll() throws IOException;

	void delete(T entidad);

	void update(T entidad) throws IOException;

}
