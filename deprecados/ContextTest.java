package testsConexion;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import models.dao.hibernate.ModelHelper;

public class ContextTest {
	private ModelHelper model;
	
	@Before
	public void init() {
		this.model = new ModelHelper();
	}
	
	@Test
	public void contextUp() {
		assertNotNull(this.model.entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		this.model.withTransaction(() -> {});
	}
}