package testsOptimizador;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.junit.*;

import models.entities.dispositivo.*;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.dispositivo.apagabilidad.NoApagable;
import models.entities.dominio.*;
import models.entities.optimizador.Optimizador;

public class TestsOptimizadorConTimer {
	ClienteResidencial cliente;

	Standard aireAcond;
	Standard lavarropas;
	Standard microondas;
	Standard heladera; //No apagable

	Caracteristica aireAcondDe3500Frigorias;
	Caracteristica lavarropasAutDe5kg;
	Caracteristica microondasConvencional;
	
	Optimizador optimizador;
	List<ClienteResidencial> clientesAOptimizar;
	
	Timer timerOptimizador;
	
	@Before
	public void initParametros() 
	{
		timerOptimizador = new Timer();
		
		optimizador = new Optimizador();
		
		cliente = new ClienteResidencial();
		
		clientesAOptimizar = new ArrayList<ClienteResidencial>();
		clientesAOptimizar.add(cliente);
		optimizador.setClientes(clientesAOptimizar);
		
		aireAcond = new Standard();
		lavarropas = new Standard();
		microondas = new Standard();
		heladera = new Standard();
		
		aireAcondDe3500Frigorias = new Caracteristica();
		lavarropasAutDe5kg = new Caracteristica();
		microondasConvencional = new Caracteristica();
		
		aireAcond.setNombre("Aire 3500 frigorias");
		lavarropas.setNombre("Lavarropas");
		microondas.setNombre("Microondas");
		heladera.setNombre("Heladera");
		
		aireAcond.setHorasUsoDiario(10);
		lavarropas.setHorasUsoDiario(2);
		microondas.setHorasUsoDiario(2);
		
		aireAcondDe3500Frigorias.setCoeficienteDeConsumo(1.613);
		aireAcondDe3500Frigorias.setRestriccionMayorIgual(90.0);
		aireAcondDe3500Frigorias.setRestriccionMenorIgual(360.0);
		
		lavarropasAutDe5kg.setCoeficienteDeConsumo(0.175);
		lavarropasAutDe5kg.setRestriccionMayorIgual(6.0);
		lavarropasAutDe5kg.setRestriccionMenorIgual(30.0);
		
		microondasConvencional.setCoeficienteDeConsumo(0.64);
		microondasConvencional.setRestriccionMayorIgual(3.0);
		microondasConvencional.setRestriccionMenorIgual(15.0);
		
		aireAcond.setCaracteristica(aireAcondDe3500Frigorias);
		lavarropas.setCaracteristica(lavarropasAutDe5kg);
		microondas.setCaracteristica(microondasConvencional);
		
		Apagable sePuedeApagar = new Apagable();
		aireAcond.setApagabilidad(sePuedeApagar);
		lavarropas.setApagabilidad(sePuedeApagar);
		microondas.setApagabilidad(sePuedeApagar);
		
		NoApagable noSePuedeApagar = new NoApagable();
		heladera.setApagabilidad(noSePuedeApagar);
		
		cliente.setConsumoEnAutomatico(true);
		
		cliente.agregarDispositivo(aireAcond);
		cliente.agregarDispositivo(lavarropas);
		cliente.agregarDispositivo(microondas);
		cliente.agregarDispositivo(heladera);
		
 	}
	
	@Test
	public void TestConTimer() throws Throwable {
		
		timerOptimizador.scheduleAtFixedRate(optimizador, 10, 1000);
		Thread.sleep(10000);
	}
}
