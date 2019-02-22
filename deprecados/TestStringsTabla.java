package testsOptimizador;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

import dispositivo.*;
import dispositivo.tipos.SubTipoDispositivo;
import dispositivo.tipos.TipoDispositivo;
import dominio.ClienteResidencial;
import optimizador.AlgoritmoOptimizadorConsumoSimplex;
import optimizador.TablaOptimizadoraBuilder;
import optimizador.tipos.TipoRestriccion;
import simplex.SimplexProblem;
import simplex.SimplexSolver;



public class TestStringsTabla 
{
	ClienteResidencial cliente;
	Dispositivo dispositivo;
	Dispositivo otroDispositivo;
	TablaOptimizadoraBuilder tablaBuilder;
	int cantDisp;
	int cantRest;
	int cantidadColumnas;
	
	@Before
	public void initParametros() 
	{
		this.tablaBuilder = new TablaOptimizadoraBuilder();
		cliente = new ClienteResidencial();
		dispositivo = new Dispositivo();
		otroDispositivo = new Dispositivo();
		cliente.agregarDispositivo(dispositivo);
		cliente.agregarDispositivo(otroDispositivo);
		this.cantDisp = cliente.cuantosDispositivosTengo();//deberia tener 2
		this.cantRest = (cantDisp * 3 ) + 1;
		this.cantidadColumnas = cantDisp + 2;
		
 	}
	
	@Test
	public void testListaDoubleAString() {
		
		List<Double> lista = Arrays.asList(1.12,2.33,3.44);

		String listaComoString = tablaBuilder.convertirListaDoubleAString(lista);
		
		//System.out.println(listaComoString);
		
		assertEquals("1.12;2.33;3.44", listaComoString);
	}
	
	@Test
	public void testPrimeraFila() 
	{
		
		String filaPrimera = tablaBuilder.generarPrimeraFila(cantDisp,cantRest,cantidadColumnas);
		
		//System.out.println(filaPrimera);
		
		assertEquals("2;7;;", filaPrimera);
	}
	
	@Test
	public void testSegundaFila() 
	{
		String filaSegunda = tablaBuilder.generarSegundaFila(cantDisp);
		
		//System.out.println(filaSegunda);
		
		assertEquals("max;1;1;0", filaSegunda);
	}
	
	@Test
	public void testTerceraFila() 
	{
		String filaTercera = tablaBuilder.generarTerceraFila(cantidadColumnas);
		
		//System.out.println(filaTercera);
		
		assertEquals("true;true;;", filaTercera);
	}
	
	@Test
	public void testFilaCuarta() {
		
		List<Double> lista = Arrays.asList(1.12,2.33,3.44);

		String filaCuarta = tablaBuilder.generarCuartaFila(lista);
		
		//System.out.println(filaCuarta);
		
		assertEquals("<=;1.12;2.33;3.44;612", filaCuarta);
	}
	
	
	@Test
	public void testGenerandoUnaRestriccion() 
	{
		TipoRestriccion tipoRestriccion = TipoRestriccion.MayorIgual;
		String restriccion = tablaBuilder.generarUnaRestriccion(4, 1, tipoRestriccion, 0);
		
		//System.out.println(restriccion);
		
		assertEquals(">=;1;0;0", restriccion);
	}
	
	@Test
	public void testGenerandoOtraRestriccion() 
	{
		TipoRestriccion tipoRestriccion = TipoRestriccion.MenorIgual;
		String restriccion = tablaBuilder.generarUnaRestriccion(5, 2, tipoRestriccion, 90);
		
		//System.out.println(restriccion);
		
		assertEquals("<=;0;1;0;90", restriccion);
	}
	
	@Test
	public void generarFilaRestriccion() throws FileNotFoundException 
	{
		DispositivoBuilder dispositivoBuilder = new DispositivoBuilder("src/test/resources/restricciones.json", "src/test/resources/coeficientes.json");
		//TipoRestriccion tipoRestriccion = TipoRestriccion.MenorIgual;
		dispositivoBuilder.activar();//revisar
		ClienteResidencial unCliente = new ClienteResidencial();
		Apagabilidad tipo = Apagabilidad.AireAcondicionado;
		SubTipoDispositivo subtipo = SubTipoDispositivo.AC2200;	
		Dispositivo dispositivo2= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo2);
		
