package models.controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class InicioController {

	private static InicioController controller = null;
	
	private InicioController() {}
	
	public static InicioController get() {
		
		if(InicioController.controller == null) InicioController.controller = new InicioController();
		
		return controller;
	}
	
	public ModelAndView inicio(Request request, Response response) {
		Map<String, Object> model=new HashMap<>();
		model.put("esPagInicio", true);
		//model.put("header", "¡Bienvenido!");
		model.put("mostrarBotonMapa", true);
		return new ModelAndView(model, "inicio.hbs");
	}
}
