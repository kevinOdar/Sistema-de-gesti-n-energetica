package models.entities.transformadores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.*;

import javax.persistence.*;

import models.entities.dominio.*;
import models.entities.zonaGeografica.ZonaGeografica;

@Entity
@Table(name="transformador")
@DiscriminatorValue("transformador")
public class Transformador extends Ubicable {

	@OneToMany(mappedBy = "transformador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ClienteResidencial> clientesConectados;
	
	@ManyToOne
	@JoinColumn(name = "zona_id", referencedColumnName = "id")
	private ZonaGeografica zona;

	@Column(name="nombre", length=512)
	private String nombre;
	
	public Transformador() {
		this.clientesConectados = new ArrayList<>();
	}

	public List<ClienteResidencial> getClientesConectados() {
		return clientesConectados;
	}

	public void setClientesConectados(List<ClienteResidencial> clientesConectados) {
		this.clientesConectados = clientesConectados;
	}

	public void agregarCliente(ClienteResidencial cliente) {
		this.clientesConectados.add(cliente);
	}
	
	public ZonaGeografica getZona() {
		return zona;
	}

	public void setZona(ZonaGeografica zona) {
		this.zona = zona;
	}


	public Double sumarConsumos(List<Double> listaConsumos) {

		Double suma = listaConsumos.stream().mapToDouble(nro -> nro.doubleValue()).sum();

		return suma;
	}
    
	public List<Double> getConsumos() {

		List<Double> listaConsumos = this.clientesConectados.stream().map(unCliente -> unCliente.cuantoConsumo())
				.collect(Collectors.toList());

		return listaConsumos;
	}
	
	public List<Double> getConsumosPorPeriodo(DateTime fechaInicio, DateTime fechaFinal){
		List<Double> listaConsumosDeUnPeriodo = this.clientesConectados.stream().map(unCliente -> unCliente.cuantoConsumoEnUnPeriodo(fechaInicio, fechaFinal))
				.collect(Collectors.toList());
		
		return listaConsumosDeUnPeriodo;
	}
	
	public double getConsumoTotalPorPeriodo(DateTime fechaInicio, DateTime fechaFinal){
		List<Double> listaConsumosDeUnPeriodo = this.clientesConectados.stream().map(unCliente -> unCliente.cuantoConsumoEnUnPeriodo(fechaInicio, fechaFinal))
				.collect(Collectors.toList());
		
		return this.sumarConsumos(listaConsumosDeUnPeriodo);
		
		}

    public List<Double> getConsumosPromedio() {
    	List<Double> listaConsumosPromedio = this.clientesConectados.stream().map(unCliente -> unCliente.cuantoConsumoPromedio())
    			.collect(Collectors.toList());
    	
    	return listaConsumosPromedio;
    }

    
    //Suministro Actual, Promedio y En un Periodo del Transformador
    
    public Double suministroActual() {

		List<Double> listaConsumos = this.getConsumos();
		Double suministroTotal = this.sumarConsumos(listaConsumos);

		return suministroTotal;
	}

	public Double suministroEnUnPeriodo(DateTime fechaInicio, DateTime fechaFinal) {
	    
		List<Double> listaConsumos = this.getConsumosPorPeriodo(fechaInicio, fechaFinal);
	    Double suministroTotalEnIntervalo = this.sumarConsumos(listaConsumos);
	    
	    return suministroTotalEnIntervalo;
	}
	
	public Double suministroPromedio() {
		
		List<Double> listaConsumos = this.getConsumosPromedio();
		Double suministroTotalPromedio = this.sumarConsumos(listaConsumos);
		
		return suministroTotalPromedio;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String unNombre) {
		this.nombre = unNombre;
	}
}
