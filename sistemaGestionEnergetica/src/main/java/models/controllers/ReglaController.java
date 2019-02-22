package models.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.dao.hibernate.DaoMySQL;
import models.dao.hibernate.ModelHelper;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dominio.ClienteResidencial;
import models.entities.regla.Regla;
import models.entities.regla.actuadores.ActuadorBase;
import models.entities.regla.criterios.Criterio;
import models.entities.sensor.Sensor;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ReglaController {

	private static ReglaController controller = null;
	private static DaoMySQL<ClienteResidencial> daoCliente = null;
	private static DaoMySQL<Inteligente> daoInteligente = null;
	private static DaoMySQL<Regla> importadorReglas = null;
	private static DaoMySQL<Sensor> importadorSensores = null;

	private ReglaController() {
	}

	public static ReglaController get() {

		if (ReglaController.controller == null)
			ReglaController.controller = new ReglaController();

		if (daoCliente == null) {
			daoCliente = new DaoMySQL<ClienteResidencial>();
			daoCliente.init();
			daoCliente.setTipo(ClienteResidencial.class);
		}

		if (daoInteligente == null) {
			daoInteligente = new DaoMySQL<Inteligente>();
			daoInteligente.init();
			daoInteligente.setTipo(Inteligente.class);
		}
		
		if(importadorReglas == null) 
		{		
			importadorReglas = new DaoMySQL<Regla>();
			importadorReglas.init();
			importadorReglas.setTipo(Regla.class);	
		}
		
		if(importadorSensores == null) 
		{		
			importadorSensores = new DaoMySQL<Sensor>();
			importadorSensores.init();
			importadorSensores.setTipo(Sensor.class);	
		}
		
		return controller;
	}

	public ModelAndView inicio(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(model, "simplex.hbs");
	}

	public ModelAndView verReglas(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		List<Inteligente> inteligentes = new ArrayList<>();
		
		String alias = request.params("alias");

		ClienteResidencial cliente = daoCliente.buscarPorAlias(alias);

		List<Dispositivo> dispositivos = cliente.getDispositivos();

		for (Dispositivo disp : dispositivos) {
			if (disp.sosInteligente()) 
			{
				Inteligente inteligente = daoInteligente.buscar(disp.getId());
				if(!inteligente.getReglas().isEmpty())//si tiene reglas
				{
					inteligentes.add(inteligente);
				}
			}
		}

		model.put("inteligentes", inteligentes);
		model.put("esAdmin", false);
		model.put("esCliente", true);
		model.put("alias", alias);
		model.put("usuario", request.session().attribute("usuario"));
		return new ModelAndView(model, "reglas.hbs");
	}
	
	public ModelAndView modificar(Request request, Response response) {
		
		Map<String, Object> model=new HashMap<>();
		int id = Integer.parseInt(request.params("id"));
		
		Regla regla = importadorReglas.buscarPorId(id);
		
		model.put("regla", regla);
		return new ModelAndView(model, "modalRegla.hbs");
	}
	
	public ModelAndView guardar(Request request, Response response) 
	{
		Map<String, Object> model=new HashMap<>();
		int id = Integer.parseInt(request.params("id"));

		Regla regla = importadorReglas.buscarPorId(id);
		regla = this.setearParametros(regla, request);
		importadorReglas.modificar(regla);	

		model.put("header", "Operación exitosa");
		model.put("mensaje", "Regla actualizada exitosamente");
		return new ModelAndView(model, "modalBase.hbs");
	}
	
	private Regla setearParametros(Regla regla, Request request) 
	{
		regla.setId(Integer.parseInt(request.params("id")));
		regla.setNombre(request.queryParams("nombre"));
		return regla;
	}
	
	public ModelAndView eliminar(Request request, Response response)
	{
		String resultado = null;
		
		Map<String, Object> model = new HashMap<>();

		ModelHelper eliminador = new ModelHelper();
		
		int id = Integer.parseInt(request.params(("id")));
		
		Regla regla = importadorReglas.buscarPorId(id);	

		resultado = eliminador.eliminar(regla);
		
		if (resultado == null) 
		{
			model.put("header", "Operación exitosamente");
			model.put("mensaje", "Regla eliminada con éxito");
			return new ModelAndView(model, "modalBase.hbs");
		}
		else
		{
			response.status(401);
			response.type("text/html");
			model.put("header", "Error");
			model.put("mensaje", "No se pudo eliminar la regla");
			return new ModelAndView(model, "modalBase.hbs");
		}
	}
	
	public ModelAndView nueva(Request request, Response response) {
		Map<String, Object> model=new HashMap<>();
		
		String alias = request.params(("alias"));
		ClienteResidencial cliente = daoCliente.buscarPorAlias(alias);
		List<Dispositivo> dispInteligente = cliente.getDispositivosInteligentes();
		
		List<Sensor> sensoresDisponibles = importadorSensores.dameTodos();
		model.put("dispInteligente", dispInteligente);
		model.put("header", "Regla");
		model.put("sensor", sensoresDisponibles);
		return new ModelAndView(model, "modalReglaNueva.hbs");
	}
	
	public ModelAndView crearNuevaRegla(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		//Hay que elegir un sensor existente? o hay que crear un nuevo sensor?
		int idSensor = Integer.parseInt(request.queryParams("idSensor"));
		Sensor sensor = importadorSensores.buscarPorId(idSensor);
		
		Regla regla = new Regla();
		regla.setNombre(request.queryParams("nombre"));

		//Instanciando ACTUADOR
		ActuadorBase actuador;
		String actuadorEnString = request.queryParams("actuador");
		try
		{
			actuador = (ActuadorBase) Class.forName("models.entities.regla.actuadores."+ actuadorEnString).newInstance();
			regla.agregarActuador(actuador);
		}
		catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) 
		{		e.printStackTrace();		}
		
		//Instanciando CRITERIO
		Criterio criterio;
		String criterioEnString = request.queryParams("tipoCriterio");
		try
		{
			criterio = (Criterio) Class.forName("models.entities.regla.criterios."+ criterioEnString).newInstance();
			criterio.setValorMaximo(Integer.parseInt(request.queryParams("rangoMayor")));
			criterio.setValorMinimo(Integer.parseInt(request.queryParams("rangoMenor")));
			criterio.setValorComparacion(Integer.parseInt(request.queryParams("valorComparacion")));
			regla.agregarUnCriterio(criterio, sensor);
		}
		catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) 
		{		e.printStackTrace();		}
		
		Inteligente dispInteligente = daoInteligente.buscarPorId(Integer.parseInt(request.queryParams("idDispositivo")));
		regla.agregarDispositivo(dispInteligente);
		importadorReglas.agregar(regla);
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Regla guardada exitosamente");
		return new ModelAndView(model, "modalBase.hbs");
	}
}
