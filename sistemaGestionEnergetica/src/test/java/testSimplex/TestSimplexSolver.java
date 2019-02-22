package testSimplex;
import org.junit.*;

import models.entities.optimizador.simplex.SimplexFacade;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class TestSimplexSolver {

	@Test
	public void testSistemaCompatibleDeterminado() {
		/**
			En este test se minimizar� el siguiente modelo de programaci�n lineal:
			- Funci�n econ�mica:
				f(x0,x1, x2) = x2+ x1+ x0
				En donde:
					- x0 -> Es un televisor LCD de 40� cuyo consumo kWh es 0.18
					- x1 -> Es un lavarropas Autom�tico de 5 kg con calentamiento de agua cuyo consumo kwH es 0.875
					- x2 -> Es un ventilador de techo cuyo consumo kWh es 0.06
			- Enfoque (GoalType):
				Maximizaci�n
			- Restricciones:
				1) 0.06x2 + 0.875x1 + 0.18x0 <= 440640, cambi� 612 
				por 440640, porque es 612x720(que son las horas en 
				total que tiene un mes de 30 d�as)
				2) x0 >= 90
				3) x0 <= 360
				4) x1 >= 6
				5) x1 <= 30
				6) x2 >= 120
				7) x2 <= 360
				8) Todas las variables son no negativas (NonNegativeConstraint(true))
			NOTA: 
				- >= : Relationship.GEQ
				- <= : Relationship.LEQ
				- =  : Relationship.EQ
		*/
		SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
		simplexFacade.crearFuncionEconomica(1,1,1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 440640, 	0.06, 	0.875, 	0.18);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 90, 		0, 		0, 		1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 360, 	0, 		0, 		1);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 6, 		0, 		1, 		0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 30, 		0, 		1, 		0);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 120, 	1, 		0, 		0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 360, 	1, 		0, 		0);
		PointValuePair solucion = simplexFacade.resolver();
		Assert.assertEquals(750, solucion.getValue(), 0.01);
		Assert.assertEquals(360, solucion.getPoint()[0], 0.01); // <--- X2
		Assert.assertEquals(30, solucion.getPoint()[1], 0.01); // <--- X1
		Assert.assertEquals(360, solucion.getPoint()[2], 0.01); // <--- X0
		System.out.println("El ventilador de techo puede ser utilizado hasta " +  solucion.getPoint()[0] + " horas durante un mes");
		System.out.println("El lavarropas Autom�tico de 5 kg con calentamiento de agua puede ser utilizado hasta " +  solucion.getPoint()[1] + " horas durante un mes");
		System.out.println("El televisor LCD de 40� puede ser utilizado hasta " +  solucion.getPoint()[2] + " horas durante un mes");
		System.out.println("Para que sea un hogar eficiente, todos los dispositivos(juntos) tienen que consumir en un mes hasta " + solucion.getValue() + " horas.");
	}

	
	@Test
	public void testSistemaIncompatible() {
		/**
			En este test se minimizar� el siguiente modelo de programaci�n lineal:
			- Funci�n econ�mica:
				f(x0,x1, x2) = x2+ x1+ x0
			- Enfoque (GoalType):
				Maximizaci�n
			- Restricciones (constraints)
				1) 180x2 + 0.875x1 + 0.18x0 <= 480
				2) x0 >= 90
				3) x0 <= 360
				4) x1 >= 6
				5) x1 <= 30
				6) x2 >= 120
				7) x2 <= 360
				8) Todas las variables son no negativas (NonNegativeConstraint(true))
			NOTA: 
				- >= : Relationship.GEQ
				- <= : Relationship.LEQ
				- =  : Relationship.EQ
		 */
		SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
		simplexFacade.crearFuncionEconomica(1,1,1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 480, 	180, 	0.875, 	0.18);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 90, 		0, 		0, 		1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 360, 	0, 		0, 		1);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 6, 		0, 		1, 		0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 30, 		0, 		1, 		0);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 120, 	1, 		0, 		0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 360, 	1, 		0, 		0);
		try{
			@SuppressWarnings("unused")
			PointValuePair solucion = simplexFacade.resolver();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testSolucionEnInfinito() {
		/**
			En este test se minimizar� el siguiente modelo de programaci�n lineal:
			- Funci�n econ�mica (LinearObjectiveFunction): 
				f(x0,x1) = 5x1+ 2x0
			- Enfoque (GoalType):
				Maximizaci�n
			- Restricciones (constraints)
				1) x0 + x1 >= 4
				2) x0 >= 2
				8) Todas las variables son no negativas (NonNegativeConstraint(true))
			NOTA: 
				- >= : Relationship.GEQ
				- <= : Relationship.LEQ
				- =  : Relationship.EQ
		 */
		SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
		simplexFacade.crearFuncionEconomica(5,2);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 4,	1, 	1);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 2, 	0, 	1);
		try{
			@SuppressWarnings("unused")
			PointValuePair solucion = simplexFacade.resolver();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//ejemplo para utilizar en el testProcesoSimplex
	@Test
	public void testSolucionNoFeasible() 
	{
		SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
		simplexFacade.crearFuncionEconomica(1,1);	
		simplexFacade.agregarRestriccion(Relationship.LEQ, 440640,  0.875, 	0.18);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 4,	1, 	0);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 0,	1, 	0);
		simplexFacade.agregarRestriccion(Relationship.GEQ, 2, 	0, 	1);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 0,	0, 	1);
		try{
			@SuppressWarnings("unused")
			PointValuePair solucion = simplexFacade.resolver();
		}
		catch (NoFeasibleSolutionException e) {
			System.out.println(e.getMessage());
		}
	}

}
