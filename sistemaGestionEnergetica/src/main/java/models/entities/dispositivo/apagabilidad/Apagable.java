package models.entities.dispositivo.apagabilidad;

import models.entities.dispositivo.apagabilidad.tipo.TipoApagabilidad;

public class Apagable implements Apagabilidad {

	@Override
	public boolean sosApagable() {
		return true;
	}

	@Override
	public TipoApagabilidad getEnum() {
		return TipoApagabilidad.Apagable;
	}
}
