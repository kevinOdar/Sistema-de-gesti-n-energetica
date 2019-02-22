package models.entities.regla.actuadores;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;
import models.entities.regla.Regla;

@Entity
@Table(name = "actuador")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class ActuadorBase extends EntidadPersistente {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regla_id", referencedColumnName = "id")
	protected Regla regla;

	@Column (name = "topic", length = 512)
	protected String topic = "/dispositivo/";
	
	public Regla getRegla() {
		return regla;
	}

	public void setRegla(Regla regla) {
		this.regla = regla;
	}
	
	public abstract void execute();

	public abstract void undo();

	public void setCanal(String unNombre) {
		this.topic =  topic + unNombre;
	}	

}