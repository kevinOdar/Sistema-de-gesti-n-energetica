package models.dao.hibernate;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import models.dao.DataAccessObject;

public class DaoMySQL<T> implements DataAccessObject<T> {

	private ModelHelper model;
	Class<T> tipoT;

	public void init() {
		this.model = new ModelHelper();
	}

	public void setTipo(Class<T> tipo) {
		this.tipoT = tipo;
	}

	@Override
	public void agregar(T objetoAPersistir) {
		model.agregar(objetoAPersistir);
	}

	public void agregarTodos(List<T> objetosAPersistir) {
		for(T unObjeto : objetosAPersistir) {
			this.agregar(unObjeto);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public T buscar(int id) {
		return (T) model.buscar(tipoT, new ImmutablePair<>("id", id));
	}

	@SuppressWarnings("unchecked")
	public T buscarPorId(int id) {
		return (T) model.buscar(tipoT, new ImmutablePair<>("id", id));
	}

	@SuppressWarnings("unchecked")
	public T buscarPorNombre(String nombre) {
		return (T) model.buscar(tipoT, new ImmutablePair<>("nombre", nombre));
	}
	
	@SuppressWarnings("unchecked")
	public T buscarPorAlias(String alias) {
		return (T) model.buscar(tipoT, new ImmutablePair<>("aliasUsuario", alias));
	}

	@Override
	public void borrar(T objetoAPersistir) {
		model.eliminar(objetoAPersistir);
	}

	@Override
	public List<T> dameTodos() {

		return (List<T>) model.buscarTodos(tipoT);
	}

	@Override
	public void modificar(T unObjeto) {
		model.modificar(unObjeto);
	}

}
