package models.entities.dispositivo.estado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import models.entities.dispositivo.Inteligente;

@Entity
@DiscriminatorValue("ahorroDeEnergia")
public class AhorroDeEnergia extends EstadoDispositivo {

	public AhorroDeEnergia() {

	}
	
	@Override
	public boolean estasPrendido() {
		return false;
	}

	@Override
	public boolean estasApagado() {
		return false;
	}

	@Override
	public void prender(Inteligente unDispositivo) {
		this.terminarEstado();
		unDispositivo.actualizarEstado(new Prendido());		
	}

	@Override
	public void apagar(Inteligente unDispositivo) {
		this.terminarEstado();
		unDispositivo.actualizarEstado(new Apagado());
	}

	@Override
	public void pasarAModoAhorroDeEnergia(Inteligente unDispositivo) {
	}

}
