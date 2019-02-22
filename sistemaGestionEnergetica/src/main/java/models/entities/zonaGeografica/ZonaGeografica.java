package models.entities.zonaGeografica;

import java.util.List;
import org.joda.time.*;

import javax.persistence.*;

import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;
import models.entities.transformadores.Ubicable;

@Entity
@Table(name = "zona_geografica")
@DiscriminatorValue("zona_geografica")
public class ZonaGeografica extends Ubicable {

	@OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transformador> transformadores;

	@Transient
	private Polygon2D poligono;

	@Transient
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ClienteResidencial cliente;
	
	public ZonaGeografica() {
	}

	public void crearZona() {
		this.poligono = new Polygon2D(this.getLatitudes(), this.getLongitudes(), this.getCantidadCoordenadas());
	}
	
	public List<Transformador> getTransformadores() {
		return transformadores;
	}

	public void setTransformadores(List<Transformador> transformadores) {
		this.transformadores = transformadores;
	}

	public void agregarTransformador(Transformador UnTransformador) {
		if (this.estaEnZona(UnTransformador)) {
			transformadores.add(UnTransformador);
		} // else tira error

	}

	// Transformador pertenece a la Zona
	public boolean estaEnZona(Transformador UnTransformador) {

		return poligono.contains(UnTransformador.getLatitud(), UnTransformador.getLongitud());
	}

	// ConsumoTotal Actual, Promedio y En un Periodo de la Zona Geografica

	public double consumoTotalDeLaZona() {
		double consumoTotal = transformadores.stream()
				.mapToDouble(unTransformador -> unTransformador.suministroActual()).sum();

		return consumoTotal;
	}

	public double consumoTotalEnUnPeriodoDeLaZona(DateTime fechaInicio, DateTime fechaFinal) {
		double consumoTotalEnUnPeriodo = transformadores.stream()
				.mapToDouble(unTransformador -> unTransformador.suministroEnUnPeriodo(fechaInicio, fechaFinal)).sum();

		return consumoTotalEnUnPeriodo;
	}

	public double consumoTotalPromedioDeLaZona() {
		double consumoTotalPromedio = transformadores.stream()
				.mapToDouble(unTransformador -> unTransformador.suministroPromedio()).sum();

		return consumoTotalPromedio;
	}

}
