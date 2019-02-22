package testGenesis;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.*;

import models.configurador.Configurador;
import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dispositivo.Standard;
import models.entities.dispositivo.apagabilidad.Apagabilidad;
import models.entities.dispositivo.apagabilidad.Apagable;
import models.entities.dispositivo.estado.Apagado;
import models.entities.dispositivo.estado.EstadoDispositivo;
import models.entities.dispositivo.estado.Prendido;
import models.entities.dominio.Administrador;
import models.entities.dominio.CategoriaConsumo;
import models.entities.dominio.ClienteResidencial;
import models.entities.regla.Regla;
import models.entities.regla.actuadores.Apagar;
import models.entities.regla.actuadores.PasarAModoAhorroEnergia;
import models.entities.regla.actuadores.Prender;
import models.entities.regla.criterios.Igualdad;
import models.entities.regla.criterios.Mayor;
import models.entities.regla.criterios.Menor;
import models.entities.sensor.Sensor;
import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;
import models.entities.zonaGeografica.ZonaGeografica;
import models.generadorReportes.VistaReporte;

public class TestGenesis {

	private CreadorCategorias creadorCategorias;
	private CreadorCaracteristicas creadorDeCaracteristicas;

	@Test
	public void persistirTodo() throws NoSuchAlgorithmException {
		this.crearCategorias();
		this.crearCaracteristicas();
		this.crearParametros();

		this.persistirAdmin();
		this.persistirKevin();

		this.persistirDispositivo();
		this.persistirDispositivoConRegla();

		this.persistirTransformador1();
		this.persistirTransformador2();

		this.persistirCosme();

		this.persistirTransformador3();

		this.persistirZonaGeografica();

		this.persistirDispStandardEnClientekodar();
		this.persistirMedicionesConNuevaRegla();
		this.persistirMasMedicionesConNuevaRegla();
		this.persistirReglaSubscriptaA2Sensores();

		this.persistirDispositivoReglaActuador();
		
		this.persistirElementosReporte();
	}

	private void persistirElementosReporte() {
		DaoMySQL<ClienteResidencial> daoCli = new DaoMySQL<ClienteResidencial>();
		daoCli.init();
		daoCli.setTipo(ClienteResidencial.class);
		List<ClienteResidencial> clientes = daoCli.dameTodos();

		DaoMySQL<Transformador> daoTrans = new DaoMySQL<Transformador>();
		daoTrans.init();
		daoTrans.setTipo(Transformador.class);
		List<Transformador> transformadores = daoTrans.dameTodos();

		DaoMySQL<Dispositivo> daoDisp = new DaoMySQL<Dispositivo>();
		daoDisp.init();
		daoDisp.setTipo(Dispositivo.class);
		List<Dispositivo> dispositivos = daoDisp.dameTodos();

		VistaReporte.get().agregarClientes(clientes, DateTime.now().minusMonths(1), DateTime.now());
		VistaReporte.get().agregarTransformadores(transformadores, DateTime.now().minusMonths(1), DateTime.now());
		VistaReporte.get().agregarDispositivos(dispositivos, DateTime.now().minusMonths(6), DateTime.now());

	}

