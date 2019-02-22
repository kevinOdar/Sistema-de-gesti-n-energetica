package models.entities.transformadores;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name = "ubicable")
@DiscriminatorValue("ubicable")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Ubicable extends EntidadPersistente {
	
	@OneToMany(mappedBy = "ubicable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Coordenada> coordenadas;

	public Ubicable() {
		coordenadas = new ArrayList<Coordenada>();
	}

	public List<Coordenada> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Coordenada> coordenadas) {
		this.coordenadas = coordenadas;
		this.setUbicableACoordenadas();
	}

	private void setUbicableACoordenadas() {
		this.coordenadas.forEach(unaCoord -> unaCoord.setUbicable(this));
	}

	public int getCantidadCoordenadas() {
		return this.getCoordenadas().size();
	}

	public double getLongitud() {
		return this.getCoordenadas().get(0).getLongitud();
	}

	public double getLatitud() {
		return this.getCoordenadas().get(0).getLatitud();
	}

	public double[] getLongitudes() {
		return this.getCoordenadas().stream().mapToDouble(unaCoordenada -> unaCoordenada.getLongitud()).toArray();
	}
	
	public double[] getLatitudes() {
		return this.getCoordenadas().stream().mapToDouble(unaCoordenada -> unaCoordenada.getLatitud()).toArray();
	}
}
