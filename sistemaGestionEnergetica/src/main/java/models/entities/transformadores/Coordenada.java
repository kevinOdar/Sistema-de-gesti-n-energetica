package models.entities.transformadores;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name = "coordenada")
public class Coordenada  extends EntidadPersistente {
	
	@ManyToOne
	@JoinColumn(name = "ubicable_id", referencedColumnName = "id")
	private Ubicable ubicable;
	
	@Column(name = "latitud")
	private double latitud;
	
	@Column(name = "longitud")
	private double longitud;

	public Coordenada() {}
	
	public Coordenada(double UnaCoordEnX, double UnaCoordEnY) {
		this.setLatitud(UnaCoordEnX);
		this.setLongitud(UnaCoordEnY);
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double coordenadaEnX) {
		this.latitud = coordenadaEnX;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double coordenadaEnY) {
		this.longitud = coordenadaEnY;
	}

	public Ubicable getUbicable() {
		return ubicable;
	}

	public void setUbicable(Ubicable ubicable) {
		this.ubicable = ubicable;
	}
	
	public String toString()
	{
		return "[" + this.getLatitud() + ", " + this.getLongitud() + "]" ; 
	}
	
	public double distanciaEntreCoordenadas(double latitud2, double longitud2) {
	//el calculo se realiza basado en la formula de haversin para la distancia entre coordenadas
		//geograficas y el resultado sera arrojado en kms
		double radioDeLaTierra = 6371;//kms
		double latitud1 = this.getLatitud();
		double longitud1 = this.getLongitud();
		double distanciaLatitudinal = Math.toRadians(latitud2 - latitud1);
		double distanciaLongitudinal = Math.toRadians(longitud2 - longitud1);
		double senoDeLaDistanciaLatitudinal = Math.sin(distanciaLatitudinal/2);
		double senoDeLaDistanciaLongitudinal = Math.sin(distanciaLongitudinal/2);
		double cuadradoDelSenoDeLaDistanciaLatitudinal = Math.pow(senoDeLaDistanciaLatitudinal, 2);
		double cuadradoDelSenoDeLaDistanciaLongitudinal = Math.pow(senoDeLaDistanciaLongitudinal, 2);
		double cosenoDeLaLatitudOrigen = Math.cos(Math.toRadians(latitud1));
		double cosenoDeLaLatitudDestino = Math.cos(Math.toRadians(latitud2));
		double formulaParteA = cuadradoDelSenoDeLaDistanciaLatitudinal + cuadradoDelSenoDeLaDistanciaLongitudinal
								* cosenoDeLaLatitudOrigen * cosenoDeLaLatitudDestino;
		double formulaParteB = 2 * Math.atan2(Math.sqrt(formulaParteA), Math.sqrt(1-formulaParteA));
		double distancia = radioDeLaTierra * formulaParteB;
		return distancia;
	}

}
