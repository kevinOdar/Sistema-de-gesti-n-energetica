package testsDominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import models.entities.dispositivo.*;
import models.entities.dominio.*;

public class TestsClienteResidencial {

	Dispositivo dispositivo;
	ClienteResidencial cliente;

	@Before
	public void initParametros() {
		cliente = new ClienteResidencial();
		dispositivo = new Standard();
		cliente.agregarDispositivo(dispositivo);
	}

	@Test
	public void testElClienteNoTieneDispositivosEncendidos() {
		assertEquals(0, cliente.cuantosDispositivosEncendidos());
	}

	@Test
	public void testElClienteTieneUnSoloDispositivo() {
		System.out.println(cliente.getDispositivos());
		assertEquals(1, cliente.cuantosDispositivosTengo());
	}

	@Test
	public void testElClienteEnciendeLaTeleYAhoraTieneUnDispositivoEncendido() {
		Inteligente teleSmart = new Inteligente();
		cliente.agregarDispositivo(teleSmart);
		teleSmart.prender();
		assertEquals(1, cliente.cuantosDispositivosEncendidos());
	}

	@Test
	public void testElClienteAgregaUnDispositivoInteligenteYAhoraTieneQuincePuntos() {

		cliente = new ClienteResidencial();
		dispositivo = new Inteligente();
		cliente.agregarDispositivo(dispositivo);
		assertEquals(15, cliente.getPuntosAcumulados());
	}

	@Test
	public void testElClienteConvierteUnDispositivoStdEnInteligente() {
		cliente = new ClienteResidencial();
		Standard dispositivo = new Standard();
		cliente.agregarDispositivo(dispositivo);
		cliente.convertirDispositivoAInteligente(dispositivo);
		assertTrue(dispositivo.sosInteligente());
	}

	@Test
	public void testElClienteConvierteUnDispositivoStdEnInteligenteYTieneDiezPuntos() {
		cliente = new ClienteResidencial();
		Standard dispositivo = new Standard();
		cliente.agregarDispositivo(dispositivo);
		cliente.convertirDispositivoAInteligente(dispositivo);
		assertEquals(10, cliente.getPuntosAcumulados());
	}
}
