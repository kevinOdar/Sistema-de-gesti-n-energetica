package testsDummies;

import java.security.NoSuchAlgorithmException;

import models.entities.dominio.ClienteResidencial;

public class Kevin {
	private static ClienteResidencial cliente = null;
  
	private Kevin() {
	}

	public static ClienteResidencial getKevin() throws NoSuchAlgorithmException {
		if (cliente != null) {
			return cliente;
		} else {
			cliente = new ClienteResidencial();
			cliente.setConsumoEnAutomatico(true);
			cliente.setContrasenia("1234");
			cliente.setAliasUsuario("kodar");
			cliente.setApellido("odar");
			cliente.setDomicilio("cucha cucha 2137");
			cliente.setNombre("kevin");
			cliente.setNumeroDocumento(34325);
			cliente.setTelefono(45883110);
			cliente.setPuntos(15);
			cliente.setTipoDocumento("dni");
			
			return cliente;
		}
	}

	public static ClienteResidencial getKevin2() throws NoSuchAlgorithmException {
		
			cliente = new ClienteResidencial();
			cliente.setConsumoEnAutomatico(true);
			cliente.setContrasenia("5678");
			cliente.setAliasUsuario("java");
			cliente.setApellido("gomez");
			cliente.setDomicilio("cachimayo 2137");
			cliente.setNombre("carlitos");
			cliente.setNumeroDocumento(35126);
			cliente.setTelefono(42465786);
			cliente.setPuntos(15);
			cliente.setTipoDocumento("dni");
			
			return cliente;
		
	}
	
}
