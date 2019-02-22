package models.entities.dominio;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name="categoria_consumo")
public class CategoriaConsumo extends EntidadPersistente {
	
	@Column(name = "nombre", nullable = false, unique = true)
	private String nombre;
	
	@Column(name = "cargoFijo", nullable = false)
	private double cargoFijo;
	
	@Column(name = "cargoVariable", nullable = false)
	private double cargoVariable;
	
	@Column(name = "consumoMinimo", nullable = false)
	private double consumoMinimo;
	
	@Column(name = "consumoMaximo", nullable = false)
	private double consumoMaximo;

	@Override
	public String toString() {
		return "{ nombre:" + nombre + ", cargoFijo:" + cargoFijo + ", cargoVariable:" + cargoVariable + ", consumoMaximo:"
				+ consumoMaximo + ", consumoMinimo:" + consumoMinimo + " }";
	}

	public double getCargoFijo() {
		return cargoFijo;
	}

	public void setCargoFijo(double unCargoFijo) {
		this.cargoFijo = unCargoFijo;
	}

	public double getCargoVariable() {
		return cargoVariable;
	}

	public void setCargoVariable(double cargoVariable) {
		this.cargoVariable = cargoVariable;
	}

	public double getConsumoMaximo() {
		return consumoMaximo;
	}

	public void setConsumoMax(int consumoMax) {
		this.consumoMaximo = consumoMax;
	}

	public double getConsumoMinimo() {
		return consumoMinimo;
	}

	public void setConsumoMin(int consumoMin) {
		this.consumoMinimo = consumoMin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void finalize() throws Throwable {

	}

	public double cualEsElCostoDeEsteConsumo(double wattsDeConsumo){
		double costoConsumoTotal = cargoFijo + cargoVariable * wattsDeConsumo;
		return costoConsumoTotal;
	}
	
}//end Categoria
