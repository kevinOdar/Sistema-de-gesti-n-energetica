package testsEntregaPersistencia.test2;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;
import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.apagabilidad.Apagabilidad;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.dispositivo.estado.Apagado;
import models.entities.dispositivo.estado.EstadoDispositivo;
import models.entities.dispositivo.estado.Prendido;

public class Test2 {
/*	Caso de prueba 2:
		Recuperar un dispositivo. Mostrar por consola todos los intervalos que estuvo
		encendido durante el último mes. Modificar su nombre (o cualquier otro atributo
		editable) y grabarlo. Recuperarlo y evaluar que el nombre coincida con el
		esperado.*/
		@Test
		public void testExisteDispositivoNuevo() 
		{
			///////////Seteando dispositivo a persistir//////////
			DaoMySQL<Caracteristica> importador = new DaoMySQL<Caracteristica>();
			importador.init();
			importador.setTipo(Caracteristica.class);
			Caracteristica caracteristica = importador.buscarPorNombre("LED 24\"");
			
			assertEquals("LED 24\"", caracteristica.getNombre());
			
			Apagabilidad apagabilidad = new Apagable();
			
			Inteligente dispositivo = new Inteligente();
			dispositivo.setConsumoEnModoAutomatico(true);
			dispositivo.setCaracteristica(caracteristica);
			dispositivo.setApagabilidad(apagabilidad);
			dispositivo.setNombre("tv");
			dispositivo.setIntensidad(15);
			
			///////Estados del dispositivo///////////////
			
			EstadoDispositivo prendidoHaceTresMeses = new Prendido();
			prendidoHaceTresMeses.setFechaInicio(DateTime.now().minusMonths(3));
			prendidoHaceTresMeses.setFechaFin(DateTime.now().minusMonths(2));
			dispositivo.agregarEstadoAHistorial(prendidoHaceTresMeses);
			
			EstadoDispositivo prendidoHace24Dias = new Prendido();
			prendidoHace24Dias.setFechaInicio(DateTime.now().minusDays(24));
			prendidoHace24Dias.setFechaFin(DateTime.now().minusDays(23));
			dispositivo.agregarEstadoAHistorial(prendidoHace24Dias);
			
			EstadoDispositivo apagado = new Apagado();
			apagado.setFechaInicio(DateTime.now().minusDays(22));
			apagado.setFechaFin(DateTime.now().minusDays(2));		
			dispositivo.agregarEstadoAHistorial(apagado);
			
			EstadoDispositivo prendidoHaceUnDia = new Prendido();
			prendidoHaceUnDia.setFechaInicio(DateTime.now().minusDays(1));
			prendidoHaceUnDia.setFechaFin(DateTime.now().minusHours(10));
			dispositivo.agregarEstadoAHistorial(prendidoHaceUnDia);
			
			/////////////////Persistiendo el dispositivo/////////
			DaoMySQL<Inteligente> importadorDisp = new DaoMySQL<Inteligente>();
			importadorDisp.init();
			importadorDisp.setTipo(Inteligente.class);
			importadorDisp.agregar(dispositivo);
			
			/////////////////////Ahora traemos el disp persistido////////
			
			DaoMySQL<Inteligente> importadorDispInteligente = new DaoMySQL<Inteligente>();
			importadorDispInteligente.init();
			importadorDispInteligente.setTipo(Inteligente.class);
			Inteligente dispInt = importadorDispInteligente.buscarPorNombre("tv");
			assertEquals("tv", dispInt.getNombre());
			
			///Mostrando por consola todos los intervalos que estuvo encendido durante el ultimo mes////
			dispInt.getHistorialDeEstados().filtrarLasVecesPrendido().stream().forEach(estadoEncendido -> 
				{
					if( dispInt.getHistorialDeEstados().cumpleFechas(estadoEncendido, DateTime.now().minusMonths(1), DateTime.now()))
					{
						System.out.print("Se encendio el " + estadoEncendido.getFechaInicio() + "\n");
						System.out.print("Se apago el " + estadoEncendido.getFechaFin() + "\n");
					}	
				}
			);
			
			////Modificar su intensidad y grabarlo
			
			dispInt.setIntensidad(24);
			importadorDispInteligente.modificar(dispInt);
			
			//////Recuperando el disp y evaluando que la intensidad coincida con el esperado
			
			Inteligente dispInt2 = importadorDispInteligente.buscarPorNombre("tv");
			assertEquals(24, dispInt2.getIntensidad());		
		}
}
