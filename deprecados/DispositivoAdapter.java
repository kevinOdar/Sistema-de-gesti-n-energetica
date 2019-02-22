package models.entities.regla;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.dao.hibernate.EntidadPersistente;
import models.entities.dispositivo.*;
import models.entities.regla.actuadores.ActuadorBase;

@Entity
@Table(name="dispositivo_adapter")
public class DispositivoAdapter extends EntidadPersistente {

	@OneToMany(mappedBy = "dispositivo_adapter", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ActuadorBase> colaDeAcciones = new ArrayList<ActuadorBase>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dispositivoInteligente_id", referencedColumnName = "id", unique = true)
	public Inteligente dispositivoReceptor;

	public Inteligente getDispositivoReceptor() {
		return dispositivoReceptor;
	}

	public void setDispositivoReceptor(Inteligente dispositivoReceptor) {
		this.dispositivoReceptor = dispositivoReceptor;
	}

	public List<ActuadorBase> getColaDeAcciones(){
		
		return this.colaDeAcciones;
	}
	
	public void agendarAccion(ActuadorBase UnaAccion){

		this.getColaDeAcciones().add(UnaAccion);
	}
	
	public void apagar(){
		this.dispositivoReceptor.apagar();
	}
	public void bajarIntensidad(){
		this.dispositivoReceptor.bajaTuIntensidad( );

	}

	public void prender(){
		this.dispositivoReceptor.prender();
	}

	public void setearTimer(){
		//this.dispositivo.setearTimer();
	}

	public void subirIntensidad(){
		this.dispositivoReceptor.subirIntensidad();	
	}
	public void ahorroDeEnergia() {
		this.dispositivoReceptor.pasarAModoAhorroDeEnergia();
    }
}
//end DispositivoAdapter
