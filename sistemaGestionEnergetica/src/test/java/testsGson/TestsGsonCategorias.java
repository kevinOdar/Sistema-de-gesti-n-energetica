package testsGson;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.entities.dominio.CategoriaConsumo;

public class TestsGsonCategorias {
	
	DaoGson<CategoriaConsumo> importador = new DaoGson<CategoriaConsumo>();
	public String ubicacion = "src/test/resources/categorias.json";
	Repositorio<CategoriaConsumo> repositorio = new Repositorio<CategoriaConsumo>();
	
	@Before
	public void initParametros()
	{
		repositorio.SetDaoActivo(importador, ubicacion);
		importador.setTipo(CategoriaConsumo.class);
	}
	
	@Test
	public void testProbandoSiImportaGson() throws FileNotFoundException 
	{
		int esperado = 9;
		assertEquals(esperado,repositorio.dameTodoLosObjetosPersistidos().size());
	}
	
}
