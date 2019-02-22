package models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.generadorReportes.Elemento;
import models.generadorReportes.VistaReporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ReportesController {

	private static DaoMySQL<Elemento> dao;

	private static ReportesController controller = null;
	
	private ReportesController() {}
	
	public static ReportesController get() {
		
		if(ReportesController.controller == null) ReportesController.controller = new ReportesController();
		
		if (dao == null) {
			dao = new DaoMySQL<Elemento>();
			dao.init();
			dao.setTipo(Elemento.class);
		}
		
		return controller;
	}
	
	public ModelAndView ver(Request request, Response response) {
		Map<String, Object> model=new HashMap<>();
		List<Elemento> transformadores = VistaReporte.get().getTransformadores();
		List<Elemento> clientes = VistaReporte.get().getClientes();
		List<Elemento> inteligentes = VistaReporte.get().getInteligentes();
		List<Elemento> standards = VistaReporte.get().getStandard();
		
		Sesion sesion = request.session().attribute("sesion");
		
		model.put("transformadores", transformadores);
		model.put("clientes", clientes);
		model.put("inteligentes", inteligentes);
		model.put("standards", standards);
		model.put("esAdmin", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);
		model.put("mostrarBotonMapa", false);
		
		return new ModelAndView(model, "reportes.hbs");
	}
	
	public ModelAndView getPeriodo(Request request, Response response) {
		Map<String, Object> model=new HashMap<>();
		model.put("header", "Configurar reporte");
		model.put("elemento", request.params("elemento"));
		return new ModelAndView(model, "modalSetPeriodo.hbs");
	}
	
	public ModelAndView refrescarHogares(Request request, Response response) {
		List<Elemento> clientes = VistaReporte.get().getClientes();
		Map<String, Object> model=new HashMap<>();
		model.put("clientes", clientes);
		return new ModelAndView(model, "reporteHogares.hbs");
	}
	
	public ModelAndView refrescarDispositivos(Request request, Response response) {
		List<Elemento> inteligentes = VistaReporte.get().getInteligentes();
		List<Elemento> standards = VistaReporte.get().getStandard();
		Map<String, Object> model=new HashMap<>();
		model.put("inteligentes", inteligentes);
		model.put("standards", standards);
		return new ModelAndView(model, "reporteDispositivos.hbs");
	}
	
	public ModelAndView refrescarTransformadores(Request request, Response response) {
		List<Elemento> transformadores = VistaReporte.get().getTransformadores();
		Map<String, Object> model=new HashMap<>();
		model.put("transformadores", transformadores);
		return new ModelAndView(model, "reporteTransformadores.hbs");
	}
}
