package testRegla;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import models.entities.dispositivo.*;
import models.entities.regla.*;
import models.entities.regla.actuadores.ActuadorBase;
import models.entities.regla.actuadores.Apagar;
import models.entities.regla.criterios.*;
import models.entities.sensor.*;

public class testReglaUnSensor {
	public Inteligente dispositivo = new Inteligente();
	public Sensor sensorTemperatura = new Sensor();
	public Regla regla = new Regla();
	public ActuadorBase apagar = new Apagar();
	
	@Before
	public void init() {
		Sensor.setMedicionesMaximas(20);
		dispositivo.prender();
		dispositivo.setNombre("dummy");
		apagar.setCanal(dispositivo.getNombre());
		Criterio criterioComp30 = new Mayor();
		criterioComp30.setValorComparacion(30);
		regla.suscribirme(sensorTemperatura);
		regla.agregarUnCriterio(criterioComp30, sensorTemperatura);
		regla.agregarActuador(apagar);
	}
	
	@Test
	public void testTieneQueApagarConTemp30() {
		sensorTemperatura.anotarMedicion(40);
		assertTrue(dispositivo.estasApagado());
		
	}
}

