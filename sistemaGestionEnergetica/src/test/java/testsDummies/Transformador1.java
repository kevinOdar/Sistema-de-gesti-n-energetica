package testsDummies;

import java.util.ArrayList;
import java.util.List;

import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;

public final class Transformador1 {

	private static Transformador transformador = null;
	private static Coordenada coordenada;
	private static List<Coordenada> coordenadas;
	
	private Transformador1() {}
	
	public static Transformador getTransformador() {
		if(transformador != null) {
			return Transformador1.transformador;
		} else {
			transformador = new Transformador();
			coordenada = new Coordenada();
			coordenadas = new ArrayList<>();
			
			coordenada.setLatitud(-34.1857989);
			coordenada.setLongitud(-58.2993033);
			
			coordenadas.add(coordenada);
			
			transformador.setCoordenadas(coordenadas);
			
			return transformador;
			
		}
	}
	
	public static Transformador getTransformador2() {
	
			transformador = new Transformador();
			coordenada = new Coordenada();
			coordenadas = new ArrayList<>();
			
			coordenada.setLatitud(-34.1857989);
			coordenada.setLongitud(-58.2993033);
			
			coordenadas.add(coordenada);
			
			transformador.setCoordenadas(coordenadas);
			
			return transformador;
			
		}
	
}
