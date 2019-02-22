
package testsGson;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.entities.dominio.ClienteResidencial;

public class TestsGsonClienteResidencial 
{
	Repositorio<ClienteResidencial> repositorio = new Repositorio<ClienteResidencial>();
	DaoGson<ClienteResidencial> importador = new DaoGson<ClienteResidencial>();
	public String ubicacion = "src/test/resources/clientes.json";
	
	
	@Before
	public void initParametros()
	{
		repositorio.SetDaoActivo(importador, ubicacion);
		importador.setTipo(ClienteResidencial.class);
	}
	
	@Test
	public void testProbandoSiImporta3Clientes() throws FileNotFoundException {
		int esperado = 3;
		assertEquals(esperado,repositorio.dameTodoLosObjetosPersistidos().size());
	}
	
	@Test
	public void testProbandoSiImportaASirCosmeFulanito() throws FileNotFoundException 
	{
		List<ClienteResidencial> todosLosObjetos =  repositorio.dameTodoLosObjetosPersistidos();
		assertEquals("cosmeFulanito",todosLosObjetos.get(0).getNombre());
	}
	
}
