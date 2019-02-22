package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("setear_timer")
public class SetearTimer extends ActuadorBase {

	public void execute() {
		ActuadorBrokerHelper.get().publicarAccion(topic, "setear timer");
	}

	public void undo() {
		ActuadorBrokerHelper.get().publicarAccion(topic, "quitar timer");

	}

}
