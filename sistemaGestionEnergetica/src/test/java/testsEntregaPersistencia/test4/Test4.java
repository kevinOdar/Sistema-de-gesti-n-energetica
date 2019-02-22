package testsEntregaPersistencia.test4;

 import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.dao.hibernate.DaoMySQL;
import models.entities.transformadores.Transformador;
import testsDummies.Transformador1;


/*Caso de prueba 4:
Recuperar todos los transformadores persistidos. Registrar la cantidad.
Agregar una instancia de Transformador al JSON de entradas. Ejecutar el
método de lectura y persistencia. Evaluar que la cantidad actual sea la anterior
+ 1.*/

public class Test4 {
	
	Repositorio<Transformador> repositorio = new Repositorio<Transformador>();
	DaoGson<Transformador> importador = new DaoGson<Transformador>();
	public String ubicacion = "src/test/resources/transformadores.json";
	
	@Before
	public void initParametros() 
	{
		repositorio.SetDaoActivo(importador, ubicacion);
		importador.setTipo(Transformador.class);
	    
	}
	
	@Test
	public void testCantidadDeTransformadores() throws FileNotFoundException {	
	
	//Recupero los Transformadores persistidos en JSON y Registro la cantidad que tiene el JSON
		importador.abrirArchivo(ubicacion); 
	    List<Transformador> transformadoresPersistidosEnJson = repositorio.dameTodoLosObjetosPersistidos(); 
	    int cantidadTransformadores = transformadoresPersistidosEnJson.size();
	
	//Creo y agrego una nueva instancia de Transformador
	 
	    Transformador transformadorNuevo = Transformador1.getTransformador();
		 transformadoresPersistidosEnJson.add(transformadorNuevo);
		 
	//Leo y Persisto en DaoMySQL
	 DaoMySQL<Transformador> importador2 = new DaoMySQL<Transformador>();
		importador2.init();
		importador2.setTipo(Transformador.class);
		importador2.agregarTodos(transformadoresPersistidosEnJson);
		
	//La cantidad actual de Transformadores es igual a la cantidad anterior + 1
		int esperado = cantidadTransformadores + 1;
		assertEquals(esperado, transformadoresPersistidosEnJson.size());
				
	}
}
