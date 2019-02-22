package models.logger;

public class Logger {

	private static Logger logger;
	
	private Logger() {}
	
	public static Logger getLogger() {
		
		if(logger == null) {
			logger = new Logger();
		}
		return logger;
	}
	
	public void logMensaje(String unString) {
		System.out.println(unString);
	}
	
	public void logNombreDispositivo(String unNombre) {
		System.out.format("\nDispositivo: %s\n", unNombre);
	}
	
	public void logHorasExcedidas(double unasHoras) {
		System.out.format("El dispositivo excedio las horas de consumo recomendadas (%.2f horas)\n", unasHoras);
	}
	
	public void logHorasOk(double unasHoras) {
		System.out.format("El dispositivo no excedio las horas de consumo recomendadas (%.2f horas)\n", unasHoras);
	}
	
	public void logEjecutarAccion(String unNombre) {
		System.out.format("Ejecutando accion correctiva sobre el dispositivo '%s' porque se excedio en horas\n", unNombre);
	}
	
	public void logHorasDeUsoMensuales(String unNombre, double unasHoras) {
		System.out.format("El dispositivo '%s' se utilizo [%.2f] horas mensuales\n", unNombre, unasHoras);
	}
	
	public void logSimplexCliente(String unNombre) {
		System.out.format("\n---Analizando al cliente '%s'---\n", unNombre);
	}
	public void logSimplexDispositivos() {
		System.out.println("\n-- Analizando los dispositivos del cliente --\n");
		System.out.println("\n-- Analisis OK, verificando uso de los dispositivos--\n");
	}
	
	public void logSolucionIncompatible(String unMensaje) {
		System.out.format("\n%s, el sistema es incompatible, el consumo del cliente no se pued optimizar\n", unMensaje);
	}
	
	public void logSolucionInfinita(String unMensaje) {
		System.out.format("\n%s, el sistema tiene solucion infinita, el consumo del cliente no se puede optimizar\n", unMensaje);
	}

	public void logSimplexDispositivoCumplidor(String nombre, double horasRecomendadas, double horasEncendidoAlMes) {
		
		this.logNombreDispositivo(nombre);
		this.logHorasOk(horasRecomendadas);
		this.logHorasDeUsoMensuales(nombre, horasEncendidoAlMes);	
	}

	public void logSimpexDispositivoNoCumple(String nombre, double horasRecomendadas, double horasEncendidoAlMes) {
		this.logNombreDispositivo(nombre);
		this.logHorasExcedidas(horasRecomendadas);
		this.logHorasDeUsoMensuales(nombre, horasEncendidoAlMes);	
	}
	
}
