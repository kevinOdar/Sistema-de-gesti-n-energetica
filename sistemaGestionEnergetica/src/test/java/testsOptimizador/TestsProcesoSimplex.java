package testsOptimizador;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import models.entities.dispositivo.*;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.dispositivo.apagabilidad.NoApagable;
import models.entities.dominio.*;
import models.entities.optimizador.Optimizador;
import models.entities.optimizador.simplex.SimplexFacade;

public class TestsProcesoSimplex 
{	
	ClienteResidencial cliente;
	ClienteResidencial otroCliente;
	ClienteResidencial tercerCliente;
	
	Standard aireAcond;
	Standard lavarropas;
	Standard microondas;
	Standard heladera; //No apagable
	
	Caracteristica aireAcondDe3500Frigorias;
	Caracteristica lavarropasAutDe5kg;
	Caracteristica microondasConvencional;
	
	
	@Before
	public void initParametros() 
	{
		cliente = new ClienteResidencial();
		
		aireAcond = new Standard();
		lavarropas = new Standard();
		microondas = new Standard();
		
		aireAcondDe3500Frigorias = new Caracteristica();
		lavarropasAutDe5kg = new Caracteristica();
		microondasConvencional = new Caracteristica();
		
		aireAcond.setHorasUsoDiario(6);
		lavarropas.setHorasUsoDiario(0.45);
		microondas.setHorasUsoDiario(0.30);
		
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
		
		cliente.setConsumoEnAutomatico(true);
		
		cliente.agregarDispositivo(aireAcond);
		cliente.agregarDispositivo(lavarropas);
		
		
		//otro cliente mas, pero no quiere que se optimice
		otroCliente = new ClienteResidencial();
		otroCliente.setConsumoEnAutomatico(false);
		otroCliente.agregarDispositivo(aireAcond);
		
		//otro cliente mas, que quiere quiere que se optimice
		tercerCliente = new ClienteResidencial();
		tercerCliente.setConsumoEnAutomatico(true);
		tercerCliente.agregarDispositivo(microondas);
 	}
	

	@Test
	public void testTiene3DispositivosYEsUnHogarEficiente()
	{
		System.out.println("\n \n ------ Comienza test 1 --------- \n \n");
		cliente.agregarDispositivo(microondas);	
		/**
			En este test se minimizar� el siguiente modelo de programaci�n lineal:
			- Funci�n econ�mica (LinearObjectiveFunction): 
				f(x0,x1, x2) = x2 + x1 + x0
				x0 -> Microondas
				x1 -> Lavarropas
				x2 -> Aire de 3500
			- Restricciones (constraints)
				1) 1.613x2 + 0.175x1 + 0.64x0 <= 440640
				2) x2 >= 90
				3) x2 <= 360
				4) x1 >= 6
				5) x1 <= 30
				6) x0 >= 3
				7) x0 <= 15
			NOTA: 
				- >= : Relationship.GEQ
				- <= : Relationship.LEQ
				- =  : Relationship.EQ
		*/
		aireAcond = (Standard) cliente.getDispositivos().get(0);
		lavarropas = (Standard) cliente.getDispositivos().get(1);
		microondas = (Standard) cliente.getDispositivos().get(2);
		
		double horasAireAcondEncendidoPorDia = aireAcond.getHorasUsoDiario();
		double horasAireAcondEncendidoAlMes = horasAireAcondEncendidoPorDia * 30.0; //suponiendo que es al finalizar el mes
		
		double horasLavarropasEncendidoPorDia = lavarropas.getHorasUsoDiario();
		double horasLavarropasEncendidoAlMes = horasLavarropasEncendidoPorDia * 30.0;
		
		double horasMicroondasEncendidoPorDia = microondas.getHorasUsoDiario();
		double horasMicroondasEncendidoAlMes = horasMicroondasEncendidoPorDia * 30.0;
		
		aireAcondDe3500Frigorias = aireAcond.getCaracteristica();
		lavarropasAutDe5kg = lavarropas.getCaracteristica();
		microondasConvencional = microondas.getCaracteristica();
			
		SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);

		simplexFacade.crearFuncionEconomica(1, 1, 1);

		simplexFacade.agregarRestriccion(Relationship.LEQ, 440640, aireAcondDe3500Frigorias.getCoeficienteDeConsumo(), lavarropasAutDe5kg.getCoeficienteDeConsumo(), microondasConvencional.getCoeficienteDeConsumo());
		simplexFacade.agregarRestriccion(Relationship.GEQ, aireAcondDe3500Frigorias.getRestriccionMayorIgual(), 1, 0, 0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, aireAcondDe3500Frigorias.getRestriccionMenorIgual(), 1, 0, 0);	
		simplexFacade.agregarRestriccion(Relationship.GEQ, lavarropasAutDe5kg.getRestriccionMayorIgual(), 0, 1, 0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, lavarropasAutDe5kg.getRestriccionMenorIgual(), 0, 1, 0);
		simplexFacade.agregarRestriccion(Relationship.GEQ, microondasConvencional.getRestriccionMayorIgual(), 0, 0, 1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, microondasConvencional.getRestriccionMenorIgual(), 0, 0, 1);
		
		PointValuePair solucion = simplexFacade.resolver();
		
