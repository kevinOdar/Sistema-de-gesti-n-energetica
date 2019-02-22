package testsDominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import models.dao.hibernate.DaoMySQL;
import models.encriptador.Encriptador;
import models.entities.dispositivo.Dispositivo;
import models.entities.dispositivo.Inteligente;
import models.entities.dominio.Administrador;
import models.entities.dominio.ClienteResidencial;

public class TestEncriptar {

	Administrador admin;
	
	@Before
	public void init() throws NoSuchAlgorithmException {

		
	}
	
	
	public void testEncriptar() throws NoSuchAlgorithmException {

		String passEsperada = Encriptador.get().encriptar("admin", admin.getSalt());
		
		
		System.out.format("Contraseña del admin: %s\n Contraseña esperada: %s\n", admin.getContrasenia(), passEsperada);
		
		assertEquals(passEsperada, admin.getContrasenia());
	}

	

	
	public void testRecuperarAdminYClave() {
		DaoMySQL<Administrador> importador = new DaoMySQL<Administrador>();
		importador.init();
		importador.setTipo(Administrador.class);
		
		Administrador admin = importador.buscarPorAlias("admin");
	
		//String passEsperada = Encriptador.get().encriptar("admin", admin.getSalt());
		
		
		//System.out.format("Contraseña del admin: %s\nContraseña esperada: %s\n", admin.getContrasenia(), passEsperada);
		
		//assertEquals(passEsperada, admin.getContrasenia());
		assertTrue(Encriptador.get().contraseniaCorrecta("admin", admin.getContrasenia(), admin.getSalt()));
	}
	
	@Test
	public void recuperarListaInteligentes() {
		DaoMySQL<Inteligente> daoInteligente = new DaoMySQL<Inteligente>();
		daoInteligente.init();
		daoInteligente.setTipo(Inteligente.class);
		
		List<Inteligente> todos = daoInteligente.dameTodos();
		
		DaoMySQL<ClienteResidencial> daoCliente = new DaoMySQL<ClienteResidencial>();
		daoCliente.init();
		daoCliente.setTipo(ClienteResidencial.class);

		ClienteResidencial cliente = daoCliente.buscarPorAlias("kodar");
		
		List<Dispositivo> disps = cliente.getDispositivos();
		
		List<Dispositivo> inteligentes = cliente.getDispositivosInteligentes();
		
		System.out.format("\n\ntamanio disps: %d\n\n", disps.size());

		System.out.format("\n\ntamanio todos: %d\n\n", todos.size());

		System.out.format("\n\ntamanio: %d\n\n", inteligentes.size());
//		for(Dispositivo inteligente : inteligentes) {
//			System.out.format("regla: %s", ((Inteligente) inteligente).getReglas().get(0).getNombre());
//		}
	}
}
