package testsDummies;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.apagabilidad.Apagable;

public final class Inteligente1 {

	private static Inteligente disp = null;
	private static DaoMySQL<Caracteristica> importador_caracteristica;
	private static Apagable apagable;
	
	private Inteligente1() {}
	
	public static Inteligente getDispositivo() {
		if (disp == null) {
			disp = new Inteligente();
			apagable = new Apagable();
			importador_caracteristica = new DaoMySQL<Caracteristica>();
			importador_caracteristica.init();
			importador_caracteristica.setTipo(Caracteristica.class);
			
			disp.setCaracteristica(importador_caracteristica.buscar(21));

			disp.setApagabilidad(apagable);
			disp.setConsumoEnModoAutomatico(true);
			disp.setNombre("luz living");
			disp.setIntensidad(30);
			
			disp.prender();
			
		}
			return disp;
		
	}
	
}
