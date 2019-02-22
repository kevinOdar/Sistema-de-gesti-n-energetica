package models.generadorReportes;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.Standard;
import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;

public final class VistaReporte extends TimerTask {

	private DaoMySQL<Elemento> dao;
	private static int intervaloActualizacion;
	private static int retardoInicioTabla;
	private static Timer timer;
	private List<ClienteResidencial> clientes;
	private List<Inteligente> inteligentes;
	private List<Standard> standard;
	private List<Transformador> transformadores;
	private static VistaReporte instancia = null;
	
	private VistaReporte() {
		dao = new DaoMySQL<Elemento>();
		dao.init();
		dao.setTipo(Elemento.class);
		clientes = new ArrayList<>();
		inteligentes = new ArrayList<>();
		standard = new ArrayList<>();
		transformadores = new ArrayList<>();
		timer = new Timer();
	}

	public final void init() {

		timer.schedule(instancia, getRetardo(), getIntervaloActualizacion());

	}

	@Override
	public void run() {
		this.actualizarClientes();
		this.actualizarDispositivos();
		this.actualizarTransformadores();
	}

	public static VistaReporte get() {
		if (instancia == null) instancia = new VistaReporte();
		
		return instancia;
	}
	///////// -------- Actualizar tabla (pedir a cada elemento su consumo)
	///////// ----------//////////
	private void actualizarTransformadores() {
		List <Elemento> elemTransf = this.getElementosPersistidos("Transformador");
		
		for (Transformador unTransformador : transformadores) {
			int id = unTransformador.getId();
			Elemento elemento = this.buscarElementoPorIDcosa(elemTransf, id);
			DateTime fecha = elemento.getFechaInicio();
			double consumo = unTransformador.getConsumoTotalPorPeriodo(fecha, DateTime.now());
			elemento.setValor(consumo);
			dao.modificar(elemento);
		}
	}

	private void actualizarDispositivos() {
		List <Elemento> elemInt = this.getElementosPersistidos("Inteligente");
		List <Elemento> elemStand = this.getElementosPersistidos("Standard");
		
		for (Inteligente unInteligente : inteligentes) {
			int id = unInteligente.getId();
			Elemento elemento = this.buscarElementoPorIDcosa(elemInt, id);

			DateTime fecha = elemento.getFechaInicio();
			DateTime fechaFin = elemento.getFechaFin();

			double consumo = unInteligente.getConsumoPorPeriodo(fecha, fechaFin);
			elemento.setValor(consumo);
			dao.modificar(elemento);
		}

		for (Standard unStandard : standard) {
			int id = unStandard.getId();
			Elemento elemento = this.buscarElementoPorIDcosa(elemStand, id);
			DateTime fecha = elemento.getFechaInicio();
			DateTime fechaFin = elemento.getFechaFin();
			double consumo = unStandard.getConsumoPorPeriodo(fecha, fechaFin);
			elemento.setValor(consumo);
			dao.modificar(elemento);
		}
		
	}

	private void actualizarClientes() {
		List <Elemento> elemClie = this.getElementosPersistidos("Cliente");

		for (ClienteResidencial unCliente : clientes) {
			int id = unCliente.getId();
			Elemento elemento = this.buscarElementoPorIDcosa(elemClie, id);
			DateTime fecha = elemento.getFechaInicio();
			DateTime fechaFin = elemento.getFechaFin();

			double consumo = unCliente.cuantoConsumoEnUnPeriodo(fecha, fechaFin);
			elemento.setValor(consumo);
			dao.modificar(elemento);
		}
	}

	private Elemento buscarElementoPorIDcosa(List<Elemento> unaLista, int unId) {
		List<Elemento> listaFiltrada = unaLista.stream().filter(unElemento -> unElemento.getIdObjeto() == unId)
		.collect(Collectors.toList());
		
		if (listaFiltrada == null) return null;
		else return listaFiltrada.get(0);
	}

	////// ------- getters y setters default ------//////
	private final List<Elemento> getElementosPersistidos() {
		return dao.dameTodos();
	}
	
	public List<Elemento> getTransformadores(){
		return this.getElementosPersistidos("Transformador");
	}
	
	public List<Elemento> getClientes(){
		return this.getElementosPersistidos("Cliente");
	}

	public List<Elemento> getInteligentes(){
		return this.getElementosPersistidos("Inteligente");
	}
	
	public List<Elemento> getStandard(){
		return this.getElementosPersistidos("Standard");
	}
	
	private List<Elemento> getElementosPersistidos(String unTipo) {
		return this.getElementosPersistidos().stream().filter(unElemento -> unElemento.getTipo().equals(unTipo))
				.collect(Collectors.toList());
	}

	public static int getRetardo() {
		return retardoInicioTabla;
	}

	public static void setRetardoInicioTabla(int retardoInicioTabla) {
		VistaReporte.retardoInicioTabla = retardoInicioTabla;
	}

	public static int getIntervaloActualizacion() {
		return intervaloActualizacion;
	}

