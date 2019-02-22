package models.entities.dispositivo.estado;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dispositivo.Inteligente;

@Entity
@Table(name = "historialDeEstados")
public class HistorialDeEstados extends EntidadPersistente {

	@OneToMany(mappedBy = "historial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EstadoDispositivo> historialEstados;

	@OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	private Inteligente dispositivo;
	
	public HistorialDeEstados() {
		this.historialEstados = new ArrayList<>();
	}

	public List<EstadoDispositivo> getHistorialEstados() {
		return historialEstados;
	}

	public void setHistorialEstados(List<EstadoDispositivo> historialEstados) {
		this.historialEstados = historialEstados;
	}

	public void agregarEstado(EstadoDispositivo unEstado) {
		this.historialEstados.add(unEstado);
		unEstado.agregarHistorial(this);
	}

	public List<EstadoDispositivo> filtrarLasVecesPrendido() {
		return this.historialEstados.stream().filter(unEstado -> unEstado instanceof Prendido)
				.collect(Collectors.toList());
	}

	public List<EstadoDispositivo> filtrarLasVecesEnAhorroDeEnergia() {
		return this.historialEstados.stream().filter(unEstado -> unEstado instanceof AhorroDeEnergia)
				.collect(Collectors.toList());
	}

	public double getDiasHistorial() {

		// me armo un periodo del historial, y le saco los días
		DateTime fechaInicioHistorial = historialEstados.get(0).getFechaInicio();
		
		DateTime fechaFinHistorial = historialEstados.get(historialEstados.size() - 1).getFechaFin();
		
		Period periodoHistorial = new Period(fechaInicioHistorial, fechaFinHistorial);

		Days dias = periodoHistorial.toStandardDays();
		
		double diasADouble = dias.getDays();
		
		return diasADouble;
	}

	public double getHorasPrendido() {
		// tengo en cuenta solo los estados prendidos
		List<EstadoDispositivo> todasLasVecesPrendido = this.filtrarLasVecesPrendido();

		// a cada estado le pregunto su duracion y lo sumo
		return todasLasVecesPrendido.stream().mapToDouble(unEstado -> unEstado.getDuracionHoras()).sum();
	}
	
	public double getHorasEnAhorroEnPeriodo(DateTime fechaInicio, DateTime fechaFin) {
		List<EstadoDispositivo> vecesEnAhorroDeEnergia = this.filtrarLasVecesEnAhorroDeEnergia();
		
		List<EstadoDispositivo> vecesEnAhorroEnFecha = vecesEnAhorroDeEnergia.stream()
				.filter(unEstado -> this.cumpleFechas(unEstado, fechaInicio, fechaFin)).collect(Collectors.toList());
		
		return vecesEnAhorroEnFecha.stream()
				.mapToDouble(unEstado -> unEstado.getDuracionEstadoEnHoras()).sum();
	}

	private Interval armarIntervaloAPartirDeFechas(DateTime unaFecha, DateTime otraFecha) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH");
		// con replace elimino la 'T' que aparece cuando el sistema te da la fecha
		// y con substring desprecio los minutos, etc...
		DateTime unaFechaFormateada = formatter.parseDateTime(unaFecha.toString().replace('T', ' ').substring(0, 13));
		DateTime otraFechaFormateada = formatter.parseDateTime(otraFecha.toString().replace('T', ' ').substring(0, 13));

		return new Interval(unaFechaFormateada, otraFechaFormateada);
	}
	
	public boolean cumpleFechas(EstadoDispositivo unEstado, DateTime fechaInicio, DateTime fechaFin) {

		Interval intervalo1 = new Interval(fechaInicio, fechaFin);
		Interval intervalo2 = this.armarIntervaloAPartirDeFechas(unEstado.getFechaInicio(), unEstado.getFechaFin());

		return (intervalo1.overlap(intervalo2) != null); // Si no se solapan tira null
	}

	public double getHorasPrendidoEnPeriodo(DateTime fechaInicio, DateTime fechaFin) {
		// 1)Filtrar el historial
		// 2) mapear a horas y sumar

		List<EstadoDispositivo> vecesPrendido = this.filtrarLasVecesPrendido();
		
		List<EstadoDispositivo> vecesPrendidoEnFecha = vecesPrendido.stream()
				.filter(unEstado -> this.cumpleFechas(unEstado, fechaInicio, fechaFin)).collect(Collectors.toList());
		
		return vecesPrendidoEnFecha.stream()
				.mapToDouble(unEstado -> unEstado.getDuracionEstadoHorasPorPeriodo(fechaInicio, fechaFin)).sum();
	}

	public DateTime getInicioHistorial() {
		return this.historialEstados.get(0).getFechaInicio().toDateTime();
	}
	
	public void setInteligente(Inteligente inteligente)
	{
		this.dispositivo = inteligente;
	}
	
}
