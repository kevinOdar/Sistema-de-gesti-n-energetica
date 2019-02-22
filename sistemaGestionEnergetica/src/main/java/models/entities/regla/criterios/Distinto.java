package models.entities.regla.criterios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("distinto")
public class Distinto extends Criterio {

	@Override
	public boolean analizar() {

		int medicionNueva = sensor.getUltimaMedicion();

		return medicionNueva != valorComparacion;
	}

}
