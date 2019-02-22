package optimizador;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import dispositivo.*;
import optimizador.tipos.TipoRestriccion;

public class TablaOptimizadoraBuilder {

	// usar
	// http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/StringUtils.html#repeat(java.lang.String,%20int)

	// ejemplo
	// para dos dispositivos (es la tabla de la foto):
	// 2 ; 7 ; ;
	// max ; 1 ; 1 ; 0
	// true ; true ; ;
	// <= ; 1,013 ; 0,11 ; 612
	// >= ; 1 ; 0 ; 0
	// >= ; 1 ; 0 ; 90
	// <= ; 1 ; 0 ; 360
	// >= ; 0 ; 1 ; 0
	// >= ; 0 ; 1 ; 90
	// <= ; 0 ; 1 ; 360

	public String construirTabla(List<Dispositivo> unaLista, double consumoMaxTotal) {

		int cantDisp = unaLista.size();
		int cantRest = (cantDisp * 3) + 1;
		int cantidadColumnas = cantDisp + 2; // esto lo saco de las filas de restricciones, que son las que más columnas
												// tienen

		String primeraFila = this.generarPrimeraFila(cantDisp, cantRest, cantidadColumnas);
		String segundaFila = this.generarSegundaFila(cantDisp);
		String terceraFila = this.generarTerceraFila(cantidadColumnas);
		String cuartaFila = this.generarCuartaFila(getCoeficientes(unaLista), consumoMaxTotal);
		String filasRestricciones = this.generarFilasRestricciones(cantDisp, cantidadColumnas, unaLista);

		String tablaCompleta = primeraFila + "\n" + segundaFila + "\n" + terceraFila + "\n" + cuartaFila + "\n"
				+ filasRestricciones;

		return tablaCompleta;
	}

	private List<Double> getCoeficientes(List<Dispositivo> unaLista) {
		return unaLista.stream().map(unDispositivo -> unDispositivo.getCoeficienteDeConsumo()).collect(Collectors.toList());
	}

	public String generarPrimeraFila(int cantDisp, int cantRestr, int cantidadColumnas) {

		// en la primera fila van: cantidad dispositivos + ';' + cantidad restricciones
		// + tantos ';' como columnas menos 2 (en realidad hay columnas - 1 ';', pero ya
		// puse 1
		// los puntos y coma se usa StringUtils.repeat

		String cantidadDispositivos = Integer.toString(cantDisp);
		String cantidadRestricciones = Integer.toString(cantRestr);

		String puntosYComas = StringUtils.repeat(';', cantidadColumnas - 2);

		String primeraFila = cantidadDispositivos + ';' + cantidadRestricciones + puntosYComas;

		return primeraFila;
	}

	public String generarSegundaFila(int cantidadDispositivos) {

		// en la segunda fila va: max + ';' + tantos '1' como dispositivos + '0' (y los
		// puntos y comas)
		// se puede usar StringUtils.repeat pero el que toma 3 parámetros

		String segundaFila = "max" + ';' + StringUtils.repeat("1;", cantidadDispositivos) + "0";

		return segundaFila;
	}

	public String generarTerceraFila(int cantidadColumnas) {
		// aca va: true + true + tantos ';' como columnas menos 2

		String terceraFila = "true" + ";" + "true" + StringUtils.repeat(';', cantidadColumnas - 2);

		return terceraFila;
	}

	public String generarCuartaFila(List<Double> listaCoeficientes, double consumoMaxTotal) {
		// esta tiene la ecuación con los coeficientes, empieza con un <= y termina con
		// un 612,
		// que es la restriccion que nos pone el dominio, pag 2 anteúltima línea

		String coeficientesString = convertirListaDoubleAString(listaCoeficientes);

		String cuartaFila = "<=" + ';' + coeficientesString + ';' + consumoMaxTotal;

		return cuartaFila;
	}

	public String convertirListaDoubleAString(List<Double> unaLista) {
		return unaLista.stream().map(unDouble -> Double.toString(unDouble)).collect(Collectors.joining(";"));
	}

	public String generarFilasRestricciones(int cantDisp, int cantidadColumnas, List<Dispositivo> dispositivos) {
		// acá tenemos que buscar la forma de repetir la lógica de meter un uno en la
		// columna
		// que le corresponde al dispositivo y ceros en el resto, la primera columna es
		// un <= o un =>,
		// y la última columna se saca de las restricciones

		TipoRestriccion restriccionMayor = TipoRestriccion.MayorIgual;
		TipoRestriccion restriccionMenor = TipoRestriccion.MenorIgual;

		String restricciones = "";
		for (int i = 1; i <= cantDisp; i++)// i<=cantDisp porque hay un conj. de restricciones por cada disp que tenga
		{
			Dispositivo dispositivo = dispositivos.get(i - 1);
			restricciones += this.generarUnaRestriccion(cantidadColumnas, i, restriccionMayor, 0) + "\n";// >= ; 1 ; 0 ;
																											// 0 esta va
																											// siempre
			restricciones += this.generarUnaRestriccion(cantidadColumnas, i, restriccionMayor,
					dispositivo.getRestriccionMenor().intValue()) + "\n";
			restricciones += this.generarUnaRestriccion(cantidadColumnas, i, restriccionMenor,
					dispositivo.getRestriccionMayor().intValue()) + "\n";
		}
		return restricciones;
	}

	public String generarUnaRestriccion(int cantidadColumnas, int posicionDispositivo, TipoRestriccion tipoRestriccion,
			int UnValorRestriccion) {
		// posicionDispositivo del cual es la restriccion?
		// por ejemplo para hacer >= ; 1 ; 0 ; 0
		// generarUnaRestriccion(4, 1, Mayor, 0);
		String signo = "";
		String valorRestriccion = Integer.toString(UnValorRestriccion);
		String restriccionDispSegunPosicion = "";
		switch (tipoRestriccion) {
		case MayorIgual:
			signo = ">=;";
			break;

		case MenorIgual:
			signo = "<=;";
			break;
		default:
			break;
		}

		for (int i = 1; i <= cantidadColumnas - 2; i++) {// a la cantidadColumnas se le resta uno porque
															// de la primera columna se encarga el case
															// y se le resta otro mas porque de la ultima columna se
															// encarga luego
			if (posicionDispositivo == i)
				restriccionDispSegunPosicion += "1;";
			else
				restriccionDispSegunPosicion += "0;";
		}
		return signo + restriccionDispSegunPosicion + valorRestriccion;
	}

}
