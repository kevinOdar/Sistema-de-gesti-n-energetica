package testsDummies;

import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.estado.Prendido;
import models.entities.dispositivo.estado.RegistroDeFechas;


import org.joda.time.DateTime;


public final class Inteligente2 {
	private static Inteligente dispositivo = null;
    private static Caracteristica caracteristica = null;
      
	private Inteligente2() {
	}

	public static Inteligente getDispositivo1() {
		
			dispositivo = new Inteligente();
		    caracteristica = new Caracteristica();
			caracteristica.setCoeficienteDeConsumo(0.12);
			caracteristica.setInteligente(true);
			
			RegistroDeFechas registroFecha = new RegistroDeFechas();
			registroFecha.setFechaInicio(DateTime.now().minusDays(9));
			registroFecha.setFechaFin(DateTime.now().minusHours(6));
			
			Prendido prendido = new Prendido();
			prendido.setRegistro(registroFecha);

			RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();
			otroRegistroFecha.setFechaInicio(DateTime.now().minusHours(3));
			otroRegistroFecha.setFechaFin(DateTime.now().minusHours(1));
			Prendido otroPrendido = new Prendido();
			otroPrendido.setRegistro(otroRegistroFecha);
          
			dispositivo.actualizarEstado(prendido);
			dispositivo.actualizarEstado(otroPrendido);
			dispositivo.setCaracteristica(caracteristica);
			
		    dispositivo.prender();
			
			return dispositivo;
		
	}
	
	  
	public static Inteligente getDispositivo2() {
        
		dispositivo = new Inteligente();
		caracteristica = new Caracteristica();
		caracteristica.setCoeficienteDeConsumo(0.16);
		caracteristica.setInteligente(true);
		
		RegistroDeFechas registroFecha = new RegistroDeFechas();
		registroFecha.setFechaInicio(DateTime.now().minusDays(7));
		registroFecha.setFechaFin(DateTime.now().minusHours(6));
		
		Prendido prendido = new Prendido();
		prendido.setRegistro(registroFecha);

		RegistroDeFechas otroRegistroFecha = new RegistroDeFechas();
		otroRegistroFecha.setFechaInicio(DateTime.now().minusHours(3));
		otroRegistroFecha.setFechaFin(DateTime.now().minusHours(2));
		Prendido otroPrendido = new Prendido();
		otroPrendido.setRegistro(otroRegistroFecha);

		dispositivo.actualizarEstado(prendido);
		dispositivo.actualizarEstado(otroPrendido);
		dispositivo.setCaracteristica(caracteristica);
		
		dispositivo.prender();
    	
		return dispositivo;
	
	}
}


  