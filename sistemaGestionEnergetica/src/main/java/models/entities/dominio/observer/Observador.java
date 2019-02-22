package models.entities.dominio.observer;

public interface Observador {
	
	public void actualizar();

	public void suscribirme(Observable unObservable);

}
