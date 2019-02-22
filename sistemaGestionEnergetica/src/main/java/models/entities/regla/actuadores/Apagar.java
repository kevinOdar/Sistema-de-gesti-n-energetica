package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("apagar")
public class Apagar extends ActuadorBase {
	
	public void execute(){
		//System.out.println("\nPublicando en" + topic + "\n");
		ActuadorBrokerHelper.get().publicarAccion(topic, "apagar");

	}

	public void undo(){
		ActuadorBrokerHelper.get().publicarAccion(topic, "prender");

	}
	
	
}//end ActuadorApagar
