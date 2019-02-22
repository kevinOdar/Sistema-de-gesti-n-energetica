package models.entities.regla;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dispositivo.Inteligente;
import models.entities.dominio.observer.Observable;
import models.entities.dominio.observer.Observador;
import models.entities.regla.actuadores.ActuadorBase;
import models.entities.regla.criterios.Criterio;
import models.entities.sensor.Sensor;

@Entity
@Table(name = "regla")
public final class Regla extends EntidadPersistente implements Observador {

	@OneToMany(mappedBy = "regla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ActuadorBase> comandos;

	@OneToMany(mappedBy = "regla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Criterio> criterios;

	@ManyToOne
	@JoinColumn(name = "dispositivo_id", referencedColumnName = "id")
	private Inteligente dispositivoInteligente;

	@ManyToMany(mappedBy = "reglas",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Sensor> sensores;

	@Column(name="nombre", length=512)
	private String nombre;
	
	public Regla() {
		this.comandos = new ArrayList<>();
		this.sensores = new ArrayList<>();
		this.criterios = new ArrayList<>();
	}

	public Inteligente getDispositivoInteligente() {
		return dispositivoInteligente;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarActuador(ActuadorBase accion) {
		comandos.add(accion);
		accion.setRegla(this);
	}


	public void quitarActuador(ActuadorBase accion) {
		comandos.remove(accion);
	}
	
	public List<ActuadorBase> getActuadores() {
		return this.comandos;
	}

	public List<Criterio>  getCriterios(){
		return this.criterios;
	}
	
	public void agregarUnCriterio(Criterio unCriterio, Sensor unSensor) {
		this.suscribirme(unSensor);
		unSensor.agregarCriterioAsociado(unCriterio);
		unCriterio.setSensor(unSensor);
		unCriterio.setRegla(this);
		this.criterios.add(unCriterio);
	}

	public void quitarCriterio(Criterio unCriterio) {
		criterios.remove(unCriterio);
	}

	@Override
	public void actualizar() {

		if (this.todosLosCriteriosDanTrue()) {
			this.comandos.forEach(unActuador -> unActuador.execute());
		}
	}

	private boolean todosLosCriteriosDanTrue() {

		return this.criterios.stream().allMatch(unCriterio -> unCriterio.analizar());
	}

	@Override
	public void suscribirme(Observable unObservable) {
		unObservable.agregarSuscripcion(this);
		this.sensores.add((Sensor) unObservable);
	}

	public void agregarDispositivo(Inteligente inteligente) {
		this.dispositivoInteligente = inteligente;
		this.comandos.forEach(unComando -> unComando.setCanal(inteligente.getNombre()));
	}

}// end Regla
