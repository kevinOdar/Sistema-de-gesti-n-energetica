package models.dao.gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.dao.DataAccessObject;
import models.dao.hibernate.EntidadPersistente;

public class DaoGsonPrueba<T> implements DataAccessObject<T> {

	private FileReader archivoPersistidos;
	private Writer archivoNuevo;

	Type tipoT;

	Gson gson = new GsonBuilder().create();

	public Type getTipoT() {
		return tipoT;
	}

	public void setTipoT(Type tipoT) {
		this.tipoT = tipoT;
	}

	/*
	 * public void abrirArchivo(String ubicacion) { try { lectorDeArchivo = new
	 * FileReader(ubicacion); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } archivo = new BufferedReader(lectorDeArchivo); }
	 */

	public void abrirArchivo(String ubicacion) throws FileNotFoundException {
		this.archivoPersistidos = new FileReader(ubicacion);
	}

	public void cerrarArchivo() throws IOException {
		archivoPersistidos.close();
	}

	public void setWriter(String ubicacion) throws IOException {
		this.archivoNuevo = new FileWriter(ubicacion);
	}

	public void cerrarWriter() throws IOException {
		this.archivoNuevo.close();
	}

	@Override
	public void modificar(T objetoAModificar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregar(T objetoAPersistir) {
		gson.toJson(objetoAPersistir, archivoNuevo);
	}

	@Override
	public void borrar(T objetoAPersistir) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<T> dameTodos() {
		Type tipoLista = new TypeToken<ArrayList<T>>() {
		}.getType();

		List<T> variosObjetos = gson.fromJson(archivoPersistidos, tipoLista);

		if (variosObjetos != null) {
			System.out.format("Objetos recuperados: %d\n", variosObjetos.size());

		} else
			System.out.println("null");

		return variosObjetos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T buscar(int id) {
		T unObjeto = null;

		Type tipoLista = new TypeToken<ArrayList<T>>() {
		}.getType();

		List<T> variosObjetos = gson.fromJson(archivoPersistidos, tipoLista);

		if (variosObjetos != null)
			System.out.format("Objetos recuperados: %d\n", variosObjetos.size());
		else
			System.out.println("null");

		unObjeto = (T) this.buscarPorId((List<EntidadPersistente>) variosObjetos, id);

		return unObjeto;
	}

	private EntidadPersistente buscarPorId(List<EntidadPersistente> lista, int id) {
		return lista.stream().filter(otroObjeto -> ((EntidadPersistente) otroObjeto).getId() == id)
				.collect(Collectors.toList()).get(0);
	}
}
