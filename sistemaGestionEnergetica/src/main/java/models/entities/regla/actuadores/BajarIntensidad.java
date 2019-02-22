package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("bajar_intensidad")
public class BajarIntensidad extends ActuadorBase {


	public void execute() {
		ActuadorBrokerHelper.get().publicarAccion(topic,"bajar intensidad");

	}

	public void undo() {
		ActuadorBrokerHelper.get().publicarAccion(topic, "subir intensidad");
	}

}// end ActuadorBajarIntensidad