	public static void setIntervaloActualizacion(int intervaloActualizacion) {
		VistaReporte.intervaloActualizacion = intervaloActualizacion;
	}

	/////// ------ agregar cosas a tabla ------////////
	private final void agregarElementoReporte(String unTipo, String unNombre, int unId, double valor, DateTime fechaInicio, DateTime fechaFin) {
		Elemento nuevoElemento = new Elemento();
		nuevoElemento.setTipo(unTipo);
		nuevoElemento.setNombre(unNombre);
		nuevoElemento.setIdObjeto(unId);
		nuevoElemento.setValor(valor);
		nuevoElemento.setFechaInicio(fechaInicio);
		nuevoElemento.setFechaFin(fechaFin);
		dao.agregar(nuevoElemento);
	}

	public final void agregarTransformadores(List<Transformador> unosTransformadores, DateTime fechaInicio, DateTime fechaFin) {
		for (Transformador unTransformador : unosTransformadores) {
			this.agregarTransformador(unTransformador, fechaInicio, fechaFin);
		}
	}
	
	public final void agregarTransformador(Transformador unTransformador, DateTime fechaInicio, DateTime fechaFin) {
		String tipo = "Transformador";
		String nombre = unTransformador.getNombre();
		int id = unTransformador.getId();
		double valor = unTransformador.getConsumoTotalPorPeriodo(fechaInicio, DateTime.now());

		this.agregarElementoReporte(tipo, nombre, id, valor, fechaInicio, fechaFin);
		transformadores.add(unTransformador);
	}

	public final void agregarClientes(List<ClienteResidencial> unosClientes, DateTime fechaInicio, DateTime fechaFin) {
		for (ClienteResidencial unCliente : unosClientes) {
			this.agregarCliente(unCliente, fechaInicio, fechaFin);
		}
	}
	
	public final void agregarCliente(ClienteResidencial unCliente, DateTime fechaInicio, DateTime fechaFin) {
		String tipo = "Cliente";
		String nombre = unCliente.getAliasUsuario();
		int id = unCliente.getId();
		double valor = unCliente.cuantoConsumoEnUnPeriodo(fechaInicio, fechaFin);

		this.agregarElementoReporte(tipo, nombre, id, valor, fechaInicio, fechaFin);
		clientes.add(unCliente);
	}

	public final void agregarDispositivos(List<Dispositivo> unosDisps, DateTime fechaInicio, DateTime fechaFin) {
		for (Dispositivo unDispositivo : unosDisps) {
			this.agregarDispositivo(unDispositivo, fechaInicio, fechaFin);
		}
	}
	
	public final void agregarDispositivo(Dispositivo unDisp, DateTime fechaInicio, DateTime fechaFin) {

		int id = unDisp.getId();
		String tipo;
		String nombre;

		if (unDisp instanceof Inteligente) {
			tipo = "Inteligente";
			nombre = unDisp.getNombre();
			inteligentes.add((Inteligente) unDisp);
		} else {
			tipo = "Standard";
			nombre = unDisp.getNombre();
			standard.add((Standard) unDisp);

		}

		double valor = unDisp.getConsumoPorPeriodo(fechaInicio, fechaFin);

		this.agregarElementoReporte(tipo, nombre, id, valor, fechaInicio, fechaFin);

	}

	/////// -------- generar string de consumos (para darle al
	/////// reporte)------////////

	public String getConsumoTransformadores() {

		List<Elemento> transformadores = this.getElementosPersistidos("Transformador");

		String consumoTransformadores = this.getStringConsumo(transformadores, "Transformadores");

		return consumoTransformadores;
	}

	public String getConsumoClientes() {

		List<Elemento> clientes = this.getElementosPersistidos("Cliente");

		String consumoClientes = this.getStringConsumo(clientes, "Clientes");

		return consumoClientes;
	}

	public String getConsumoDispositivos() {

		List<Elemento> disPositivosInteligentes = this.getElementosPersistidos("Inteligente");

		String consumoInteligentes = this.getStringConsumo(disPositivosInteligentes, "Dispositivos Inteligentes");

		List<Elemento> dispositivosStandard = this.getElementosPersistidos("Standard");

		String consumoStandards = this.getStringConsumo(dispositivosStandard, "Dispositivos Standard");

		return consumoInteligentes + consumoStandards;
	}

	private String getStringConsumo(List<Elemento> elementos, String tipoElemento) {
		String consumoElementos = "\nConsumo de " + tipoElemento + ":\n";

		for (Elemento unElemento : elementos) {

			int id = unElemento.getIdObjeto();
			double valor = unElemento.getValor();
			DateTime fechaInicio = unElemento.getFechaInicio();
			DateTimeFormatter formato = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
			String fecha = formato.print(fechaInicio);

			consumoElementos = consumoElementos + "\nid: " + id + "\n" + "Consumo: " + valor + "\n" + "Desde: " + fecha
					+ "\n";
		}

		return consumoElementos;
	}

}
