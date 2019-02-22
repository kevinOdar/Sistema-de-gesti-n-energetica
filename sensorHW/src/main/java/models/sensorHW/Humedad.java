package models.sensorHW;

public class Humedad extends TipoSensor {

	private int humedadMaxima = 100;
	private int humedadMinima = 18;

	@Override
	public void run() {

		nuevaMedicion = this.tomarNuevaMedicion();

		//System.out.printf("\nHumedad: %d%% %n", this.nuevaMedicion);

		this.publicarMedicion(nuevaMedicion);
	}

	public int tomarNuevaMedicion() {
		int numeroRandom = random.nextInt((humedadMaxima - humedadMinima) + 1) + humedadMinima;
		return numeroRandom;
	}
}
