package models.entities.optimizador;

import java.util.Timer;

public class ProcesoOptimizador {

	Timer timerOptimizador;
	Optimizador optimizador;
	public static int tiempoOptimizador = 1000;
	public static int intervaloInicial = 10;
	
	public ProcesoOptimizador() {
		this.timerOptimizador = new Timer();

		this.optimizador = new Optimizador();
	}
	
	public void init() {
		timerOptimizador.scheduleAtFixedRate(optimizador, ProcesoOptimizador.intervaloInicial, ProcesoOptimizador.tiempoOptimizador);
	}

	public Timer getTimerOptimizador() {
		return timerOptimizador;
	}

	public void setTimerOptimizador(Timer timerOptimizador) {
		this.timerOptimizador = timerOptimizador;
	}

	public Optimizador getOptimizador() {
		return optimizador;
	}

	public void setOptimizador(Optimizador optimizador) {
		this.optimizador = optimizador;
	}

	public int getTiempoOptimizador() {
		return tiempoOptimizador;
	}

	public int getIntervaloInicial() {
		return intervaloInicial;
	}
	
	public static void setTiempoOptimizador(int unTiempo) {
		ProcesoOptimizador.tiempoOptimizador = unTiempo;
	}
	
	public static void setIntervaloInicial(int unIntervalo) {
		ProcesoOptimizador.intervaloInicial = unIntervalo;
	}
	
}
