package models.entities.optimizador;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.optim.linear.UnboundedSolutionException;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import models.entities.dispositivo.*;
import models.entities.dominio.*;
import models.entities.optimizador.simplex.AlgoritmoOptimizadorConsumoSimplex;
import models.entities.regla.actuadores.ActuadorBase;
import models.logger.Logger;

public class Optimizador extends TimerTask {
	private AlgoritmoOptimizadorConsumo optimizador;
	private List<ClienteResidencial> clientes;
	private List<ActuadorBase> acciones;
	private int ordenDispositivo; // desde 0(el primero) hasta el ultimo, por cada cliente
	private Logger logger = null;
	private boolean hayQueLoggear = false;
	private String solucionString;//para model
	private double dispositivosEstuvieronEncendido;//para model
    
	@Override
	public void run() {
		this.setOptimizador(new AlgoritmoOptimizadorConsumoSimplex());
		this.optimizador.setEnfoque(GoalType.MAXIMIZE);
		this.optimizarCadaCliente();
	}

	public List<ActuadorBase> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<ActuadorBase> actuadores) {
		this.acciones = actuadores;
	}

	private void optimizarCadaCliente() {
		List<ClienteResidencial> clientesConConsumoActivado = this.clientes.stream()
				.filter(cliente -> cliente.getConsumoEnAutomatico()).collect(Collectors.toList());
		clientesConConsumoActivado.stream().forEach(cliente -> {

			if (hayQueLoggear)
				logger.logSimplexCliente(cliente.getNombre());

			List<Dispositivo> dispositivosApagables = this.filtrarApagables(cliente.getDispositivos());
			// 2do filtramos dispositivos que se puedan apagar
			/*
			 * Aplico el algoritmo a toda la lista de dispositivo, ya que no puedo enviar
			 * uno por uno porque el calculo no se podr�a hacer (necesita de todos los
			 * dispositivos).
			 */
			this.optimizador.setDispositivosAOptimizar(dispositivosApagables);
			// empiezo a controlar segun los resultados
			try {
				PointValuePair solucion = this.optimizador.calcularConsumoOptimo();

				if (hayQueLoggear)
					logger.logSimplexDispositivos();

				// aplico la solucion
				this.aplicarSolucionADispositivos(dispositivosApagables, solucion);

			} catch (NoFeasibleSolutionException e) {

				if (hayQueLoggear)
					logger.logSolucionIncompatible(e.getMessage());

			} catch (UnboundedSolutionException e) {

				if (hayQueLoggear)
					logger.logSolucionInfinita(e.getMessage());
			}
		});
	}

	public void aplicarSolucionADispositivos(List<Dispositivo> dispositivosAOptimizar, PointValuePair solucion) {
		ordenDispositivo = 0;

		// de los dispositivos me quedo con los que no cumplen la solucion
		List<Dispositivo> dispositivosNoCumplidores = dispositivosAOptimizar.stream()
				.filter(dispositivo -> !this.dispositivoCumpleSolucion(dispositivo, solucion))
				.collect(Collectors.toList());

		// de los que no cumplen, solo necesito los inteligentes
		List<Dispositivo> dispInteligentesAOptimizar = this.getInteligentes(dispositivosNoCumplidores);

		dispInteligentesAOptimizar.stream().forEach(dispositivo -> this.aplicarAcciones((Inteligente) dispositivo));

	}

	private List<Dispositivo> getInteligentes(List<Dispositivo> unaLista) {
		return unaLista.stream().filter(dispositivo -> dispositivo instanceof Inteligente).collect(Collectors.toList());
	}

	public boolean dispositivoCumpleSolucion(Dispositivo dispositivo, PointValuePair solucion) {
		double horasEncendidoAlMes = dispositivo.getConsumoMensual();
           
		boolean cumpleSolucion = horasEncendidoAlMes <= solucion.getPoint()[ordenDispositivo];

		if (cumpleSolucion) {

			if (hayQueLoggear)
				logger.logSimplexDispositivoCumplidor(dispositivo.getNombre(), solucion.getPoint()[ordenDispositivo],
						horasEncendidoAlMes);

		} else {

			if (hayQueLoggear)
				logger.logSimpexDispositivoNoCumple(dispositivo.getNombre(), solucion.getPoint()[ordenDispositivo],
						horasEncendidoAlMes);

		}

		ordenDispositivo++;
		return cumpleSolucion;
	}

	public void aplicarAcciones(Inteligente dispositivo) {

		if (hayQueLoggear)
			logger.logEjecutarAccion(dispositivo.getNombre());
		acciones.stream().forEach(accion -> 
		{
			accion.setCanal(dispositivo.getNombre());
			accion.execute();
		});
	}

	private List<Dispositivo> filtrarApagables(List<Dispositivo> unaLista) {
		return unaLista.stream().filter(dispositivo -> dispositivo.sosUnDispositivoApagable())
				.collect(Collectors.toList());
	}

	public AlgoritmoOptimizadorConsumo getOptimizador() {
		return optimizador;
	}

	public void setOptimizador(AlgoritmoOptimizadorConsumo optimizador) {
		this.optimizador = optimizador;
	}

	public List<ClienteResidencial> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteResidencial> clientes) {
		this.clientes = clientes;
	}

	public boolean isHayQueLoggear() {
		return hayQueLoggear;
	}

	public void setHayQueLoggear(boolean hayQueLoggear) {

		if (hayQueLoggear && logger == null) {
			logger = Logger.getLogger();
		} else
			logger = null;

		this.hayQueLoggear = hayQueLoggear;
	}

	public String mostrarSolucionSimplexDe(ClienteResidencial cliente)
	{
		//inicializando optimizador
		this.setOptimizador(new AlgoritmoOptimizadorConsumoSimplex());
		this.optimizador.setEnfoque(GoalType.MAXIMIZE);
		//
		
		List<Dispositivo> dispositivos= cliente.getDispositivos();
		this.optimizador.setDispositivosAOptimizar(dispositivos);
		
		try {
			PointValuePair solucion = this.optimizador.calcularConsumoOptimo();
			this.armarStringPorCadaDipositivoYSuSolucion(dispositivos, solucion);
			return solucionString;
		} catch (NoFeasibleSolutionException e) {

			return e.getMessage();

		} catch (UnboundedSolutionException e) {
			return e.getMessage();
		}
	}
	
	private void armarStringPorCadaDipositivoYSuSolucion(List<Dispositivo> dispositivos, PointValuePair solucion)
	{	
		ordenDispositivo = 0;
		solucionString = "";
		dispositivosEstuvieronEncendido = 0.0;
		dispositivos.stream().forEach(dispositivo -> 
		{
			double horasEncendidoAlMes = dispositivo.getConsumoMensual();//getConsumo?? getConsumo devuelve watts por mes, pero quiero horas??
			String solucionParticular = "El dispositivo: " + dispositivo.getCaracteristica().getNombre() + 
					" consumi�: " + horasEncendidoAlMes + " y deber�a consumir hasta: " +
					solucion.getPoint()[ordenDispositivo] + ". ";
			dispositivosEstuvieronEncendido += horasEncendidoAlMes;
			ordenDispositivo++;
			solucionString += solucionParticular;
		});
		
		solucionString += "Para que sea un hogar eficiente, todos los dispositivos(juntos) tienen que consumir en un mes hasta " + solucion.getValue() + " horas.";
		solucionString += "Estuvieron encendidos " + dispositivosEstuvieronEncendido + " \n";
	}
	
}
