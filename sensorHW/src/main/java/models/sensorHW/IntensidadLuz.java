package models.sensorHW;

public class IntensidadLuz extends TipoSensor {

	@Override
	public void run() {

		nuevaMedicion = this.tomarNuevaMedicion();

		//System.out.printf("Intensidad en nivel %d %n", this.nuevaMedicion);

		this.publicarMedicion(nuevaMedicion);
	}

	protected int tomarNuevaMedicion() {
		return random.nextInt(101);
	}
}
