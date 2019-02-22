package testsIntegracion;

import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.regla.Regla;
import models.entities.regla.actuadores.Apagar;
import models.entities.regla.actuadores.Prender;
import models.entities.regla.criterios.Igualdad;
import models.entities.sensor.Sensor;

public class TestSensorHastaDisp {

	public static void main(String[] args) throws InterruptedException{

		Sensor movimientoSW;
		Caracteristica smartLed40;
		Inteligente dispositivo;
		Apagar apagar;
		Prender prender;
		Igualdad hayMov;
		Igualdad noHayMov;
		Regla prenderSiHayMov;
		Regla apagarNoHayMov;

		prender = new Prender();
		apagar = new Apagar();
		
		hayMov = new Igualdad();
		noHayMov = new Igualdad();
		
		hayMov.setValorComparacion(1);
		noHayMov.setValorComparacion(0);
		
		smartLed40 = new Caracteristica();
		smartLed40.setCoeficienteDeConsumo(0.08);
		smartLed40.setDeBajoConsumo(true);
		smartLed40.setInteligente(true);
		dispositivo = new Inteligente();
		dispositivo.setNombre("SLL (Smart Led Light)");
		dispositivo.setCaracteristica(smartLed40);
		dispositivo.prender();

		movimientoSW = new Sensor();
		movimientoSW.iniciarSubscriptorBroker();
		movimientoSW.setTopic("movimiento");

		prenderSiHayMov = new Regla();
		prenderSiHayMov.agregarActuador(prender);
		prenderSiHayMov.agregarUnCriterio(hayMov, movimientoSW);
		prenderSiHayMov.agregarDispositivo(dispositivo);
		
		apagarNoHayMov = new Regla();
		apagarNoHayMov.agregarActuador(apagar);
		apagarNoHayMov.agregarUnCriterio(noHayMov, movimientoSW);
		apagarNoHayMov.agregarDispositivo(dispositivo);
		
		

		
		
	}
}
