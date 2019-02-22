package models.entities.dispositivo;

import javax.persistence.*;

import org.joda.time.DateTime;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dispositivo.apagabilidad.Apagabilidad;
import models.entities.dispositivo.apagabilidad.tipo.TipoApagabilidad;
import models.entities.dominio.ClienteResidencial;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
@Table(name = "dispositivo")
public abstract class Dispositivo extends EntidadPersistente {
	
	@ManyToOne
	@JoinColumn(name = "caracteristica_id", referencedColumnName = "id")
	protected Caracteristica caracteristica;

	@Enumerated(EnumType.STRING)
	@Column(name = "apagabilidad")
	protected TipoApagabilidad tipoApagabilidad;

	@Transient
	protected Apagabilidad apagabilidadDispositivo;
	
	@Column(name = "nombre", length=512, unique=true)
	protected String nombre;

	@ManyToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private ClienteResidencial cliente;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public abstract int getPuntos();

	public abstract boolean sosInteligente();

	///// ----Metodos que se relacionan con si es apagable o no el
	///// dispositivo-----/////
	public void setApagabilidad(Apagabilidad unaApagabilidad) {
		this.apagabilidadDispositivo = unaApagabilidad;
		this.tipoApagabilidad = unaApagabilidad.getEnum();
	}

	public boolean sosUnDispositivoApagable() {
		return this.apagabilidadDispositivo.sosApagable();
	}

/////----Metodos que se relacionan con los estados del dispositivo-----/////
	public abstract boolean estasPrendido();

	public abstract boolean estasApagado();

/////----Metodos relacionados al consumo y al uso del dispositivo-----/////
	public abstract double getHorasUsoDiario();

	public double getHorasUsoMensual() {
		return this.getHorasUsoDiario() * 24 * 31;
	}

	public double getConsumoPorHora() {
		return getConsumoEnTantasHoras(1);
	}

	public abstract double getConsumoEnTantasHoras(double unasHoras);

	public abstract double getConsumoDiario();

	public abstract double getConsumoMensual();

/////----Metodos relacionados a la caracteristica y al seteo de la caracteristica-----/////
///La caracteristica tiene: restriccion mayor / menor y un coeficiente de consumo
	public Caracteristica getCaracteristica() {
		return this.caracteristica;
	}

	public abstract void setCaracteristica(Caracteristica unaCaracteristica);

	public Double getCoeficienteDeConsumo() {
		return this.caracteristica.getCoeficienteDeConsumo();
	}

	public Double getRestriccionMayorIgual() {
		return this.caracteristica.getRestriccionMayorIgual();
	}

	public Double getRestriccionMenorIgual() {
		return this.caracteristica.getRestriccionMenorIgual();
	}
	
	//Solo para persistir
	public void setCliente(ClienteResidencial cliente)
	{
		this.cliente = cliente;
	}

	public abstract double getConsumoPorPeriodo(DateTime fechaInicio, DateTime now);
	
	public TipoApagabilidad getApagabilidad()
	{
		return this.tipoApagabilidad;
	}
}