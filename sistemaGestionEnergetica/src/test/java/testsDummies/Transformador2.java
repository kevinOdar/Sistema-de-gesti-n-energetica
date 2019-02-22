package testsDummies;

import java.util.ArrayList;
import java.util.List;

import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;

public final class Transformador2 {

	private static Transformador transformador = null;
	private static Coordenada coordenada;
	private static List<Coordenada> coordenadas;
	
	private Transformador2() {}
	
	public static Transformador getTransformador() {
		if(transformador != null) {
			return Transformador2.transformador;
		} else {
			transformador = new Transformador();
			coordenada = new Coordenada();
			coordenadas = new ArrayList<>();
			
			coordenada.setLatitud(-34.5125655);
			coordenada.setLongitud(-58.3000211);
			
			coordenadas.add(coordenada);
			
			transformador.setCoordenadas(coordenadas);
			
			return transformador;
			
		}
	}
}
