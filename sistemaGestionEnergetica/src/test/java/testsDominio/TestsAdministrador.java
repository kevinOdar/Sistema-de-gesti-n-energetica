package testsDominio;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import models.dao.Repositorio;
import models.dao.gson.DaoGson;
import models.entities.dominio.Administrador;

public class TestsAdministrador 
{
	Administrador administrador;
	DateTime fechaDePrueba;
	
	//prueba con un administrador creado en etapa de ejecuci'on
	@Before
	public void initParametros()
	{
		administrador = new Administrador();
		fechaDePrueba = DateTime.now();
	}

	@Test
    public void testLleva11MesesComoAdministrador()
	{
		
		administrador.setFechaAlta(fechaDePrueba.minusMonths(11));
		assertEquals(11, administrador.tiempoTranscurridoDesdeAlta());
    }
	

    //test con administradores importados de un archivo json
	DaoGson<Administrador> importador = new DaoGson<Administrador>();
	public String ubicacion = "src/test/resources/administrador.json";
	Repositorio<Administrador> repositorio = new Repositorio<Administrador>();
	
	@Test
	public void testProbandoSiImporta() throws FileNotFoundException 
	{
		repositorio.SetDaoActivo(importador, ubicacion);
		importador.setTipo(Administrador.class);
		int esperado = 2;
		assertEquals(esperado,repositorio.dameTodoLosObjetosPersistidos().size());
	}
} 
