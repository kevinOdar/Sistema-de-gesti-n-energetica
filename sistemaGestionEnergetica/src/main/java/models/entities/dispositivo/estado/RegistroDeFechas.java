package models.entities.dispositivo.estado;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Embeddable
public class RegistroDeFechas   {
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime fechaInicio;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime fechaFin;
	
	@Transient
	public Duration duracionEstado;
		
	public RegistroDeFechas()
	{
		this.fechaInicio = null;
		this.fechaFin = null;
	}

	public DateTime getFechaInicio() {
		return this.fechaInicio;
	
	}

	public void setFechaInicio(DateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public DateTime getFechaFin() {

			return fechaFin;
	}

	public void setFechaFin(DateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getHoraInicio() {
		return this.getFechaInicio().getHourOfDay();
	}

	public double getHoraFin() {
		return getFechaFin().getHourOfDay();
	}

	public void setDuracion() {
		// this.duracionEstado = Duration.between(fechaInicio, fechaFin);
	}

	public double getDuracionEstadoTerminadoHoras() {
		
		Period periodo = new Period(this.getFechaInicio(),this.getFechaFin());
		
		Duration duracion = periodo.toStandardDuration();
		
		return duracion.getStandardHours();
	}
	
	public double getDuracionEstadoAlMomento() {

		Period periodo = new Period(this.getFechaInicio(),DateTime.now());
		
		Duration duracion = periodo.toStandardDuration();
		
		return duracion.getStandardHours();
		
	}

	private Interval armarIntervaloAPartirDeFechas(DateTime unaFecha, DateTime otraFecha) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH");
		// con replace elimino la 'T' que aparece cuando el sistema te da la fecha
		// y con substring desprecio los minutos, etc...
		DateTime unaFechaFormateada = formatter.parseDateTime(unaFecha.toString().replace('T', ' ').substring(0, 13));
		DateTime otraFechaFormateada = formatter.parseDateTime(otraFecha.toString().replace('T', ' ').substring(0, 13));

		return new Interval(unaFechaFormateada, otraFechaFormateada);
	}

	public double getDuracionEstadoHorasDentroPeriodo(EstadoDispositivo estadoDispositivo, DateTime fechaInicioAPedido,
			DateTime fechaFinAPedido) {

		Interval intervalo1 = this.armarIntervaloAPartirDeFechas(estadoDispositivo.getFechaInicio(), estadoDispositivo.getFechaFin());

		Interval intervalo2 = new Interval(fechaInicioAPedido, fechaFinAPedido);

		Duration duracion = new Duration(intervalo1.overlap(intervalo2).getStart(),
				intervalo1.overlap(intervalo2).getEnd());

		return duracion.getStandardHours();
	}

	public double getDuracionDelEstadoActualHorasDentroPeriodo(EstadoDispositivo estadoDispositivo, DateTime fechaInicioRango,
			DateTime fechaFinRango) {
		
		DateTime fechaHoy = DateTime.now();
		
		Interval intervalo1 = this.armarIntervaloAPartirDeFechas(estadoDispositivo.getFechaInicio(), fechaHoy);
		Interval intervalo2 = new Interval(fechaInicioRango, fechaFinRango);
		
		Duration duracion = new Duration(intervalo1.overlap(intervalo2).getStart(),
				intervalo1.overlap(intervalo2).getEnd());
		
		return duracion.getStandardHours();
	}
}
