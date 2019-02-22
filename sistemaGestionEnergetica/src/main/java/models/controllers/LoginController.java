package models.controllers;

import java.util.HashMap;
import java.util.Map;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.encriptador.Encriptador;
import models.entities.dominio.Administrador;
import models.entities.dominio.ClienteResidencial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {
	private static LoginController controller = null;
	private static DaoMySQL<Administrador> daoAdmin = null;
	private static DaoMySQL<ClienteResidencial> daoCliente = null;

	private LoginController() {
	}

	public static LoginController get() {

		if (LoginController.controller == null)
			LoginController.controller = new LoginController();

		if (daoAdmin == null) {
			daoAdmin = new DaoMySQL<Administrador>();
			daoAdmin.init();
			daoAdmin.setTipo(Administrador.class);
		}

		if (daoCliente == null) {
			daoCliente = new DaoMySQL<ClienteResidencial>();
			daoCliente.init();
			daoCliente.setTipo(ClienteResidencial.class);
		}

		return controller;
	}

	public ModelAndView loginUsuario(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		model.put("header", "Iniciar sesión");
		return new ModelAndView(model, "modalLogin.hbs");

	}
	
	public ModelAndView verificarLogin(Request request, Response response) {
		String alias = request.params("alias");
		Administrador admin = null;
		ClienteResidencial cliente = null;

		try {
			admin = daoAdmin.buscarPorAlias(alias);
		} catch (Exception e) {
		}

		try {
			cliente = daoCliente.buscarPorAlias(alias);
		} catch (Exception e) {
		}

		if (admin == null && cliente == null) {
			Map<String, Object> model = new HashMap<>();
			model.put("header", "Error de logueo");
			model.put("mensaje", "No se encontró el alias");
			response.status(400);
			response.type("text/html");
			return new ModelAndView(model, "modalError.hbs");

		} else if (admin != null && cliente == null) {
			return this.verificarLoginAdmin(request, response, admin);
		} else {
			return this.verificarLoginCliente(request, response, cliente);
		}

	}

	private ModelAndView verificarLoginAdmin(Request request, Response response, Administrador admin) {
		Map<String, Object> model = new HashMap<>();
		String vista = null;
		String alias = request.params("alias");
		String contrasenia = request.queryParams("contrasenia");

		if (Encriptador.get().contraseniaCorrecta(contrasenia, admin.getContrasenia(), admin.getSalt())) {
			String ruta = "/admin/inicio_admin/" + alias;
			model.put("destino", ruta);
			model.put("header", "Logueo exitoso");
			model.put("mensaje", "Redireccionando...");
			vista = "modalOk.hbs";
			
			Sesion sesion = new Sesion();
			sesion.setAlias(admin.getAliasUsuario());
			sesion.setEstaLogueado(true);
			sesion.setTipoUsuario("admin");
			request.session().attribute("sesion", sesion);
			
		} else {
			model.put("header", "Error de logueo");
			model.put("mensajeError", "Clave incorrecta");
			vista = "modalError.hbs";
			response.type("text/html");
			response.status(400);
		}

		return new ModelAndView(model, vista);

	}

	public ModelAndView verificarLoginCliente(Request request, Response response, ClienteResidencial cliente) {
		Map<String, Object> model = new HashMap<>();
		String vista = null;
		String alias = request.params("alias");
		String contrasenia = request.queryParams("contrasenia");

		if (Encriptador.get().contraseniaCorrecta(contrasenia, cliente.getContrasenia(), cliente.getSalt())) {
			String ruta = "cliente/inicio_cliente/" + alias;
			model.put("destino", ruta);
			model.put("header", "Logueo exitoso");
			model.put("mensaje", "Redireccionando...");
			vista = "modalOk.hbs";

			Sesion sesion = new Sesion();
			sesion.setAlias(cliente.getAliasUsuario());
			sesion.setEstaLogueado(true);
			sesion.setTipoUsuario("cliente");
			request.session().attribute("sesion", sesion);

			return new ModelAndView(model, vista);

		} else {
			model.put("header", "Error de logueo");
			model.put("mensajeError", "Clave incorrecta");
			vista = "modalError.hbs";
			response.status(400);
			return null;
		}
	}
}