		System.out.println("\nEl aire puede ser utilizado hasta " +  solucion.getPoint()[0] + " horas durante un mes.");
		System.out.println("Estuvo encendido " + horasAireAcondEncendidoAlMes + " horas \n");
		System.out.println("El lavarropas puede ser utilizado hasta " +  solucion.getPoint()[1] + " horas durante un mes.");
		System.out.println("Estuvo encendido " + horasLavarropasEncendidoAlMes + " horas \n");
		System.out.println("El microondas puede ser utilizado hasta " +  solucion.getPoint()[2] + " horas durante un mes.");
		System.out.println("Estuvo encendido " + horasMicroondasEncendidoAlMes + " horas \n");
		System.out.println("Para que sea un hogar eficiente, todos los dispositivos(juntos) tienen que consumir en un mes hasta " + solucion.getValue() + " horas.");
		System.out.println("Estuvieron encendidos " + (horasAireAcondEncendidoAlMes + horasLavarropasEncendidoAlMes + horasMicroondasEncendidoAlMes) + " horas \n");
		
		Assert.assertTrue(horasAireAcondEncendidoAlMes <= solucion.getPoint()[0] );
		Assert.assertTrue(horasLavarropasEncendidoAlMes <= solucion.getPoint()[1] );
		Assert.assertTrue(horasMicroondasEncendidoAlMes <= solucion.getPoint()[2] );
		
		Assert.assertTrue(horasAireAcondEncendidoAlMes + horasLavarropasEncendidoAlMes + horasMicroondasEncendidoAlMes <= solucion.getValue());	
	}
	

	@Test
	public void testTiene2DispApagablesYElProcesoSimplexDeterminaSiEsUnHogarEficiente()
	{	
		System.out.println("\n \n ------ Comienza test 2 --------- \n \n");
		Optimizador optimizador = new Optimizador();
		List<ClienteResidencial> clientesAOptimizar = new ArrayList<ClienteResidencial>();
		clientesAOptimizar.add(0, cliente);
		optimizador.setClientes(clientesAOptimizar);
		optimizador.run();
	}
	
	@Test
	public void testTiene2DispApagablesYUnoSoloNoApagableElProcesoSimplexSoloAnaliza3Disp()
	{	
		System.out.println("\n \n ------ Comienza test 3 --------- \n \n");
		//Dispositivo no apagable
		heladera = new Standard();
		NoApagable noSePuedeApagar = new NoApagable();
		heladera.setApagabilidad(noSePuedeApagar);
		cliente.agregarDispositivo(heladera);
		Optimizador optimizador = new Optimizador();
		List<ClienteResidencial> clientesAOptimizar = new ArrayList<ClienteResidencial>();
		clientesAOptimizar.add(0, cliente);
		optimizador.setClientes(clientesAOptimizar);
		optimizador.run();
	}

	
	@Test
	public void testHaydosClientesQueQuierenYOtroQueNoYElProcesoSimplexSoloAnalizaDos()
	{	
		System.out.println("\n \n ------ Comienza test 4 --------- \n \n");
		Optimizador optimizador = new Optimizador();
		List<ClienteResidencial> clientesAOptimizar = new ArrayList<ClienteResidencial>();
		clientesAOptimizar.add(0, cliente);
		clientesAOptimizar.add(1, otroCliente);
		clientesAOptimizar.add(2, tercerCliente);
		optimizador.setClientes(clientesAOptimizar);
		optimizador.run();
	}
	
	@Test
	public void testDe2ClientesEnUnoNoSePuedeAnalizarElresultadoYEnElOtroSi()
	{	
		ClienteResidencial cliente;
		ClienteResidencial otroCliente;
		
		Standard disp1;
		Standard disp2;
		
		Caracteristica caract1;
		Caracteristica caract2;

		cliente = new ClienteResidencial();
		otroCliente = new ClienteResidencial();
		
		disp1 = new Standard();
		disp2 = new Standard();
		
		caract1 = new Caracteristica();
		caract2 = new Caracteristica();
		
		disp1.setHorasUsoDiario(6);
		disp2.setHorasUsoDiario(0.45);
		
		caract1.setCoeficienteDeConsumo(0.18);
		caract1.setRestriccionMayorIgual(4.0);
		caract1.setRestriccionMenorIgual(0.0);
		
		caract2.setCoeficienteDeConsumo(0.875);
		caract2.setRestriccionMayorIgual(2.0);
		caract2.setRestriccionMenorIgual(0.0);
		
		disp1.setCaracteristica(caract1);
		disp2.setCaracteristica(caract2);
		
		Apagable sePuedeApagar = new Apagable();
		disp1.setApagabilidad(sePuedeApagar);
		disp2.setApagabilidad(sePuedeApagar);
		
		cliente.setConsumoEnAutomatico(true);
		
		cliente.agregarDispositivo(disp1);
		cliente.agregarDispositivo(disp2);
		
		//otro cliente mas, que quiere quiere que se optimice
		otroCliente = new ClienteResidencial();
		otroCliente.setConsumoEnAutomatico(true);
		otroCliente.agregarDispositivo(microondas);
		
		System.out.println("\n \n ------ Comienza test 5 --------- \n \n");
		Optimizador optimizador = new Optimizador();
		List<ClienteResidencial> clientesAOptimizar = new ArrayList<ClienteResidencial>();
		clientesAOptimizar.add(0, cliente);
		clientesAOptimizar.add(1, otroCliente);
		optimizador.setClientes(clientesAOptimizar);
		optimizador.run();
	}

}
