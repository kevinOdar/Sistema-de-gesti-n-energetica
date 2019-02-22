package dispositivo;

import java.time.LocalDateTime;

public class Apagado implements EstadoDispositivo {
	
	Dispositivo dispositivo ;
	int intensidad ;
	
	public Apagado ( Dispositivo myDispositivo)
	{
		this.dispositivo=myDispositivo;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public LocalDateTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	private double consumo = 0;
	private LocalDateTime horaFin;
	private LocalDateTime horaInicio;

	
	/*
	 * cuantoConsumiste(LocalDateTime inicio, LocalDateTime fin) tiene que devolver cuanto consumió
	 * un dispositivo en las ultimas n horas, si es un dispositivo convertido de standard
	 * a inteligente, entonces vamos a utilizar en el período en que fue standard
	 * el promedio del consumo por la cantidad de horas al día que debió ser utilizado
	 * en dicho período (el estimativo del que habla el enunciado), lo mismo
	 * para los otros estados... 
	 * 
	 * Un dispositivo convertido a inteligente no vuelve a ser standard
	 * 
	 * un dispositivo convertido de standard a inteligente, queda en encendido por defecto
	 * 
	 */

	public void apagate(){
		
	}

	public void bajaTuIntensidad(){

	}

	public void cambiarDeEstado(EstadoDispositivo estadoDispositivo){

	}

	public double cuantoConsumiste(double horas){
		return 0;
	}

	public double cuantoConsumiste(LocalDateTime periodoInicial, LocalDateTime periodoFinal){
		return 0;
	}

	public boolean estasApagado(){
		return true;
	}

	public boolean estasPrendido(){
		return false;
	}

	public void persistirCambioDeEstado(){

	}

	public void prendete(){
		this.dispositivo.cambiarDeEstado(dispositivo.getEstadoPrendido());
	}

	public void subiTuIntensidad(){

	}

	@Override
	public void ahorraEnergia() {
		this.dispositivo.cambiarDeEstado(dispositivo.getEstadoAhorroDeEnergia());
	}

	@Override
	public double cuantoConsumisPorHora() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHorasUsoDiario( double horas ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHorasUsoDiario() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double cuantoConsumisPorDia() {
		// TODO Auto-generated method stub
		return 0;
	}
}//end Apagado