package models.entities.dispositivo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name = "caracteristica")
public class Caracteristica extends EntidadPersistente {
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="restriccionMayorIgual")
	private Double restriccionMayorIgual;
	
	@Column(name="restriccionMenorIgual")
	private Double restriccionMenorIgual;
	
	@Column(name="coeficienteDeConsumo")
	private Double coeficienteDeConsumo;

	@Column(name="esDeBajoConsumo")
	private boolean deBajoConsumo;
	
	@Column(name="esInteligente")
	private boolean inteligente;
	
	//persistencia
	@OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Dispositivo> dispositivos;
	
	public Caracteristica()
	{
		dispositivos = new ArrayList<Dispositivo>();
	}
	
	public boolean isInteligente() {
		return inteligente;
	}
	
	public void setInteligente(boolean inteligente) {
		this.inteligente = inteligente;
	}

	public boolean isDeBajoConsumo() {
		return deBajoConsumo;
	}

	public void setDeBajoConsumo(boolean esDeBajoConsumo) {
		this.deBajoConsumo = esDeBajoConsumo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getRestriccionMayorIgual() {
		return restriccionMayorIgual;
	}

	public void setRestriccionMayorIgual(Double restriccionMayorIgual) {
		this.restriccionMayorIgual = restriccionMayorIgual;
	}

	public Double getRestriccionMenorIgual() {
		return restriccionMenorIgual;
	}

	public void setRestriccionMenorIgual(Double restriccionMenorIgual) {
		this.restriccionMenorIgual = restriccionMenorIgual;
	}

	public Double getCoeficienteDeConsumo() {
		return coeficienteDeConsumo;
	}

	public void setCoeficienteDeConsumo(Double coeficiente) {
		this.coeficienteDeConsumo = coeficiente;
	}
	
	//Persistencia bidireccional
	public void agregarDispositivo(Dispositivo dispositivo)
	{
		this.dispositivos.add(dispositivo);
	}

}
