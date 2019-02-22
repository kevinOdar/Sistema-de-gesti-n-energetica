package testsGson;


import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.entities.transformadores.Transformador;

public class TestsGsonTransformador 
{
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
	public void testProbandoSiImporta1Transformador() throws FileNotFoundException {
		int esperado = 1;
		assertEquals(esperado,repositorio.dameTodoLosObjetosPersistidos().size());
	}
}
	