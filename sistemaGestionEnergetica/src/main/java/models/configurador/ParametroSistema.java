package models.configurador;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;

@Entity
@Table(name = "parametro_sistema")
public class ParametroSistema extends EntidadPersistente {

	@Column(name = "nombre", nullable = false, length=512)
	private String nombre;

	@Column(name = "valor")
	private int valor;
	
	@Column(name = "valor_string")
	private String valorString;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreParametro) {
		this.nombre = nombreParametro;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valorParametro) {
		this.valor = valorParametro;
	}

	public String getValorString() {
		return valorString;
	}

	public void setValorString(String valorString) {
		this.valorString = valorString;
	}

	
}
