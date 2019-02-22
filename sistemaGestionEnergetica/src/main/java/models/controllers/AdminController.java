package models.controllers;

import java.util.HashMap;
import java.util.Map;

import models.controllers.helpers.Sesion;
//import models.entities.dominio.Administrador;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdminController {
	private static AdminController controller = null;
	
	private AdminController() {}
	
	public static AdminController get() {
		
		if(AdminController.controller == null) AdminController.controller = new AdminController();
		
		return controller;
	}
		
	public ModelAndView inicio(Request request, Response response) {
		
		Sesion sesion = request.session().attribute("sesion");
		
		String saludo = "¡Bienvenido administrador: "+ sesion.getAlias() +"!";
		
		Map<String, Object> model=new HashMap<>();
		model.put("header", saludo);
		model.put("esAdmin", true);
		model.put("mostrarBotonMapa", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);
		return new ModelAndView(model, "inicio_admin.hbs");
	}
	
	
}