	private void persistirCosme() {
		DaoMySQL<CategoriaConsumo> importador2 = new DaoMySQL<CategoriaConsumo>();
		importador2.init();
		importador2.setTipo(CategoriaConsumo.class);
		CategoriaConsumo cat1 = importador2.buscar(8);

		Coordenada coordenada1 = new Coordenada();

		coordenada1.setLatitud(-34.1857985);
		coordenada1.setLongitud(-58.2993031);

		List<Coordenada> coordenadas1 = new ArrayList<Coordenada>();

		coordenadas1.add(coordenada1);

		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		ClienteResidencial cliente = new ClienteResidencial();
		cliente.setConsumoEnAutomatico(true);
		cliente.setAliasUsuario("cosFul");
		cliente.setApellido("Fulanito");
		cliente.setDomicilio("Algun lado 123");
		cliente.setNombre("Cosme");
		cliente.setNumeroDocumento(987654321);
		cliente.setTelefono(123456789);
		cliente.setPuntos(9999);
		cliente.setTipoDocumento("dni");
		try {
			cliente.setContrasenia("cosme5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cliente.setCategoria(cat1);
		cliente.setCoordenadas(coordenadas1);
		cliente.setFechaAltaServicio(DateTime.now().minusMonths(8));

		importador.init();
		importador.setTipo(ClienteResidencial.class);
		importador.agregar(cliente);
	}

	public void persistirDispositivoReglaActuador() {
		Sensor movimientoSW;
		Inteligente dispositivo;
		Apagar apagar;
		Prender prender;
		Igualdad hayMov;
		Igualdad noHayMov;
		Regla prenderSiHayMov;
		Regla apagarNoHayMov;

		prender = new Prender();
		apagar = new Apagar();

		hayMov = new Igualdad();
		noHayMov = new Igualdad();

		hayMov.setValorComparacion(1);
		noHayMov.setValorComparacion(0);

		DaoMySQL<Caracteristica> importador = new DaoMySQL<Caracteristica>();
		importador.init();
		importador.setTipo(Caracteristica.class);
		Caracteristica caracteristica = importador.buscarPorNombre("LED 40\"");

		Apagable apagable = new Apagable();
		
		dispositivo = new Inteligente();
		dispositivo.setNombre("pequenioLED40");
		dispositivo.setCaracteristica(caracteristica);
		dispositivo.prender();
		dispositivo.setApagabilidad(apagable);
		
		movimientoSW = new Sensor();
		movimientoSW.setTopic("movimiento");

		prenderSiHayMov = new Regla();
		prenderSiHayMov.setNombre("prenderSiHayMov");
		prenderSiHayMov.agregarActuador(prender);
		prenderSiHayMov.agregarUnCriterio(hayMov, movimientoSW);
		//prenderSiHayMov.agregarDispositivo(dispositivo);

		apagarNoHayMov = new Regla();
		apagarNoHayMov.setNombre("apagarNoHayMov");
		apagarNoHayMov.agregarActuador(apagar);
		apagarNoHayMov.agregarUnCriterio(noHayMov, movimientoSW);
		//apagarNoHayMov.agregarDispositivo(dispositivo);

		dispositivo.agregarRegla(apagarNoHayMov);
		dispositivo.agregarRegla(prenderSiHayMov);
		
		DaoMySQL<Inteligente> importadorDispositivos = new DaoMySQL<Inteligente>();
		importadorDispositivos.init();
		importadorDispositivos.setTipo(Inteligente.class);
		
		importadorDispositivos.agregar(dispositivo);

		
		DaoMySQL<ClienteResidencial> importadorClienteResidencial = new DaoMySQL<ClienteResidencial>();
		importadorClienteResidencial.init();
		importadorClienteResidencial.setTipo(ClienteResidencial.class);
		ClienteResidencial cliente = importadorClienteResidencial.buscarPorAlias("cosFul");

		cliente.agregarDispositivo(importadorDispositivos.buscarPorNombre("pequenioLED40"));
		
		importadorClienteResidencial.modificar(cliente);
	}

	private void persistirZonaGeografica() {

		DaoMySQL<ZonaGeografica> importador = new DaoMySQL<ZonaGeografica>();
		importador.init();
		importador.setTipo(ZonaGeografica.class);

		List<Coordenada> coordenadas = new ArrayList<>();
		ZonaGeografica zona = new ZonaGeografica();
		Coordenada coord1 = new Coordenada();
		Coordenada coord2 = new Coordenada();
		Coordenada coord3 = new Coordenada();
		Coordenada coord4 = new Coordenada();

		//
		coord1.setLatitud(-34.609073);
		coord1.setLongitud(-58.408263);
		coordenadas.add(coord1);
		
		//
		coord2.setLatitud(-34.592152);
		coord2.setLongitud(-58.420075);
		coordenadas.add(coord2);

		//
		coord3.setLatitud(-34.598961);
		coord3.setLongitud(-58.444269);
		coordenadas.add(coord3);

		//
		coord4.setLatitud(-34.619394);
		coord4.setLongitud(-58.448046);
		coordenadas.add(coord4);

		zona.setCoordenadas(coordenadas);
		importador.agregar(zona);

	}

	private void persistirTransformador3() {
		Transformador transformador = new Transformador();
		Coordenada coordenada = new Coordenada();
		List<Coordenada> coordenadas = new ArrayList<>();
		DaoMySQL<Transformador> importador = new DaoMySQL<Transformador>();
		importador.init();
		importador.setTipo(Transformador.class);
		coordenada.setLatitud(-34.603333);
		coordenada.setLongitud(-58.420431);
		
		coordenadas.add(coordenada);

		transformador.setNombre("transf10A");
		transformador.setCoordenadas(coordenadas);

		DaoMySQL<ClienteResidencial> importadorClienteResidencial = new DaoMySQL<ClienteResidencial>();
		importadorClienteResidencial.init();
		importadorClienteResidencial.setTipo(ClienteResidencial.class);
		ClienteResidencial cliente = importadorClienteResidencial.buscarPorAlias("cosFul");

		transformador.agregarCliente(cliente);

		importador.agregar(transformador);
	}

	private void persistirTransformador2() {

		Transformador transformador = new Transformador();
		Coordenada coordenada = new Coordenada();
		List<Coordenada> coordenadas = new ArrayList<>();
		DaoMySQL<Transformador> importador = new DaoMySQL<Transformador>();
		importador.init();
		importador.setTipo(Transformador.class);
		coordenada.setLatitud(-34.606612);
		coordenada.setLongitud(-58.435545);

		coordenadas.add(coordenada);

		transformador.setNombre("transf002B");
		transformador.setCoordenadas(coordenadas);

		importador.agregar(transformador);
	}

	private void persistirTransformador1() {
		Transformador transformador = new Transformador();
		Coordenada coordenada = new Coordenada();
		List<Coordenada> coordenadas = new ArrayList<>();
		DaoMySQL<Transformador> importador = new DaoMySQL<Transformador>();
		importador.init();
		importador.setTipo(Transformador.class);
		coordenada.setLatitud(-34.598601);
		coordenada.setLongitud(-58.419884);

		
		coordenadas.add(coordenada);

		transformador.setNombre("transf572G");
		transformador.setCoordenadas(coordenadas);

		importador.agregar(transformador);
	}

	private void persistirDispositivoConRegla() {
		Inteligente disp;
		Regla regla = null;
		Igualdad igualA30;
		Sensor unSensor;
		Prender prender;
		DaoMySQL<Regla> dao;
		DaoMySQL<Inteligente> daoIntel;
		DaoMySQL<Sensor> daoSensor;
		DaoMySQL<Caracteristica> importador_caracteristica;
		Apagable apagable;

		DaoMySQL<ClienteResidencial> importadorClienteResidencial = new DaoMySQL<ClienteResidencial>();
		importadorClienteResidencial.init();
		importadorClienteResidencial.setTipo(ClienteResidencial.class);
		ClienteResidencial cliente = importadorClienteResidencial.buscarPorAlias("kodar");

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
		disp.setCliente(cliente);

		EstadoDispositivo prendidoHaceTresMeses = new Prendido();
		prendidoHaceTresMeses.setFechaInicio(DateTime.now().minusMonths(3));
		prendidoHaceTresMeses.setFechaFin(DateTime.now().minusMonths(2));
		disp.agregarEstadoAHistorial(prendidoHaceTresMeses);

		EstadoDispositivo prendidoHace24Dias = new Prendido();
		prendidoHace24Dias.setFechaInicio(DateTime.now().minusDays(24));
		prendidoHace24Dias.setFechaFin(DateTime.now().minusDays(23));
		disp.agregarEstadoAHistorial(prendidoHace24Dias);

		disp.apagar();
		regla = new Regla();
		unSensor = new Sensor();
		igualA30 = new Igualdad();
		prender = new Prender();

		unSensor.setTopic("intensidadLuz");

		igualA30.setValorComparacion(30);

		regla.setNombre("prender luz");
		regla.agregarActuador(prender);
		regla.agregarUnCriterio(igualA30, unSensor);

		disp.agregarRegla(regla);

		dao.agregar(regla);

		cliente.agregarDispositivo(disp);
		importadorClienteResidencial.modificar(cliente);
	}

	private void persistirDispositivo() {
		DaoMySQL<ClienteResidencial> importadorClienteResidencial = new DaoMySQL<ClienteResidencial>();
		importadorClienteResidencial.init();
		importadorClienteResidencial.setTipo(ClienteResidencial.class);
		ClienteResidencial cliente = importadorClienteResidencial.buscarPorAlias("kodar");

		/////////// Seteando dispositivo a persistir//////////
		DaoMySQL<Caracteristica> importador = new DaoMySQL<Caracteristica>();
		importador.init();
		importador.setTipo(Caracteristica.class);
		Caracteristica caracteristica = importador.buscarPorNombre("LED 24\"");

		Apagabilidad apagabilidad = new Apagable();

		Inteligente dispositivo = new Inteligente();
		dispositivo.setConsumoEnModoAutomatico(true);
		dispositivo.setCaracteristica(caracteristica);
		dispositivo.setApagabilidad(apagabilidad);
		dispositivo.setNombre("tv");
		dispositivo.setIntensidad(15);
		dispositivo.setCliente(cliente);
		dispositivo.apagar();

		/////// Estados del dispositivo///////////////

		EstadoDispositivo prendidoHaceTresMeses = new Prendido();
		prendidoHaceTresMeses.setFechaInicio(DateTime.now().minusMonths(3));
		prendidoHaceTresMeses.setFechaFin(DateTime.now().minusMonths(2));
		dispositivo.agregarEstadoAHistorial(prendidoHaceTresMeses);

		EstadoDispositivo prendidoHace24Dias = new Prendido();
		prendidoHace24Dias.setFechaInicio(DateTime.now().minusDays(24));
		prendidoHace24Dias.setFechaFin(DateTime.now().minusDays(23));
		dispositivo.agregarEstadoAHistorial(prendidoHace24Dias);

		EstadoDispositivo apagado = new Apagado();
		apagado.setFechaInicio(DateTime.now().minusDays(22));
		apagado.setFechaFin(DateTime.now().minusDays(2));
		dispositivo.agregarEstadoAHistorial(apagado);

		EstadoDispositivo prendidoHaceUnDia = new Prendido();
		prendidoHaceUnDia.setFechaInicio(DateTime.now().minusDays(1));
		prendidoHaceUnDia.setFechaFin(DateTime.now().minusHours(10));
		dispositivo.agregarEstadoAHistorial(prendidoHaceUnDia);

		///////////////// Persistiendo el dispositivo/////////
		DaoMySQL<Inteligente> importadorDisp = new DaoMySQL<Inteligente>();
		importadorDisp.init();
		importadorDisp.setTipo(Inteligente.class);
		importadorDisp.agregar(dispositivo);

		cliente.agregarDispositivo(dispositivo);
		importadorClienteResidencial.modificar(cliente);
	}

	private void crearCategorias() {
		creadorCategorias = new CreadorCategorias();
		creadorCategorias.init();

	}

	private void crearCaracteristicas() {
		creadorDeCaracteristicas = new CreadorCaracteristicas();
		creadorDeCaracteristicas.init();
	}

	private void crearParametros() {
		

		// maximo mediciones sensor
		Configurador.get().agregarParametro("cantidad mediciones sensor", 20, "");

		// parametros del optimizador
		Configurador.get().agregarParametro("intervalo optimizador", 10, "");
		Configurador.get().agregarParametro("tiempo optimizador", 1000, "");

		// parametros del reporte
		Configurador.get().agregarParametro("retardo reporte", 10, "");
		Configurador.get().agregarParametro("tiempo reporte", 1000, "");

		// parametros de la tabla de reporte
		Configurador.get().agregarParametro("retardo tabla", 10, "");
		Configurador.get().agregarParametro("tiempo tabla", 1000, "");

		// ip del broker
		Configurador.get().agregarParametro("ip broker", 0, "localhost");

	}

	private void persistirAdmin() throws NoSuchAlgorithmException {
		Administrador admin = new Administrador();

		admin.setAliasUsuario("admin");
		admin.setNombre("admin");
		admin.setApellido("admin");
		admin.setFechaAlta(DateTime.now());
		admin.setContrasenia("admin");

		DaoMySQL<Administrador> persistidor = new DaoMySQL<Administrador>();
		persistidor.init();
		persistidor.setTipo(Administrador.class);

		persistidor.agregar(admin);

	}

	private void persistirKevin() throws NoSuchAlgorithmException {
		DaoMySQL<CategoriaConsumo> importador2 = new DaoMySQL<CategoriaConsumo>();
		importador2.init();
		importador2.setTipo(CategoriaConsumo.class);
		CategoriaConsumo cat1 = importador2.buscar(1);

		Transformador trans = new Transformador();

		Coordenada coordenada1 = new Coordenada();
		Coordenada coordenada2 = new Coordenada();

		coordenada1.setLatitud(3.3);
		coordenada1.setLongitud(4.4);

		coordenada2.setLatitud(3.3);
		coordenada2.setLongitud(4.4);

		List<Coordenada> coordenadas1 = new ArrayList<Coordenada>();
		List<Coordenada> coordenadas2 = new ArrayList<Coordenada>();

		coordenadas1.add(coordenada1);
		coordenadas2.add(coordenada2);

		trans.setCoordenadas(coordenadas2);

		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		ClienteResidencial cliente = new ClienteResidencial();
		cliente.setConsumoEnAutomatico(true);
		cliente.setAliasUsuario("kodar");
		cliente.setApellido("odar");
		cliente.setDomicilio("cucha cucha 2137");
		cliente.setNombre("kevin");
		cliente.setNumeroDocumento(34325);
		cliente.setTelefono(45883110);
		cliente.setPuntos(15);
		cliente.setTipoDocumento("dni");
		cliente.setContrasenia("kevin123");
		cliente.setCategoria(cat1);
		cliente.setCoordenadas(coordenadas1);
		cliente.setFechaAltaServicio(DateTime.now().minusMonths(8));

		importador.init();
		importador.setTipo(ClienteResidencial.class);
		importador.agregar(cliente);
	}

	private void persistirDispStandardEnClientekodar() {
		DaoMySQL<ClienteResidencial> importadorClienteResidencial = new DaoMySQL<ClienteResidencial>();
		importadorClienteResidencial.init();
		importadorClienteResidencial.setTipo(ClienteResidencial.class);
		ClienteResidencial cliente = importadorClienteResidencial.buscarPorAlias("kodar");

		DaoMySQL<Standard> importadorDispositivos = new DaoMySQL<Standard>();
		importadorDispositivos.init();
		importadorDispositivos.setTipo(Standard.class);
		Standard stan = new Standard();
		Apagable apagabilidad = new Apagable();
		stan.setApagabilidad(apagabilidad);
		stan.setHorasUsoDiario(15.0);
		stan.setCliente(cliente);
		DaoMySQL<Caracteristica> importadorCaracteristicas = new DaoMySQL<Caracteristica>();
		importadorCaracteristicas.init();
		importadorCaracteristicas.setTipo(Caracteristica.class);
		Caracteristica carac = importadorCaracteristicas.buscarPorId(3);

		stan.setCaracteristica(carac);
		stan.setConsumoPorHora(46.0);
		stan.setNombre("stan");
		importadorDispositivos.agregar(stan);
	}

	private void persistirMedicionesConNuevaRegla() {
		Sensor sensor = new Sensor();
		sensor.setTopic("temperatura");
		sensor.agregarMedicion(45);
		sensor.agregarMedicion(95);
		sensor.agregarMedicion(25);
		sensor.agregarMedicion(15);

		Inteligente disp = new Inteligente();

		Mayor mayorA30 = new Mayor();
		; // Criterio
		mayorA30.setValorComparacion(30);
		Apagar apagar = new Apagar(); // Actuador

		DaoMySQL<Inteligente> daoIntel;
		daoIntel = new DaoMySQL<Inteligente>();
		daoIntel.init();
		daoIntel.setTipo(Inteligente.class);
		disp = daoIntel.buscarPorId(1);

		Regla regla = new Regla();
		regla.setNombre("apagar tv");
		regla.agregarActuador(apagar);
		regla.agregarUnCriterio(mayorA30, sensor);

		disp.agregarRegla(regla);

		daoIntel.modificar(disp);
	}

	private void persistirMasMedicionesConNuevaRegla() {
		Sensor sensor = new Sensor();
		sensor.agregarMedicion(325);
		sensor.agregarMedicion(2315);
		sensor.agregarMedicion(3125);
		sensor.agregarMedicion(125);
		sensor.setTopic("humedad");

		Inteligente disp = new Inteligente();

		Menor menorA30 = new Menor();
		; // Criterio
		menorA30.setValorComparacion(30);
		Prender prender = new Prender(); // Actuador

		DaoMySQL<Inteligente> daoIntel;
		daoIntel = new DaoMySQL<Inteligente>();
		daoIntel.init();
		daoIntel.setTipo(Inteligente.class);
		disp = daoIntel.buscarPorId(1);

		Regla regla = new Regla();
		regla.setNombre("prender tv");
		regla.agregarActuador(prender);
		regla.agregarUnCriterio(menorA30, sensor);

		disp.agregarRegla(regla);

		daoIntel.modificar(disp);
	}

	private void persistirReglaSubscriptaA2Sensores() {
		Sensor sensor = new Sensor();
		sensor.setTopic("otraIntensidad");
		sensor.agregarMedicion(34);
		sensor.agregarMedicion(1234);
		sensor.agregarMedicion(3325);
		sensor.agregarMedicion(13);

		Inteligente disp = new Inteligente();

		Igualdad igualA25 = new Igualdad();
		; // Criterio
		igualA25.setValorComparacion(25);
		PasarAModoAhorroEnergia pasarAModoAhorroEnergia = new PasarAModoAhorroEnergia(); // Actuador

		DaoMySQL<Inteligente> daoIntel;
		daoIntel = new DaoMySQL<Inteligente>();
		daoIntel.init();
		daoIntel.setTipo(Inteligente.class);
		disp = daoIntel.buscarPorId(2);

		Regla regla = new Regla();
		regla.setNombre("pasar la lampara A Modo De Ahorro Energia");
		regla.agregarActuador(pasarAModoAhorroEnergia);
		regla.agregarUnCriterio(igualA25, sensor);

		// Segundo sensor
		Sensor otroSensor = new Sensor();
		otroSensor.agregarMedicion(24);
		otroSensor.agregarMedicion(225);
		otroSensor.agregarMedicion(23);
		otroSensor.setTopic("algunaIntensidad");

		regla.agregarUnCriterio(igualA25, otroSensor);
		disp.agregarRegla(regla);

		daoIntel.modificar(disp);
	}

}
