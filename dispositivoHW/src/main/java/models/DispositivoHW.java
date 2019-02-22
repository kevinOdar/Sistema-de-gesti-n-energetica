package models;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public final class DispositivoHW {

	private static MqttClient cliente;

	private static String ip = "localhost";

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("/*** Hola! soy un dispositivo HW ***/\n");

		
		System.out.print("\nDesea setear una ip distinta a localhost?:\t");
		String setIP = scanner.nextLine();
		if (setIP.equalsIgnoreCase("si")) {
			System.out.println("Ingrese la IP:\t");
			String unaIP = scanner.nextLine();
			setIp(unaIP);
		}

		iniciarConexionBroker();

		System.out.print("\nIngrese el topic al que debo suscribirme:\t");
		String unTopic = scanner.nextLine();
		agregarTopic(unTopic);
		
		while (true) {
			System.out.print("Dispositivo HW activo, ingrese 'salir' para terminarlo\t");
			String selection = scanner.nextLine();
				if (selection.equalsIgnoreCase("salir")) {
				System.out.println("\nAdios!\n");
				break;
			}
		}
		
		scanner.close();
		System.exit(0);
	}

	/// ---- ip ------///
	private static void setIp(String ipNueva) {
		ip = ipNueva;
		System.out.println("\nIP seteada correctamente\n");
	}

	//////// ------ broker --------//////
	private static void iniciarConexionBroker() {

		MqttDefaultFilePersistence directorio = new MqttDefaultFilePersistence("broker/");
		try {
			cliente = new MqttClient("tcp://" + ip + ":1883", MqttClient.generateClientId(), directorio);
		} catch (MqttException e1) {
			e1.printStackTrace();
		}

		cliente.setCallback(new MqttCallback() {
			public void connectionLost(Throwable throwable) {
				System.out.println("Se perdio la conexion\n");
			}

			public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
				String mensaje = new String(mqttMessage.getPayload());
				System.out.println("\nMensaje recibido: "+mensaje);
				ejecutarAccion(mensaje);
			}

			public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
			}
		});

		try {
			cliente.connect();
			
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}

		System.out.println("Todo ok, conectado al broker\n");
	}

	private static void ejecutarAccion(String mensaje) {

		if (mensaje.equalsIgnoreCase("prender")) {
			System.out.println("Me tengo que prender\n");
		}

		if (mensaje.equalsIgnoreCase("apagar")) {
			System.out.println("Me tengo que apagar\n");
		}

		if (mensaje.equalsIgnoreCase("subir intensidad")) {
			System.out.println("Tengo que ser más intenso\n");
		}

		if (mensaje.equalsIgnoreCase("bajar intensidad")) {
			System.out.println("Tengo que ser menos intenso\n");
		}

		if (mensaje.equalsIgnoreCase("activar modo ahorro")) {
			System.out.println("Pasando a modo ahorro de energia\n");
		}

		// TODO chequear
		if (mensaje.equalsIgnoreCase("desactivar modo ahorro")) {
			System.out.println("Me tengo que prender\n");
		}

	}
	

	private static void agregarTopic(String unTopic) {

		try {
			cliente.subscribe(unTopic);
			System.out.println("Suscripto a: " + unTopic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
