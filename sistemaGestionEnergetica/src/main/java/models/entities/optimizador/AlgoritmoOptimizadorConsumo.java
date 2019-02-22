package models.entities.optimizador;

import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import models.entities.dispositivo.*;

public interface AlgoritmoOptimizadorConsumo 
{
	public void setDispositivosAOptimizar(List<Dispositivo> dispositivosAOptimizar);
	public PointValuePair calcularConsumoOptimo();
	public void setEnfoque(GoalType enfoque);
}
