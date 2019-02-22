package testsEstados;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.*;

import models.entities.dispositivo.estado.*;

public class TestHistorialEstados {

	HistorialDeEstados historial;
	EstadoDispositivo estado;
	EstadoDispositivo otroEstado;
	EstadoDispositivo unEstadoMas;
	@Before
	public void init () {
		historial = new HistorialDeEstados();
		estado = new Prendido();
		otroEstado = new Apagado();
		unEstadoMas = new Prendido();
	}
	
	@Test
	public void TestHistorialConDosDias() {

		
		estado.setFechaInicio(DateTime.now().minusDays(25));
		estado.setFechaFin(DateTime.now().minusDays(24));
		
		otroEstado.setFechaInicio(DateTime.now().minusDays(3));
		otroEstado.setFechaFin(DateTime.now().minusDays(2));
	
		unEstadoMas.setFechaInicio(DateTime.now().minusDays(1));
		unEstadoMas.setFechaFin(DateTime.now());
	
		historial.agregarEstado(estado);
		historial.agregarEstado(otroEstado);
		historial.agregarEstado(unEstadoMas);
		
		double diasHistorial = historial.getDiasHistorial();
		
		System.out.format("Resultado test diasHistorial: %s\n" ,diasHistorial);
		assertEquals(25.0,diasHistorial,0);
	}
	
	@Test
	public void TestHistorialHorasPrendido() {
		estado.setFechaInicio(DateTime.now().minusDays(25));
		estado.setFechaFin(DateTime.now().minusDays(24));
		
		otroEstado.setFechaInicio(DateTime.now().minusDays(3));
		otroEstado.setFechaFin(DateTime.now().minusDays(2));
	
		unEstadoMas.setFechaInicio(DateTime.now().minusDays(1));
		unEstadoMas.setFechaFin(DateTime.now());
	
		historial.agregarEstado(estado);
		historial.agregarEstado(otroEstado);
		historial.agregarEstado(unEstadoMas);
		
		double diasHistorial = historial.getDiasHistorial();
		double horasPrendido = historial.getHorasPrendido();
		
		System.out.println("\n---Test horas prendido---");
		System.out.format("Historial tiene tantos dias: %s\n" ,diasHistorial);
		System.out.format("Historial tiene tantas horas prendido: %s\n" ,horasPrendido);

		assertEquals(48.0,horasPrendido,0);
	}
}
