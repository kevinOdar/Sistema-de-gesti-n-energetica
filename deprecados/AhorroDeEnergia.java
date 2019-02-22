package dispositivo;

import java.time.LocalDateTime;

public class AhorroDeEnergia implements EstadoDispositivo {

	private double consumo;
	private LocalDateTime horaFin;
	private LocalDateTime horaInicio;
	Dispositivo dispositivo ;
	int intensidad ;
	
	@Override
	public void bajaTuIntensidad()
	{
		--this.intensidad ;
	}
	
	@Override
	public void subiTuIntensidad()
	{
		++this.intensidad ;
	}
	
	public AhorroDeEnergia ( Dispositivo myDispositivo)
	{
		this.dispositivo=myDispositivo;
	}

	public AhorroDeEnergia(){

	}

	public void apagate(){
		this.dispositivo.cambiarDeEstado(dispositivo.getEstadoApagado()) ;
	}

	/**
	 * 
	 * @param estadoDispositivo
	 */
	public void cambiarDeEstado(EstadoDispositivo estadoDispositivo){

	}

	/**
	 * 
	 * @param horas
	 */
	public double cuantoConsumiste(double horas){
		return 0;
	}

	public double cuantoConsumiste(LocalDateTime periodoInicial, LocalDateTime periodoFinal){
		return 0;
	}

	public boolean estasApagado(){
		return false;
	}

	public boolean estasPrendido(){
		return true;
	}

	public void persistirCambioDeEstado(){
		
	}

	public void prendete(){
		this.dispositivo.cambiarDeEstado(this.dispositivo.getEstadoPrendido());
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

	@Override
	public void ahorraEnergia() {
		
	}

	@Override
	public double cuantoConsumisPorHora() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double cuantoConsumisPorDia() {
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
}//end AhorroDeEnergia