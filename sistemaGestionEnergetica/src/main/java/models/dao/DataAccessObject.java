package models.dao;

import java.util.List;

public interface DataAccessObject<T> {	
		
	public void modificar(T objetoAModificar);

	public void agregar(T objetoAPersistir);

	public void borrar(T objetoAPersistir);

	public List<T> dameTodos();

	public T buscar(int id);

	
}

