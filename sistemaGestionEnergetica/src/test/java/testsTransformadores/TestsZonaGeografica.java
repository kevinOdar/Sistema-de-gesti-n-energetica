package testsTransformadores;

import org.junit.Before;
//import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

import models.entities.dispositivo.*;
import models.entities.dominio.*;
import models.entities.transformadores.Transformador;

import models.entities.zonaGeografica.ZonaGeografica;
import testsDummies.Transformador1;
import testsDummies.Transformador2;
import testsDummies.Zona1;

public class TestsZonaGeografica {
	
	 public TestsZonaGeografica() {}
	
	 ZonaGeografica zona = Zona1.getZona();
	
	 Transformador transformador = Transformador1.getTransformador();
	 Transformador otroTransformador = Transformador2.getTransformador();
	 
	 ClienteResidencial unCliente = new ClienteResidencial();
	 ClienteResidencial otroCliente = new ClienteResidencial();
	 
	 Inteligente unDispositivo = new Inteligente();
	 Inteligente otroDispositivo = new Inteligente();
	 
	 Caracteristica unaCaracteristica = new Caracteristica();
	 Caracteristica otraCaracteristica = new Caracteristica();
	 
	 @Before
	 public void init() {
		 zona.crearZona();
		 
		 unaCaracteristica.setInteligente(true);
		 otraCaracteristica.setInteligente(true);
		 
	 }
 
         //Cantidad de transformadores que tiene la Zona Geografica.	
	@Test
	public void cantidadDeTransformadoresDeLaZona() {
		
		ArrayList<Transformador> transformadores = new ArrayList<Transformador>();
		
		transformadores.add(transformador);
		transformadores.add(otroTransformador);
        
		zona.setTransformadores(transformadores);
		int esperado = 2;
		
		assertEquals(esperado, zona.getTransformadores().size());
	}
	           //Consumo Total de la Zona Geografica
	@Test
	public void consumoTotalDeUnaZona() {
		
		//Creo los clientes y le agrego sus respectivos dispositivos.
				unCliente.agregarDispositivo(unDispositivo);
				otroCliente.agregarDispositivo(otroDispositivo);
				
		//Prendo los dispositivos
				unDispositivo.prender();
				otroDispositivo.prender();
				
		//Agrego la caracteristica respectiva, junto con el coeficiente de consumo, a cada dispositivo.
			       unDispositivo.setCaracteristica(unaCaracteristica);
			       unaCaracteristica.setCoeficienteDeConsumo(1.25);
			       otroDispositivo.setCaracteristica(otraCaracteristica);
			       otraCaracteristica.setCoeficienteDeConsumo(1.50);
			       
		//Agrego los clientes a los Transformadores de la Zona
			       ArrayList<ClienteResidencial> clientesConectados = new ArrayList<ClienteResidencial>();
					clientesConectados.add(unCliente);
					clientesConectados.add(otroCliente);
			       transformador.setClientesConectados(clientesConectados);
			       
			       ArrayList<ClienteResidencial> clientesConectadosAOtroTransformador = new ArrayList<ClienteResidencial>();
			       clientesConectadosAOtroTransformador.add(otroCliente);
			       otroTransformador.setClientesConectados(clientesConectadosAOtroTransformador);
		//Agrego los transformadores a la Zona Geografica
			       ArrayList<Transformador> transformadoresConectados = new ArrayList<Transformador>();
			       transformadoresConectados.add(transformador);
			       transformadoresConectados.add(otroTransformador);
			       zona.setTransformadores(transformadoresConectados);
			       
		//Defino el valor esperado y pruebo el test
			       double esperado = 4.25;
			   
			       assertEquals(0, Double.compare(esperado, zona.consumoTotalDeLaZona()));
		
	}
	
}