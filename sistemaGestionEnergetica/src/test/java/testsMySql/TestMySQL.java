package testsMySql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dominio.CategoriaConsumo;

public class TestMySQL {

	
	@Test
	public void testExisteCategoria1() {
		DaoMySQL<CategoriaConsumo> importador = new DaoMySQL<CategoriaConsumo>();
		importador.init();
		importador.setTipo(CategoriaConsumo.class);
		CategoriaConsumo cat1 = importador.buscar(1);
		assertEquals("R1", cat1.getNombre());
	}
	
	@Test
	public void testProbandoDameTodos() {
		DaoMySQL<CategoriaConsumo> importador = new DaoMySQL<CategoriaConsumo>();
		importador.init();
		importador.setTipo(CategoriaConsumo.class);
		List<CategoriaConsumo> categorias = importador.dameTodos();
		assertNotNull(categorias);
	}
	
	@Test
	public void testExisteCategoria() {
		DaoMySQL<Caracteristica> importador = new DaoMySQL<Caracteristica>();
		importador.init();
		importador.setTipo(Caracteristica.class);
		Caracteristica caracteristica = importador.buscarPorNombre("LED 24\"");
		assertEquals("LED 24\"", caracteristica.getNombre());
	}
}