		String listaDeRestriccion = tablaBuilder.generarFilasRestricciones(unCliente);
		//System.out.println(listaDeRestriccion);
		assertEquals(">=;1;0\n>=;1;90\n<=;1;360\n", listaDeRestriccion); 
		
	}	
	
	@Test
	public void generarFilasRestricciones() throws FileNotFoundException 
	{
		DispositivoBuilder dispositivoBuilder = new DispositivoBuilder("src/test/resources/restricciones.json", "src/test/resources/coeficientes.json");
		dispositivoBuilder.activar();//revisar
		ClienteResidencial unCliente = new ClienteResidencial();
		//creando y agregando un dispositivo
		Apagabilidad tipo = Apagabilidad.AireAcondicionado;
		SubTipoDispositivo subtipo = SubTipoDispositivo.AC2200;	
		Dispositivo dispositivo2= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo2);
		
		
		//creando y agregando otro dispositivo
		tipo = Apagabilidad.Lavarropas;
		subtipo = SubTipoDispositivo.LavaAuto5kgCalAgua;	
		Dispositivo dispositivo3= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo3);
		
		String listaDeRestriccion = tablaBuilder.generarFilasRestricciones(unCliente);
		//System.out.println(listaDeRestriccion);
		assertEquals(">=;1;0;0\n>=;1;0;90\n<=;1;0;360\n>=;0;1;0\n>=;0;1;6\n<=;0;1;30\n", listaDeRestriccion);	
	}	
	
	
	@Test
	public void testTablaCompleta() throws FileNotFoundException 
	{
		DispositivoBuilder dispositivoBuilder = new DispositivoBuilder("src/test/resources/restricciones.json", "src/test/resources/coeficientes.json");
		dispositivoBuilder.activar();//revisar
		ClienteResidencial unCliente = new ClienteResidencial();
		//creando y agregando un dispositivo
		Apagabilidad tipo = Apagabilidad.AireAcondicionado;
		SubTipoDispositivo subtipo = SubTipoDispositivo.AC2200;	
		Dispositivo dispositivo2= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo2);
		
		
		//creando y agregando otro dispositivo
		tipo = Apagabilidad.Lavarropas;
		subtipo = SubTipoDispositivo.LavaAuto5kgCalAgua;	
		Dispositivo dispositivo3= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo3);
		
		String listaDeRestriccion = tablaBuilder.construirTabla(unCliente);
		System.out.println(listaDeRestriccion);
		//assertEquals(">=;1;0;0\n>=;1;0;90\n<=;1;0;360\n>=;0;1;0\n>=;0;1;6\n<=;0;1;30\n", listaDeRestriccion);
	}

	
	@Test
	public void testSimplex() throws FileNotFoundException
	{
		DispositivoBuilder dispositivoBuilder = new DispositivoBuilder("src/test/resources/restricciones.json", "src/test/resources/coeficientes.json");
		dispositivoBuilder.activar();//revisar
		ClienteResidencial unCliente = new ClienteResidencial();
		//creando y agregando un dispositivo
		Apagabilidad tipo = Apagabilidad.AireAcondicionado;
		SubTipoDispositivo subtipo = SubTipoDispositivo.AC2200;	
		Dispositivo dispositivo2= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo2);
		
		
		//creando y agregando otro dispositivo
		tipo = Apagabilidad.Lavarropas;
		subtipo = SubTipoDispositivo.LavaAuto5kgCalAgua;	
		Dispositivo dispositivo3= dispositivoBuilder.getDispositivoNuevo(tipo, subtipo, false);
		unCliente.agregarDispositivo(dispositivo3);
		
		SimplexProblem simplex = new SimplexProblem();
		String listaDeRestriccion = tablaBuilder.construirTabla(unCliente);
		simplex.parse(listaDeRestriccion);
//		SimplexSolver optimizador = new SimplexSolver();
//		//System.out.println(optimizador.optimizarConsumo(unCliente));
//		TablaOptimizadoraBuilder builderTabla = new TablaOptimizadoraBuilder();
//		SimplexProblem problemaSimplex = new SimplexProblem();
//		String tablaOptimizacion = builderTabla.construirTabla(unCliente);
//		problemaSimplex.parse(tablaOptimizacion);
//		//double[][] resultado = optimizador.solve(problemaSimplex);
//		System.out.println(optimizador.solve(problemaSimplex));
		
		
//		String[] s = simplex.recuperarDatosDe(listaDeRestriccion);
//		System.out.println(listaDeRestriccion);
//		for(int i=0; i<20; i++)
//		{
//			System.out.println(s[i]);
//		} 
		
		
		//assertEquals([],s);
	}
	
}
