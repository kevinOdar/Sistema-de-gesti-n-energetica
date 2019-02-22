package testsDominio;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.entities.dominio.CategoriaConsumo;

public class TestsCategorias 
{
	CategoriaConsumo categoria = new CategoriaConsumo();
	DaoGson<CategoriaConsumo> importador = new DaoGson<CategoriaConsumo>();
	public String ubicacion = "src/test/resources/categorias.json";
	Repositorio<CategoriaConsumo> repositorio = new Repositorio<CategoriaConsumo>();

	@Before
	public void initParametros() 
	{
		categoria.setNombre("R1");
		repositorio.SetDaoActivo(importador, ubicacion);
		importador.setTipo(CategoriaConsumo.class);
	}
	
	@Test
	public void testCategoriaNombre() 
	{
		String esperado = "R1";
		String real = categoria.getNombre();
		
		assertEquals(esperado,real);
	}
	
	@Test
	public void testProbarSiExisteUnaCategoria() throws FileNotFoundException 
	{
		List<CategoriaConsumo> categorias = repositorio.dameTodoLosObjetosPersistidos();
		assertEquals("R1",categorias.get(0).getNombre());
	}
}
