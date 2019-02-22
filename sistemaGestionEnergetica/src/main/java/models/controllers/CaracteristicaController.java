package models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.dao.hibernate.ModelHelper;
import models.entities.dispositivo.Caracteristica;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CaracteristicaController {
	private static CaracteristicaController controller = null;
	private static DaoMySQL<Caracteristica> importador = null;
	
	private CaracteristicaController() {}
	
	public static CaracteristicaController get() {
		
		if(CaracteristicaController.controller == null) CaracteristicaController.controller = new CaracteristicaController();
		
		if(importador == null) {
			importador = new DaoMySQL<Caracteristica>();
			importador.init();
			importador.setTipo(Caracteristica.class);
		}
		
		return controller;
	}
	
	public ModelAndView verCaracteristicas(Request request, Response response) {
		
		List<Caracteristica> caracteristicas = importador.dameTodos();
		
		Map<String, Object> model=new HashMap<>();
		Sesion sesion = request.session().attribute("sesion");
		
		model.put("caracteristicas", caracteristicas);
		model.put("esAdmin", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);

		return new ModelAndView(model, "caracteristicas.hbs");
	}
	
	private ModelAndView mostrar(Request request, Response response, boolean permiteEdicion, boolean modificarDatos) {
		
		Map<String, Object> model=new HashMap<>();
		int id = Integer.parseInt(request.params("id"));
		
		Caracteristica caracteristica = importador.buscar(id);
		model.put("header", "Característica");
		model.put("modificarDatos", modificarDatos);
		model.put("caracteristica", caracteristica);
		model.put("permiteEdicion", permiteEdicion);
		model.put("esInteligente", caracteristica.isInteligente());
		model.put("esDeBajoConsumo", caracteristica.isDeBajoConsumo());
		return new ModelAndView(model, "modalCaracteristica.hbs");
	}

	public ModelAndView ver(Request request, Response response) {
		return this.mostrar(request, response, false, false);

	}
	
	public ModelAndView modificar(Request request, Response response) {
		return this.mostrar(request, response, true, true);
	}
	
	public ModelAndView guardar(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		int id = Integer.parseInt(request.params("id"));
		
		Caracteristica caracteristica = importador.buscar(id);
		
		if (caracteristica == null) {
			response.status(401);
			return null;
		} else {
		
		this.setearParametros(caracteristica, request);
		
		importador.modificar(caracteristica);
			
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Característica actualizada exitosamente");
		return new ModelAndView(model, "modalBase.hbs");
		}
	}

	public ModelAndView crearNuevaCategoria(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		Caracteristica caracteristica = new Caracteristica();

		this.setearParametros(caracteristica, request);

		importador.agregar(caracteristica);

		model.put("header", "Operación exitosa");
		model.put("mensaje", "Característica creada con éxito");

		return new ModelAndView(model, "modalBase.hbs");
	}

	private void setearParametros(Caracteristica caracteristica, Request request) {

		caracteristica.setNombre(request.queryParams("nombre"));
		caracteristica.setCoeficienteDeConsumo(Double.parseDouble(request.queryParams("coeficienteConsumo")));
		caracteristica.setDeBajoConsumo(Boolean.parseBoolean(request.queryParams("esDeBajoConsumo")));
		caracteristica.setInteligente(Boolean.parseBoolean(request.queryParams("esInteligente")));
		caracteristica.setRestriccionMayorIgual(Double.parseDouble(request.queryParams("mayorIgual")));
		caracteristica.setRestriccionMenorIgual(Double.parseDouble(request.queryParams("menorIgual")));


	}
	
	public ModelAndView eliminar(Request request, Response response) {
		String resultado = null;
		
		Map<String, Object> model = new HashMap<>();

		ModelHelper eliminador = new ModelHelper();
		
		int id = Integer.parseInt(request.params(("id")));
		Caracteristica caracteristica = importador.buscar(id);
		
		resultado = eliminador.eliminar(caracteristica);
		
		if (resultado == null) {
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Característica eliminada con éxito");

		return new ModelAndView(model, "modalBase.hbs");
		} else {
			response.status(401);
			response.type("text/html");
			model.put("header", "Error");
			model.put("mensaje", "No se pudo eliminar la característica");

			return new ModelAndView(model, "modalBase.hbs");
		}
		
	}
	
	public ModelAndView nueva(Request request, Response response) {

		Map<String, Object> model = new HashMap<>();
		model.put("header", "Característica");
		model.put("permiteEdicion", true);
		model.put("caracteristicaNueva", true);
		return new ModelAndView(model, "modalCaracteristica.hbs");
	}
}
