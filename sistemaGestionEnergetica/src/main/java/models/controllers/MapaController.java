package models.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;

import models.dao.hibernate.DaoMySQL;
import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;
import models.entities.zonaGeografica.PoligonoMapaCalor;
import models.entities.zonaGeografica.ZonaGeografica;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MapaController {
	private static MapaController controller = null;
	private static DaoMySQL<ZonaGeografica> daoZonas = null;
	private static DaoMySQL<Transformador> daoTransf = null;
	

	private MapaController() {
	}

	public static MapaController get() {

		if (MapaController.controller == null)
			MapaController.controller = new MapaController();

		if (daoZonas == null) {
			daoZonas = new DaoMySQL<ZonaGeografica>();
			daoZonas.init();
			daoZonas.setTipo(ZonaGeografica.class);
		}
		
		if (daoTransf == null) {
			daoTransf = new DaoMySQL<Transformador>();
			daoTransf.init();
			daoTransf.setTipo(Transformador.class);
		}

		return controller;
	}

	public ModelAndView verMapa(Request request, Response response) {
		return new ModelAndView(null, "mapa_inicio.hbs");
	}
	
	public String getJsonTransformadores() {
		
		List<Transformador> transformadores = daoTransf.dameTodos();
		String inicio = "{\"saludo\": \"hola\", \"transformadores\" : [" ;
		String cierre = "]}" ;
		String json = inicio + getJsonTransformadoresElements(transformadores) + cierre ;
		
		return json;
	}

	
	private String getJsonTransformadoresElements (List<Transformador> transformadores) {
		
		String json = "";
		int cantidadDeTransf = transformadores.size();
		
		for(int indice = 0; indice < cantidadDeTransf; indice++ ) {
			
			Transformador transformador = transformadores.get(indice);
			json = agregarTransformadorEnFormatoJson(json, transformador) ;
			json = agregarComaSiCorresponde(json, indice, cantidadDeTransf) ;
			
		}
		return json;
	}
	
	public String getJsonZonas( )
	{
		List<ZonaGeografica> zonasOG = daoZonas.dameTodos() ;
		List<PoligonoMapaCalor> zonas = getZonasMapa(zonasOG) ;
		double consumoInstantaneo = 1000 ;
		for ( PoligonoMapaCalor z : zonas)
		{
			z.setConsumoInstantaneo(consumoInstantaneo * z.getId());
			System.out.println("consumo instantaneo para el ID "+z.getId()+ ": "+z.getConsumoInstantaneo());
		}
		Collections.sort(zonas, new Comparator<PoligonoMapaCalor>() {
			@Override
			public int compare(PoligonoMapaCalor poligono, PoligonoMapaCalor otroPoligono) {
				if (poligono.getConsumoInstantaneo() >  otroPoligono.getConsumoInstantaneo())
					return -1 ;
				
				if (poligono.getConsumoInstantaneo() <  otroPoligono.getConsumoInstantaneo()) 
					return 1 ;
				
				return 0 ;
			}
		});
		for ( PoligonoMapaCalor z : zonas)
		{
			System.out.println("consumo instantaneo luego de ordenar por consumo decreciente para el ID "+z.getId()+ ": "+z.getConsumoInstantaneo());
		}
		setOpacidadATodosSegunOrdenDeConsumo(zonas) ;
		for ( PoligonoMapaCalor z : zonas)
		{
			System.out.println("opacidad para el ID "+z.getId()+ ": "+z.getOpacidad());
		}
		int cantidadDeZonas = zonas.size() ;
		
		String json = "{\"zonas\" : [" ;
		for(int indice = 0; indice < cantidadDeZonas; indice++ ) {
			double opacidad = zonas.get(indice).getOpacidad() ;
			PoligonoMapaCalor zona= zonas.get(indice);
			json = agregarZonaEnFormatoJson(json, zona, opacidad) ;
			json = agregarComaSiCorresponde(json, indice, cantidadDeZonas) ;
			
		}
		json += "]}" ;
		return json ;
	}
	
	private void setOpacidadATodosSegunOrdenDeConsumo(List <PoligonoMapaCalor> zonas)
	{
		PoligonoMapaCalor zonaMaximoConsumo = zonas.get(0) ;
		zonas.stream().forEach(z -> z.determinarOpacidadSegunMax(zonaMaximoConsumo));
	}
	
	private List<PoligonoMapaCalor> getZonasMapa(List<ZonaGeografica> zonas)
	{
		List<PoligonoMapaCalor> zonasMapa = new ArrayList<>() ;
		for ( int i = 0 ; i < zonas.size() ; i++)
		{
			PoligonoMapaCalor zonaPoligono = new PoligonoMapaCalor() ;

			int id = zonas.get(i).getId() ;
			List<Coordenada> coordenadas = zonas.get(i).getCoordenadas() ;
			
			//así debería andar pero buoh...
//			double consumo = zonas.get(i).consumoTotalEnUnPeriodoDeLaZona(DateTime.now().
//							minusDays(2), DateTime.now()) ;
//			zonaPoligono.setConsumoInstantaneo(consumo);
			
			zonaPoligono.setId(id);
			zonaPoligono.setCoordenadas(coordenadas);
			
			zonasMapa.add(zonaPoligono) ;
		}
		return zonasMapa ; 
	}
	
	private String agregarTransformadorEnFormatoJson(String concat, Transformador transformador)
	{
		//String texto = "{\"long\": -58.420033, \"lat\": -34.598546 }";
		concat +="{ "+"\"nombre\" : "+"\""+ transformador.getNombre() +"\""+", " ;
		concat += "\"lat\" : " + transformador.getLatitud() + ", " ;
		concat += "\"long\" : " + transformador.getLongitud() + "}";
		return concat ;
	}
	
	private String agregarComaSiCorresponde(String concat, int indice, int cantidad )
	{
		if ( hayMasElementos(indice, cantidad) )
		{
			concat += "," ;
		}
		return concat ;
	}
	
	private boolean hayMasElementos( int indice, int cantidadDeElementos)
	{
		return indice != cantidadDeElementos - 1 ;
	}
	
	//aca recibo una sola zona, debería recibir también un 'colorDeLinea', de 'relleno' y una opacidad
	private String agregarZonaEnFormatoJson(String concat, PoligonoMapaCalor zona, double opacidad)
	{
		concat += "{ " +"\"id\" : "+ zona.getId() + ", " ;
		List<Coordenada> coords = zona.getCoordenadas() ;
		concat += "\"opacidad\" : " + opacidad + ", ";
		concat += "\"coordenadas\" : [" ;
		for ( int i = 0; i < coords.size() ; i++ )
		{
			concat += "{ \"lat\" : " + coords.get(i).getLatitud() + ", " ;
			concat += "\"long\" : " + coords.get(i).getLongitud() + " }";
			//concat += "["+ coords.get(i).getLatitud() +", "+ coords.get(i).getLongitud() +"]";
			concat = agregarComaSiCorresponde(concat, i, coords.size()) ;
		}
		concat += "]}" ;
		return concat ;
	}
	

	public double getConsumo(String nombreTransformador) {
		Transformador transformador = daoTransf.buscarPorNombre(nombreTransformador);
		return transformador.getConsumoTotalPorPeriodo(DateTime.now().minusMonths(1), DateTime.now());
	}
}
