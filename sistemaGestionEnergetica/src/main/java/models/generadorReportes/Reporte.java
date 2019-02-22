package models.generadorReportes;

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;

import models.entities.dispositivo.Dispositivo;
import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;

public final class Reporte extends TimerTask {

	private static int tiempoIntervalo;
	private static int retardo;
	private Timer unTimer;

	public Reporte() {
	}

	public final void init() {

		unTimer.schedule(this, getRetardo(), getTiempoIntervalo());
		VistaReporte.get().init();
	}
	
	@Override
	public void run() {

		this.mostrarConsumoClientes();
		this.mostrarConsumoDispositivos();
		this.mostrarConsumoTransformadores();
		
	}

	public final void agregarTransformador(Transformador unTransformador, DateTime fechaInicio, DateTime fechaFin) {
		VistaReporte.get().agregarTransformador(unTransformador, fechaInicio, fechaFin);
	}

	public final void agregarCliente(ClienteResidencial unCliente, DateTime fechaInicio, DateTime fechaFin) {
		VistaReporte.get().agregarCliente(unCliente, fechaInicio, fechaFin);
	}

	public final void agregarDispositivo(Dispositivo unCliente, DateTime fechaInicio, DateTime fechaFin) {
		VistaReporte.get().agregarDispositivo(unCliente, fechaInicio, fechaFin);
	}

	private void mostrarConsumoTransformadores() {
		String consumo = VistaReporte.get().getConsumoTransformadores();
		System.out.println(consumo);
	}

	private void mostrarConsumoClientes() {
		String consumo = VistaReporte.get().getConsumoClientes();
		System.out.println(consumo);
	}

	private void mostrarConsumoDispositivos() {
		String consumo = VistaReporte.get().getConsumoDispositivos();
		System.out.println(consumo);
	}

	public static void setRetardoReporte(int unRetardo) {
		retardo = unRetardo;
	}

	private static int getRetardo() {
	
		return retardo;
	
	}
	
	public static void setTiempoReporte(int unTiempo) {
		tiempoIntervalo = unTiempo;
	
	}
	
	private static int getTiempoIntervalo() {
		return tiempoIntervalo;
	}
}
