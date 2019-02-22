package models.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.dao.hibernate.DaoMySQL;
import models.entities.dominio.ClienteResidencial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HogarController {


	private static HogarController controller = null;
	private static DaoMySQL<ClienteResidencial> daoClientes = null;
	
	private HogarController() {}
	
	public static HogarController get() {
		
		if(HogarController.controller == null) HogarController.controller = new HogarController();
		
		if(daoClientes == null) {
			daoClientes = new DaoMySQL<ClienteResidencial>();
			daoClientes.init();
			daoClientes.setTipo(ClienteResidencial.class);
		}
		
		return controller;
	}
	
	public ModelAndView inicio(Request request, Response response) {
		Map<String, Object> model=new HashMap<>();
		model.put("alias", request.params("aliasCliente"));
		return new ModelAndView(model, "modalEstadoHogar.hbs");
	}
	
	public ModelAndView verHogares(Request request, Response response) {
		List<ClienteResidencial> clientes = daoClientes.dameTodos();
		
		Map<String, Object> model=new HashMap<>();
		model.put("esAdmin", true);
		model.put("clientes", clientes);
		model.put("logueado", true);

		return new ModelAndView(model,"hogares.hbs");
	}
}
