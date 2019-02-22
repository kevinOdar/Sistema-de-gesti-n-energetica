package models.sensorHW;

import java.util.Random;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public abstract class TipoSensor extends TimerTask{

	protected Random random;
	protected int nuevaMedicion;
	protected String canal;
	protected String ip = "localhost";
	MqttClient cliente = null;
	
	public TipoSensor(){
		this.random = new Random();
	}
	
	@Override
	public abstract void run();
	
	protected void publicarMedicion(int nuevaMedicion) {
		String mensaje = String.valueOf(nuevaMedicion);
		
		MqttMessage message = new MqttMessage();
        message.setPayload(mensaje.getBytes());
        
        try {
			cliente.publish(canal, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract int tomarNuevaMedicion();

	//////// ------ broker --------//////
	public void iniciarConexionBroker() {

		MqttDefaultFilePersistence directorio = new MqttDefaultFilePersistence("broker/");
		try {
			cliente = new MqttClient("tcp://" + ip + ":1883", MqttClient.generateClientId(), directorio);
		} catch (MqttException e1) {
			e1.printStackTrace();
		}

		try {
			cliente.connect();

		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}

		System.out.println("Todo ok, sensor HW activo\n");
	}

	public void agregarTopic(String unTopic) {

		try {
			cliente.subscribe(unTopic);
			canal = unTopic;
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
