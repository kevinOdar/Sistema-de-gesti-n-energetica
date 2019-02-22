package testsEstados;


import static org.junit.Assert.assertEquals;

import org.joda.time.*;
import org.junit.*;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.estado.*;

public class TestDuracionEstado {
	
	EstadoDispositivo prendido;
	
	@Before
	public void init() {
		prendido = new Prendido();
	}

	@Test
	public void testDuracionEstadoTerminado() {
		prendido.setFechaInicio(DateTime.now().minusHours(10));
		prendido.terminarEstado();
		double horas = prendido.getDuracionEstadoEnHoras();
		
		System.out.format("Resultado test estado terminado: %s\n" ,horas);
		assertEquals(10.0, horas,0);
	}
	
	@Test
	public void testDuracionEstadoTerminadoConFechaSeteada() {
		prendido.setFechaInicio(DateTime.now().minusHours(10));
		prendido.terminarEstado();
		prendido.setFechaFin(DateTime.now().minusHours(5));
		double horas = prendido.getDuracionEstadoEnHoras();
		
		System.out.format("Resultado test estado terminado con fecha seteada: %s\n" ,horas);
		assertEquals(5.0, horas,0);
	}

	@Test
	public void testDuracionEstadoQueNoTermino() {
		prendido.setFechaInicio(DateTime.now().minusHours(20));
		double horas = prendido.getDuracionHastaElMomento();
	
		System.out.format("Resultado test estado no terminado: %s\n",horas);
		
		DaoMySQL<EstadoDispositivo> dao = new DaoMySQL<EstadoDispositivo>();
		dao.init();
		dao.setTipo(EstadoDispositivo.class);
		dao.agregar(prendido);
		
		assertEquals(20.0, horas,0);

	}
}
