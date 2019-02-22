package testsEntregaPersistencia.test3;

import org.junit.*;

import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.regla.Regla;
import models.entities.regla.actuadores.Prender;
import models.entities.regla.criterios.Igualdad;
import models.entities.sensor.Sensor;

public class Test3a {
	/*
	 * Caso de prueba 3: 
	 * a)Crear una nueva regla. Asociarla a un dispositivo. Agregar
	 * condiciones y acciones. Persistirla. 
	 * b)Recuperarla y ejecutarla.
	 * c)Modificar alguna condición y persistirla. Recuperarla y evaluar que la condición
	 * modificada posea la última modificación.
	 */

	private Inteligente disp;
	private Regla regla = null;
	private Igualdad igualA30;
	private Sensor unSensor;
	private Prender prender;
	private DaoMySQL<Regla> dao;
	private DaoMySQL<Inteligente> daoIntel;
	private DaoMySQL<Sensor> daoSensor;
	private DaoMySQL<Caracteristica> importador_caracteristica;
	private Apagable apagable;

	@Before
	public void init() {
		dao = new DaoMySQL<Regla>();
		dao.init();
		dao.setTipo(Regla.class);

		daoIntel = new DaoMySQL<Inteligente>();
		daoIntel.init();
		daoIntel.setTipo(Inteligente.class);

		daoSensor = new DaoMySQL<Sensor>();
		daoSensor.init();
		daoSensor.setTipo(Sensor.class);

		disp = new Inteligente();
		apagable = new Apagable();
		importador_caracteristica = new DaoMySQL<Caracteristica>();
		importador_caracteristica.init();
		importador_caracteristica.setTipo(Caracteristica.class);
		
		disp.setCaracteristica(importador_caracteristica.buscar(21));

		disp.setApagabilidad(apagable);
		disp.setConsumoEnModoAutomatico(true);
		disp.setNombre("luz living");
		disp.setIntensidad(30);
		
		disp.apagar();
		regla = new Regla();
		unSensor = new Sensor();
		igualA30 = new Igualdad();
		prender = new Prender();
		
	}

	@Test
	public void testPersistirRegla() {

		
		
		igualA30.setValorComparacion(30);

		regla.setNombre("prender luz");
		regla.suscribirme(unSensor);
		regla.agregarActuador(prender);
		regla.agregarUnCriterio(igualA30, unSensor);

		disp.agregarRegla(regla);
		
		dao.agregar(regla);
	}



	

}
