package dispositivo;

import java.util.ArrayList;
import java.util.List;

public class Dispositivo {

	public int idDispositivo;
	private String nombre;
	public String descripcion;
	public EstadoDispositivo estadoDispositivo;
	private List<EstadoDispositivo> historialDeEstados = new ArrayList<EstadoDispositivo>();
	private Caracteristica caracteristica;
	private TipoDispositivo tipo;
	
	
	private Double horasUso; //Lo agrego solo para probar 
	
	public void addEstadoAlHistorial(EstadoDispositivo myEstadoDispositivo) {
		this.historialDeEstados.add(myEstadoDispositivo);
	}

	public void cambiarDeEstado(EstadoDispositivo myEstadoDispositivo) {
		/*
		 * Un dispositivo nuevo no tiene un estado, hasta que se lo setea... esto es
		 * menos rígido que imponerle un estado, pero nos obliga a hacer la pregunta, si
		 * el estadoDispositivo es una instancia de EstadoDispositivo -> el estado del
		 * dispositivo fue seteado, caso contrario no tengo porqué guardarlo en el
		 * historial de estado (fundamental para calcular consumo)
		 */
		// si el dispositivo tiene en estadoDispositivo
		// una instancia de EstadoDispositivo, entonces...
		// guardo el estado actual en el historial.
		if (this.estadoDispositivo instanceof EstadoDispositivo) {
			this.addEstadoAlHistorial(estadoDispositivo);
		}
		// Si el estado actual no es DispositivoStandard...entonces
		// le pido al estadoDispositivo que gestione el cambio de estado
		if (!(this.estadoDispositivo instanceof DispositivoStandard)) {
			this.estadoDispositivo = myEstadoDispositivo;
		}
	}

	public Dispositivo() {

	}

	public double cuantoConsumisPorDia() {
		return this.estadoDispositivo.cuantoConsumisPorDia();
	}

	public void setHorasUsoDiario(double myHoras) {
		//this.estadoDispositivo.setHorasUsoDiario(myHoras);
		this.horasUso = myHoras; //Solo para probar 
	}
	
	public Double getHorasUsoDiario() //Solo para probar 
	{	return this.horasUso; 	} //Solo para probar 
	
	public void setConsumo(double myConsumo) {
		this.estadoDispositivo.setConsumo(myConsumo);
	}

	public void apagate() {
		this.estadoDispositivo.apagate();
	}

	public void prendete() {
		this.estadoDispositivo.prendete();
	}

	public void ahorraEnergia() {
		this.estadoDispositivo.ahorraEnergia();
	}

	public boolean estasPrendido() {
		return this.estadoDispositivo.estasPrendido();
	}

	public int getPuntosPorDispositivoInteligenteNuevo() {
		if (this.sosInteligente()) {
			return 15;
		}
		return 0;
	}

	public boolean sosInteligente() {
		if (this.estadoDispositivo instanceof DispositivoStandard) {
			return false;
		}
		return true;
	}

	public boolean estasApagado() {
		return this.estadoDispositivo.estasApagado();
	}

	public int getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(int idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public Apagado getEstadoApagado() {
		return new Apagado(this);
	}

	public Prendido getEstadoPrendido() {
		return new Prendido(this);
	}

	public AhorroDeEnergia getEstadoAhorroDeEnergia() {
		return new AhorroDeEnergia(this);
	}

	public EstadoDispositivo getEstadoDispositivoStandard() {
		return new DispositivoStandard(this);
	}

	private void convertiteEnInteligente(EstadoDispositivo myEstadoDispositivo) {
		this.addEstadoAlHistorial(this.estadoDispositivo);
		this.estadoDispositivo = myEstadoDispositivo;
	}

	public int getPuntosPorConversionEnInteligente(EstadoDispositivo nuevoEstadoDispositivo) {
		if (!this.sosInteligente() && !(nuevoEstadoDispositivo instanceof DispositivoStandard)) {
			this.convertiteEnInteligente(nuevoEstadoDispositivo);
			return 10;
		}
		return 0;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoDispositivo getEstadoDispositivo() {
		return estadoDispositivo;
	}

	public double cuantoConsumiste(double ultimasNHoras) {
		return this.estadoDispositivo.cuantoConsumiste(ultimasNHoras);
	}

	public void subiTuIntensidad() {
		this.estadoDispositivo.subiTuIntensidad();
	}

	public void bajaTuIntensidad() {
		this.estadoDispositivo.bajaTuIntensidad();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setearTimer() {
		// TODO Auto-generated method stub

	}

	public Double cuantoConsumoEnTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getRestriccionMayor() {
		return this.caracteristica.getRestriccionMayorIgual();
	}

	public Double getRestriccionMenor() {
		return this.caracteristica.getRestriccionMenorIgual();
	}

	public Double getCoeficiente() {
		return this.caracteristica.getCoeficiente();
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public TipoDispositivo getTipo() {
		return tipo;
	}

	public void setTipo(TipoDispositivo tipo) {
		this.tipo = tipo;
	}
}