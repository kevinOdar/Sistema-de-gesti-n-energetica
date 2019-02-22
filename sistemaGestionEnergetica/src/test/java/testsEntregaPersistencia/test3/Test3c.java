package testsEntregaPersistencia.test3;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.dao.hibernate.DaoMySQL;
import models.entities.regla.Regla;
import models.entities.regla.criterios.Igualdad;

public class Test3c {
	private DaoMySQL<Regla> dao;

	@Before
	public void init() {
		dao = new DaoMySQL<Regla>();
		dao.init();
		dao.setTipo(Regla.class);
	}
	
	@Test
	public void testModificarValorCriterio() {
		Regla reglaRecup = dao.buscarPorNombre("prender luz");
		Igualdad critRecup = (Igualdad) reglaRecup.getCriterios().get(0);
		
		critRecup.setValorComparacion(40);
		
		dao.modificar(reglaRecup);
		
		Regla reglaModifRecup = dao.buscarPorNombre("prender luz");
		Igualdad critModifRecup = (Igualdad) reglaModifRecup.getCriterios().get(0);
		
		assertEquals(40, critModifRecup.getValorComparacion());
	}
}
