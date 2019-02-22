package testsEntregaPersistencia.test5;

import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import org.joda.time.DateTime;

import junit.framework.Assert;
import models.dao.hibernate.DaoMySQL;
import models.entities.dispositivo.Caracteristica;
import models.entities.dispositivo.Inteligente;

import models.entities.dominio.ClienteResidencial;
import models.entities.transformadores.Transformador;
import testsDummies.Inteligente2;
import testsDummies.Kevin;
import testsDummies.Transformador1;


/*Caso de prueba 5:
Dado un hogar y un período, mostrar por consola (interfaz de comandos) el
consumo total. Dado un dispositivo y un período, mostrar por consola su
consumo promedio. Dado un transformador y un período, mostrar su consumo
promedio. Recuperar un dispositivo asociado a un hogar de ese transformador e
incrementar un 1000 % el consumo para ese período. Persistir el dispositivo.
Nuevamente mostrar el consumo para ese transformador.*/


public class Test5 {
	
		private static ClienteResidencial cliente = null;
		private static Inteligente dispositivo = null;
	    private static Inteligente dispositivo2 = null;
		private static Transformador transformador = null;
	
		@Before
		public void initParametros() throws NoSuchAlgorithmException {
			cliente = Kevin.getKevin2();
			dispositivo = Inteligente2.getDispositivo1();
			dispositivo2 = Inteligente2.getDispositivo2();
			cliente.agregarDispositivo(dispositivo);
			cliente.agregarDispositivo(dispositivo2);
			transformador = Transformador1.getTransformador2();
			transformador.agregarCliente(cliente);
		}
								
		//Consumo en un Periodo de un Hogar

		@Test
		public void test01consumoDeUnHogarEnUnPeriodo() {
			
			double esperado = 0.56;
			double consumoDeUnHogarEnElPeriodo = cliente.cuantoConsumoEnUnPeriodo(DateTime.now().minusHours(8), DateTime.now().minusHours(2));
			Assert.assertEquals(esperado, consumoDeUnHogarEnElPeriodo);
			
			System.out.println("\n \n ------ Comienza test1 --------- \n \n");
			System.out.println("El Consumo del Hogar en el periodo ingresado es de:" + "\n" + consumoDeUnHogarEnElPeriodo);
		    //Resultado Correcto = 0.56
		}
	
		//Consumo Promedio en un Periodo de un Dispositivo
		@Test
		public void test02consumoPromedioDeUnDispositivoEnUnPeriodo() {
			 
			double esperado = 3.1799999999999997;
			double consumoPromedioDeUnDispositivo = dispositivo.getConsumoDiario();
            Assert.assertEquals(esperado, consumoPromedioDeUnDispositivo); 
			
			System.out.println("\n \n ------ Comienza test2 --------- \n \n");
			System.out.println("El Consumo Promedio del Dispositivo en el periodo ingresado es de:" + "\n" + consumoPromedioDeUnDispositivo);
            //Resultado Correcto = 3.1799999999999997
		}
		
		
		//Consumo Promedio en un Periodo de un Transformador
		@Test
		public void test03consumoPromedioDeUnTransformador() {
			
			double esperado = 7.553333333333334;
			double consumoPromedioDeUnTransformador = transformador.suministroPromedio();
			Assert.assertEquals(esperado, consumoPromedioDeUnTransformador);
	        
			System.out.println("\n \n ------ Comienza test3 --------- \n \n");
			System.out.println("El Consumo Promedio del Transformador en el periodo ingresado es de:" + "\n" + consumoPromedioDeUnTransformador);
		    //Resultado correcto = 7.553333333333334
		}

	
		
		//Consumo de Un Transformador, luego de incrementar 100% el consumo de un dispositivo
		@Test
		public void test04consumoPromedioDeUnTransformadorEnUnPeriodo() {
			
			    // Incremento consumo de dispositivo un 1000%
			DaoMySQL<Caracteristica> importador_caracteristica;
			importador_caracteristica = new DaoMySQL<Caracteristica>();
			importador_caracteristica.init();
			importador_caracteristica.setTipo(Caracteristica.class);
			importador_caracteristica.buscar(1).setCoeficienteDeConsumo(dispositivo.getCoeficienteDeConsumo() * 10);
			dispositivo.setCaracteristica(importador_caracteristica.buscar(1));
			
			DaoMySQL<ClienteResidencial> importador_clienteResidencial;
			importador_clienteResidencial = new DaoMySQL<ClienteResidencial>();
			importador_clienteResidencial.init();
			importador_clienteResidencial.setTipo(ClienteResidencial.class);
			dispositivo.setCliente(importador_clienteResidencial.buscar(1));
				
			    //Persisto dispositivo en DaoMySQL
			DaoMySQL<Inteligente> importador = new DaoMySQL<Inteligente>();
		    importador.init();
			importador.setTipo(Inteligente.class);
			importador.agregar(dispositivo);
		   	   
	           //Muestro el nuevo consumo Promedio del Transformador
			double esperado = 36.173333333333333;
			double nuevoConsumoPromedioDeUnTransformador = transformador.suministroPromedio();
			Assert.assertEquals(esperado, nuevoConsumoPromedioDeUnTransformador);
			
			System.out.println("\n \n ------ Comienza test4 --------- \n \n");
			System.out.println("El Nuevo Consumo Promedio del Transformador en el periodo ingresado es de:" + "\n" + nuevoConsumoPromedioDeUnTransformador);
	        //Resultado correcto = 36.173333333333333
		}	
 
}			
			
	      


				


