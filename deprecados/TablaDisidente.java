package models.entities.generadorReportes;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.Standard;
import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;

public final class TablaDisidente extends TimerTask{
	
	private DaoMySQL<Elemento> dao;
	private int intervaloActualizacion;
	private static Timer timer;
	
	public TablaDisidente() {
		dao = new DaoMySQL<Elemento>();
	}


	public final void init() {
		//Este timer comienza ahora y se actualiza cada x horas...
		timer.schedule(this, Calendar.getInstance().getTime(), getIntervaloActualizacion());
	}
	
	@Override
	public void run() {
		//this.actualizarClientes();
		this.actualizarDispositivos();
		//this.actualizarTransformadores();
		this.mostrarConsumoDispositivos();
	}

	private void actualizarDispositivos() {
		//Primero los disp. inteligentes
		DaoMySQL<Inteligente> importadorDispInteligente = new DaoMySQL<Inteligente>();
		importadorDispInteligente.init();
		importadorDispInteligente.setTipo(Inteligente.class);
		List<Inteligente> dispInteligentes = importadorDispInteligente.dameTodos();
		dispInteligentes.stream().forEach( inteligente -> 
		{
			//se almacena el consumo cada x horas, asi, cuando el timer vuelva a activarse, grabaria 
			//el consumo del intervalo entre la ultima actualizacion hasta ahora y asi consecutivamente..
			
			inteligente.getConsumoPorPeriodo(DateTime.now().minusHours(intervaloActualizacion), DateTime.now());
			String nombre = "Inteligente id: "+ inteligente.getId();
			double valor = inteligente.getConsumoPorPeriodo(DateTime.now().minusHours(intervaloActualizacion), DateTime.now());
			//para mi, la "fechainicio" no iria
			this.agregarElementoReporte(nombre, valor, DateTime.now().minusHours(intervaloActualizacion));	
		});
		
		//Ahora los disp. standard
		DaoMySQL<Standard> importadorDispStandards = new DaoMySQL<Standard>();
		importadorDispStandards.init();
		importadorDispStandards.setTipo(Standard.class);
		List<Standard> dispStandards = importadorDispStandards.dameTodos();
		dispStandards.stream().forEach( standard -> 
		{
			standard.getConsumoPorPeriodo(DateTime.now().minusHours(intervaloActualizacion), DateTime.now());
			String nombre = "Standard id: " + standard.getId();
			double valor = standard.getConsumoPorPeriodo(DateTime.now().minusHours(intervaloActualizacion), DateTime.now());
			//para mi, la "fechainicio" no iria
			this.agregarElementoReporte(nombre, valor, DateTime.now().minusHours(intervaloActualizacion));	
		});
	//
	}

	//////------- getters y setters default ------//////
	public long getIntervaloActualizacion() {
		return intervaloActualizacion;
	}

	public void setIntervaloActualizacion(int intervaloActualizacion) {
		this.intervaloActualizacion = intervaloActualizacion;
	}

	///////------ agregar cosas a tabla ------////////
	public final void agregarElementoReporte(String nombre, double valor, DateTime fechaInicio) {
		Elemento nuevoElemento = new Elemento();
		nuevoElemento.setTipo(nombre);
		nuevoElemento.setValor(valor);
		nuevoElemento.setFechaInicio(fechaInicio);
		
		dao.agregar(nuevoElemento);
	}


	///////-------- generar string de consumos (para darle al reporte)------////////

	public void mostrarConsumoDispositivos() {
		List<Elemento> elementos = dao.dameTodos();
		elementos.stream().forEach( elemento ->
		{
			System.out.println( elemento.getTipo() + "y su consumo fue" + elemento.getValor() + 
					"durante las ultimas " + this.getIntervaloActualizacion() + "horas.");
		});
	}
}

