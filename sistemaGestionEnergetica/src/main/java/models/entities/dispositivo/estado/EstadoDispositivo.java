package models.entities.dispositivo.estado;

import javax.persistence.*;

import org.joda.time.*;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dispositivo.Inteligente;

@Entity
@Table(name = "estadoDispositivo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class EstadoDispositivo extends EntidadPersistente {

	@Embedded
	protected RegistroDeFechas registro;
	
	@ManyToOne
	@JoinColumn(name = "historial_id", referencedColumnName = "id")
	public HistorialDeEstados historial; //creacion especialmente para
										//la relacion oneToMany de historialDeEstado

	public EstadoDispositivo() {
		this.registro = new RegistroDeFechas();
		this.registro.setFechaInicio(DateTime.now());
	}

	////// ----------------///
	public abstract boolean estasPrendido();

	public abstract boolean estasApagado();

	///// -------manejo del tiempo--------//////
	public double getDuracionHoras() {

		return this.registro.getDuracionEstadoTerminadoHoras();
	}
	
	public double getDuracionEstadoHorasPorPeriodo(DateTime fechaInicioAPedido, DateTime fechaFinAPedido) {
		return this.registro.getDuracionEstadoHorasDentroPeriodo(this, fechaInicioAPedido, fechaFinAPedido);
	}

	public double getDuracionDelEstadoActualHorasDentroPeriodo(DateTime fechaInicioRango, DateTime fechaFinRango) {
		return this.registro.getDuracionDelEstadoActualHorasDentroPeriodo(this,fechaInicioRango,fechaFinRango);
	}
	
	public double getHoraInicio() {
		return registro.getHoraInicio();
	}
	
	public double getHoraFin() {
		return registro.getHoraFin();
	}

	public DateTime getFechaInicio() {
		return registro.getFechaInicio();
	}

	public void setFechaInicio(DateTime fechaInicio) {
		this.registro.setFechaInicio(fechaInicio);
	}
	
	public DateTime getFechaFin() {
		return registro.getFechaFin();
	}
	
	public void setFechaFin(DateTime fechaFin) {
		this.registro.setFechaFin(fechaFin);
	}

	///// ---------metodos relacionados con el cambio entre estados----////
	public void terminarEstado() {
		this.registro.setFechaFin(DateTime.now());
	}

	public abstract void prender(Inteligente unDispositivo);

	public abstract void apagar(Inteligente unDispositivo);

	public abstract void pasarAModoAhorroDeEnergia(Inteligente unDispositivo);

	public void setRegistro(RegistroDeFechas registro) {
		this.registro = registro;
	}

	public double getDuracionEstadoEnHoras(){
		return this.registro.getDuracionEstadoTerminadoHoras();
	}
	
	public double getDuracionHastaElMomento() {
	return this.registro.getDuracionEstadoAlMomento();
	}
	
	public void agregarHistorial(HistorialDeEstados historial)
	{
		this.historial = historial;
	}
}