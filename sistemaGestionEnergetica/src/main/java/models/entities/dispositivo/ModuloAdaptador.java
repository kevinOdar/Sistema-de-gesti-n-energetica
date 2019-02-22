package models.entities.dispositivo;

public class ModuloAdaptador extends Inteligente {

	private Standard dispositivoAdaptado;
	
	public ModuloAdaptador(Standard unDispositivo) {
		this.dispositivoAdaptado = unDispositivo;
		this.dispositivoAdaptado.setModuloAdaptador(this);
		this.setPuntos(10);
	}


}
