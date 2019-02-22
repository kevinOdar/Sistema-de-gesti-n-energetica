package models.generadorReportes;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name="elemento_reporte")
public final class Elemento extends EntidadPersistente{

	@Column(name="tipo", length=512)
	private String tipo;
	
	@Column(name="nombre", length=512)
	private String nombre;

	@Column(name="idObjeto")
	private int idObjeto;
	
	@Column(name="valor")
	private double valor;
	
	@Column(name="fecha_inicio")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fechaInicio;
	
	@Column(name="fecha_fin")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fechaFin;

	public int getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

	public final DateTime getFechaInicio() {
		return fechaInicio;
	}

	public final void setFechaInicio(DateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public final String getTipo() {
		return tipo;
	}

	public final void setTipo(String unTipo) {
		this.tipo = unTipo;
	}

	public final double getValor() {
		return valor;
	}

	public final void setValor(double unValor) {
		this.valor = unValor;
	}

	public final void setFechaFin(DateTime unaFechaFin) {
		this.fechaFin = unaFechaFin;		
	}
	
	public final DateTime getFechaFin() {
		return this.fechaFin;
	}

	public final String getFechaInicioString() {
		DateTime fecha = this.getFechaInicio();
		DateTimeFormatter formato = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
		String string = formato.print(fecha);
		return string;
	}
	
	public final String getFechaFinString() {
		DateTime fecha = this.getFechaFin();
		DateTimeFormatter formato = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
		String string = formato.print(fecha);
		return string;
	}
	
	public final String getNombre() {
		return nombre;
	}

	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
