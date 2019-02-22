package models.dao;

import java.util.List;

import models.dao.gson.DaoGson;

public class Repositorio<T> {
	private DaoGson<T> dao_activo;

	private DataAccessObject<T> getDaoActivo() {
		return this.dao_activo;
	}

	public void SetDaoActivo(DaoGson<T> lector, String ubicacion) {
		dao_activo = lector;
		dao_activo.abrirArchivo(ubicacion);
	}

	public void agregarItem(T itemParaAgregar) {
		try {
			this.getDaoActivo().agregar(itemParaAgregar);
		} catch (Exception e) {
			System.out.println("error en agregar");
		}
	}

	public void borrarItem(T cliente) {
		try {

		} catch (Exception e) {
			System.out.println("error en metodo borrar");
		}
	}

	public List<T> dameTodoLosObjetosPersistidos() {
		try {
			List<T> todosLosItems = this.getDaoActivo().dameTodos();
			return todosLosItems;
		}

		catch (Exception e) {
			System.out.println("error en metodo dameTodos");
			return null;
		}
	}

}// end repositorio