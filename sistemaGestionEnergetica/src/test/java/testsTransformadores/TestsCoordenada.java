package testsTransformadores;

import static org.junit.Assert.assertEquals;  

import org.junit.Before;
import org.junit.Test;

import models.entities.transformadores.Coordenada;

public class TestsCoordenada {

	
	Coordenada unaCoordenada;
	Coordenada otraCoordenada;
	double latitud2;
	double longitud2;
	double distanciaEntreUnayOtra;
  
  
	@Before
	public void initParametros() 
	{
		unaCoordenada = new Coordenada(45, 34);
		otraCoordenada = new Coordenada(23,48);
		latitud2 = otraCoordenada.getLongitud();
		longitud2 = otraCoordenada.getLatitud();
	}
	
	@Test
	public void testDistanciaEntreDosPuntosEs904() 
	{
    //el valor delta acepta un margen de error de 5 kms teniendo en cuenta el error que la misma 
    //formula acarrea
		distanciaEntreUnayOtra = unaCoordenada.distanciaEntreCoordenadas(latitud2, longitud2);
		assertEquals(904, distanciaEntreUnayOtra, 5);
		
  }
  
}
