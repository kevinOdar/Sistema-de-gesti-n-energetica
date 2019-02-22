package models.entities.zonaGeografica;

import models.entities.transformadores.Ubicable;

public class PoligonoMapaCalor extends Ubicable 
{
	private double opacidad ;
	private double consumoInstantaneo ;

	
	//tengo que tener una lista de objetos de este tipo, ordenada según el consumo instantaneo,
	//luego relaciono el primero con un porcentaje, ejemplo...
	
	
	public void determinarOpacidadSegunMax(PoligonoMapaCalor poligonoMax)
	{
		double opMax = poligonoMax.getConsumoInstantaneo() ;
		this.setOpacidad(this.getConsumoInstantaneo() / opMax);
	}
	
	public double getOpacidad() {
		return opacidad;
	}
	
	public void setOpacidad(double opacidad) {
		this.opacidad = opacidad;
	}
	
	public double getConsumoInstantaneo() {
		return consumoInstantaneo;
	}
	
	public void setConsumoInstantaneo(double consumoInstantaneo) {
		this.consumoInstantaneo = consumoInstantaneo;
	}
	
}
