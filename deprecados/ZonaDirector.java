package models.entities.zonaGeografica;

public class ZonaDirector {
	
private ZonaGeograficaBuilder zonaGeograficaBuilder;
	
	public void armarZonaGeografica() { 
		zonaGeograficaBuilder.buildCoordenadas();
		zonaGeograficaBuilder.buildCantidadPuntos();
		zonaGeograficaBuilder.buildTransformadores();
	}
	public void setZonaGeograficaBuilder(ZonaGeograficaBuilder unaZonaGeograficaBuilder) {
		zonaGeograficaBuilder = unaZonaGeograficaBuilder;
	}
	public ZonaGeografica getZonaGeografica() {
		return zonaGeograficaBuilder.getZonaGeografica();
	}

}
