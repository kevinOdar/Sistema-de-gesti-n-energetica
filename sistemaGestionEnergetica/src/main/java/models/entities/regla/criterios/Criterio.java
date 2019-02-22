package models.entities.regla.criterios;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;
import models.entities.regla.Regla;
import models.entities.sensor.Sensor;

@Entity
@Table(name = "criterio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Criterio extends EntidadPersistente {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "regla_id", referencedColumnName = "id")
	private Regla regla;

	@Column(name = "valorComparacion")
	protected int valorComparacion;

	@Column(name = "valorMaximo")
	protected int valorMaximo;

	@Column(name = "valorMinimo")
	protected int valorMinimo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	protected Sensor sensor;

	public int getValorComparacion() {
		return valorComparacion;
	}

	public void setValorComparacion(int valorComparacion) {
		this.valorComparacion = valorComparacion;
	}

	public int getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(int valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public int getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(int valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Regla getRegla() {
		return regla;
	}

	public void setRegla(Regla regla) {
		this.regla = regla;
	}

	public abstract boolean analizar();
}
