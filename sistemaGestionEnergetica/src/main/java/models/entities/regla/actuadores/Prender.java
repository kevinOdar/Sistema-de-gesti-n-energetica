package models.entities.regla.actuadores;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("prender")
public class Prender extends ActuadorBase {
	
	public void execute(){
		//System.out.println("\nPublicando en" + topic + "\n");
		ActuadorBrokerHelper.get().publicarAccion(topic, "prender");
	}

	public void undo(){

		ActuadorBrokerHelper.get().publicarAccion(topic, "apagar");
	}

}//end ActuadorPrender
