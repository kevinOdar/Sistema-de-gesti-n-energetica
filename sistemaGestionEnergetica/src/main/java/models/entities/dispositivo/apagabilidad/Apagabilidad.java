package models.entities.dispositivo.apagabilidad;

import models.entities.dispositivo.apagabilidad.tipo.TipoApagabilidad;

public interface Apagabilidad {

	public boolean sosApagable();
	
	public TipoApagabilidad getEnum();
	
}
