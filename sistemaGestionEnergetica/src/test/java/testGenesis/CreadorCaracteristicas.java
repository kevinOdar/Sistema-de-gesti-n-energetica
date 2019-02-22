package testGenesis;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;

public class CreadorCaracteristicas {
	private DaoMySQL<Caracteristica> persistidor;
	
	private Caracteristica aire3500frigorias;
	private Caracteristica aire2200frigorias;
	private Caracteristica tvTubo21;
	private Caracteristica tvTubo29a34;
	private Caracteristica tvLCD40;
	private Caracteristica tvLED24;
	private Caracteristica tvLED32;
	private Caracteristica tvLED40;
	private Caracteristica heladeraConFreezer;
	private Caracteristica heladeraSinFreezer;
	private Caracteristica lavarropasAutomaticoCalientaAgua5KG;
	private Caracteristica lavarropasAutomatico5KG;
	private Caracteristica lavarropasSemiAutomatico5KG;
	private Caracteristica ventiladorDePie;
	private Caracteristica ventiladorDeTecho;
	private Caracteristica lamparaHalogena40W;
	private Caracteristica lamparaHalogena60W;
	private Caracteristica lamparaHalogena100W;
	private Caracteristica lampara11W;
	private Caracteristica lampara15W;
	private Caracteristica lampara20W;
	private Caracteristica pcEscritorio;
	private Caracteristica microondasConvencional;
	private Caracteristica planchaAVapor;

