package models.sensorHW;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

public final class SensorHW {

	private static Timer timer;
	private static TipoSensor tipoSensor;
	private static Random random;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		random = new Random();
		timer = new Timer();

		System.out.println("/*** Hola! soy un sensor HW ***/\n");

		/*
		 * Orden de creacion
			1ro Tipo de sensor
			2do Setear IP (si es necesario)
			3ro Iniciar broker
			4to agregar topic
			5to iniciar sensor
		 */

		System.out.print("\nElija un tipo de sensor:");
		System.out.println("\n1) Temperatura\n2) Humedad\n3) Movimiento\n4) Intensidad luz\nSu eleccion:\t");
		String unTipo = scanner.nextLine();
		setTipo(unTipo);

		System.out.print("\nDesea setear una ip distinta a localhost?:\t");
		String setIP = scanner.nextLine();
		if (setIP.equalsIgnoreCase("si")) {
			System.out.print("\nIngrese la IP:\t");
			String unaIP = scanner.nextLine();
			tipoSensor.setIp(unaIP);
		}

		tipoSensor.iniciarConexionBroker();

		System.out.print("\nIngrese el topic al que debo publicar:\t");
		String unTopic = scanner.nextLine();
		tipoSensor.agregarTopic(unTopic);

		System.out.print("\nComenzando a publicar...\n");

		timer.scheduleAtFixedRate(tipoSensor, 10, getTiempoRandom());

		while(true) {
			System.out.print("Sensor HW activo, ingrese 'salir' para terminarlo\t");
			String salir = scanner.nextLine();
			if (salir.equalsIgnoreCase("salir")) { 
				System.out.println("\nChau!\n");
				break;
			}
		}
		
		scanner.close();
		System.exit(0);

	}

	private static void setTipo(String unTipo) {

		switch (unTipo) {
		case "1":
			tipoSensor = new Temperatura();
			break;

		case "2":
			tipoSensor = new Humedad();
			break;

		case "3":
			tipoSensor = new Movimiento();
			break;

		case "4":
			tipoSensor = new IntensidadLuz();
			break;
		}
	}

	// -- timer --//
	private static int getTiempoRandom() {
		return random.nextInt(9999 * 4);
	}
}
