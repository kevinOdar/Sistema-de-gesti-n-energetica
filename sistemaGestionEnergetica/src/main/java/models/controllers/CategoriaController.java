package models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.dao.hibernate.ModelHelper;
import models.entities.dominio.CategoriaConsumo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CategoriaController {
	private static CategoriaController controller = null;
	private static DaoMySQL<CategoriaConsumo> importador = null;

	private CategoriaController() {
	}

	public static CategoriaController get() {

		if (CategoriaController.controller == null)
			CategoriaController.controller = new CategoriaController();
		if (importador == null) {
			importador = new DaoMySQL<CategoriaConsumo>();
			importador.init();
			importador.setTipo(CategoriaConsumo.class);
		}
		return controller;
	}

	public ModelAndView verCategorias(Request request, Response response) {
		List<CategoriaConsumo> categorias = importador.dameTodos();

		Sesion sesion = request.session().attribute("sesion");

		Map<String, Object> model = new HashMap<>();
		model.put("categorias", categorias);
		model.put("esAdmin", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);		
		return new ModelAndView(model, "categorias.hbs");
	}
	
	public ModelAndView nueva(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		model.put("header", "Categoría de consumo");
		model.put("permiteEdicion", true);
		model.put("categoriaNueva", true);
		return new ModelAndView(model, "modalCategoria.hbs");
	}

	public ModelAndView mostrar(Request request, Response response, boolean permiteEdicion, boolean modificarDatos) {
		Map<String, Object> model = new HashMap<>();
		int id = Integer.parseInt(request.params("id"));
		CategoriaConsumo categoria = importador.buscar(id);
		model.put("header", "Categoría de consumo");
		model.put("categoria", categoria);
		model.put("permiteEdicion", permiteEdicion);
		model.put("modificarDatos", modificarDatos);
		return new ModelAndView(model, "modalCategoria.hbs");
	}

	public ModelAndView ver(Request request, Response response) {
		return this.mostrar(request, response, false, false);

	}

	public ModelAndView modificar(Request request, Response response) {
		return this.mostrar(request, response, true, true);
	}

	public ModelAndView guardar(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		int idCategoria = Integer.parseInt(request.params("id"));
		
		CategoriaConsumo categoria = importador.buscar(idCategoria);
		
		if (categoria == null) {
			response.status(401);
			return null;
		} else {
		
		this.setearParametros(categoria, request);
		
		importador.modificar(categoria);
			
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Categoría de consumo actualizada exitosame");

		return new ModelAndView(model, "modalBase.hbs");
		}
	}

	public ModelAndView crearNuevaCategoria(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		CategoriaConsumo categoria = new CategoriaConsumo();

		this.setearParametros(categoria, request);

		importador.agregar(categoria);

		model.put("header", "Operación exitosa");
		model.put("mensaje", "Categoría de consumo creada con exito");

		return new ModelAndView(model, "modalBase.hbs");
	}

	private void setearParametros(CategoriaConsumo categoria, Request request) {

		categoria.setNombre(request.queryParams("nombre"));
		categoria.setConsumoMax(Integer.parseInt(request.queryParams("consumoMaximo")));
		categoria.setConsumoMin(Integer.parseInt(request.queryParams("consumoMinimo")));
		categoria.setCargoFijo(Integer.parseInt(request.queryParams("cargoFijo")));
		categoria.setCargoVariable(Integer.parseInt(request.queryParams("cargoVariable")));

	}
	
	public ModelAndView eliminar(Request request, Response response) {
		String resultado = null;
		
		Map<String, Object> model = new HashMap<>();

		ModelHelper eliminador = new ModelHelper();
		
		int id = Integer.parseInt(request.params(("id")));
		CategoriaConsumo categoria = importador.buscar(id);
		
		resultado = eliminador.eliminar(categoria);
		
		if (resultado == null) {
		model.put("header", "Operación exitosa");
		model.put("mensaje", "Categoría de consumo eliminada con exito");

		return new ModelAndView(model, "modalBase.hbs");
		} else {
			response.status(401);
			response.type("text/html");
			model.put("header", "Error");
			model.put("mensaje", "No se pudo eliminar la categoría");
			return new ModelAndView(model, "modalBase.hbs");
		}
	}
}
