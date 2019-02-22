package models.dao.hibernate;

import javax.persistence.*;

@MappedSuperclass
public abstract class EntidadPersistente {
	@Id
	@GeneratedValue
	protected int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int unId) {
		this.id = unId;
	}
}
