package models.entities.regla.criterios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("rangoExcluyente")
public class RangoExcluyente extends Criterio {
	
	@Override
	public boolean analizar() {

		int medicionNueva = sensor.getUltimaMedicion();

		return medicionNueva > valorMinimo && medicionNueva < valorMaximo;
	}

}
