package testsOptimizador;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.dispositivo.estado.Prendido;
import models.entities.dispositivo.estado.RegistroDeFechas;
import models.entities.dominio.ClienteResidencial;
import models.entities.optimizador.Optimizador;
import models.entities.regla.actuadores.ActuadorBase;
import models.entities.regla.actuadores.Apagar;

public class TestsOptimizadorParaInteligentes 
{
	Inteligente aireAcond;
	Caracteristica aireAcondDe3500Frigorias;
	DateTimeFormatter formatter;
	RegistroDeFechas registroFecha;
	ClienteResidencial cosmeFulanito;
	Apagable sePuedeApagar;
	Optimizador optimizador;
	List<ClienteResidencial> clientesAOptimizar;
	
	@Before
	public void init() 
	{
		cosmeFulanito = new ClienteResidencial();
		cosmeFulanito.setNombre("Cosme");
		aireAcond = new Inteligente();
		aireAcond.setNombre("Aire Acond. de 3500 frigorias");
		aireAcondDe3500Frigorias = new Caracteristica();
		aireAcondDe3500Frigorias.setCoeficienteDeConsumo(1.613);
		aireAcondDe3500Frigorias.setRestriccionMayorIgual(90.0);
		aireAcondDe3500Frigorias.setRestriccionMenorIgual(360.0);
		aireAcondDe3500Frigorias.setInteligente(true);
		formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH");
		registroFecha = new RegistroDeFechas();
		sePuedeApagar = new Apagable();
		aireAcond.setApagabilidad(sePuedeApagar);
		cosmeFulanito.setConsumoEnAutomatico(true);
		optimizador = new Optimizador();
		optimizador.setHayQueLoggear(true);
		clientesAOptimizar = new ArrayList<ClienteResidencial>();
		
	}
	@Test
	public void testtieneDosDispYApagaSoloElExcedido() {
		System.out.println("\n \n ------ Comienza test 1 --------- \n \n");
		
		registroFecha.setFechaInicio(DateTime.now().minusHours(300));
		registroFecha.setFechaFin(DateTime.now());
		
		Prendido prendido = new Prendido();
		prendido.setRegistro(registroFecha);

		RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();
		otroRegistroFecha.setFechaInicio(DateTime.now().minusDays(1));
		Prendido otroPrendido = new Prendido();
		otroPrendido.setRegistro(otroRegistroFecha);

		aireAcond.actualizarEstado(prendido);
		aireAcond.actualizarEstado(otroPrendido);
		aireAcond.setCaracteristica(aireAcondDe3500Frigorias);
		cosmeFulanito.agregarDispositivo(aireAcond);
		
		clientesAOptimizar.add(cosmeFulanito);
		optimizador.setClientes(clientesAOptimizar);
		
		//segundo disp.
		Inteligente lavarropas =  new Inteligente();
		lavarropas.setNombre("Lavarropas automatico");
		Caracteristica lavarropasAutDe5kg;
		lavarropasAutDe5kg = new Caracteristica();
		
		lavarropasAutDe5kg.setCoeficienteDeConsumo(0.175);
		lavarropasAutDe5kg.setRestriccionMayorIgual(6.0);
		lavarropasAutDe5kg.setRestriccionMenorIgual(30.0);
		lavarropasAutDe5kg.setInteligente(true);
		lavarropas.setCaracteristica(lavarropasAutDe5kg);
		
		lavarropas.actualizarEstado(prendido);
		lavarropas.actualizarEstado(otroPrendido);
		lavarropas.setApagabilidad(sePuedeApagar);
		cosmeFulanito.agregarDispositivo(lavarropas);

		
		Apagar apagar = new Apagar();
		List<ActuadorBase> acciones = new ArrayList<ActuadorBase>();
		acciones.add(apagar);
		optimizador.setAcciones(acciones);
		
		optimizador.run();
		Assert.assertTrue(lavarropas.estasApagado());		
	}
	
}
