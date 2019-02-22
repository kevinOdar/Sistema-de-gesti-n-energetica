package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("pasar_a_modo_ahorro")
public class PasarAModoAhorroEnergia extends ActuadorBase {

	public void execute() {
		ActuadorBrokerHelper.get().publicarAccion(topic, "activar modo ahorro");
	}

	public void undo() {
		ActuadorBrokerHelper.get().publicarAccion(topic, "desactivar modo ahorro");
	}

}// end ActuadorPasarAModoAhorroEnergia
