package co.edu.unbosque.model.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileHandler<T> {

	/**
	 * Guarda todos los datos recibidos en archivos tipo  OBJETO
	 * @param filePath
	 * @param data
	 * @throws IOException
	 */
	public void saveAll(String filePath, List<T> data) throws IOException {
		ensureDirectoryExists(filePath);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(data);
		}
	}
	
	/**
	 * Guarda todos los datos recibidos en archivos de texto
	 * @param filePath
	 * @param data
	 * @throws IOException
	 */
	public void saveAllTXTFile(String filePath, List<T> data) throws IOException {
		File file = new File(filePath);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for(T item: data) {
				bw.write(item.toString());
				bw.newLine();
			}
		}
	}
	
	public void saveTXTFile(String filePath, T data) throws IOException {
		File file = new File(filePath);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
			bw.write(data.toString());
			bw.newLine();
		}
	}
	
	public List<String> loadAllTXT(String filePath) throws IOException {
		List<String> lines = new ArrayList<String>();
		File file = new File(filePath);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				if(line != null) {
				lines.add(line);
				}
			}
		}
		return lines;
	}

	// Carga una lista de objetos desde un archivo binario
	public List<T> loadAll(String filePath) {
		List<T> data = new ArrayList<T>();
		File file = new File(filePath);
		if (!file.exists()) {
			return data; // Devuelve una lista vacía si el archivo no existe
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			data = (List<T>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Archivo de Datos Vacio");
		}
		return data;
	}

	// Guarda un solo objeto en el archivo (agrega el objeto a la lista existente)
	public void save(String filePath, T item) throws IOException {
		List<T> data = loadAll(filePath);
		data.add(item);
		saveAll(filePath, data);
	}
	
	// Guarda un solo objeto en el archivo (agrega el objeto a la lista existente)
		public void saveTXT(String filePath, T item) throws IOException {
			saveTXTFile(filePath, item);
		}


	// Borra un objeto específico de la lista en el archivo binario
	public void delete(String filePath, T item) throws IOException {
		List<T> data = loadAll(filePath);
		data.remove(item);
		saveAll(filePath, data);
	}

	// Actualiza un objeto específico en el archivo, reemplazándolo en la lista
	public void update(String filePath, T updatedItem, Identifiable<T> identifier) throws IOException {
		List<T> data = loadAll(filePath);
		for (int i = 0; i < data.size(); i++) {
			if (identifier.isEqual(data.get(i), updatedItem)) {
				data.set(i, updatedItem);
				break;
			}
		}
		saveAll(filePath, data);
	}
	public List<T> findAllBin(String filePath) {
	    List<T> data = new ArrayList<>();
	    File file = new File(filePath);
	    if (!file.exists()) {
	        return data; // Devuelve una lista vacía si el archivo no existe
	    }
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	        data = (List<T>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return data;
	}

	// Métodopara asegurar que el directorio existe
	private void ensureDirectoryExists(String filePath) {
		File file = new File(filePath);
		File directory = file.getParentFile();
		if (directory != null && !directory.exists()) {
			directory.mkdirs();
		}
	}

	// Interfaz para identificar objetos en una lista
	public interface Identifiable<T> {
		boolean isEqual(T original, T updated);
	}
}
