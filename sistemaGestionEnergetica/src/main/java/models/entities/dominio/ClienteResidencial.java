package models.entities.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;

import models.entities.dispositivo.*;
import models.entities.transformadores.Coordenada;
import models.entities.transformadores.Transformador;
import models.entities.zonaGeografica.ZonaGeografica;

@Entity
@Table(name = "cliente_residencial")
@DiscriminatorValue("clienteResidencial")
public class ClienteResidencial extends Usuario {

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Dispositivo> dispositivos;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private CategoriaConsumo categoria;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fechaAltaServicio;

	@Column(name="puntos_acumulados")
	private int puntosAcumulados;

	@Column(name="consumo_en_automatico")
	boolean consumoEnAutomatico;
	
	@ManyToOne
	@JoinColumn(name = "transformador_id", referencedColumnName = "id")
	public Transformador transformador;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public ZonaGeografica zona;
	
	@Transient
	private List<Inteligente> inteligentes = null;
	
	public ClienteResidencial() {
		this.dispositivos = new ArrayList<Dispositivo>();
		this.inteligentes = new ArrayList<Inteligente>();
		this.setPuntos(0);
		this.setConsumoEnAutomatico(false);
	}
	
	///// ----Ubicacion-----///////////
	@Override
	public void setCoordenadas(List<Coordenada> coordenadas) {
		super.setCoordenadas(coordenadas);
	 
     if(this.getZona() != null) {
		List<Transformador> transformadoresConectados =
	    zona.getTransformadores().stream().filter(transformadorConectado -> transformadorConectado.getClientesConectados().contains(this))
	    .collect(Collectors.toList());
		
	 try {
         transformador = transformadoresConectados.get(0);
		 
	      this.setTransformador(transformador);
		  transformador.agregarCliente(this);
		}
	 catch(Exception e) {
			//Tira error, el cliente no esta conectado a ninguno de los Transformadores de la Zona
		}
	   }
	 else {}  //Tira error, Cliente tiene que saber a que Zona pertenece.
	}
	
	public ZonaGeografica getZona() {
		return this.zona;
	}
	
	public void setZona(ZonaGeografica zona) {
	    this.zona = zona;
	}
 //El getTransformador no se lo hice porque Cliente nunca sabe cual es su Transformador
	public void setTransformador(Transformador transformador) {
		this.transformador = transformador;
	}
	
	////// ---puntos-----////////////
	public int getPuntosAcumulados() {
		return this.puntosAcumulados;
	}

	public void setPuntos(int puntos) {
		this.puntosAcumulados = puntos;
	}

	public void sumarPuntos(int valor) {
		this.puntosAcumulados = this.puntosAcumulados + valor;
	}

	//// -------categoria------////
	public CategoriaConsumo getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaConsumo categoria) {
		this.categoria = categoria;
	}

	/// ------fechas--------////
	public DateTime getFechaAltaServicio() {
		return this.fechaAltaServicio;
	}

	public void setFechaAltaServicio(DateTime fecha) {
		this.fechaAltaServicio = fecha;

	}
	
	public String getFechaAltaServicioString() {
		DateTime fecha = this.getFechaAltaServicio();
		DateTimeFormatter formato = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
		String string = formato.print(fecha);
		return string;
	}

	//////// ------dispositivos-----/////
	public void setConsumoEnAutomatico(boolean UnBool) {
		this.consumoEnAutomatico = UnBool;
	}

	public void agregarDispositivo(Dispositivo dispositivo) {
		this.dispositivos.add(dispositivo);

		if (dispositivo.sosInteligente()) {
			this.sumarPuntos(dispositivo.getPuntos());
			this.inteligentes.add((Inteligente) dispositivo);
		}
		
		dispositivo.setCliente(this);
	}

	public List<Dispositivo> getDispositivos() {
		return this.dispositivos;
	}

	public int cuantosDispositivosTengo() {
		int totalDispositivos = this.dispositivos.size();
		return totalDispositivos;
	}

	public int cuantosDispositivosApagados() {
		return (int) this.filtrarLosPrendibles().stream().filter(d -> d.estasApagado()).count();
	}

	public int cuantosDispositivosEncendidos() {

		return (int) this.filtrarLosPrendibles().stream().filter(d -> d.estasPrendido()).count();

	}

	private boolean esPrendible(Dispositivo unDispositivo) {
		return unDispositivo instanceof Inteligente || unDispositivo instanceof ModuloAdaptador;
	}

	public List<Dispositivo> filtrarLosPrendibles() {
		return this.dispositivos.stream().filter(unDispositivo -> this.esPrendible(unDispositivo))
				.collect(Collectors.toList());
	}

	public boolean tengoAlgunDispositivoEncendido() {
		return this.filtrarLosPrendibles().stream().anyMatch(dispositivo -> dispositivo.estasPrendido());
	}

	public List<Dispositivo> cualesDispositivosTengoEncendidos() {

		return this.filtrarLosPrendibles().stream().filter(dispositivo -> dispositivo.estasPrendido())
				.collect(Collectors.toList());

	}

	public void convertirDispositivoAInteligente(Standard unDispositivo) {
		// TODO
		ModuloAdaptador moduloNuevo = new ModuloAdaptador(unDispositivo);
		this.getDispositivos().remove(unDispositivo);
		this.getDispositivos().add(moduloNuevo);
		this.sumarPuntos(moduloNuevo.getPuntos());
	}

	public List<Dispositivo> getDispositivosInteligentes() {
		//return this.inteligentes;
		return dispositivos.stream().filter(disp -> disp.sosInteligente()).collect(Collectors.toList());
	}
	
	//////////------------consumo----------//////////
	// para calcular el costo de consumo, la categoria utiliza el consumo de kw/h
	public List<Double> generarListaDeConsumo() {

		return this.cualesDispositivosTengoEncendidos().stream().map(dispositivo -> dispositivo.getConsumoPorHora())
				.collect(Collectors.toList());

	}
	
    public List<Double> generarListaDeConsumoEnUnPeriodo(DateTime fechaInicio, DateTime fechaFinal) {
    	return this.getDispositivos().stream().map(dispositivo -> dispositivo.getConsumoPorPeriodo(fechaInicio, fechaFinal))
    			.collect(Collectors.toList());
    }
    
    public List<Double> generarListaDeConsumoPromedio(){
    	return this.getDispositivos().stream().map(dispositivo -> dispositivo.getConsumoDiario())
    			.collect(Collectors.toList());
    }
    
	public double cuantoConsumo() {
		return this.generarListaDeConsumo().stream().mapToDouble(nro -> nro.doubleValue()).sum();

	}
    
	public double cuantoConsumoEnUnPeriodo(DateTime fechaInicio, DateTime fechaFinal) {
		return this.generarListaDeConsumoEnUnPeriodo(fechaInicio, fechaFinal).stream()
				.mapToDouble(nro -> nro.doubleValue()).sum();
				
	}
	
	public double cuantoConsumoPromedio() {
		return this.generarListaDeConsumoPromedio().stream().mapToDouble(nro -> nro.doubleValue()).sum();
	}
	
	public boolean getConsumoEnAutomatico() {
		return this.consumoEnAutomatico;
	}

	public double cuantoCuestaMiConsumo() {
		return this.categoria.cualEsElCostoDeEsteConsumo(this.cuantoConsumo());
	}
	
	public double getConsumoActual() {
		return this.cuantoConsumoEnUnPeriodo(DateTime.now().minusDays(1), DateTime.now());
	}
	
	public double getConsumoMensual() {
		return this.cuantoConsumoEnUnPeriodo(DateTime.now().minusMonths(1), DateTime.now());
	}


}
