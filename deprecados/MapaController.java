package models.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import models.dao.hibernate.DaoMySQL;
import models.entities.zonaGeografica.PoligonoMapaCalor;
import models.entities.zonaGeografica.Polygon2D;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MapaController {
	
	private static ArrayList<PoligonoMapaCalor> poligonosMapa ;
	private static Polygon2D poligonoCABA ;
	private static double consumoMax ;
	private static DaoMySQL <PoligonoMapaCalor> importador = null ;
	
	private static MapaController controller = null ;
	
	public static MapaController get() {
		
		if(MapaController.controller == null) {
			MapaController.controller = new MapaController();
		}
		
		if(importador == null) {
			importador = new DaoMySQL<PoligonoMapaCalor>();
			importador.init();
			importador.setTipo(PoligonoMapaCalor.class);
			
			poligonosMapa =	new ArrayList<PoligonoMapaCalor>() ;
			
			//previo a persistir todo lo cargo desde memoria
//			cargarEjemplos() ;
			
			
		}
		
		return controller;
	}
	
	public static ArrayList<PoligonoMapaCalor> getPoligonosOrdenados()
	{
		Collections.sort(poligonosMapa);
		return poligonosMapa ;
	}
	
	public void cargarEjemplos()
	{
		//cargo caba
		
		//se crean zonas
		
		//se crean transformadores para esas zonas
		
		//se cargan clientes en esa zona
		
		//se cargan dispositivos para esos clientes
		
		//se calcula el consumo de la ultima de cada una de esas zonas
		
		//cargo poligonosMapaCalor
		
			//double consumoMax = poligono[0].getConsumo
		
			//foreach poligono <- poligono.opacidad = e -> e.getConsumo / consumoMax
		
	}
	
	
	public ModelAndView mostrarMapaConsumo(Request request, Response response)
	{
		Map<String, Object> model = new HashMap<>() ;
		/*cada poligono va a ser una zona geografica que se envía desde acá,
		(CABA está harcodeada)
		cada objeto poligono de una clase nueva que voy a usar va a tener:
		   -consumoInstantaneo, medicion que va a mostrar cuando se clickee en el pin
		   -un punto interior al poligono, donde se colocará el pin
		   -la opacidad del rojo que a mayor consumo muestre un rojo más intenso
		   -el id de la zona geografica
		*/
		
		
		
		model.put("zonas", getPoligonosOrdenados()) ;
		model.put("caba", poligonoCABA) ;
		return new ModelAndView(model, "mapa_consumo.hbs") ;
	}
	
	
}
