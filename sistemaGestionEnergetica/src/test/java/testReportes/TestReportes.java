package testReportes;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Dispositivo;
import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;
import models.generadorReportes.VistaReporte;

public class TestReportes {

	@Before
	public void init() {
		setearTablaReporte();

		VistaReporte.get().init();

	}
	
	@Test
	public void test() {
	System.out.println(VistaReporte.get().getConsumoClientes());	
	System.out.println("\n");
	System.out.println(VistaReporte.get().getConsumoTransformadores());	

	}
	
	private static void setearTablaReporte() {
		List<ClienteResidencial> clientes = getClientesSistema();
		List<Transformador> transformadores = getTransformadoresSistema();
		List<Dispositivo> dispositivos = getDispositivosSistema();

		VistaReporte.setIntervaloActualizacion(10000);
		VistaReporte.setRetardoInicioTabla(1000);

		VistaReporte.get().agregarClientes(clientes, DateTime.now().minusMonths(1), DateTime.now());
		VistaReporte.get().agregarTransformadores(transformadores, DateTime.now().minusMonths(1), DateTime.now());
		VistaReporte.get().agregarDispositivos(dispositivos, DateTime.now().minusMonths(6), DateTime.now());
	}

	private static List<ClienteResidencial> getClientesSistema() {
		DaoMySQL<ClienteResidencial> dao = new DaoMySQL<ClienteResidencial>();
		dao.init();
		dao.setTipo(ClienteResidencial.class);

		return dao.dameTodos();
	}

	private static List<Transformador> getTransformadoresSistema() {
		DaoMySQL<Transformador> dao = new DaoMySQL<Transformador>();
		dao.init();
		dao.setTipo(Transformador.class);
		return dao.dameTodos();
	}

	private static List<Dispositivo> getDispositivosSistema() {
		DaoMySQL<Dispositivo> dao = new DaoMySQL<Dispositivo>();
		dao.init();
		dao.setTipo(Dispositivo.class);
		return dao.dameTodos();
	}
}
