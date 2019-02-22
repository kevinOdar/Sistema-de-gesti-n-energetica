package testRegla;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import models.entities.dispositivo.*;
import models.entities.regla.*;
import models.entities.regla.actuadores.ActuadorBase;
import models.entities.regla.actuadores.Apagar;
import models.entities.regla.criterios.*;
import models.entities.sensor.*;

public class testReglaVariosSensores {
	public Inteligente dispositivo = new Inteligente();
	public Sensor sensorTemperatura = new Sensor();
	public Sensor sensorHumedad = new Sensor();
	public Regla regla = new Regla();
	public ActuadorBase apagar = new Apagar();

	@Before
	public void init() {
		dispositivo.setNombre("dummy");
		dispositivo.prender();
		apagar.setCanal(dispositivo.getNombre());
		Criterio criterioComp30 = new Mayor();
		Criterio criterioHumedad = new Igualdad();
		criterioComp30.setValorComparacion(30);
		criterioHumedad.setValorComparacion(20);
		regla.suscribirme(sensorTemperatura);
		regla.suscribirme(sensorHumedad);
		regla.agregarUnCriterio(criterioComp30, sensorTemperatura);
		regla.agregarUnCriterio(criterioHumedad, sensorHumedad);
		regla.agregarActuador(apagar);
	}

	@Test
	public void testNoTieneQueApagarConTemp30() {
		sensorTemperatura.anotarMedicion(30);
		assertFalse(dispositivo.estasApagado());

	}

	@Test
	public void testTieneQueApagarConTemp40YHum20() {
		sensorTemperatura.anotarMedicion(40);
		sensorHumedad.anotarMedicion(20);
		assertTrue(dispositivo.estasApagado());

	}
}
