package testsDummies;

import java.util.ArrayList;
import java.util.List;

import models.entities.transformadores.Coordenada;
import models.entities.zonaGeografica.ZonaGeografica;

public final class Zona1 {

	private static Coordenada coord1;
	private static Coordenada coord2;
	private static Coordenada coord3;
	private static Coordenada coord4;
	private static Coordenada coord5;
	private static Coordenada coord6;
	
	private static List<Coordenada> coordenadas;
	
	private static ZonaGeografica zona = null;
	
	private Zona1() {}
	
	public static ZonaGeografica getZona() {
		if(zona != null) {
			return zona;
		} else {
			coordenadas = new ArrayList<>();
			zona = new ZonaGeografica();
			coord1 = new Coordenada();
			coord2 = new Coordenada();
			coord3 = new Coordenada();
			coord4 = new Coordenada();
			coord5 = new Coordenada();
			coord6 = new Coordenada();
			//
			coord1.setLatitud(-34.1852919);
			coord1.setLongitud(-58.3002024);
			coordenadas.add(coord1);
			
			//
			coord2.setLatitud(-34.1864335);
			coord2.setLongitud(-58.3001311);
			coordenadas.add(coord2);
			
			//
			coord3.setLatitud(-34.1853531);
			coord3.setLongitud(-58.2990609);
			coordenadas.add(coord3);
			
			//
			coord4.setLatitud(-34.1863644);
			coord4.setLongitud(-58.2989768);
			coordenadas.add(coord4);
			
			//
			coord5.setLatitud(-34.1862543);
			coord5.setLongitud(-58.2979865);
			coordenadas.add(coord5);
			
			//
			coord6.setLongitud(-34.9865444);
			coord6.setLatitud(-58.2989654);
			coordenadas.add(coord6);

			zona.setCoordenadas(coordenadas);
			
			return zona;
		}
	}
}
