package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("subir_intensidad")
public class SubirIntensidad extends ActuadorBase {
	
	public void execute(){
		ActuadorBrokerHelper.get().publicarAccion(topic, "subir intensidad");

	}

	public void undo(){
		ActuadorBrokerHelper.get().publicarAccion(topic, "bajar intensidad");

	}

	
}//end ActuadorSubirIntensidad
