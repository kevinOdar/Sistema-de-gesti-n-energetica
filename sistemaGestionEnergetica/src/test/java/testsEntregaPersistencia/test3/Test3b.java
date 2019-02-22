package testsEntregaPersistencia.test3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Inteligente;
import models.entities.regla.Regla;
import models.entities.sensor.Sensor;

public class Test3b {
	private DaoMySQL<Regla> dao;

	@Before
	public void init() {
		dao = new DaoMySQL<Regla>();
		dao.init();
		dao.setTipo(Regla.class);
	}

	@Test
	public void testRecuperarRegla() {
		Regla reglaRecup = dao.buscarPorNombre("prender luz");

		assertEquals("prender luz", reglaRecup.getNombre());
	}

	@Test
	public void testEjecutarReglaRecuperada() {
		Regla reglaRecup = dao.buscarPorNombre("prender luz");
		Inteligente dispRecup = reglaRecup.getDispositivoInteligente();
		Sensor unSensRecup = reglaRecup.getSensores().get(0);
		
		dispRecup.apagar();

		unSensRecup.anotarMedicion(30);
		
		assertTrue(dispRecup.estasPrendido());
	}
}
