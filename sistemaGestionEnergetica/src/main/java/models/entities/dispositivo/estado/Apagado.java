package models.entities.dispositivo.estado;

import javax.persistence.*;
import models.entities.dispositivo.Inteligente;

@Entity
@DiscriminatorValue("apagado")
public class Apagado extends EstadoDispositivo {

	public Apagado() {
	}
	
	@Override
	public boolean estasPrendido() {
		return false;
	}

	@Override
	public boolean estasApagado() {
		return true;
	}

	@Override
	public void prender(Inteligente unDispositivo) {
		this.terminarEstado();
		unDispositivo.actualizarEstado(new Prendido());
	}

	@Override
	public void apagar(Inteligente unDispositivo) {		
	}

	@Override
	public void pasarAModoAhorroDeEnergia(Inteligente unDispositivo) {		
	}

}
