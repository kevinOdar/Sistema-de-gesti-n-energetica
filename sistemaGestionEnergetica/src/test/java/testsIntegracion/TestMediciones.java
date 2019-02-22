package testsIntegracion;

import models.entities.sensor.Sensor;

public class TestMediciones {

	public static void main(String[] args) throws InterruptedException{

		Sensor humedadSW;
		Sensor intensidadSW;
		Sensor temperaturaSW;
		Sensor movimientoSW;
		
		humedadSW = new Sensor();
		intensidadSW = new Sensor();
		temperaturaSW = new Sensor();
		movimientoSW = new Sensor();
					
		humedadSW.iniciarSubscriptorBroker();
		intensidadSW.iniciarSubscriptorBroker();
		temperaturaSW.iniciarSubscriptorBroker();
		movimientoSW.iniciarSubscriptorBroker();
		
		humedadSW.setTopic("humedad");
		intensidadSW.setTopic("intensidad_luz");
		temperaturaSW.setTopic("temperatura");
		movimientoSW.setTopic("movimiento");
	}
}
