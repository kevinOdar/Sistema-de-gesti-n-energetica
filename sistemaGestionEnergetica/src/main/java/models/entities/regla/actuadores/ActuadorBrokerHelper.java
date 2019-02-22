package models.entities.regla.actuadores;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public final class ActuadorBrokerHelper {

	public String ip;
	protected MqttClient cliente = null;
	private static ActuadorBrokerHelper instancia = null;

	private ActuadorBrokerHelper() {
	}

	public static ActuadorBrokerHelper get() {
		if (instancia == null)
			instancia = new ActuadorBrokerHelper();
		return instancia;
	}

	public void iniciarConexion() {

		MqttDefaultFilePersistence directorio = new MqttDefaultFilePersistence("broker/actuador/");
		try {
			cliente = new MqttClient("tcp://" + ip + ":1883", MqttClient.generateClientId(), directorio);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		try {
			cliente.connect();
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	protected void publicarAccion(String unTopic, String unaAccion) {

		MqttMessage message = new MqttMessage();
        message.setPayload(unaAccion.getBytes());
        
        try {
			cliente.publish(unTopic, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
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
