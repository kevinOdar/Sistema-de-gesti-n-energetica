package testsDispositivos;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.*;

import junit.framework.Assert;
import models.entities.dispositivo.*;
import models.entities.dispositivo.estado.*;

public class TestConsumoInteligente {
	Inteligente dispositivoInteligente;
	Caracteristica caracteristica;

	Inteligente aireAcond;
	Caracteristica aireAcondDe3500Frigorias;
	
	DateTimeFormatter formatter;
	RegistroDeFechas registroFecha;
	

	@Before
	public void init() {
		caracteristica = new Caracteristica();
		caracteristica.setCoeficienteDeConsumo(20.0);
		caracteristica.setInteligente(true);
		dispositivoInteligente = new Inteligente();
		dispositivoInteligente.setCaracteristica(caracteristica);

		aireAcond = new Inteligente();
		aireAcondDe3500Frigorias = new Caracteristica();
		aireAcondDe3500Frigorias.setCoeficienteDeConsumo(1.613);
		aireAcondDe3500Frigorias.setRestriccionMayorIgual(90.0);
		aireAcondDe3500Frigorias.setRestriccionMenorIgual(360.0);
		aireAcondDe3500Frigorias.setInteligente(true);
	}

	@Test
	public void TestConsumoDespuesDe10Horas() {
		System.out.println("\n \n ------ Comienza test 1 --------- \n \n");

		EstadoDispositivo prendido = new Prendido();
		prendido.setFechaInicio(DateTime.now().minusHours(10));
		prendido.setFechaFin(DateTime.now());
		dispositivoInteligente.agregarEstadoAHistorial(prendido);

		double consumo = dispositivoInteligente.getConsumoDiario();
		double dias = dispositivoInteligente.getHistorialDeEstados().getDiasHistorial();
		double horasPrendido = dispositivoInteligente.getHistorialDeEstados().getHorasPrendido();

		System.out.format("El historial tiene tantos dias: %s\n", dias);
		System.out.format("El historial tiene tantas horas de uso: %s\n", horasPrendido);
		System.out.format("Resultado test consumo diario: %s\n", consumo);

		assertEquals(200.0, consumo, 0);
	}

	@Test
	public void testAireQueEstuvoPrendidoYQueEstaPrendido() {
		System.out.println("\n \n ------ Comienza test 2 --------- \n \n");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH");

		// 24horas de diferencia
		// Equivalentes
		DateTime fechaInicioRandom = formatter.parseDateTime("2018-08-03 02");
		DateTime fechaFinRandom = formatter.parseDateTime("2018-08-04 02");

		DateTime fechaInicioRandom2 = DateTime.parse("2018-08-03T02:15:30");// para setear fecha estado
		DateTime fechaFinRandom2 = DateTime.parse("2018-08-04T02:15:30"); // para setear fecha estado

		// Con replace elimino la 'T' que aparece cuando el sistema te da la fecha
		// y con substring desprecio los minutos, etc...
		DateTime fechaAyer = formatter
				.parseDateTime(DateTime.now().minusDays(1).toString().replace('T', ' ').substring(0, 13));
		DateTime fechaHoy = formatter.parseDateTime(DateTime.now().toString().replace('T', ' ').substring(0, 13));

		// Primer estado
		// seteo de registro de fecha del primer estado
		RegistroDeFechas registroFecha = new RegistroDeFechas();

		registroFecha.setFechaInicio(fechaInicioRandom2);
		registroFecha.setFechaFin(fechaFinRandom2);
		Prendido prendido = new Prendido();
		prendido.setRegistro(registroFecha);
		// tendria que dar: 1.613 * 24 hrs. = 38.712

		// segundo estado, actual (sin fecha final)
		RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();

		otroRegistroFecha.setFechaInicio(DateTime.now().minusDays(1));// tambien diferencia de 24 hrs
		Prendido otroPrendido = new Prendido();
		otroPrendido.setRegistro(otroRegistroFecha);
		// tendria que dar: 1.613 * 24 hrs. = 38.712 (solo este estado)

		// seteando dispositivo
		aireAcond.actualizarEstado(prendido);
		aireAcond.actualizarEstado(otroPrendido);
		aireAcond.setCaracteristica(aireAcondDe3500Frigorias);// coeficiente = 1.613

		// Probando los resultados

		// Rango desde 2018-08-03 02 hasta ahora (para que solapen los 2 intervalos

		Duration duracion = new Duration(fechaInicioRandom, fechaFinRandom);// un dia de diferencia
		Duration otraDuracion = new Duration(fechaAyer, fechaHoy);// un dia de diferencia
		double resultadoPrueba1 = duracion.getStandardHours() * aireAcondDe3500Frigorias.getCoeficienteDeConsumo();
		double resultadoPrueba2 = otraDuracion.getStandardHours() * aireAcondDe3500Frigorias.getCoeficienteDeConsumo();

		double resultado = aireAcond.getConsumoPorPeriodo(fechaInicioRandom, fechaHoy); // Rango desde 2018-08-03 02
																							// hasta ahora

		System.out.format("Resultado prueba 1: %s,\t Resultado prueba 2: %s\n", resultadoPrueba1, resultadoPrueba2);
		System.out.println(
				"El resultado es: " + resultado + " y tiene que dar: " + (resultadoPrueba1 + resultadoPrueba2));

		Assert.assertEquals(resultado, resultadoPrueba1 + resultadoPrueba2);
	}
	
