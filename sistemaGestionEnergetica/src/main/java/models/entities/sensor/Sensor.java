package models.entities.sensor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dominio.observer.*;
import models.entities.regla.Regla;
import models.entities.regla.criterios.Criterio;

@Entity
@Table(name = "sensor")
public final class Sensor extends EntidadPersistente implements Observable {

	@Transient
	public static int medicionesMaximas = 10;

	@Transient
	private int cantidadDeMediciones;

	@ElementCollection
	@CollectionTable(name = "medicion", joinColumns = @JoinColumn(name = "sensor_id"))
	@Column(name = "medicion")
	private List<Integer> mediciones;

	@ManyToMany
	private List<Regla> reglas;

	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Criterio> criteriosAsociados;

	@Column(name = "topic", length = 512)
	private String topic;

	@Column(name = "ip_broker", length = 512)
	public static String ip;

	@Transient
	private MqttClient client = null;

	@Column(name = "nombre_sensor_hw", length = 512)
	private String nombreSensorHW;

	public Sensor() {
		this.reglas = new ArrayList<>();
		this.mediciones = new ArrayList<>(medicionesMaximas);
		this.cantidadDeMediciones = 0;
		this.criteriosAsociados = new ArrayList<>();
	}

	public static void setMedicionesMaximas(int unInt) {
		medicionesMaximas = unInt;
	}

	public static int getMedicionesMaximas() {
		return medicionesMaximas;
	}

	public List<Criterio> getCriteriosAsociados() {
		return criteriosAsociados;
	}

	public void setCriteriosAsociados(List<Criterio> criteriosAsociados) {
		this.criteriosAsociados = criteriosAsociados;
	}

	public void agregarCriterioAsociado(Criterio unCriterio) {
		this.criteriosAsociados.add(unCriterio);
	}

	public List<Integer> getMediciones() {
		return mediciones;
	}

	public void agregarMedicion(int unaMedicion) {
		if (this.cantidadDeMediciones == medicionesMaximas) {
			this.mediciones = getMedicionesSinLaMedicionMasVieja(this.mediciones);
			this.mediciones.add(medicionesMaximas - 1, unaMedicion);
		} else {
			this.mediciones.add(this.cantidadDeMediciones++, unaMedicion);
		}
	}

	public List<Integer> getMedicionesSinLaMedicionMasVieja(List<Integer> enteros) {
		List<Integer> enterosAux = new ArrayList<Integer>(medicionesMaximas);

		enterosAux.addAll(0, enteros.subList(1, this.cantidadDeMediciones));

		return enterosAux;
	}

	public void quitarMedicion(int unaPosicion) {
		List<Integer> medicionesAux = new ArrayList<Integer>(medicionesMaximas);

		if (unaPosicion > 0 && unaPosicion < this.cantidadDeMediciones) {
			medicionesAux.addAll(0, this.mediciones.subList(0, unaPosicion));

			medicionesAux.addAll(unaPosicion, this.mediciones.subList(unaPosicion + 1, this.cantidadDeMediciones));
			this.mediciones = medicionesAux;
			this.cantidadDeMediciones--;
		} else if (unaPosicion == 0) {
			this.mediciones = this.getMedicionesSinLaMedicionMasVieja(this.mediciones);
			this.cantidadDeMediciones--;
		} else {
			System.out.println("no sé eliminar una posición negativa o cuya cardinalidad "
					+ "es mayor que la cantidad de mediciones cargadas");
		}
	}

	public void anotarMedicion(int nuevaMedicion) {
		this.agregarMedicion(nuevaMedicion);
		this.avisarASuscriptores();
	}

	public int getUltimaMedicion() {
		return this.mediciones.get(this.mediciones.size() - 1);
	}

	///// ------------Comportamiento de observable---------//////
	@Override
	public void agregarSuscripcion(Observador unaRegla) {
		this.reglas.add((Regla) unaRegla);
	}

	@Override
	public void agregarSuscripciones(Observador... variasReglas) {
		for (Observador unaRegla : variasReglas) {
			this.agregarSuscripcion((Regla) unaRegla);
		}
	}

	@Override
	public void borrarSuscripcion(Observador unaRegla) {
		this.reglas.remove((Regla) unaRegla);
	}

	public void avisarASuscriptores() {
		this.reglas.forEach(unaRegla -> unaRegla.actualizar());
	}

	public void iniciarSubscriptorBroker() {

		MqttDefaultFilePersistence directorio = new MqttDefaultFilePersistence("broker/sensorSW/");
		try {
			client = new MqttClient("tcp://" + ip + ":1883", MqttClient.generateClientId(), directorio);
		} catch (MqttException e) {
			System.out.println("no se por qué llegué acá");
			e.printStackTrace();
		}

		client.setCallback(new MqttCallback() {
			public void connectionLost(Throwable throwable) {
				System.out.println("Se perdio la conexion");
			}

			public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
				String mensaje = new String(mqttMessage.getPayload());
				int nuevaMedicion = Integer.valueOf(mensaje);
				//System.out.println("Medicion de " + nombreSensorHW + " recibida:\t" + mensaje + "\n");
				anotarMedicion(nuevaMedicion);
			}

			public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
			}
		});

		try {
			client.connect();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setTopic(String unTopic) {
		this.nombreSensorHW = unTopic;
		this.topic = "/sensorHW/" + unTopic;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		Sensor.ip = ip;
	}

	public void suscribirATopic() {
		try {
			if(topic != null) client.subscribe(topic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getNombreSensorHW()
	{
		return this.nombreSensorHW;
	}
}
