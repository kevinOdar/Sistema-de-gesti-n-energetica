package testsDispositivos;

import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.*;

import models.entities.dispositivo.*;
import models.entities.dispositivo.estado.*;

public class TestsDispositivoInteligente {
	Inteligente dispositivoInteligente;
	Inteligente dispositivo;

	@Before
	public void init() {
		dispositivo = new Inteligente();
		dispositivoInteligente = new Inteligente();
		dispositivoInteligente.setNombre("dispositivoInteligente");
	}

	@Test
	public void testElDispositivoEstaPrendido() {
		dispositivoInteligente.prender();
		assertTrue(dispositivoInteligente.estasPrendido());
	}

	@Test
	public void testApagarDispositivo() {
		dispositivoInteligente.apagar();
		assertTrue(dispositivoInteligente.estasApagado());
	}
	
	@Test
	public void testHorasUsoDiarioDispositivo() {
		EstadoDispositivo prendido1 = new Prendido();
		prendido1.setFechaInicio(DateTime.now().minusDays(24));
		prendido1.setFechaFin(DateTime.now().minusDays(23));
		dispositivo.agregarEstadoAHistorial(prendido1);
		
		EstadoDispositivo apagado = new Apagado();
		apagado.setFechaInicio(DateTime.now().minusDays(22));
		apagado.setFechaFin(DateTime.now().minusDays(2));		
		dispositivo.agregarEstadoAHistorial(apagado);
		
		EstadoDispositivo prendido2 = new Prendido();
		prendido2.setFechaInicio(DateTime.now().minusDays(1));
		prendido2.setFechaFin(DateTime.now().minusHours(10));
		dispositivo.agregarEstadoAHistorial(prendido2);
		
		double horasUso = dispositivo.getHorasUsoDiario();
		double horasPrendidoHistorial = dispositivo.getHistorialDeEstados().getHorasPrendido();
		double diasHistorial = dispositivo.getHistorialDeEstados().getDiasHistorial();
		
		System.out.format("Dias del historial: %s\n" , diasHistorial);
		System.out.format("Horas prendido en el historial: %s\n" , horasPrendidoHistorial);
		System.out.format("Resultado test horas uso diario: %s\n" , horasUso);

	}
}
