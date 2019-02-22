package models.dao.hibernate;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class ModelHelper {
	private static EntityManagerFactory factory;
	private static ThreadLocal<EntityManager> threadLocal;
	
	static {
		try {
			ModelHelper.factory = Persistence.createEntityManagerFactory("db");
			ModelHelper.threadLocal = new ThreadLocal<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EntityManager entityManager() {
		return ModelHelper.getEntityManager();
	}
	
	public static EntityManager getEntityManager() {
		EntityManager manager = ModelHelper.threadLocal.get();
		if (manager == null || !manager.isOpen()) {
		    manager = ModelHelper.factory.createEntityManager();
		    ModelHelper.threadLocal.set(manager);
		}
		return manager;
	}
	
	public static void rollback(){
	    	EntityManager em = ModelHelper.getEntityManager();
			EntityTransaction tx = em.getTransaction();
			if(tx.isActive()){
				tx.rollback();
			}
	}
	 
	private String execute(String deNombre, Object unObjeto) {
		String resultado = null;
		this.initTransaccion();
		try{
			Method unMetodo = this.entityManager().getClass().getMethod(deNombre, new Object().getClass());
			this.initTransaccion();
			unMetodo.invoke(this.entityManager(),unObjeto);
			this.commitTransaccion();
		}
		catch(Exception ex) {
			resultado = ex.toString();
			ex.printStackTrace();
		}
		return resultado;
	}
	
	private void initTransaccion() {
		if(!this.entityManager().getTransaction().isActive()) {
			this.entityManager().getTransaction().begin();
		}
	}
	
	private void commitTransaccion() {
		if(this.entityManager().getTransaction().isActive()) {
			this.entityManager().getTransaction().commit();
		}
	}
	
	public String agregar(Object unObjeto) {
		String resultado = null;
		resultado = this.execute("persist", unObjeto);
		return resultado;
	}
	
	public String modificar(Object unObjeto) {
		String resultado = null;
		resultado = this.execute("merge", unObjeto);
		return resultado;
	}
	
	public String eliminar(Object unObjeto) {
		String resultado = null;
		resultado = this.execute("remove", unObjeto);
		return resultado;
	}
	
	public <T> T buscar(Class<T> clase, int id) {
		T find = (T) this.entityManager().find(clase, id);
		return find;
	}
	
	@SuppressWarnings("unchecked")
	private <T> TypedQuery<T> generarQueryPara(Class<T> clase, ImmutablePair<Object, Object> ... pair){
		String condiciones =  " where ";
		for(int index = 0; index<pair.length; index++) {
			condiciones+=(pair[index].left.toString()+" =:"+pair[index].left.toString());
		}
		TypedQuery<T> query = this.entityManager().createQuery("from "+clase.getName()+condiciones, clase);
		for(int index = 0; index<pair.length; index++) {
			query.setParameter(pair[index].left.toString(), pair[index].right);
		}
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T buscar(Class<T> clase, ImmutablePair<Object, Object> ... pair) {
		TypedQuery<T> query = this.generarQueryPara(clase, pair);
		List<T> resultados = query.getResultList();
		return resultados.get(query.getFirstResult());
	}
	
	public <T> List<T> buscarTodos(Class<T> clase) {
		List<T> resultList = (List<T>) this.entityManager().createQuery("from "+clase.getName(), clase).getResultList();
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> buscarTodos(Class<T> clase, ImmutablePair<Object, Object> ... pair) {
		TypedQuery<T> query = this.generarQueryPara(clase, pair);
		return query.getResultList();
	}
	
	public void cerrarEntityManager() {
		EntityManager em = threadLocal.get();
		ModelHelper.threadLocal.set(null);
		em.close();
    }
	
	public  void withTransaction(Runnable action) {
		withTransaction(() -> {
			action.run();
			return null;
		});
	}
	
    public  <A> A withTransaction(Supplier<A> action) {
    	this.initTransaccion();
    	try {
    		A result = action.get();
    		this.commitTransaccion();
			return result;
    	} catch(Throwable e) {
    		rollback();
    		throw e;
    	}
    }
}