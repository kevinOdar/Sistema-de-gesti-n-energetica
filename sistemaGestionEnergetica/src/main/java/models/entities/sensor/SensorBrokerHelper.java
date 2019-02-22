package models.entities.sensor;

import java.util.ArrayList;
import java.util.List;

import models.dao.hibernate.DaoMySQL;

public final class SensorBrokerHelper{
	
	private static SensorBrokerHelper instancia = null;
	private DaoMySQL<Sensor> daoSensores;
	private List<Sensor> sensoresSistema;
	
	private SensorBrokerHelper () {
		daoSensores = new DaoMySQL<Sensor>();
		daoSensores.init();
		daoSensores.setTipo(Sensor.class);
		sensoresSistema = new ArrayList<>();
	}
	
	public static SensorBrokerHelper get() {
		if (instancia == null) instancia = new SensorBrokerHelper();
		
		return instancia;
	}
	
	public void iniciarConexiones() {
		sensoresSistema = daoSensores.dameTodos();
		
		if (sensoresSistema != null) {
			sensoresSistema.forEach(unSensor -> this.iniciarSensor(unSensor));
		}
	}

	private void iniciarSensor(Sensor unSensor) {

		unSensor.iniciarSubscriptorBroker();
		unSensor.suscribirATopic();
		
	}

}
