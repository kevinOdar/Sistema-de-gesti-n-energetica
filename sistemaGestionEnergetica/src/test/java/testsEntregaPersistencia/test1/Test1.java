package testsEntregaPersistencia.test1;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import models.dao.hibernate.DaoMySQL;
import models.entities.dominio.CategoriaConsumo;
import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;
import testsDummies.Kevin;

public class Test1 {
	/*Crear 1 usuario nuevo. Persistirlo. Recuperarlo, modificar la geolocalización y
	grabarlo. Recuperarlo y evaluar que el cambio se haya realizado.*/
	
	@Test
	public void testExisteCatUsuarioNuevo() throws NoSuchAlgorithmException {
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

		List<Coordenada> coordenadas1 =new ArrayList<Coordenada>();
		List<Coordenada> coordenadas2 =new ArrayList<Coordenada>();

		coordenadas1.add(coordenada1);
		coordenadas2.add(coordenada2);

		
		trans.setCoordenadas(coordenadas2);
		
		DaoMySQL<ClienteResidencial> importador = new DaoMySQL<ClienteResidencial>();
		ClienteResidencial cliente = Kevin.getKevin();
		cliente.setContrasenia("kevin123");
		cliente.setCategoria(cat1);
		cliente.setCoordenadas(coordenadas1);
		cliente.setFechaAltaServicio(DateTime.now().minusMonths(8));
		
		importador.init();
		importador.setTipo(ClienteResidencial.class);
		importador.agregar(cliente);
		
		ClienteResidencial clienteBuscado = importador.buscarPorNombre("kevin");
		assertEquals("kevin", clienteBuscado.getNombre());
	}
}
