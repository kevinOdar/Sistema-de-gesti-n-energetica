package models.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.dao.hibernate.ModelHelper;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.Standard;
import models.entities.dominio.ClienteResidencial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class DispositivoController
{
	private static DispositivoController controller = null;
	private static DaoMySQL<Dispositivo> importadorDispositivos = null;
	private static DaoMySQL<Inteligente> importadorInteligentes = null;
	private static DaoMySQL<Standard> importadorStandard =  null;
	private static DaoMySQL<Caracteristica> importadorCaracteristicas = null;

	private DispositivoController() {}
	
	public static DispositivoController get() 
	{
		if(DispositivoController.controller == null) 
			DispositivoController.controller = new DispositivoController();
		if(importadorDispositivos == null) 
		{		
			importadorDispositivos = new DaoMySQL<Dispositivo>();
			importadorDispositivos.init();
			importadorDispositivos.setTipo(Dispositivo.class);	
		}
		
		if(importadorInteligentes == null) 
		{		
			importadorInteligentes = new DaoMySQL<Inteligente>();
			 importadorInteligentes.init();
			 importadorInteligentes.setTipo(Inteligente.class);
		}
		
		if(importadorStandard == null) 
		{
			importadorStandard = new DaoMySQL<Standard>();
			importadorStandard.init();
			importadorStandard.setTipo(Standard.class);
		}

		if(importadorCaracteristicas == null) 
		{
			importadorCaracteristicas = new DaoMySQL<Caracteristica>();
			importadorCaracteristicas.init();
			importadorCaracteristicas.setTipo(Caracteristica.class);
		}
		return controller;
	}
	
	public ModelAndView verDispositivos(Request request, Response response)
	{
		Map<String, Object> model=new HashMap<>();
		
		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		importador.init();
		importador.setTipo(ClienteResidencial.class);
		
		String alias = request.params("aliasCliente");	
		ClienteResidencial cliente = importador.buscarPorAlias(alias);
		
		List<Dispositivo> dispositivos = cliente.getDispositivos();
		List<Inteligente> dispInteligentes = new ArrayList<Inteligente>();
		List<Standard> dispStandards = new ArrayList<Standard>();
		
		dispositivos.stream().forEach(disp ->  
		{
			if(disp.sosInteligente())
			{
				dispInteligentes.add(importadorInteligentes.buscarPorId(disp.getId()));
			}
			else
			{
				dispStandards.add(importadorStandard.buscarPorId(disp.getId()));
			}
		});

		Sesion sesion = request.session().attribute("sesion");

		
		model.put("aliasCliente", alias);
		model.put("dispInteligentes", dispInteligentes);
		model.put("dispStandards", dispStandards);
		model.put("esAdmin", false);
		model.put("esCliente", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);

		return new ModelAndView(model, "dispositivos.hbs");
	}
	
	public ModelAndView modificar(Request request, Response response) {
		
		Map<String, Object> model=new HashMap<>();
		int id = Integer.parseInt(request.params("id"));

		List<Caracteristica> caracteristicas = importadorCaracteristicas.dameTodos();
		
		Dispositivo disp = importadorDispositivos.buscarPorId(id);
		
		if(disp.sosInteligente())
		{
			Inteligente dispInteligente = importadorInteligentes.buscarPorId(id);
			model.put("dispositivo", dispInteligente);
			model.put("esInteligente", true);
		}
		else
		{
			Standard dispStandard = importadorStandard.buscarPorId(id);
			model.put("dispositivo", dispStandard);
			model.put("esInteligente", false);
		}

		model.put("caracteristicas", caracteristicas);
		model.put("modificarDatos", true);
		return new ModelAndView(model, "modalDispositivo.hbs");
	}
	
	public ModelAndView guardar(Request request, Response response) 
	{
		Map<String, Object> model=new HashMap<>();
		int id = Integer.parseInt(request.params("id"));
		
		Dispositivo disp = importadorDispositivos.buscarPorId(id);	
		
		if(disp.sosInteligente())
		{
			Inteligente inteligente = importadorInteligentes.buscarPorId(id);
			inteligente = this.setearParametrosInteligente(inteligente, request);
			importadorInteligentes.modificar(inteligente);	
		}
		else
		{
			Standard standard = importadorStandard.buscarPorId(id);
			standard = this.setearParametrosStandard(standard, request);
			importadorStandard.modificar(standard);	
		}
			
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Dispositivo actualizado exitosamente");
		return new ModelAndView(model, "modalBase.hbs");
	}

	private Inteligente setearParametrosInteligente(Inteligente dispositivo, Request request) 
	{
		Caracteristica caracteristica = importadorCaracteristicas.buscarPorNombre(request.queryParams("caracteristicaInt"));
		dispositivo.setId(Integer.parseInt(request.params("id")));
		dispositivo.setConsumoEnModoAutomatico(Boolean.parseBoolean(request.queryParams("modoAutomatico")));
		dispositivo.setCaracteristica(caracteristica);
		dispositivo.setIntensidad(Integer.parseInt(request.queryParams("intensidad")));
		return dispositivo;
	}
	
	private Standard setearParametrosStandard(Standard dispositivo, Request request) 
	{
		dispositivo.setId(Integer.parseInt(request.params("id")));
		Caracteristica caracteristica = importadorCaracteristicas.buscarPorNombre(request.queryParams("caracteristicaStan"));
		dispositivo.setConsumoPorHora(Double.parseDouble(request.queryParams("consumoPorHora")));
		dispositivo.setHorasUsoDiario(Double.parseDouble(request.queryParams("horasUsoDiario")));
		dispositivo.setCaracteristica(caracteristica);
		return dispositivo;
	}
	
	public ModelAndView eliminar(Request request, Response response)
	{
		String resultado = null;
		
		Map<String, Object> model = new HashMap<>();

		ModelHelper eliminador = new ModelHelper();
		
		int id = Integer.parseInt(request.params(("id")));
		
		Dispositivo disp = importadorDispositivos.buscarPorId(id);	

		resultado = eliminador.eliminar(disp);
		
		if (resultado == null) 
		{
			model.put("header", "Operación exitosa");
			model.put("mensaje", "Dispositivo eliminado con éxito");
			return new ModelAndView(model, "modalBase.hbs");
		}
		else
		{
			response.status(401);
			response.type("text/html");
			model.put("header", "Error");
			model.put("mensaje", "No se pudo eliminar el dispositivo");
			return new ModelAndView(model, "modalBase.hbs");
		}
	}
	
	public ModelAndView nuevoStandard(Request request, Response response)
	{
		Map<String, Object> model = new HashMap<>();
		
		String aliasCliente = request.params("aliasCliente");
		List<Caracteristica> caracteristicas = importadorCaracteristicas.dameTodos();

		model.put("aliasCliente", aliasCliente);
		model.put("esNuevo", true);
		model.put("esInteligente", false);
		model.put("caracteristicas", caracteristicas);
		return new ModelAndView(model, "modalDispositivo.hbs");
	}
	
	public ModelAndView guardarStandard(Request request, Response response)
	{
		Map<String, Object> model = new HashMap<>();
	
		Caracteristica caracteristica = importadorCaracteristicas.buscarPorNombre(request.queryParams("caracteristicaStan"));
		Standard standard = new Standard();
		standard.setConsumoPorHora(Double.parseDouble(request.queryParams("consumoPorHora")));
		standard.setHorasUsoDiario(Double.parseDouble(request.queryParams("horasUsoDiario")));
		standard.setCaracteristica(caracteristica);
		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		importador.init();
		importador.setTipo(ClienteResidencial.class);
		
		String alias = request.params("aliasCliente");	
		ClienteResidencial cliente = importador.buscarPorAlias(alias);

		cliente.agregarDispositivo(standard);
		
		importadorStandard.agregar(standard);
		
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Dispositivo Standard guardado con éxito");
		return new ModelAndView(model, "modalBase.hbs");
	}
	
	public ModelAndView nuevoInteligente(Request request, Response response)
	{
		Map<String, Object> model = new HashMap<>();
		
		String aliasCliente = request.params("aliasCliente");
		System.out.print(aliasCliente);
		List<Caracteristica> caracteristicas = importadorCaracteristicas.dameTodos();

		model.put("aliasCliente", aliasCliente);
		model.put("esNuevo", true);
		model.put("esInteligente", true);
		model.put("caracteristicas", caracteristicas);
		return new ModelAndView(model, "modalDispositivo.hbs");
	}
	
	public ModelAndView guardarInteligente(Request request, Response response)
	{
		Map<String, Object> model = new HashMap<>();
		Inteligente inteligente = new Inteligente();
		
		Caracteristica caracteristica = importadorCaracteristicas.buscarPorNombre(request.queryParams("caracteristicaInt"));
		inteligente.setConsumoEnModoAutomatico(Boolean.parseBoolean(request.queryParams("modoAutomatico")));
		inteligente.setCaracteristica(caracteristica);
		inteligente.setIntensidad(Integer.parseInt(request.queryParams("intensidad")));
		
		inteligente.prender();
		inteligente.apagar();
		inteligente.prender();
		
		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		importador.init();
		importador.setTipo(ClienteResidencial.class);
		
		String alias = request.params("aliasCliente");	
		ClienteResidencial cliente = importador.buscarPorAlias(alias);

		cliente.agregarDispositivo(inteligente);
		
		importadorInteligentes.agregar(inteligente);
		
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Dispositivo inteligente guardado con éxito");
		return new ModelAndView(model, "modalBase.hbs");
	}
}
