package models.server;

import models.configurador.Configurador;
import models.entities.regla.actuadores.ActuadorBrokerHelper;
import models.entities.sensor.SensorBrokerHelper;
import models.generadorReportes.VistaReporte;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {

	public static void main(String[] args) {
			
		Configurador.get().configurarSistema();

		VistaReporte.get().init();
		
		SensorBrokerHelper.get().iniciarConexiones();
				
		ActuadorBrokerHelper.get().iniciarConexion();
		
		Spark.port(9000);

		Router.init();

		DebugScreen.enableDebugScreen();
	}

}
