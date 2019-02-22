package models.entities.optimizador.simplex;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import models.entities.dispositivo.*;
import models.entities.optimizador.AlgoritmoOptimizadorConsumo;

public class AlgoritmoOptimizadorConsumoSimplex implements AlgoritmoOptimizadorConsumo {
	private SimplexFacade optimizador;
	private GoalType enfoque;
	private List<Dispositivo> dispositivosAOptimizar;
	private List<Caracteristica> caracteristicas;

	private int ordenDispositivo = 0;

	public AlgoritmoOptimizadorConsumoSimplex() {
	}

	public PointValuePair calcularConsumoOptimo() {
		optimizador = new SimplexFacade(enfoque, true);
		this.completarFuncionEconomica();
		this.completarRestriccionMaximoConsumoMensual();
		this.completarLasRestriccionesRestantes();
		PointValuePair solucion = optimizador.resolver();
		return solucion;
	}

	public void completarFuncionEconomica() {
		double variables[] = this.generarUnArrayDeDoublesConElMismoNumero(this.dispositivosAOptimizar.size(), 1.0);
		this.optimizador.crearFuncionEconomica(variables);
	}

	public void completarRestriccionMaximoConsumoMensual() {
		caracteristicas = dispositivosAOptimizar.stream().map(dispositivo -> dispositivo.getCaracteristica())
				.collect(Collectors.toList());
		double coeficientes[] = caracteristicas.stream()
				.mapToDouble(caracteristica -> caracteristica.getCoeficienteDeConsumo()).toArray();
		optimizador.agregarRestriccion(Relationship.LEQ, 440640, coeficientes); 
	}

	public void completarLasRestriccionesRestantes() {
		ordenDispositivo = 0;
		caracteristicas.stream().forEach(caracteristica -> {
			double arrayDoubles[] = this.generarUnArrayDeDoublesConElMismoNumero(caracteristicas.size(), 0.0);
			arrayDoubles[ordenDispositivo] = 1.0;
			optimizador.agregarRestriccion(Relationship.GEQ, caracteristica.getRestriccionMayorIgual(), arrayDoubles);
			optimizador.agregarRestriccion(Relationship.LEQ, caracteristica.getRestriccionMenorIgual(), arrayDoubles);
			ordenDispositivo++;
		});
	}

	public double[] generarUnArrayDeDoublesConElMismoNumero(int longitud, double numeroARepetir) {
		double array[] = new double[longitud];
		for (int i = 0; i < longitud; i++) {
			array[i] = numeroARepetir;
		}
		return array;
	}

	public void setEnfoque(GoalType enfoque) {
		this.enfoque = enfoque;
	}

	public void setDispositivosAOptimizar(List<Dispositivo> dispositivosAOptimizar) {
		this.dispositivosAOptimizar = dispositivosAOptimizar;
	}

}
