package models.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dominio.ClienteResidencial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ClienteController {
	private static ClienteController controller = null;
	private static DaoMySQL<ClienteResidencial> importador = null;
	
	private ClienteController() {}
	
	public static ClienteController get()
	{
		
		if(ClienteController.controller == null) ClienteController.controller = new ClienteController();
		if(importador == null) {
			importador = new DaoMySQL<ClienteResidencial>();
			importador.init();
			importador.setTipo(ClienteResidencial.class);
		}
		return controller;
	}
		
	public ModelAndView inicio(Request request, Response response) 
	{
		Sesion sesion = request.session().attribute("sesion");

		String saludo = "Bienvenido cliente: "+ sesion.getAlias() +"!";
		Map<String, Object> model=new HashMap<>();
		model.put("header", saludo);
		model.put("mostrarBotonMapa", true);
		model.put("alias", sesion.getAlias());
		model.put("esAdmin", false);
		model.put("esCliente", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);
		return new ModelAndView(model, "inicio_cliente.hbs");
	}

	public ModelAndView mostrarConsumoPorPeriodo(Request request, Response response) 
	{
		Map<String, Object> model=new HashMap<>();
		
		String fechaDesdeEnString = request.queryParams("fechaDesde");
		String fechaHastaEnString = request.queryParams("fechaHasta");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		DateTime fechaDesde = formatter.parseDateTime(fechaDesdeEnString);
		DateTime fechaHasta = formatter.parseDateTime(fechaHastaEnString);
		
		ClienteResidencial cliente = importador.buscarPorAlias(request.params("aliasCliente"));
		double cuandoConsumioEnCiertoPeriodo = cliente.cuantoConsumoEnUnPeriodo(fechaDesde, fechaHasta);
		//System.out.println("hasta llega" + cuandoConsumioEnCiertoPeriodo);
		model.put("resultado", cuandoConsumioEnCiertoPeriodo);
		return new ModelAndView(model, "modalMostrarConsumoPorPeriodo.hbs");
	}	
	
	public ModelAndView mostrarMediciones(Request request, Response response) 
	{
		Map<String, Object> model=new HashMap<>();
		
		ClienteResidencial cliente = importador.buscarPorAlias(request.params("aliasCliente"));
		List<Dispositivo> disps = cliente.getDispositivos();
		
		DaoMySQL<Inteligente> importadorInteligentes = new DaoMySQL<Inteligente>();
		importadorInteligentes.init();
		importadorInteligentes.setTipo(Inteligente.class);
		
		List<Inteligente> dispInteligentes = new ArrayList<Inteligente>();
		disps.stream().forEach( disp ->
			{
				if(disp.sosInteligente())
				{
					Inteligente inteligente = importadorInteligentes.buscarPorId(disp.getId());
					dispInteligentes.add(inteligente);
				}
			}
		);
		model.put("inteligentes", dispInteligentes);
		model.put("logueado", true);
		return new ModelAndView(model, "mediciones.hbs");
	}
}