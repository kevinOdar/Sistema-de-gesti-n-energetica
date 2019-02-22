package models.entities.regla.criterios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("menor")
public class Menor extends Criterio {

	@Override
	public boolean analizar() {

		int medicionNueva = sensor.getUltimaMedicion();

		return medicionNueva < valorComparacion;
	}

}