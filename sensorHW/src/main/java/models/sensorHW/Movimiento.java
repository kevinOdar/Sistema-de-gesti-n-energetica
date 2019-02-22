package models.sensorHW;

public class Movimiento extends TipoSensor {

	@Override
	public void run() {
		nuevaMedicion = this.tomarNuevaMedicion();

		if (nuevaMedicion == 0) {
			//System.out.printf("\nNo hay movimiento %n");
		} else {
			//System.out.printf("\nHay movimiento %n");
		}

		this.publicarMedicion(nuevaMedicion);
	}

	protected int tomarNuevaMedicion() {
		return random.nextInt(2);
	}

}
