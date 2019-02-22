package testRegla;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import models.entities.dispositivo.*;
import models.entities.regla.actuadores.Apagar;
import models.entities.regla.actuadores.PasarAModoAhorroEnergia;
import models.entities.regla.actuadores.Prender;


public class testActuadores {
	public Caracteristica smartLed40;
	public Inteligente dispositivo;
	public Apagar apagar;
	public Prender prender;
	public PasarAModoAhorroEnergia ahorroEnergia;

	public int intensidad;
	
	@Before
	public void init() {
		smartLed40 = new Caracteristica();
		smartLed40.setCoeficienteDeConsumo(0.08);
		smartLed40.setDeBajoConsumo(true);
		smartLed40.setInteligente(true);
		dispositivo = new Inteligente();
		dispositivo.setNombre("SLL (Smart Led Light)");
		dispositivo.setCaracteristica(smartLed40);
		dispositivo.prender();//seteamos estado	ya que no se inicializa con ninguno
		apagar = new Apagar();
		apagar.setCanal(dispositivo.getNombre());
		prender = new Prender();
		prender.setCanal(dispositivo.getNombre());
		ahorroEnergia = new PasarAModoAhorroEnergia();
		ahorroEnergia.setCanal(dispositivo.getNombre());

	
	}
	
	@Test
	public void testApagarDispositivoPrendido() {
		//El dispositivo se encuentra inicialmente prendido
		//por el adapter.prender() en el @Before.
		this.apagar.execute();
		assertTrue(this.dispositivo.estasApagado());
	}
	
	@Test
	public void testAhorroDeEnergiaPasaAPrendido() {
		//Si un dispositivo(prendido) entra en modo "ahorro de energia" 
	   // y se le diera la orden de encederse, debera volver a dicho modo(prendido).
		this.ahorroEnergia.execute();
		this.prender.execute();
		assertTrue(this.dispositivo.estasPrendido());
	}
	
}