	@Test
	public void TestConsumoPeriodoMenorAlTiempoDeEstado() {
		System.out.println("\n \n ------ Comienza test 3 --------- \n \n");

		EstadoDispositivo prendido = new Prendido();
		prendido.setFechaInicio(DateTime.now().minusDays(10));
		prendido.setFechaFin(DateTime.now());
		dispositivoInteligente.agregarEstadoAHistorial(prendido);

		double consumo = dispositivoInteligente.getConsumoPorPeriodo(DateTime.now().minusDays(5).toDateTime(), DateTime.now().minusDays(3).toDateTime());
		double horas = prendido.getDuracionEstadoHorasPorPeriodo(DateTime.now().minusDays(5).toDateTime(), DateTime.now().minusDays(3).toDateTime());
		double horasEstado = prendido.getDuracionEstadoEnHoras();

		System.out.format("El estado duro unas %s horas\n", horasEstado);
		System.out.format("El estado estuvo %s horas dentro del periodo\n", horas);
		System.out.format("Resultado test consumo: %s\n", consumo);

		//assertEquals(200.0, consumo, 0);
	}
	
	@Test
	public void TestDispConsumeDuranteLasUltimasNHoras() 
	{
		System.out.println("\n \n ------ Comienza test 4 --------- \n \n");
		aireAcond = new Inteligente();
		RegistroDeFechas registroFecha = new RegistroDeFechas();
		registroFecha.setFechaInicio(DateTime.now().minusHours(10));
		registroFecha.setFechaFin(DateTime.now().minusHours(6));
		
		Prendido prendido = new Prendido();
		prendido.setRegistro(registroFecha);

		RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();
		otroRegistroFecha.setFechaInicio(DateTime.now().minusHours(2));
		Prendido otroPrendido = new Prendido();
		otroPrendido.setRegistro(otroRegistroFecha);

		aireAcond.actualizarEstado(prendido);
		aireAcond.actualizarEstado(otroPrendido);
		aireAcond.setCaracteristica(aireAcondDe3500Frigorias);
		
		System.out.println("Estuvo prendido 4hrs., estuvo apagado 4 hrs. y se volvio a prender hace 2 hrs." + aireAcond.getConsumoUltimasNHoras(7));
		
		Assert.assertEquals(3.226, aireAcond.getConsumoUltimasNHoras(7));
	}
	@Test
	public void TestDispConsumeEnUnPeriodo() 
	{
		System.out.println("\n \n ------ Comienza test 5 --------- \n \n");
		aireAcond = new Inteligente();
		RegistroDeFechas registroFecha = new RegistroDeFechas();
		registroFecha.setFechaInicio(DateTime.now().minusHours(10));
		registroFecha.setFechaFin(DateTime.now().minusHours(6));
		
		Prendido prendido = new Prendido();
		prendido.setRegistro(registroFecha);

		RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();
		otroRegistroFecha.setFechaInicio(DateTime.now().minusHours(3));
		Prendido otroPrendido = new Prendido();
		otroPrendido.setRegistro(otroRegistroFecha);

		aireAcond.actualizarEstado(prendido);
		aireAcond.actualizarEstado(otroPrendido);
		aireAcond.setCaracteristica(aireAcondDe3500Frigorias);
		
		Assert.assertEquals(3.226, aireAcond.getConsumoPorPeriodo(DateTime.now().minusHours(8), DateTime.now().minusHours(2)));
		
	}
}
