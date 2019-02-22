package models.configurador;

import models.dao.hibernate.DaoMySQL;
import models.entities.optimizador.ProcesoOptimizador;
import models.entities.regla.actuadores.ActuadorBrokerHelper;
import models.entities.sensor.Sensor;
import models.generadorReportes.Reporte;
import models.generadorReportes.VistaReporte;

public class Configurador {

	private static DaoMySQL<ParametroSistema> importador;
	private static int cantidadMedicionesSensor;
	private static int intervaloOptimizador;
	private static int tiempoOptimizador;
	private static int retardoReporte;
	private static int tiempoReporte;
	private static int retardoTabla;
	private static int tiempoTabla;
	private static Configurador instancia = null;
	
	private Configurador() {

	}

	public static Configurador get() {
		if (importador == null) {
		importador = new DaoMySQL<ParametroSistema>();
		importador.init();
		importador.setTipo(ParametroSistema.class);
		}
		
		if (instancia == null) {
			instancia = new Configurador();
		}
		return instancia;
	}

	public void configurarSistema() {

		// configurar sensor
		cantidadMedicionesSensor = importador.buscarPorNombre("cantidad mediciones sensor").getValor();
		Sensor.setMedicionesMaximas(cantidadMedicionesSensor);

		// configurar optimizador
		intervaloOptimizador = importador.buscarPorNombre("intervalo optimizador").getValor();
		tiempoOptimizador = importador.buscarPorNombre("tiempo optimizador").getValor();
		ProcesoOptimizador.setIntervaloInicial(intervaloOptimizador);
		ProcesoOptimizador.setTiempoOptimizador(tiempoOptimizador);

		// configurar tabla reporte
		retardoTabla = importador.buscarPorNombre("retardo tabla").getValor();
		tiempoTabla = importador.buscarPorNombre("tiempo tabla").getValor();
		VistaReporte.setRetardoInicioTabla(retardoTabla);
		VistaReporte.setIntervaloActualizacion(tiempoTabla);

		// configurar reporte
		retardoReporte = importador.buscarPorNombre("retardo reporte").getValor();
		tiempoReporte = importador.buscarPorNombre("tiempo reporte").getValor();
		Reporte.setRetardoReporte(retardoReporte);
		Reporte.setRetardoReporte(tiempoReporte);
		
		//configurar ip broker
		String ip_broker = importador.buscarPorNombre("ip broker").getValorString();
		Sensor.setIp(ip_broker);
		ActuadorBrokerHelper.get().setIp(ip_broker);
	}

	public void agregarParametro(String unNombre, int unValor, String unValorString) {
		ParametroSistema nuevoParametro = new ParametroSistema();
		nuevoParametro.setNombre(unNombre);
		nuevoParametro.setValor(unValor);
		nuevoParametro.setValorString(unValorString);
		importador.agregar(nuevoParametro);
	}
	
	public void setCantidadMedicionesSensor(int unValor) {
		modificarParametro("cantidad mediciones sensor", unValor);
		Sensor.setMedicionesMaximas(unValor);
	}

	public void setTiempoOptimizador(int unValor) {
		modificarParametro("tiempo optimizador", unValor);
		ProcesoOptimizador.setTiempoOptimizador(unValor);
	}

	public void setIntervaloOptimizador(int unValor) {
		modificarParametro("intervalo optimizador", unValor);
		ProcesoOptimizador.setIntervaloInicial(unValor);
	}

	public void setRetardoTabla(int unValor) {
		modificarParametro("retardo tabla", unValor);

		VistaReporte.setRetardoInicioTabla(unValor);
	}

	public void setTiempoTabla(int unValor) {

		modificarParametro("tiempo tabla", unValor);

		VistaReporte.setIntervaloActualizacion(unValor);

	}
	
	public void setRetardoReporte(int unValor) {
		modificarParametro("retardo reporte", unValor);

		VistaReporte.setRetardoInicioTabla(unValor);
	}

	public void setTiempoReporte(int unValor) {

		modificarParametro("tiempo reporte", unValor);

		VistaReporte.setIntervaloActualizacion(unValor);

	}

	private void modificarParametro(String nombre, int unValor) {
		ParametroSistema parametroModificado = importador.buscarPorNombre(nombre);
		parametroModificado.setValor(unValor);
		importador.modificar(parametroModificado);
	}


}
