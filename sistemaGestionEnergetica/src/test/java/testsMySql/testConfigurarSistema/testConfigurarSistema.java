package testsMySql.testConfigurarSistema;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import models.configurador.Configurador;
import models.entities.sensor.Sensor;

public class testConfigurarSistema {


	@Before
	public void init() {
		Sensor.setMedicionesMaximas(0);
	}
	
	@Test
	public void testSensorAceptaHasta20Mediciones() {
		Configurador.get().configurarSistema();
		assertEquals(20, Sensor.getMedicionesMaximas());
	}
}
