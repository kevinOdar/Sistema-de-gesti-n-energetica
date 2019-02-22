package testsTransformadores;

import org.junit.*;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import models.entities.dispositivo.*;
import models.entities.dominio.*;
import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;
import models.entities.zonaGeografica.ZonaGeografica;
import testsDummies.*;

public class TestsTransformador {
	 public TestsTransformador() {}
	   
	 ZonaGeografica zona = Zona1.getZona();
	
	 Transformador transformador = Transformador1.getTransformador();
	 
	 ClienteResidencial unCliente = new ClienteResidencial();
	 ClienteResidencial otroCliente = new ClienteResidencial();
	 
	 Inteligente unDispositivo = new Inteligente();
	 Inteligente otroDispositivo = new Inteligente();
	 
	 Caracteristica unaCaracteristica = new Caracteristica();
	 Caracteristica otraCaracteristica = new Caracteristica();
	 
	 @Before
	 public void init() {
		 zona.crearZona();
		 transformador.setZona(zona);
		 
		 unaCaracteristica.setInteligente(true);
		 otraCaracteristica.setInteligente(true);
		 
	 }
         //Transformador pertenece a una Zona Geografica	
	@Test
	public void elTransformadorEstaEnZona() {
		
		boolean esperado = true;
		
		assertEquals(esperado, zona.estaEnZona(transformador));
	}
	
	@Test
	public void elTransformadorNoEstaEnZona() {
		
		
		Coordenada coordenadasNuevas = new Coordenada(-32.3000521, -54.3000524);
		List<Coordenada> coordsNuevas = new ArrayList<>();
		
		coordsNuevas.add(coordenadasNuevas);
		
		transformador.setCoordenadas(coordsNuevas);
        boolean esperado = false;
        
		assertEquals(esperado, zona.estaEnZona(transformador));
	}
	
	       //Suministro Actual de un Transformador
	@Test
	public void suministroActual() {
		
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
	       
//Agrego los clientes al Transformador
	       ArrayList<ClienteResidencial> clientesConectados = new ArrayList<ClienteResidencial>();
			clientesConectados.add(unCliente);
			clientesConectados.add(otroCliente);
	       transformador.setClientesConectados(clientesConectados);
	       
//Defino el valor esperado y pruebo el test
	       double esperado = 2.75;
	   
	       assertEquals(0, Double.compare(esperado, transformador.suministroActual()));

    }
	
}
	

