package models.entities.dominio;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Months;

@Entity
@Table(name="administrador")
@DiscriminatorValue("administrador")
public class Administrador extends Usuario {
    
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="fecha_alta")
	private DateTime fechaAlta;

	public Administrador() {
		this.fechaAlta = DateTime.now();
	}
	

	public DateTime getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(DateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public int tiempoTranscurridoDesdeAlta() {
		int meses = Months.monthsBetween(getFechaAlta(), DateTime.now()).getMonths();
		return meses;
	}
	
	public void darDeAltaCategoria(List<CategoriaConsumo> listaCategorias, CategoriaConsumo unaCategoria){
             listaCategorias.add(0, unaCategoria);
	}	

	public void darDeBajaCategoria(List<CategoriaConsumo> listaCategorias, CategoriaConsumo unaCategoria){
        listaCategorias.remove(unaCategoria);
	}

	public void altaUsuario(List<Usuario> listaUsuarios, Usuario unUsuario){
		listaUsuarios.add(0, unUsuario);
                  }
	
	public void bajaUsuario(List<Usuario> listaUsuarios, Usuario unUsuario){
	listaUsuarios.remove(unUsuario);
    }


}