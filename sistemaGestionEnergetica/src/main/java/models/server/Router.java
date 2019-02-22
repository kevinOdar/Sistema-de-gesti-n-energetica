package models.server;

import models.controllers.*;
import models.controllers.helpers.Sesion;
import models.server.spark.utils.BooleanHelper;
import models.server.spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
	private static HandlebarsTemplateEngine engine;
	
	private static void initEngine() {
		Router.engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue).build();

	}

	public static void init() {
		Router.initEngine();
		Spark.staticFileLocation("/public");
		Router.configure();
	}

	private static void configure() {
		/* restricciones */
		Spark.before("/inicio", (req, res) -> {

			Sesion sesion = req.session().attribute("sesion");
			
			if (sesion != null && sesion.getEstaLogueado()) {

				if (sesion.getTipoUsuario().equals("admin")) {
					res.redirect("/admin/inicio_admin/" + sesion.getAlias());
				}
				
				if(sesion.getTipoUsuario().equals("cliente")) {
					res.redirect("/cliente/inicio_cliente/" + sesion.getAlias());
				}
			}

		});

		Spark.before("/admin/*", (req, res) -> {

			Sesion sesion = req.session().attribute("sesion");
			
			if (sesion != null && sesion.getEstaLogueado()) {

				if (!sesion.getTipoUsuario().equals("admin"))
					Spark.halt(401, "Acceso no permitido");
			} else {
				Spark.halt(401, "Debe iniciar sesion");
			}

		});

		Spark.before("/cliente/*", (req, res) -> {

			Sesion sesion = req.session().attribute("sesion");
			
			if (sesion != null && sesion.getEstaLogueado()) {

				if (!sesion.getTipoUsuario().equals("cliente"))
					Spark.halt(401, "Acceso no permitido");
			} else {
				Spark.halt(401, "Debe iniciar sesion");
			}

		});

		/* inicio */
		Spark.get("/", (req, res) -> {
			res.redirect("/inicio");
			return null;
		}, Router.engine);
		Spark.get("/inicio", InicioController.get()::inicio, Router.engine);

		/* mapa consumo */
		Spark.get("/mapa_consumo", MapaController.get()::verMapa, Router.engine);
		Spark.get("/mapa_consumo/zonas", (req, res) -> {
			res.type("application/json");
			String zonas = MapaController.get().getJsonZonas();
			System.out.println(zonas);
			return zonas ;
		});
		
		/* mapa consumo */
		Spark.get("/mapa_consumo", MapaController.get()::verMapa, Router.engine);
		Spark.get("/mapa_consumo/transformadores", (req, res) -> {
			res.type("application/json");
			String transfs = MapaController.get().getJsonTransformadores();
			return transfs;
		});
		
		
		
		Spark.get("/mapa_consumo/transformadores/:nombre", (req, res) -> {
			res.type("application/json");
			double consumo = MapaController.get().getConsumo(req.params("nombre"));
			return "{ \"consumo\" :"+ consumo +"}";
		});

		/* login */
		Spark.get("/login/usuario", LoginController.get()::loginUsuario, Router.engine);
		Spark.get("/login/usuario/:alias", LoginController.get()::verificarLogin, Router.engine);

		/*Log out*/
		Spark.get("/logout", (req, res) -> {
			
			Sesion sesion = req.session().attribute("sesion");
			
			if (sesion != null) sesion.setEstaLogueado(false);
			
			res.redirect("/inicio");
			
			return null;
		});
		
		/* equipo */
		Spark.get("/equipo", EquipoController.get()::ver, Router.engine);

		/* admin */
		Spark.get("/admin/inicio_admin/", (req, res) -> {
			Sesion sesion = req.session().attribute("sesion");

			res.redirect("/admin/inicio_admin/" + sesion.getAlias());
			return null;
		}, Router.engine);
		Spark.get("/admin/inicio_admin/:alias", AdminController.get()::inicio, Router.engine);

		/* categoria */
		Spark.get("/admin/categorias", CategoriaController.get()::verCategorias, Router.engine);
		Spark.get("/admin/categoria/nueva", CategoriaController.get()::nueva, Router.engine);
		Spark.post("/admin/categoria/nueva", CategoriaController.get()::crearNuevaCategoria, Router.engine);
		Spark.get("/admin/categoria/:id", CategoriaController.get()::ver, Router.engine);
		Spark.put("/admin/categoria/:id", CategoriaController.get()::modificar, Router.engine);
		Spark.post("/admin/categoria/:id", CategoriaController.get()::guardar, Router.engine);
		Spark.delete("/admin/categoria/:id", CategoriaController.get()::eliminar, Router.engine);

		/* caracteristicas */
		Spark.get("/admin/caracteristicas", CaracteristicaController.get()::verCaracteristicas, Router.engine);
		Spark.get("/admin/caracteristica/nueva", CaracteristicaController.get()::nueva, Router.engine);
		Spark.post("/admin/caracteristica/nueva", CaracteristicaController.get()::crearNuevaCategoria, Router.engine);
		Spark.get("/admin/caracteristica/:id", CaracteristicaController.get()::ver, Router.engine);
		Spark.put("/admin/caracteristica/:id", CaracteristicaController.get()::modificar, Router.engine);
		Spark.post("/admin/caracteristica/:id", CaracteristicaController.get()::guardar, Router.engine);
		Spark.delete("/admin/caracteristica/:id", CaracteristicaController.get()::eliminar, Router.engine);

		/* reportes */
		Spark.get("/admin/reportes", ReportesController.get()::ver, Router.engine);
		Spark.get("/admin/reportes/periodo/:elemento", ReportesController.get()::getPeriodo, Router.engine);
		Spark.get("/admin/reportes/Hogares", ReportesController.get()::refrescarHogares, Router.engine);
		Spark.get("/admin/reportes/Dispositivos", ReportesController.get()::refrescarDispositivos, Router.engine);
		Spark.get("/admin/reportes/Transformadores", ReportesController.get()::refrescarTransformadores, Router.engine);

		/* cliente */
		Spark.get("/cliente/inicio_cliente/", (req, res) -> {
			Sesion sesion = req.session().attribute("sesion");
			res.redirect("/cliente/inicio_cliente/" + sesion.getAlias());
			return null;
		}, Router.engine);
		Spark.get("/cliente/inicio_cliente/:alias", ClienteController.get()::inicio, Router.engine);
		Spark.get("/cliente/consumoPorPeriodo/:aliasCliente", ClienteController.get()::mostrarConsumoPorPeriodo,
				Router.engine);
		Spark.get("/cliente/mediciones/:aliasCliente", ClienteController.get()::mostrarMediciones, Router.engine);

		/* Regla */
		Spark.get("/cliente/reglas/:alias", ReglaController.get()::verReglas, Router.engine);
		Spark.put("/cliente/regla/:id", ReglaController.get()::modificar, Router.engine);
		Spark.post("/cliente/regla/:id", ReglaController.get()::guardar, Router.engine);
		Spark.delete("cliente/regla/:id", ReglaController.get()::eliminar, Router.engine);
		Spark.get("/cliente/regla/nueva/:alias", ReglaController.get()::nueva, Router.engine);
		Spark.post("/cliente/regla/nueva/", ReglaController.get()::crearNuevaRegla, Router.engine);

		/* Dispositivos */
		Spark.get("/cliente/dispositivos/:aliasCliente", DispositivoController.get()::verDispositivos, Router.engine);
		Spark.put("/cliente/dispositivo/:id", DispositivoController.get()::modificar, Router.engine);
		Spark.post("/cliente/dispositivo/:id", DispositivoController.get()::guardar, Router.engine);
		Spark.delete("cliente/dispositivo/:id", DispositivoController.get()::eliminar, Router.engine);
		Spark.get("/cliente/dispositivoStandard/nuevo/:aliasCliente", DispositivoController.get()::nuevoStandard,
				Router.engine);
		Spark.post("/cliente/dispositivoStandard/nuevo/:aliasCliente", DispositivoController.get()::guardarStandard,
				Router.engine);
		Spark.get("/cliente/dispositivoInteligente/nuevo/:aliasCliente", DispositivoController.get()::nuevoInteligente,
				Router.engine);
		Spark.post("/cliente/dispositivoInteligente/nuevo/:aliasCliente",
				DispositivoController.get()::guardarInteligente, Router.engine);

		/* Estado hogar */
		Spark.get("/cliente/hogar/:aliasCliente", HogarController.get()::inicio, Router.engine);
		Spark.get("/admin/hogares", HogarController.get()::verHogares, Router.engine);
		
		/*Optimizacion*/
		Spark.get("/cliente/optimizacion/:aliasCliente", OptimizacionController.get()::verOptimizacion, Router.engine);
	}

}