	public void init() {
		persistidor = new DaoMySQL<Caracteristica>();
		persistidor.init();
		persistidor.setTipo(Caracteristica.class);
		
		//
		aire3500frigorias = new Caracteristica();
		aire3500frigorias.setNombre("Aire acondicionado de 3500 frigorias");
		aire3500frigorias.setRestriccionMenorIgual(360.0);
		aire3500frigorias.setRestriccionMayorIgual(90.0);
		aire3500frigorias.setCoeficienteDeConsumo(1.613);
		aire3500frigorias.setDeBajoConsumo(false);
		aire3500frigorias.setInteligente(true);
		persistidor.agregar(aire3500frigorias);
		
		//
		aire2200frigorias = new Caracteristica();
		aire2200frigorias.setNombre("Aire acondicionado de 2200 frigorias");
		aire2200frigorias.setRestriccionMenorIgual(360.0);
		aire2200frigorias.setRestriccionMayorIgual(90.0);
		aire2200frigorias.setCoeficienteDeConsumo(1.013);
		aire2200frigorias.setDeBajoConsumo(true);
		aire2200frigorias.setInteligente(true);
		persistidor.agregar(aire2200frigorias);
		
		//
		tvTubo21 = new Caracteristica();
		tvTubo21.setNombre("TV Tubo fluorescente 21\" ");
		tvTubo21.setRestriccionMenorIgual(360.0);
		tvTubo21.setRestriccionMayorIgual(90.0);
		tvTubo21.setCoeficienteDeConsumo(0.075);
		tvTubo21.setDeBajoConsumo(false);
		tvTubo21.setInteligente(false);
		persistidor.agregar(tvTubo21);
		
		//
		tvTubo29a34 = new Caracteristica();
		tvTubo29a34.setNombre("TV Tuvo fluorescente 29\" a 34\"");
		tvTubo29a34.setRestriccionMenorIgual(360.0);
		tvTubo29a34.setRestriccionMayorIgual(90.0);
		tvTubo29a34.setCoeficienteDeConsumo(0.175);
		tvTubo29a34.setDeBajoConsumo(false);
		tvTubo29a34.setInteligente(false);
		persistidor.agregar(tvTubo29a34);

		//
		tvLCD40 = new Caracteristica();
		tvLCD40.setNombre("LCD de 40\"");
		tvLCD40.setRestriccionMenorIgual(360.0);
		tvLCD40.setRestriccionMayorIgual(90.0);
		tvLCD40.setCoeficienteDeConsumo(0.18);
		tvLCD40.setDeBajoConsumo(false);
		tvLCD40.setInteligente(false);
		persistidor.agregar(tvLCD40);

		//
		tvLED24 = new Caracteristica();
		tvLED24.setNombre("LED 24\"");
		tvLED24.setRestriccionMenorIgual(360.0);
		tvLED24.setRestriccionMayorIgual(90.0);
		tvLED24.setCoeficienteDeConsumo(0.04);
		tvLED24.setDeBajoConsumo(true);
		tvLED24.setInteligente(true);
		persistidor.agregar(tvLED24);

		//
		tvLED32 = new Caracteristica();
		tvLED32.setNombre("LED 32\"");
		tvLED32.setRestriccionMenorIgual(360.0);
		tvLED32.setRestriccionMayorIgual(90.0);
		tvLED32.setCoeficienteDeConsumo(0.055);
		tvLED32.setDeBajoConsumo(true);
		tvLED32.setInteligente(true);
		persistidor.agregar(tvLED32);

		//
		tvLED40 = new Caracteristica();
		tvLED40.setNombre("LED 40\"");
		tvLED40.setRestriccionMenorIgual(360.0);
		tvLED40.setRestriccionMayorIgual(90.0);
		tvLED40.setCoeficienteDeConsumo(0.08);
		tvLED40.setDeBajoConsumo(true);
		tvLED40.setInteligente(true);
		persistidor.agregar(tvLED40);

		//
		heladeraConFreezer = new Caracteristica();
		heladeraConFreezer.setNombre("Heladera con freezer");
		heladeraConFreezer.setRestriccionMenorIgual(0.0);
		heladeraConFreezer.setRestriccionMayorIgual(0.0);
		heladeraConFreezer.setCoeficienteDeConsumo(0.09);
		heladeraConFreezer.setDeBajoConsumo(true);
		heladeraConFreezer.setInteligente(true);
		persistidor.agregar(heladeraConFreezer);
		
		//
		heladeraSinFreezer = new Caracteristica();
		heladeraSinFreezer.setNombre("Heladera sin freezer");
		heladeraSinFreezer.setRestriccionMenorIgual(0.0);
		heladeraSinFreezer.setRestriccionMayorIgual(0.0);
		heladeraSinFreezer.setCoeficienteDeConsumo(0.075);
		heladeraSinFreezer.setDeBajoConsumo(true);
		heladeraSinFreezer.setInteligente(true);
		persistidor.agregar(heladeraSinFreezer);

		//
		lavarropasAutomaticoCalientaAgua5KG = new Caracteristica();
		lavarropasAutomaticoCalientaAgua5KG.setNombre("Lavarropas automatico de 5 Kg con calentamiento de agua");
		lavarropasAutomaticoCalientaAgua5KG.setRestriccionMenorIgual(30.0);
		lavarropasAutomaticoCalientaAgua5KG.setRestriccionMayorIgual(6.0);
		lavarropasAutomaticoCalientaAgua5KG.setCoeficienteDeConsumo(0.875);
		lavarropasAutomaticoCalientaAgua5KG.setDeBajoConsumo(false);
		lavarropasAutomaticoCalientaAgua5KG.setInteligente(false);
		persistidor.agregar(lavarropasAutomaticoCalientaAgua5KG);
		
		//
		lavarropasAutomatico5KG = new Caracteristica();
		lavarropasAutomatico5KG.setNombre("Lavarropas automatico de 5 Kg");
		lavarropasAutomatico5KG.setRestriccionMenorIgual(30.0);
		lavarropasAutomatico5KG.setRestriccionMayorIgual(6.0);
		lavarropasAutomatico5KG.setCoeficienteDeConsumo(0.175);
		lavarropasAutomatico5KG.setDeBajoConsumo(true);
		lavarropasAutomatico5KG.setInteligente(true);
		persistidor.agregar(lavarropasAutomatico5KG);

		//
		lavarropasSemiAutomatico5KG = new Caracteristica();
		lavarropasSemiAutomatico5KG.setNombre("Lavarropas semi-automatico de 5 Kg");
		lavarropasSemiAutomatico5KG.setRestriccionMenorIgual(30.0);
		lavarropasSemiAutomatico5KG.setRestriccionMayorIgual(6.0);
		lavarropasSemiAutomatico5KG.setCoeficienteDeConsumo(0.1275);
		lavarropasSemiAutomatico5KG.setDeBajoConsumo(true);
		lavarropasSemiAutomatico5KG.setInteligente(false);
		persistidor.agregar(lavarropasSemiAutomatico5KG);

		//
		ventiladorDePie = new Caracteristica();
		ventiladorDePie.setNombre("Ventilador de pie");
		ventiladorDePie.setRestriccionMenorIgual(360.0);
		ventiladorDePie.setRestriccionMayorIgual(120.0);
		ventiladorDePie.setCoeficienteDeConsumo(0.09);
		ventiladorDePie.setDeBajoConsumo(true);
		ventiladorDePie.setInteligente(false);
		persistidor.agregar(ventiladorDePie);
		
		//
		ventiladorDeTecho = new Caracteristica();
		ventiladorDeTecho.setNombre("Ventilador de techo");
		ventiladorDeTecho.setRestriccionMenorIgual(360.0);
		ventiladorDeTecho.setRestriccionMayorIgual(120.0);
		ventiladorDeTecho.setCoeficienteDeConsumo(0.06);
		ventiladorDeTecho.setDeBajoConsumo(true);
		ventiladorDeTecho.setInteligente(true);
		persistidor.agregar(ventiladorDeTecho);

		//
		lamparaHalogena40W = new Caracteristica();
		lamparaHalogena40W.setNombre("Lampara halogena de 40W");
		lamparaHalogena40W.setRestriccionMenorIgual(360.0);
		lamparaHalogena40W.setRestriccionMayorIgual(90.0);
		lamparaHalogena40W.setCoeficienteDeConsumo(0.04);
		lamparaHalogena40W.setDeBajoConsumo(false);
		lamparaHalogena40W.setInteligente(true);
		persistidor.agregar(lamparaHalogena40W);

		//
		lamparaHalogena60W = new Caracteristica();
		lamparaHalogena60W.setNombre("Lampara halogena 60W");
		lamparaHalogena60W.setRestriccionMenorIgual(360.0);
		lamparaHalogena60W.setRestriccionMayorIgual(90.0);
		lamparaHalogena60W.setCoeficienteDeConsumo(0.06);
		lamparaHalogena60W.setDeBajoConsumo(false);
		lamparaHalogena60W.setInteligente(true);
		persistidor.agregar(lamparaHalogena60W);

		//
		lamparaHalogena100W = new Caracteristica();
		lamparaHalogena100W.setNombre("Lampara halogena de 100W");
		lamparaHalogena100W.setRestriccionMenorIgual(360.0);
		lamparaHalogena100W.setRestriccionMayorIgual(90.0);
		lamparaHalogena100W.setCoeficienteDeConsumo(0.015);
		lamparaHalogena100W.setDeBajoConsumo(false);
		lamparaHalogena100W.setInteligente(true);
		persistidor.agregar(lamparaHalogena100W);

		//
		lampara11W = new Caracteristica();
		lampara11W.setNombre("Lampara de 11W");
		lampara11W.setRestriccionMenorIgual(360.0);
		lampara11W.setRestriccionMayorIgual(90.0);
		lampara11W.setCoeficienteDeConsumo(0.011);
		lampara11W.setDeBajoConsumo(true);
		lampara11W.setInteligente(true);
		persistidor.agregar(lampara11W);

		//
		lampara15W = new Caracteristica();
		lampara15W.setNombre("Lampara de 15W");
		lampara15W.setRestriccionMenorIgual(360.0);
		lampara15W.setRestriccionMayorIgual(90.0);
		lampara15W.setCoeficienteDeConsumo(0.015);
		lampara15W.setDeBajoConsumo(true);
		lampara15W.setInteligente(true);
		persistidor.agregar(lampara15W);

		//
		lampara20W = new Caracteristica();
		lampara20W.setNombre("Lampara de 20W");
		lampara20W.setRestriccionMenorIgual(360.0);
		lampara20W.setRestriccionMayorIgual(90.0);
		lampara20W.setCoeficienteDeConsumo(0.02);
		lampara20W.setDeBajoConsumo(true);
		lampara20W.setInteligente(true);
		persistidor.agregar(lampara20W);

		//
		pcEscritorio = new Caracteristica();
		pcEscritorio.setNombre("PC de escritorio");
		pcEscritorio.setRestriccionMenorIgual(360.0);
		pcEscritorio.setRestriccionMayorIgual(60.0);
		pcEscritorio.setCoeficienteDeConsumo(0.4);
		pcEscritorio.setDeBajoConsumo(true);
		pcEscritorio.setInteligente(true);
		persistidor.agregar(pcEscritorio);
		
		//
		microondasConvencional = new Caracteristica();
		microondasConvencional.setNombre("Microondas convencional");
		microondasConvencional.setRestriccionMenorIgual(15.0);
		microondasConvencional.setRestriccionMayorIgual(3.0);
		microondasConvencional.setCoeficienteDeConsumo(0.64);
		microondasConvencional.setDeBajoConsumo(true);
		microondasConvencional.setInteligente(false);
		persistidor.agregar(microondasConvencional);
		
		//
		planchaAVapor = new Caracteristica();
		planchaAVapor.setNombre("Plancha a vapor");
		planchaAVapor.setRestriccionMenorIgual(30.0);
		planchaAVapor.setRestriccionMayorIgual(3.0);
		planchaAVapor.setCoeficienteDeConsumo(0.75);
		planchaAVapor.setDeBajoConsumo(true);
		planchaAVapor.setInteligente(false);
		persistidor.agregar(planchaAVapor);
		
	}
}
