package models.controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EquipoController {
	private static EquipoController controller = null;
	
	private EquipoController() {}
	
	public static EquipoController get() {
		
		if(EquipoController.controller == null) EquipoController.controller = new EquipoController();
		
		return controller;
	}
		
	public ModelAndView ver(Request request, Response response) {
		//String saludo = "Quiénes somos";
		Map<String, Object> model=new HashMap<>();
		//model.put("header", saludo);
		return new ModelAndView(model, "equipo.hbs");
	}
	
	
}
