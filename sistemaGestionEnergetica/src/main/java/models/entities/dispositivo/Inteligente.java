package models.entities.dispositivo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.joda.time.*;

import models.entities.dispositivo.estado.*;
import models.entities.regla.Regla;
import models.entities.regla.actuadores.ActuadorBase;

@Entity
@Table(name = "inteligente")
@DiscriminatorValue("inteligente")
public class Inteligente extends Dispositivo {

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private HistorialDeEstados historialEstados;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected EstadoDispositivo estadoActual;

	@Column(name = "consumoEnModoAutomatico")
	private boolean consumoEnModoAutomatico;

	@Column(name = "puntos")
	protected int puntos;

	@Column(name = "intensidad")
	private int intensidad;

	@Transient
	private List<ActuadorBase> colaDeAcciones;

	@OneToMany(mappedBy = "dispositivoInteligente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Regla> regla;

	public Inteligente() {
		this.colaDeAcciones = new ArrayList<>();
		this.estadoActual = null;
		this.historialEstados = new HistorialDeEstados();
		this.historialEstados.setInteligente(this);
		this.consumoEnModoAutomatico = false;
		this.regla = new ArrayList<>();
		this.setPuntos(15);
	}

	public List<Regla> getReglas() {
		return this.regla;
	}

	public void setIntensidad(int myIntensidad) {
		this.intensidad = myIntensidad;
	}

	public int getIntensidad() {
		return this.intensidad;
	}

	public HistorialDeEstados getHistorialDeEstados() {
		return this.historialEstados;
	}

	@Override
	public boolean sosInteligente() {
		return true;
	}

	

	//////// -----------------------/////
	public int getPuntos() {
		return this.puntos;
	}

	protected void setPuntos(int unosPuntos) {
		this.puntos = unosPuntos;
	}

	///////// ---------------------------///////
	public boolean isConsumoEnModoAutomatico() {
		return consumoEnModoAutomatico;
	}

	public void setConsumoEnModoAutomatico(boolean ajustarConsumoAutomaticamente) {
		this.consumoEnModoAutomatico = ajustarConsumoAutomaticamente;
	}

	///// ----estados y cambios de estados----////
	@Override
	public boolean estasPrendido() {
		return this.estadoActual.estasPrendido();
	}

	@Override
	public boolean estasApagado() {
		return this.estadoActual.estasApagado();
	}

	public void prender() {
		if (this.estadoActual != null) {
			this.estadoActual.prender(this);
		} else
			this.setEstadoActual(new Prendido());
	}

	public void apagar() {

		if (this.estadoActual != null) {
			this.estadoActual.apagar(this);
		} else
			this.setEstadoActual(new Apagado());

	}

	public void pasarAModoAhorroDeEnergia() {
		if (this.estadoActual != null) {
			this.estadoActual.pasarAModoAhorroDeEnergia(this);
		} else
			this.setEstadoActual(new AhorroDeEnergia());
	}

	public void actualizarEstado(EstadoDispositivo nuevoEstado) {

		if (this.estadoActual != null) {
			this.historialEstados.agregarEstado(this.estadoActual);
			this.setEstadoActual(nuevoEstado);
		} else
			this.setEstadoActual(nuevoEstado);
	}

	public void agregarEstadoAHistorial(EstadoDispositivo unEstado) {
		this.historialEstados.agregarEstado(unEstado);
	}

	private void setEstadoActual(EstadoDispositivo unEstado) {
		this.estadoActual = unEstado;
	}

	//// --------horas de uso---------////////

	@Override
	public double getHorasUsoDiario() {

		double diasHistorial = this.historialEstados.getDiasHistorial();

		double horasTotales = this.historialEstados.getHorasPrendido();

		if (diasHistorial != 0) {
			return horasTotales / diasHistorial;
		} else
			return horasTotales;
	}

	////////////// -------consumo------------////////////////////
	@Override
	public double getConsumoEnTantasHoras(double unasHoras) {
		return this.caracteristica.getCoeficienteDeConsumo() * unasHoras;
	}

	@Override
	public double getConsumoDiario() {

		DateTime inicioHistorial = this.historialEstados.getInicioHistorial();

		double consumoTotalHistorial = this.getConsumoPorPeriodo(inicioHistorial, DateTime.now());

		double diasHistorial = this.historialEstados.getDiasHistorial();

		if (diasHistorial != 0 && consumoTotalHistorial != 0) {
			return consumoTotalHistorial / diasHistorial;
		} else
			return -1;
	}

	@Override
	public double getConsumoMensual() {
		return this.getConsumoPorPeriodo(DateTime.now().minusMonths(1), DateTime.now());
	}

	private double getConsumoEnAhorroDeEnergia(double unasHoras) {
		return (this.caracteristica.getCoeficienteDeConsumo() / 10) * unasHoras;
	}

	// cual fue el consumo total comprendido en un periodo
	@Override
	public double getConsumoPorPeriodo(DateTime fechaInicio, DateTime fechaFin) {
		double horasTotalesPrendido = 0;
		double horasEnAhorro = 0;

		horasTotalesPrendido = this.historialEstados.getHorasPrendidoEnPeriodo(fechaInicio, fechaFin);

		// Para horas en ahorro de energia (primero chequeo que esté seteado de bajo
		// consumo)
		if (this.getCaracteristica().isDeBajoConsumo()) {
			horasEnAhorro = this.historialEstados.getHorasEnAhorroEnPeriodo(fechaInicio, fechaFin);
		}

		return this.getConsumoEnTantasHoras(horasTotalesPrendido) + this.getConsumoEstadoActual(fechaInicio, fechaFin)
				+ this.getConsumoEnAhorroDeEnergia(horasEnAhorro);

	}

	private double getConsumoEstadoActual(DateTime fechaInicioRango, DateTime fechaFinRango) {
		if (this.estadoActual instanceof Prendido) {
			double horasEnEstado = this.estadoActual.getDuracionDelEstadoActualHorasDentroPeriodo(fechaInicioRango,
					fechaFinRango);
			return this.getConsumoEnTantasHoras(horasEnEstado);
		} else if (this.estadoActual instanceof AhorroDeEnergia) {
			double horasEnEstado = this.estadoActual.getDuracionDelEstadoActualHorasDentroPeriodo(fechaInicioRango,
					fechaFinRango);
			return this.getConsumoEnAhorroDeEnergia(horasEnEstado);
		} else
			return 0;

	}

	// cuanta energia consumieron durante las ultimas N horas
	public double getConsumoUltimasNHoras(int ultimasHoras) {
		return getConsumoPorPeriodo(DateTime.now().minusHours(ultimasHoras), DateTime.now());
	}

	/////// --------Acciones------////

	public void setearTimer() {
		// TODO Auto-generated method stub

	}

	public void quitarTimer() {
		// TODO Auto-generated method stub
	}

	public void subirIntensidad() {
		this.intensidad++;
	}

	public void bajaTuIntensidad() {
		this.intensidad--;
	}

	@Override
	public void setCaracteristica(Caracteristica unaCaracteristica) {
		if (!unaCaracteristica.isInteligente()) {
			throw new RuntimeException("No se puede asignar la caracteristica porque no es inteligente");
		} else {
			this.caracteristica = unaCaracteristica;
			// persistencia bidireccionalidad
			this.caracteristica.agregarDispositivo(this);
		}
	}

	public void agregarRegla(Regla regla) {
		this.regla.add(regla);
		regla.agregarDispositivo(this);
	}

	public EstadoDispositivo getEstadoActual() {
		return this.estadoActual;
	}

	public String getEstasPrendido() {
		if (this.estasPrendido())
			return "Encendido";
		else
			return "Apagado";
	}

	public String getConsumoEnModoAutomatico() {
		if (this.consumoEnModoAutomatico)
			return "Si";
		else
			return "No";
	}
}
