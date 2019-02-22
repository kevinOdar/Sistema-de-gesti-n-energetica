package models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import models.controllers.helpers.Sesion;
import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Dispositivo;
import models.entities.dominio.ClienteResidencial;
import models.entities.optimizador.Optimizador;
import models.entities.optimizador.simplex.AlgoritmoOptimizadorConsumoSimplex;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OptimizacionController {
	private static OptimizacionController controller = null;
	private static DaoMySQL<ClienteResidencial> importador = null;
	private int ordenDispositivo;
	private double solucionDouble;
	private double dispositivosEstuvieronEncendido;
	private double horasEncendidoAlMes;
	private double horasRecomendadas;
	
	private OptimizacionController() {}
	
	public static OptimizacionController get() {
		
		if(OptimizacionController.controller == null) OptimizacionController.controller = new OptimizacionController();
		if(importador == null) {
			importador = new DaoMySQL<ClienteResidencial>();
			importador.init();
			importador.setTipo(ClienteResidencial.class);
		}
		return controller;
	}

	public ModelAndView verOptimizacion(Request request, Response response) {
		
		Map<String, Object> model=new HashMap<>();
	  //Inicializo Cliente
		ClienteResidencial cliente = importador.buscarPorAlias(request.params("aliasCliente"));
	    List<Dispositivo> dispositivos = cliente.getDispositivos();	    
	  //Inicializo Optimizador
        Optimizador optimizador = new Optimizador();
		optimizador.setOptimizador(new AlgoritmoOptimizadorConsumoSimplex());
	    optimizador.getOptimizador().setEnfoque(GoalType.MAXIMIZE);
		optimizador.getOptimizador().setDispositivosAOptimizar(dispositivos);
		
		PointValuePair solucion = optimizador.getOptimizador().calcularConsumoOptimo();
	     ordenDispositivo = 0;
		 solucionDouble = 0.0;
		 dispositivosEstuvieronEncendido = 0.0;
		dispositivos.stream().forEach(dispositivo -> 
		{ 
			horasEncendidoAlMes = dispositivo.getConsumoPorHora();            //  Consumo de cada Dispositivo
			horasRecomendadas = solucion.getPoint()[ordenDispositivo];        //  Consumo Recomendado a cada Dispositivo
			dispositivosEstuvieronEncendido += horasEncendidoAlMes;            //  Consumo Total de todos los Dispositivos
			ordenDispositivo++;
		});
		
		solucionDouble = solucion.getValue();       //Solucion Simplex
		
		model.put("dispositivosCliente", dispositivos);
		model.put("consumohs", horasEncendidoAlMes);
		model.put("consumoRecomendado", horasRecomendadas);
		model.put("consumoTotal", dispositivosEstuvieronEncendido);
		model.put("solucionSimplex", solucionDouble);
		model.put("hogarEficiente", solucionDouble>dispositivosEstuvieronEncendido);
		
		Sesion sesion = request.session().attribute("sesion");

		
		model.put("esAdmin", false);
		model.put("esCliente", true);
		model.put("usuario", sesion.getAlias());
		model.put("logueado", true);
		return new ModelAndView(model, "optimizacion.hbs");
	}	
	
}

