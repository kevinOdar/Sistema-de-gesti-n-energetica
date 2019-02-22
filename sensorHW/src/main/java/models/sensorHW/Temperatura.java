package models.sensorHW;

public class Temperatura extends TipoSensor {

	private int temperaturaMaxima = 35;
	private int temperaturaMinima = 7;

	@Override
	public void run() {

		nuevaMedicion = this.tomarNuevaMedicion();

		//System.out.printf("\nTemperatura: %d °C %n \n", this.nuevaMedicion);

		this.publicarMedicion(nuevaMedicion);
	}

	protected int tomarNuevaMedicion() {
		return random.nextInt(((temperaturaMaxima - temperaturaMinima) + 1) + temperaturaMinima);
	}
}