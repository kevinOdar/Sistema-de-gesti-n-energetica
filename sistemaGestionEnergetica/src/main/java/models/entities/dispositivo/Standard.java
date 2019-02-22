package models.entities.dispositivo;

import javax.persistence.*;

import org.joda.time.DateTime;
import org.joda.time.Hours;

@Entity
@DiscriminatorValue("standard")
@Table(name = "standard")
public class Standard extends Dispositivo {

	@Column(name = "horas_uso_diario")
	private double horasUsoDiario;

	@Column(name = "consumo_por_hora")
	private double consumoPorHora;

	@Transient
	private ModuloAdaptador moduloAdaptador;

	@Column(name = "esta_adaptado")
	private boolean estaAdaptado;

	public Standard() {
		this.moduloAdaptador = null;
		this.estaAdaptado = false;
	}

	@Override
	public int getPuntos() {
		return 0;
	}

	@Override
	public boolean sosInteligente() {

		if (this.moduloAdaptador != null) {
			return this.moduloAdaptador.sosInteligente();
		} else
			return false;
	}

	public void setHorasUsoDiario(double horasUsoDiario) {
		this.horasUsoDiario = horasUsoDiario;
	}

	public void setConsumoPorHora(double consumoPorHora) {
		this.consumoPorHora = consumoPorHora;
	}

	@Override
	public boolean estasPrendido() {
		return false;
	}

	@Override
	public boolean estasApagado() {
		return false;
	}

	@Override
	public double getHorasUsoDiario() {
		return this.horasUsoDiario;
	}
////////--------------------/////////////////////

	@Override
	public double getConsumoEnTantasHoras(double unasHoras) {
		return this.consumoPorHora * unasHoras;
	}

	@Override
	public double getConsumoDiario() {
		return this.getConsumoEnTantasHoras(24);
	}

	@Override
	public double getConsumoMensual() {
		return this.getConsumoEnTantasHoras(24 * 31);
	}

	//////// --------------------//////////
	public void setModuloAdaptador(ModuloAdaptador unModulo) {
		this.moduloAdaptador = unModulo;
		this.estaAdaptado = true;
	}

	public boolean estaAdaptado() {
		return this.moduloAdaptador != null;
	}

	///////// -------------/////////
	@Override
	public void setCaracteristica(Caracteristica unaCaracteristica) {
		if (unaCaracteristica.isInteligente()) {
			throw new RuntimeException("No se puede asignar la caracteristica porque es inteligente");
		} else {
			this.caracteristica = unaCaracteristica;
			this.consumoPorHora = caracteristica.getCoeficienteDeConsumo();
		}
	}

	@Override
	public double getConsumoPorPeriodo(DateTime fechaInicio, DateTime now) {

		int horas = Hours.hoursBetween(fechaInicio, now).getHours();

		return this.getConsumoEnTantasHoras(horas);
	}
	
	public double getConsumoPorHora()
	{
		return this.consumoPorHora;
	}
	
	public boolean getEstaAdaptado()
	{
		return this.estaAdaptado;
	}
}
