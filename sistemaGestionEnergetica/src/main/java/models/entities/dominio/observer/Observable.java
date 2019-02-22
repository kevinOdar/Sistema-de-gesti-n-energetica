package models.entities.dominio.observer;

public interface Observable {
	
	public abstract void agregarSuscripcion(Observador unObservador);
	
	public abstract void agregarSuscripciones(Observador ... variosObservadores) ;
	
	public abstract void borrarSuscripcion(Observador unObservador);
	
	public abstract void avisarASuscriptores();

	
}
