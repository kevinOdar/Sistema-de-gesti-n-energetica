package models.entities.dispositivo.apagabilidad;

import models.entities.dispositivo.apagabilidad.tipo.TipoApagabilidad;

public class NoApagable implements Apagabilidad {

	@Override
	public boolean sosApagable() {
		return false;
	}

	@Override
	public TipoApagabilidad getEnum() {
		return TipoApagabilidad.NoApagable;
	}

}
