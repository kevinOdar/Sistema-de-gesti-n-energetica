package testMapaController;

import models.controllers.MapaController;

public class TestStringJsonZonas {
	public static void main(String[] args) {
		MapaController cont = MapaController.get() ;
		System.out.println(cont.getJsonZonas());
		System.out.println(cont.getJsonTransformadores());
	}
}
