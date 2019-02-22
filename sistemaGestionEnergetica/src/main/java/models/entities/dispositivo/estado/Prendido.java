package models.entities.dispositivo.estado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import models.entities.dispositivo.Inteligente;

@Entity
@DiscriminatorValue("encendido")
public class Prendido extends EstadoDispositivo {

	
	public Prendido() {

	}
	
	@Override
	public boolean estasPrendido() {
		return true;
	}

	@Override
	public boolean estasApagado() {
		return false;
	}

	@Override
	public void prender(Inteligente unDispositivo) {
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
