package testsGson;

import java.io.IOException;

import org.junit.*;

import models.dao.gson.DaoGsonPrueba;
import models.entities.dominio.CategoriaConsumo;

public class TestGson {
	DaoGsonPrueba<CategoriaConsumo> importador = new DaoGsonPrueba<CategoriaConsumo>();
	public String ubicacion = "src/test/resources/categorias.json";
	public String prueba = "src/test/resources/categoriasPrueba.json";
	public CategoriaConsumo catDummy;
	public CategoriaConsumo catDummy2;
	
	@Before
	public void initParametros() throws IOException {
		catDummy = new CategoriaConsumo();
		catDummy2 = new CategoriaConsumo();
		catDummy.setId(1);
		catDummy.setNombre("catDummy");
		catDummy2.setId(2);
		catDummy2.setNombre("catDummy2");
		importador.setTipoT(CategoriaConsumo.class);
		importador.setWriter(prueba);
	}
	
	@Test
	public void testPersistir2() throws IOException {
		importador.agregar(catDummy);
		importador.agregar(catDummy2);
		importador.cerrarWriter();
	}
	
	@Test
	public void testRecuperar() throws IOException {
		importador.abrirArchivo(ubicacion);
		CategoriaConsumo cat = importador.buscar(2);
		importador.cerrarArchivo();
		if(cat != null) System.out.format("%s\n", cat.getNombre());
		else System.out.println("no encontrado");
	}
}
